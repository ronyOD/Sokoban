package commands;

import model.data.directions.Direction;
import model.policy.LevelPolicyException;
import model.policy.Policy;

public class MoveLevelCommand extends LevelCommand {

	private Policy policy;
	private Direction direction;

	@Override
	public void execute() throws LevelCommandException {
		try {
			if (policy != null)
				policy.move(level, direction);
		} catch (LevelPolicyException e) {
			throw new LevelCommandException(e);
		}
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
