package model.policy;

import model.data.directions.Direction;
import model.data.levels.Level;

public interface Policy {

	   /**
	   * Policy interface - represents games policy.
	   * This method returns a new level after move action
	   * @param level This is the first parameter to move method
	   * @param direction This is the second parameter to move method
	   * @return Level This returns level which represents the level state
	   * @exception LevelPolicyException
	   * after the move action - if valid.
	   */
	Level move(Level level, Direction direction) throws LevelPolicyException;

}