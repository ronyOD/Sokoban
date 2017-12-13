package solution.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import application.SokobanApplication;
import controller.MyController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import model.data.directions.Direction;
import model.data.levels.Level;
import model.policy.LevelPolicyException;
import protocol.Command;
import search.Action;
import search.Solution;

/**
* <h1>DisplaySolutionDialog</h1>
* This class displays the solution dialog.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/
public class DisplaySolutionDialog extends SokobanApplication implements Runnable {

	private String solution;
	
	/**
	* Constructor
	* @param solution
	*/
	public DisplaySolutionDialog(String solution) {
		this.solution = solution;
	}
	
	/**
	* This method displays the solution dialog and handles the response from
	* user - hint/full solution
	*/
	@Override
	public void run() {
		
		Solution mySolution = new Solution(solution); //check 
		List<String> choices = new ArrayList<>();
		choices.add("I want a full solution");
		choices.add("give me a hint!");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Solution Options", choices);
		dialog.setTitle("Solution Dialog");
		dialog.setHeaderText("Solution Dialog");
		dialog.setContentText("Choose: ");

		Optional<String> result = dialog.showAndWait();

		if(result.get().equals(choices.get(0))){
			//display solution animation on GUI..
			System.out.println(this.solution);
			String levelName = MyController.it().getModel().getLevel().getLevelName() + ".txt";
			MyController.it().command(new Command("Load", levelName, Command.NORMAL));

			//simulate solution
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						if(!mySolution.getActions().isEmpty()){
							Action action = mySolution.getFirstAction();
							Level level = MyController.it().getModel().getLevel();
							String direction = action.getName().split(" ")[1];
							Direction d = Level.getDirection(direction);
							try {
								Level currLevel = MyController.it().getModel().getPolicy().move(level, d);
								MyController.it().getModel().setLevel(currLevel);
								MyController.it().getModel().notifyObservers();
							} catch (LevelPolicyException e) {
								e.printStackTrace();
							}
							mySolution.getActions().remove(0);
						}
					}
				}, 0, 500);
			}
		else{
			//display a hint to GUI
			dialog.close();
			String hint = solution.split(",")[0];
			System.out.println(hint);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Sokoban");
			alert.setHeaderText("Here's your hint!");
			alert.setContentText(mySolution.getFirstAction().getName());
			alert.showAndWait();
			
		}
		
	}

}
