package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/**
* <h1>SokobanController</h1>
* This class represents the JavaFx Controller.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class SokobanController implements Initializable {

	private static SokobanController sokobanController;

	/**
	* This method gets the levels pane.
	* @return Pane.
	*/
	public Pane getLevel() {
		return level;
	}

	/**
	* This method returns the load MenuItem.
	* @return MenuItem.
	*/
	public MenuItem getLoad() {
		return load;
	}

	/**
	* This method returns the save MenuItem.
	* @return MenuItem.
	*/
	public MenuItem getSave() {
		return save;
	}
	/**
	* This method returns the results MenuItem.
	* @return MenuItem.
	*/
	public MenuItem getResults(){
		return results;
	}
	/**
	* This method returns the exit MenuItem.
	* @return MenuItem.
	*/
	public MenuItem getExit() {
		return exit;
	}
	/**
	* This method returns the solution MenuItem.
	* @return MenuItem.
	*/
	public MenuItem getSolution(){
		return solution;
	}

	@FXML
	private Pane level;
	@FXML
	private MenuItem load;
	@FXML
	private MenuItem save;
	@FXML
	private MenuItem results;
	@FXML
	private MenuItem solution;
	@FXML
	private MenuItem exit;

	/**
	* This method initializes the controller.
	*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SokobanController.sokobanController = this;
	}

	/**
	* This method returns the controller.
	* @return SokobanController
	*/
	public static SokobanController it() {
		return sokobanController;
	}
}
