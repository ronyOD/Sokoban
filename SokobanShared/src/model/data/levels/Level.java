package model.data.levels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Floor;
import model.data.cells.Target;
import model.data.directions.Direction;
import model.data.directions.Down;
import model.data.directions.Left;
import model.data.directions.Right;
import model.data.directions.Up;
import model.data.positions.Position;
import model.policy.LevelPolicyException;

public class Level implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5102163318935147723L;
	
	private Cell[][] cells;
	
	private Position playerPosition;
	
	private ArrayList<Position> targets;
	//Unique id
	private String levelName;
	//moves count
	private int movesCount;
	//time elapsed
	private long tStart = 0;
	
	private double totalTime;
	
	public Level(Cell[][] cells, Position playerPosition, ArrayList<Position> targets) throws LevelException {
		
		if (cells == null)
			throw new LevelException("cells are null");

		if (playerPosition == null)
			throw new LevelException("player position is null");
		this.cells = cells;
		
		//TODO: validate that targets is not null and there is at least 1 target else throw exception
		if(targets != null && targets.size() > 0){
			this.targets = targets;
		}

		this.movesCount = 0;
		this.playerPosition = playerPosition;
		this.tStart = System.nanoTime();
	}
	
	public Level(Level level) {
		this.cells = level.getCells();
		this.levelName = level.getLevelName();
		this.playerPosition = level.getPlayerPosition();
		this.targets = level.getTargets();
	}

	public Level(Cell[][] newCells) {
		this.cells = newCells;
	}

	public Cell[][] getCells(){
		return this.cells;
	}
	
	public void setCell(Position position, Cell cell){
		this.cells[position.getH()][position.getW()] = cell;
	}
	
	public String getLevelName() {
		return levelName;
	}


	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public ArrayList<Position> getTargets(){
		return this.targets;
	}
	public void settotalTime(){
		long tEnd = System.nanoTime();
		long tDelta = tEnd - this.tStart;
		this.totalTime = (double)tDelta/1000000000.0;
	}
	
	public double gettotalTime(){
		return this.totalTime;
	}
	
	public void incrementmovesCount(){
		this.movesCount++;
	}
	
	public int getmovesCount(){
		return this.movesCount;
	}
	
	public ArrayList<Position> getBoxes(){
		ArrayList<Position> boxes = new ArrayList<Position>();
		for(int i=1; i<cells.length; i++){
			//System.out.println(cells[i].length);
			for(int j=1; j<cells[i].length; j++){
				if(cells[i][j] instanceof Box){
					boxes.add(new Position(i,j));
				}
			}
		}
		return boxes;
	}
	
	public synchronized Cell get(Position position) {
		return isPositionValid(position) ? cells[position.getH()][position.getW()] : null;
	}

	public synchronized void set(Position position, Cell cell) {
		if (!isPositionValid(position))
			return;
		cells[position.getH()][position.getW()] = cell;
	}
	

	public synchronized void setPlayerPosition(Position position) {
		playerPosition = position;
		incrementmovesCount();
	}

	public synchronized Position getPlayerPosition() {
		return playerPosition;
	}

	public boolean isPositionValid(Position position) {
		return (position.getH() > 0 && position.getH() < cells.length && position.getW() > 0 && position.getW() < cells[position.getH()].length);
	}

	public int height() {
		return cells.length-1;
	}

	public static Position nextPosition(Position position, Direction direction) throws LevelPolicyException {
		if (direction instanceof Up)
			return new Position(position.getH() + 1, position.getW());
		if (direction instanceof Down)
			return new Position(position.getH() - 1, position.getW());
		if (direction instanceof Left)
			return new Position(position.getH(), position.getW() - 1);
		if (direction instanceof Right)
			return new Position(position.getH(), position.getW() + 1);
		throw new LevelPolicyException("no such direction: " + direction);
	}

	public static Position prevPosition(Position position, Direction direction) throws LevelPolicyException {
		if (direction instanceof Up)
			return new Position(position.getH() - 1, position.getW());
		if (direction instanceof Down)
			return new Position(position.getH() + 1, position.getW());
		if (direction instanceof Left)
			return new Position(position.getH(), position.getW() + 1);
		if (direction instanceof Right)
			return new Position(position.getH(), position.getW() - 1);
		throw new LevelPolicyException("no such direction: " + direction);
	}

	public HashMap<Position, Direction> getEmptyNeighboringPositions(Position currPosition){
		
		HashMap<Position, Direction> emptyNeighboringPositions = new HashMap<Position, Direction>();
		ArrayList<Direction> directions = new ArrayList<Direction>();
		
		directions.addAll(Arrays.asList(new Up(), new Down(), new Right(), new Left()));
		
		try {
			for(Direction direction : directions){
				Position position = new Position(nextPosition(currPosition, direction));
				if(isPositionValid(position)){
					if(cells[position.getH()][position.getW()] instanceof Floor){
						emptyNeighboringPositions.put(position, direction);
					}
				}
			}
			return emptyNeighboringPositions;
		} catch (LevelPolicyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkNeighboringPositions(Position position, Direction direction){
		//ArrayList<Direction> directions = new ArrayList<>();
		//directions.addAll(Arrays.asList(new Up(), new Down()));
		try {
			Position p = nextPosition(position, direction);
			if(isPositionValid(p) && get(p) instanceof Target){ //review condition logic
				Position oppositePosition = nextPosition(position, oppositeDirection(direction));
				if(isPositionValid(oppositePosition) && (get(oppositePosition) instanceof Floor || oppositePosition == playerPosition)){
					//return path as valid.
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Direction oppositeDirection(Direction direction){
		if(direction instanceof Up){
			return new Down();
		}
		else if(direction instanceof Down){
			return new Up();
		}
		else if(direction instanceof Left){
			return new Right();
		}
		else{
			return new Left();
		}
	}
	
	public static ArrayList<Direction> getDirectionsArray(){
		ArrayList<Direction> directions = new ArrayList<>();
		directions.addAll(Arrays.asList(new Up(), new Down(), new Left(), new Right()));
		return directions;
	}
	
	public static Direction getDirection(String direction){
		for(Direction d : getDirectionsArray()){
			if(direction.equals(d.toString())){
				return d;
			}
		}
		return null;
	}
	
	public boolean IsWon()
	{
		//check that in all targets positions there are boxes
		for (Position p : targets)
			if(!(this.cells[p.getH()][p.getW()] instanceof Box)){
				return false;
			}
		this.settotalTime();
		return true;
	}
}
