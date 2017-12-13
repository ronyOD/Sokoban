package model.policy;

import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Floor;
import model.data.cells.Wall;
import model.data.directions.Direction;
import model.data.levels.Level;
import model.data.positions.Position;

/**
* <h1>MySokobanPolicy</h1>
* This class extends  LevelPolicy.
* MySokobanPolicy defines the policy for movement in Sokoban.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/

public class MySokobanPolicy extends LevelPolicy {

	/**
	* This method pushes a box to a certain direction - if possible.
	* @param level This is the first parameter to move method
	* @param position is the second parameter - current object position.
	* @param direction  This is the third parameter.
	* @return void.
	*/
	@Override
	protected void push(Level level, Position position, Direction direction) throws LevelPolicyException {
		if (level == null || position == null || direction == null || !level.isPositionValid(position))
			return;
		Cell currCell = level.get(position);
		if (!(currCell instanceof Box))
			return;
		Position nextPosition = Level.nextPosition(position, direction);
		if (!level.isPositionValid(nextPosition))
			return;
		Cell nextCell = level.get(nextPosition);
		if (nextCell instanceof Wall)
			return;
		if (nextCell instanceof Box)
			push(level, nextPosition, direction);
		level.set(nextPosition, currCell);
		level.set(position, new Floor());
	}

	/**
	* This method pulls a box to a certain direction - if possible.
	* @param level This is the first parameter to move method
	* @param position is the second parameter - current object position.
	* @param direction  This is the third parameter.
	* @return void.
	*/
	@Override
	protected void pull(Level level, Position position, Direction direction) throws LevelPolicyException {
		if (level == null || position == null || direction == null || !level.isPositionValid(position))
			return;
		Cell currCell = level.get(position);
		if (!(currCell instanceof Box))
			return;
		Position prevPosition = Level.prevPosition(position, direction);
		if (!level.isPositionValid(prevPosition))
			return;
		Cell prevCell = level.get(prevPosition);
		if (prevCell instanceof Box)
			pull(level, prevPosition, direction);
		level.set(prevPosition, new Floor());
		level.set(position, prevCell);
	}

	/**
	* This method checks if push is valid.
	* @param level This is the first parameter to move method
	* @param direction  This is the third parameter.
	* @return Boolean.
	*/
	@Override
	protected boolean isPushValid(Level level, Direction direction) throws LevelPolicyException {
		if (level == null || direction == null)
			return false;
		Position currentPlayerPosition = level.getPlayerPosition();
		Position nextPlayerPosition = Level.nextPosition(currentPlayerPosition, direction);
		if (!level.isPositionValid(nextPlayerPosition))
			return false;
		Cell nextPlayerCell = level.get(nextPlayerPosition);
		if (nextPlayerCell instanceof Wall)
			return false;
		if (nextPlayerCell instanceof Box) {
			Position nextBoxPosition = Level.nextPosition(nextPlayerPosition, direction);
			if (!level.isPositionValid(nextBoxPosition))
				return false;
			Cell nextBoxCell = level.get(nextBoxPosition);
			if (nextBoxCell instanceof Wall || nextBoxCell instanceof Box)
				return false;
		}
		return true;
	}

	/**
	* This method checks if pull is valid.
	* @param level This is the first parameter to move method
	* @param direction  This is the third parameter.
	* @return Boolean.
	*/
	@Override
	protected boolean isPullValid(Level level, Direction direction) throws LevelPolicyException {
		return false;
	}

}
