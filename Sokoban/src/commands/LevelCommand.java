package commands;

import model.data.levels.Level;
/**
* <h1>LevelCommand</h1>
* This abstract class is a super class to all level commands.
* @author  Ron Yaish
* @version 1.0
* @since   213-7-2017
*/
public abstract class LevelCommand {

	
	public abstract void execute() throws LevelCommandException;

	protected Level level;

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public LevelCommand() {}
}
