package controller.client;

import java.io.ObjectOutputStream;
import java.net.Socket;

import errors.Errors;
import interfaces.Commandable;
import model.data.levels.Level;
import protocol.Command;

public class MyClient implements Commandable {

	private Level level;
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
	* This method sends Commands to the endpoint.
	* @param command
	* @return Nothing
	*/
	private void send(Command command) throws ClientException {
		Socket socket = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			socket = new Socket(host, port);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(command);
		} catch (Exception e) {
			throw new ClientException(e);
		} finally {
			try {
				if (objectOutputStream != null)
					objectOutputStream.close();
				if (socket != null && !socket.isClosed()) {
					if (socket.getInputStream() != null)
						socket.getInputStream().close();
					socket.close();
				}
			} catch (Exception e) {
				throw new ClientException(e);
			}
		}
	}

	public Level getLevel() {
		return level;
	}

	/**
	* This method sends Commands to the endpoint.
	* @param command
	*/
	@Override
	public void command(Command command) {
		try {
			send(command);
		} catch (ClientException e) {
			Errors.report(e);
		}
	}
}
