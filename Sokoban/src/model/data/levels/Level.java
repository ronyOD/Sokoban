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

/**
* <h1>Level</h1>
* This class holds all the data functionality relevant to a level.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/
public class Level implements Serializable {
	
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

	/**
	* This method returns the cells which represents the level.
	* @return Cell[][]
	*/
	public Cell[][] getCells(){
		return this.cells;
	}
	
	/**
	* This method sets a certain cell.
	* @param position - cell position
	* @param cell 
	*/
	public void setCell(Position position, Cell cell){
		this.cells[position.getH()][position.getW()] = cell;
	}
	
	/**
	* This method returns the level name
	* @return String
	*/
	public String getLevelName() {
		return levelName;
	}

	/**
	* This method sets the level name.
	* @param levelName.
	*/
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	/**
	* This method gets the targets position list.
	* @return Array List of targets positions.
	*/
	public ArrayList<Position> getTargets(){
		return this.targets;
	}
	
	/**
	* This method sets the total time in which a level was solved.
	*/
	public void settotalTime(){
		long tEnd = System.nanoTime();
		long tDelta = tEnd - this.tStart;
		this.totalTime = (double)tDelta/1000000000.0;
	}
	
	/**
	* This method gets the total time in which a level was solved.
	* @return double
	*/
	public double gettotalTime(){
		return this.totalTime;
	}
	
	/**
	* This method increment the moves count.
	*/
	public void incrementmovesCount(){
		this.movesCount++;
	}
	
	/**
	* This method returns the moves count.
	* @return int.
	*/
	public int getmovesCount(){
		return this.movesCount;
	}
	
	/**
	* This method returns the boxes position list.
	* @return Array List of boxes positions.
	*/
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
	
	/**
	* This method returns the cell if its valid.
	* @param position
	* @return Cell
	*/
	public synchronized Cell get(Position position) {
		return isPositionValid(position) ? cells[position.getH()][position.getW()] : null;
	}

	public synchronized void set(Position position, Cell cell) {
		if (!isPositionValid(position))
			return;
		cells[position.getH()][position.getW()] = cell;
	}
	

	/**
	* This method sets the player position.
	* @param position
	*/
	public synchronized void setPlayerPosition(Position position) {
		playerPosition = position;
		incrementmovesCount();
	}

	/**
	* This method gets the player position.
	* @return Position
	*/
	public synchronized Position getPlayerPosition() {
		return playerPosition;
	}

	/**
	* This method checks if a position is valid.
	* @param position
	* @return Boolean
	*/
	public boolean isPositionValid(Position position) {
		return (position.getH() > 0 && position.getH() < cells.length && position.getW() > 0 && position.getW() < cells[position.getH()].length);
	}

	/**
	* This method returns level height.
	* @return int
	*/
	public int height() {
		return cells.length-1;
	}

	/**
	* This method returns the next position.
	* @param position
	* @param direction
	* @exception LevelPolicyException
	* @return Position if the input is valid.
	*/
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

	/**
	* This method returns the previous position.
	* @param position
	* @param direction
	* @exception LevelPolicyException
	* @return Position if the input is valid.
	*/
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

	/**
	* This method returns all empty neighboring positions.
	* @param currPosition
	* @return HashMap of Position, Direction.
	*/
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
	
	/**
	* This method returns the opposite direction.
	* @param direction
	* @return Direction
	*/
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
	
	/**
	* This method returns all directions.
	* @return Array List of all directions.
	*/
	public static ArrayList<Direction> getDirectionsArray(){
		ArrayList<Direction> directions = new ArrayList<>();
		directions.addAll(Arrays.asList(new Up(), new Down(), new Left(), new Right()));
		return directions;
	}
	
	/**
	* This method returns direction object.
	* @param direction
	* @return Direction.
	*/
	public static Direction getDirection(String direction){
		for(Direction d : getDirectionsArray()){
			if(direction.equals(d.toString()) || direction.equals(d.toString().toLowerCase())){
				return d;
			}
		}
		return null;
	}
	
	/**
	* This method checks if the level is completed.
	* @return Boolean
	*/
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
