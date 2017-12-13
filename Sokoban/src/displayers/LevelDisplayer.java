package displayers;

import model.data.levels.Level;

/**
* <h1>LevelDisplayer</h1>
* This interface defines the methods for the level displayer.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public interface LevelDisplayer {
	public void display(Level level) throws LevelDisplayerException;
	public void displaySaveResultsDialog();
	public void displayResults();
	public void displaySolutionDialog(String solution);
}
