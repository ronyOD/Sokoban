package model.policy;

import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Wall;
import model.data.directions.Direction;
import model.data.levels.Level;
import model.data.positions.Position;

public abstract class LevelPolicy implements Policy {

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
