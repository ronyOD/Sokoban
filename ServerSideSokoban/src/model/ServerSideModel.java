package model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import SokobanServer.MyServer;

public class ServerSideModel extends Observable{

	private Map<String, Socket> connectedClients = new HashMap<>();
	private Map<String, String> tasks = new HashMap<>();
	private MyServer server;
	
	private static final ServerSideModel instance = new ServerSideModel();
	private ServerSideModel(){}
	
	public static ServerSideModel getInstance(){
		return instance;
	}
	
	public void setServer(MyServer server){
		this.server = server;
	}
	
	public MyServer getServer(){
		return server;
	}
	
	public void addTask(String userName, String task){
		tasks.put(userName, task);
		setChanged();
		List<String> params = new LinkedList<>();
		params.add("AddTask");
		params.add(userName);
		params.add(task);
		notifyObservers(params);
	}
	
	public void removeTask(String userName){

		List<String> params = new LinkedList<>();
		tasks.remove(userName);
		setChanged();
		params.add("RemoveTask");
		params.add(userName);
		params.add(tasks.get(userName));
		notifyObservers(params);
	}
	
	public void addClient(String userName, Socket socket){
		connectedClients.put(userName, socket);
		setChanged();
		List<String> params = new LinkedList<>();
		params.add("Add");
		params.add(userName);
		notifyObservers(params);
	}
	
	public void disconnectClient(String userName){
		Socket socket = connectedClients.get(userName);
		try {
			socket.close();
			connectedClients.remove(userName);
			setChanged();
			List<String> params = new LinkedList<>();
			params.add("Remove");
			params.add(userName);
			notifyObservers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeClient(String userName){
		connectedClients.remove(userName);
	}
	
	public void limitNumOfClients(int numOfClients){
		List<String> keysAsArray = new ArrayList<>(this.connectedClients.keySet());
		while(this.connectedClients.size() > numOfClients){
			Random rand = new Random();
			String randomKey = keysAsArray.get(rand.nextInt(keysAsArray.size()));
			disconnectClient(randomKey);
		}
	}

	public void closeServer(){
		try {
			this.server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
