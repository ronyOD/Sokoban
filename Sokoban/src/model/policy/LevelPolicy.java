package model.policy;

import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Wall;
import model.data.directions.Direction;
import model.data.levels.Level;
import model.data.positions.Position;

/**
* <h1>LevelPolicy</h1>
* This abstract class implements the Policy interface.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/
public abstract class LevelPolicy implements Policy {

	/**
	* This method returns a new level after the move action
	* @param level This is the first parameter to move method
	* @param direction  This is the second parameter to move method
	* @return Level This returns level which represents the level state
	* after the move action - if valid.
	*/
	@Override
	public Level move(Level level, Direction direction) throws LevelPolicyException {
		if (level == null || direction == null)
			return level;
		if (isMoveValid(level, direction)) {
			// push
			if (isPushValid(level, direction)) {
				push(level, Level.nextPosition(level.getPlayerPosition(), direction), direction);
			}
			// pull
			if (isPullValid(level, direction)) {
				pull(level, Level.prevPosition(level.getPlayerPosition(), direction), direction);
			}
			level.setPlayerPosition(Level.nextPosition(level.getPlayerPosition(), direction));
		}
		return level;
	}

	/**
	* This method checks if a move is valid.
	* @param level This is the first parameter to move method.
	* @param direction  This is the second parameter to move method.
	* @return Boolean which represents if the move is valid.
	*/
	private boolean isMoveValid(Level level, Direction direction) throws LevelPolicyException {
		if (level == null || direction == null)
			return false;
		Position nextPlayerPosition = Level.nextPosition(level.getPlayerPosition(), direction);
		if (!level.isPositionValid(nextPlayerPosition))
			return false;
		Cell nextPlayerCell = level.get(nextPlayerPosition);
		if (nextPlayerCell instanceof Wall)
			return false;
		if (nextPlayerCell instanceof Box)
			return isPushValid(level, direction);
		return true;
	}

	protected abstract void push(Level level, Position position, Direction direction) throws LevelPolicyException;

	protected abstract void pull(Level level, Position position, Direction direction) throws LevelPolicyException;

	protected abstract boolean isPushValid(Level level, Direction direction) throws LevelPolicyException;

	protected abstract boolean isPullValid(Level level, Direction direction) throws LevelPolicyException;

}
