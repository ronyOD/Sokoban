package client;

import java.io.File;

public class Demo {

	private static final int port = 1235;
	private static final String host = "localhost";
	
	public static void main(String[] args) {
		TCPClient client = new TCPClient();
		client.setHost(host);
		client.setPort(port);
		
		String levelName = "level1.txt";
		client.send(levelName, "client", new File(levelName));
		
		TCPClient client2 = new TCPClient();
		client2.setHost(host);
		client2.setPort(port);
		
		String levelName2 = "level2.txt";
		client.send(levelName2, "client2", new File(levelName2));
	}
}
