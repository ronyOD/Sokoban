package commands;
import displayers.LevelDisplayer;

public class DisplaySolutionDialogLevelCommand extends LevelCommand {

	private LevelDisplayer levelDisplayer;
	private String solution;


	public void setLevelDisplayer(LevelDisplayer levelDisplayer) {
		// TODO Auto-generated method stub
		this.levelDisplayer = levelDisplayer;
	}
	
	@Override
	public void execute() throws LevelCommandException {
		if(levelDisplayer != null)
			levelDisplayer.displaySolutionDialog(solution);
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

}
