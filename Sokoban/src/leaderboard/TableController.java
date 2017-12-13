package leaderboard;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.data.hibernate.Records;

/**
* <h1>TableController</h1>
* This class sets the TableView, and handles TableView events.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class TableController {

	private DataModel model;
	
	/**
	* This method will initialize the table view data model
	* Handles TableView events.
	* @param model
	*/
	@SuppressWarnings("unchecked")
	public void initModel(DataModel model){
		if(this.model != null){
			throw new IllegalStateException("Model can only be initialized once");
		}
		
		this.model = model;
		//variables
		TableView<Records> table = new TableView<Records>();
		TextField textField = new TextField();
		ObservableList<Records> filteredData = FXCollections.observableArrayList();
		
		//set columns
		TableColumn<Records, String> LevelNameCol = new TableColumn<Records, String>("Level Name");
        LevelNameCol.setMinWidth(100);
        LevelNameCol.setCellValueFactory(new PropertyValueFactory<Records, String>("LevelName"));
        TableColumn<Records, String> UserNameCol = new TableColumn<Records, String>("User Name");
        UserNameCol.setMinWidth(150);
        UserNameCol.setCellValueFactory(new PropertyValueFactory<Records, String>("UserName"));
        TableColumn<Records, Integer> NumOfStepsCol = new TableColumn<Records, Integer>("Moves");
        NumOfStepsCol.setMinWidth(100);
        NumOfStepsCol.setCellValueFactory(new PropertyValueFactory<Records, Integer>("NumOfSteps"));
        TableColumn<Records, Double> TimeCol = new TableColumn<Records, Double>("Time");
        TimeCol.setMinWidth(150);
        TimeCol.setCellValueFactory(new PropertyValueFactory<Records, Double>("Time"));
		
        textField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				updateFilteredData(filteredData, model.getData(), textField);
			}
		});
        
        
        //table cell onMouseEvent
        UserNameCol.setCellFactory(new Callback<TableColumn<Records, String>, TableCell<Records, String>>() {
            @Override
            public TableCell<Records, String> call(TableColumn<Records, String> col) {
                final TableCell<Records, String> cell = new TableCell<Records, String>() {
                    @Override
                    public void updateItem(String firstName, boolean empty) {
                        super.updateItem(firstName, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(firstName);
                        }
                    }
                 };
                 //event handler
                 cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         if (event.getClickCount() > 1) {
                        	 TableView<Records> tableByUserName = new TableView<Records>();
                        	 DataModel model = new DataModel(new DAL().GetDataByUserName(cell.getItem()));
                        	 tableByUserName.setItems(model.getData());
                        	 tableByUserName.getColumns().addAll(LevelNameCol, UserNameCol, NumOfStepsCol, TimeCol);
                        	 DisplayResults dr = new DisplayResults(tableByUserName);
                        	 Platform.runLater(dr);
                         }
                     }
                 });
                 return cell;
            }
        });
        
        //set table
		table.setItems(this.model.getData());
        table.getColumns().addAll(LevelNameCol, UserNameCol, NumOfStepsCol, TimeCol);

		DisplayResults dr = new DisplayResults(table);
		Platform.runLater(dr);
	}
	
	//filter
    private void updateFilteredData(ObservableList<Records> filteredData, ObservableList<Records> data, TextField textField) {
        filteredData.clear();
        
        if(textField.getText() == null || textField.getText().isEmpty()){
        	return;
        }
        else {
        for (Records r : data) {
            if (r.getUserName().toLowerCase().equals(textField.toString().toLowerCase()) || 
            		r.getLevelName().toLowerCase().equals(textField.toString().toLowerCase())) {
                filteredData.add(r);
            }
        }
    }
    }
}
