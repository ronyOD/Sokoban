package boot;

import SokobanServer.ClientHandler;
import SokobanServer.MyServer;

public class Demo {
	private static final int port = 1235;
	//private static final String host = "localhost";

	public static void main(String[] args) throws InterruptedException {
		
		MyServer server = new MyServer(port, new ClientHandler());
		server.startServer();
		
	}

}
