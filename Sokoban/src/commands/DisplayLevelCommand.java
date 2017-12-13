package commands;

import displayers.LevelDisplayer;
import displayers.LevelDisplayerException;

public class DisplayLevelCommand extends LevelCommand {

	private LevelDisplayer levelDisplayer;

	public void setLevelDisplayer(LevelDisplayer levelDisplayer) {
		this.levelDisplayer = levelDisplayer;
	}

	/**
	* This method will display a level.
	*/
	@Override
	public void execute() throws LevelCommandException {
		try {
			if (levelDisplayer != null)
				levelDisplayer.display(level);
		} catch (LevelDisplayerException e) {
			throw new LevelCommandException(e);
		}
	}
}
