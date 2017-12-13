package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.ServerSideModel;
import viewmodel.ServerSideViewModel;

public class MainWindowController {
	
	
	@FXML
	private Button button;
	
	@FXML
	private Label label;
	
	@FXML
	private ListView<String> myListView;
	
	@FXML
	private ListView<String> myTasksListView;
	
	@FXML
	private TextField numOfClients;
	
	private ServerSideViewModel vm;
	
	private StringProperty numOfClientsProperty = new SimpleStringProperty();
	
	
    public void setViewModel(ServerSideViewModel vm) {
    	this.vm = vm;
    	myListView.itemsProperty().bind(vm.clientsList);
    	myTasksListView.itemsProperty().bind(vm.tasks);
    	numOfClientsProperty.bind(numOfClients.textProperty());
    	
    }

    @FXML
    private void handleDisconnectButtonAction(ActionEvent event) {        
    	myListView.getItems().remove(myListView.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleEndTaskButtonAction(ActionEvent event){
    	myTasksListView.getItems().remove(myTasksListView.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void handleLimitNumOfClients(ActionEvent event){
    	ServerSideModel.getInstance().limitNumOfClients(Integer.parseInt(numOfClients.getText()));
    }
    
    @FXML
    private void handleCloseServerButtonAction(ActionEvent event){
    	ServerSideModel.getInstance().closeServer();
    }
}
