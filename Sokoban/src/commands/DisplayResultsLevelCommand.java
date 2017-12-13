package commands;

import displayers.LevelDisplayer;

public class DisplayResultsLevelCommand extends LevelCommand {

	private LevelDisplayer levelDisplayer;

	public void setLevelDisplayer(LevelDisplayer levelDisplayer) {
		this.levelDisplayer = levelDisplayer;
	}
	
	@Override
	public void execute() throws LevelCommandException {

		levelDisplayer.displayResults();
	}

}
