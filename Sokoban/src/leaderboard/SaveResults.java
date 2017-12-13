package leaderboard;

import java.util.Optional;

import application.SokobanApplication;
import controller.MyController;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
/**
* <h1>SaveResults</h1>
* This class defines the save results dialog presentation.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class SaveResults extends SokobanApplication implements Runnable {
	
	/**
	* Constructor
	*/
	public SaveResults(){}
	
	/**
	* This method will present the save results dialog.
	*/
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TextInputDialog dialog = new TextInputDialog();
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.initOwner(application.SokobanApplication.getpStage());
        dialog.setTitle("Records");

        int movesCount = MyController.it().getModel().getLevel().getmovesCount();
        double time = MyController.it().getModel().getLevel().gettotalTime();
        String levelName = MyController.it().getModel().getLevel().getLevelName();

        String data = "You finished level #" + levelName +" in "+ Integer.toString(movesCount) + " moves! " 
        		 + "Time: "	+ Double.toString(time);
        dialog.setHeaderText(data);
        dialog.setContentText("Please enter your name: ");
        
        Optional<String> res = dialog.showAndWait();
        if (res.isPresent()){
        	new DAL().InsertRecord(res);
        }
	}
}
