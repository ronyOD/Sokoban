package search;

import java.util.ArrayList;
import java.util.HashMap;

import model.data.cells.Floor;
import model.data.cells.Target;
import model.data.directions.Direction;
import model.data.levels.Level;
import model.data.positions.Position;

public class SokobanBoxToTargetAdapter extends SokobanSearchable {

	SokobanSearchable searchable;
	private Position playerPos;
	
	public SokobanBoxToTargetAdapter(Level level) {
		super(level);
		//searchable = new SokobanSearchable(level);
		playerPos = getPlayerPosition();
		
	}
	
	public SokobanBoxToTargetAdapter() {
	}

	@Override
	public HashMap<Action, State<Position>> getAllPossibleStates(State<Position> state) {
		
		try {
		HashMap<Action, State<Position>> map = new HashMap<>();
		ArrayList<Direction> directions = Level.getDirectionsArray();
		Position boxPosition = state.getState(); //holds box position
		//Position playerPosition = this.getLevel().getPlayerPosition();
		
		//in order to push box down -> clear position above and beneath box
		//same logic applies to all directions
		//player position will be regarded as clear if its on the pushing side.
		//target position will be regarded as clear if its on the receiving side.
		Level level = this.getLevel();
		
		for(Direction direction : directions){
			Position p = Level.nextPosition(boxPosition, direction);
			if(level.isPositionValid(p) && 
					(level.get(p) instanceof Target || level.get(p) instanceof Floor)){
						//is it wise to check it here?
						Position oppositePosition = Level.nextPosition(boxPosition, Level.oppositeDirection(direction));
						if(level.isPositionValid(oppositePosition) &&
								(level.get(oppositePosition) instanceof Floor || oppositePosition == playerPos || level.get(oppositePosition) instanceof Target)){
									map.put(new Action("Push " + direction.toString()), new State<Position>(p));
						}
			}
		}
		
		return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Position getPlayerPosition(){
		Position pos = this.getLevel().getPlayerPosition();
		this.getLevel().setCell(new Position(pos.getH(), pos.getW()), new Floor());
		return pos;
	}
}
