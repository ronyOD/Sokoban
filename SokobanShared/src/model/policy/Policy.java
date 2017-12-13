package model.policy;

import model.data.directions.Direction;
import model.data.levels.Level;

public interface Policy {

	Level move(Level level, Direction direction) throws LevelPolicyException;

}