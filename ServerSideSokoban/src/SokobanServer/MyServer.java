package SokobanServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyServer {

	private int port;
	private ExecutorService executor;
	private boolean stopped = false;
	private int maxNumOfThreads = 5;
	private ClientHandler ch;
	
	public MyServer(int port, ClientHandler ch) {
		this.port = port;
		executor = Executors.newFixedThreadPool(maxNumOfThreads);
		this.ch = ch;
	}
	
	public void stopServer(){
		this.stopped = true;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	
	public void startServer(){
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Hello from server");
			//server.setSoTimeout(10000);
			while(!stopped){
				Socket aClient = server.accept();
				System.out.println("Connection esatblished");
				executor.execute(new Runnable(){

					@Override
					public void run() {
						try {
							ch.handle(aClient, aClient.getInputStream(), aClient.getOutputStream());
							if(!aClient.isClosed()){
								aClient.getInputStream().close();
								//aClient.getOutputStream().close();
								aClient.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
				
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		
		try {
			executor.shutdown();
			executor.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			stopped = true;
		}
		
	}	
}