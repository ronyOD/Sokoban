package search;
import java.util.HashMap;

import model.data.directions.Direction;
import model.data.levels.Level;
import model.data.positions.Position;

public class SokobanSearchable implements Searchable<Position> {
	private Level level;
	private State<Position> goalState;
	private State<Position> initialState;

	public SokobanSearchable(){
		
	}
	public SokobanSearchable(Level level) {
		this.level = level;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setInitialState(Position position){
		this.initialState = new State<Position>(position);
	}
	
	public void setInitialStatePlayerPosition(){
		this.initialState = new State<Position>(level.getPlayerPosition());
	}

	@Override
	public State<Position> getInitialState() {
		return initialState;
	}

	@Override
	public State<Position> getGoalState() {
		return this.goalState;
	}
	
	public void setGoalState(Position goal){
		this.goalState = new State<Position>(goal);
	}

	@Override
	public HashMap<Action, State<Position>> getAllPossibleStates(State<Position> state){
		
		//Level level = model.getLevel();
		HashMap<Position, Direction> emptyPositions = level.getEmptyNeighboringPositions(state.getState());
		//Position emptyPos = getEmptySquarePosition(model.getLevel().getCells()); //get empty position from sokoban level??
		
		HashMap<Action, State<Position>> map = new HashMap<Action, State<Position>>();
		
		for(Position key : emptyPositions.keySet()){
			map.put(new Action("Move " + emptyPositions.get(key).toString()), new State<Position>(key));
		}

		return map;
	}
	

	@Override
	public int costOfMove(State<Position> src, State<Position> dest) {
		return 1;
	}

}
