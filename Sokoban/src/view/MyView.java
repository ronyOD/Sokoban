package view;

import java.util.Observable;

import application.SokobanApplication;
import displayers.LevelDisplayer;
import displayers.LevelDisplayerException;
import interfaces.Stoppable;
import model.data.levels.Level;
/**
* <h1>MyView</h1>
* This class defines the View using the SokobanApplication class.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class MyView extends Observable implements LevelDisplayer, Stoppable {

	/**
	* This method displays the level.
	* @param level
	*/
	@Override
	public void display(Level level) throws LevelDisplayerException {
		SokobanApplication.it().display(level);
	}
	/**
	* This method displays the save results dialog.
	*/
	@Override
	public void displaySaveResultsDialog(){
		SokobanApplication.it().displaySaveResultsDialog();
	}

	/**
	* This method stops the app.
	*/
	@Override
	public void stop() {
		SokobanApplication.it().exit();
	}
	/**
	* This method displays the results.
	*/
	@Override
	public void displayResults() {
		SokobanApplication.it().displayResults();
		
	}
	/**
	* This method displays the solution dialog.
	* @param solution
	*/
	@Override
	public void displaySolutionDialog(String solution) {
		SokobanApplication.it().displaySolutionDialog(solution);
		
	}

}