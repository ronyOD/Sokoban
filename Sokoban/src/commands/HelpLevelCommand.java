package commands;

import interfaces.Helpable;

public class HelpLevelCommand extends LevelCommand {

	private Helpable helpable;

	@Override
	public void execute() throws LevelCommandException {
		if (helpable != null)
			helpable.help();
	}

	public void setHelpable(Helpable helpable) {
		this.helpable = helpable;
	}

}
