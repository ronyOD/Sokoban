package leaderboard;

import application.SokobanApplication;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.hibernate.Records;

/**
* <h1>DisplayResults</h1>
* This class defines the records persentation.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class DisplayResults extends SokobanApplication implements Runnable {

	@FXML
	private TableView<Records> table = new TableView<Records>();

	/**
	* Constructor
	* @param table
	*/
	public DisplayResults(TableView<Records> table){
		this.table = table;
	}
	
	/**
	* This method is used to set the items in TableView
	* @param data
	*/
	public void setTable(ObservableList<Records> data){
		this.table.setItems(data);
	}
	
	/**
	* This method is used to run the records presentation - JavaFX
	*/
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Scene scene = new Scene(new Group());
        final Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(application.SokobanApplication.getpStage());
        dialog.setTitle("Leader Board");
        dialog.setWidth(520);
        dialog.setHeight(500);
        //dialog.addEventFilter(, eventFilter);
        
        final Label label = new Label("Highest Scores");
        label.setFont(new Font("Arial", 20));
		
        final VBox box = new VBox();
        box.setSpacing(5);
        box.setPadding(new Insets(10, 0, 0, 10));
        box.getChildren().addAll(label, table);
        ((Group) scene.getRoot()).getChildren().addAll(box);
        dialog.setScene(scene);
        dialog.show();
		
	}
}
