package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;

/**
* <h1>TCPClient</h1>
* This class manages client connection to the server side.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/
public class TCPClient {

	private String host;
	private int port;

	/**
	* This method sets the endpoint host.
	* @param host
	*/
	public void setHost(String host) {
		this.host = host;
	}

	/**
	* This method sets the endpoint port.
	* @param port
	*/
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	* This method sends and receive data to the server.
	* @param levelName
	* @param userName
	* @param levelFile
	* @return String
	*/
	public String send(String levelName, String userName, File levelFile){
		
		Socket socket = null;
		ObjectOutputStream out = null;
		String solution = null;
		
		try {
			
			socket = new Socket(host, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			//out.write(levelName.getBytes(Charset.forName("UTF-8")));
			String levelNameWithSuffix = levelName;
			out.writeObject(levelNameWithSuffix);
			out.writeObject(userName);
			byte[] fileContent = Files.readAllBytes((levelFile.toPath()));
			out.writeInt(fileContent.length);
			out.write(fileContent);
			out.flush();

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			solution = input.readLine();
			
			System.out.println(solution);
			
			input.close();
			out.close();
			socket.close(); //check
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solution;
	}
}
