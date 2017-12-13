package commands;

import interfaces.Stoppable;

public class ExitLevelCommand extends LevelCommand {

	private Stoppable stoppable;

	@Override
	public void execute() throws LevelCommandException {
		if (stoppable != null)
			stoppable.stop();
		// System.out.println("Stop");
	}

	public void setStoppable(Stoppable abortable) {
		this.stoppable = abortable;
	}

}
