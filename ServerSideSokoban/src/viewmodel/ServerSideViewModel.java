package viewmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.ServerSideModel;

public class ServerSideViewModel implements Observer {

	private ServerSideModel adminModel;
	private ObservableList<String> observableList;
	private ObservableList<String> observableTaskList;
	public ListProperty<String> clientsList;
	public ListProperty<String> tasks;
	
	public ServerSideViewModel(ServerSideModel adminModel) {
		this.adminModel = adminModel;
		this.observableList = FXCollections.observableArrayList();
		this.observableTaskList = FXCollections.observableArrayList();
		this.clientsList = new SimpleListProperty<String>();
		this.clientsList.set(observableList);
		this.tasks = new SimpleListProperty<String>();
		this.tasks.set(observableTaskList);
		
		observableList.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				while(c.next()){
					if (c.getRemovedSize() > 0) {
						
						for (String client: c.getRemoved()) {
							adminModel.removeClient(client);
						}
					}
				}
			}
		});
		
		observableTaskList.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				while(c.next()){
					if(c.getRemovedSize() > 0){
						for(String clientTask : c.getRemoved()){
							adminModel.removeTask(clientTask);
						}
					}
				}
				
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if (o == adminModel) {
			List<String> params = (LinkedList<String>)arg;
			String op = params.get(0);
			String clientName = params.get(1);
			
			if (op.equals("Add"))
				observableList.add(clientName);
			else if(op.equals("Remove"))
				observableList.remove(clientName);
			else{
				String taskName = params.get(2);
				if(op.equals("AddTask"))
					observableTaskList.add(clientName +" "+ taskName);
				else
					observableTaskList.remove(clientName +" "+ taskName);
			}
		}
	}
}