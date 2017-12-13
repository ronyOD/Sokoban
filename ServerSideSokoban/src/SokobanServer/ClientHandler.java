package SokobanServer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import boot.Solver;
import heuristics.ManhattanDistance;
import javafx.application.Platform;
import model.ServerSideModel;
import model.data.positions.Position;
import search.BFS;
import search.Solution;

public class ClientHandler {
	
	private ExecutorService executor;
	public ClientHandler() {
		this.executor = Executors.newFixedThreadPool(5);
	}
	
	private void generateFile(String levelName, byte[] level) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(levelName+".txt");
			fos.write(level);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handle(Socket aClient, InputStream inputStream, OutputStream outputStream) {
		
		ObjectInputStream in = null;
		PrintWriter writer = null;
		String userName = null;
		try {
			in = new ObjectInputStream(inputStream);
			writer = new PrintWriter(outputStream);
			//level name
			String levelName = (String) in.readObject();
			System.out.println(levelName);
			//user name
			userName = (String) in.readObject();
			System.out.println(userName);
			//read file
			int length = in.readInt();
			byte[] level = new byte[length];
			in.readFully(level);
			
			generateFile(levelName, level);
			Platform.runLater(addClient(userName, aClient));
			Platform.runLater(addTask(userName, "Fetching solution.."));

			String solution = getSolutionFromService(levelName);
			if(solution == null || solution.equals("Not Found")){
				Platform.runLater(removeTask(userName));
				Platform.runLater(addTask(userName, "Solving " + levelName));
				//call strips..
				//save solution via web service.
				Solution mySolution = solveLevel(levelName+".txt");
				writer.write(mySolution.toCsv());
				writer.flush();
				saveSolution(levelName, mySolution.toCsv());
				Platform.runLater(removeTask(userName));
				
			}
			else{
				writer.write(solution);
				writer.flush();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			Platform.runLater(removeTask(userName));
			Platform.runLater(removeClient(userName));
		}
	}

	private String getSolutionFromService(String levelName) {
		
		WebClient webClient = new WebClient();
 		String levelNameNoSuffix = levelName.split("\\.")[0];
 		System.out.println(levelNameNoSuffix);
		String solution = webClient.get(levelNameNoSuffix);
		return solution;
	}
	
	private boolean saveSolution(String levelName, String solution){
		boolean saved = false;
		String res = null;
		try {
			WebClient client = new WebClient();
			res = client.post(levelName, solution);
			if(res.equals("Solution saved")){
				saved = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saved;
	}
	
	private Solution solveLevel(String levelName){
		
		Future<Solution> futureSolution = executor.submit(new Callable<Solution>() {

			@Override
			public Solution call() throws Exception {
				// TODO Auto-generated method stub
				Solver solver = new Solver(levelName, new BFS<Position>(), new BFS<Position>(), new ManhattanDistance());
				return solver.solve();
			}
		});
		Solution result = null;
		try {
			result = futureSolution.get();
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Runnable addClient(String userName, Socket socket){
		return new Runnable() {
			
			@Override
			public void run() {
				ServerSideModel.getInstance().addClient(userName, socket);
			}
		};
	}
	private Runnable removeClient(String userName){
		return new Runnable() {
			
			@Override
			public void run() {
				ServerSideModel.getInstance().removeClient(userName);
			}
		};
	}
	private Runnable addTask(String userName, String task){
		return new Runnable() {
			
			@Override
			public void run() {
				ServerSideModel.getInstance().addTask(userName, task);
			}
		};
	}
	private Runnable removeTask(String userName){
		return new Runnable() {
			
			@Override
			public void run() {
				ServerSideModel.getInstance().removeClient(userName);
			}
		};
	}
}
