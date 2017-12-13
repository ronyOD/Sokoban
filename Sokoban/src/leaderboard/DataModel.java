package leaderboard;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.data.hibernate.Records;

/**
* <h1>DataModel</h1>
* Model for records presentation.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class DataModel {

	private ObservableList<Records> data = FXCollections.observableArrayList();
	
	/**
	* Constructor
	* @param records
	*/
	public DataModel(List<Records> records){
		for(Records r : records){
			data.add(r);
		}
	}
	
	/**
	* This method is used to get records data
	* @return an Observable List of Records
	*/
	public ObservableList<Records> getData() {
		return data;
	}
	
	/**
	* This method is used to set records data
	* @param data
	*/
	public void setData(ObservableList<Records> data) {
		this.data = data;
	}
}
