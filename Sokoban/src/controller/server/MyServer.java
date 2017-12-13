package controller.server;

import java.io.ObjectInputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import controller.MyController;
import errors.Errors;
import interfaces.Startable;
import interfaces.Stoppable;
import protocol.Command;
/**
* <h1>MyServer</h1>
* This class represents the clients server.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/
public class MyServer implements Stoppable, Startable, Runnable {

	private boolean stopped;

	private MyController controller;

	private Thread thread;

	private ServerSocket serverSocket;

	/**
	* This method returns the controller.
	* @return Controller.
	*/
	public MyController getController() {
		return controller;
	}

	/**
	* Constructor
	*/
	public MyServer() {
		super();
	}

	/**
	* This method sets the controller.
	* @param controller
	*/
	public void setController(MyController controller) {
		this.controller = controller;
	}

	/**
	* This method starts listening on the socket.
	* @param port
	* @exception ServerException
	*/
	public void listen(int port) throws ServerException {
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(1000);
		} catch (Exception e) {
			throw new ServerException(e);
		}
	}

	/**
	* This method stops the server.
	*/
	@Override
	public void stop() {
		stopped = true;
	}

	/**
	* This method starts the server in a different thread.
	*/
	@Override
	public void start() {
		thread = new Thread(this, "Server");
		thread.start();
	}

	/**
	* This method runs the server.
	* It gets data from the socket and sends it to the controller.
	*/
	@Override
	public void run() {
		while (!stopped) {
			try {
				Socket socket = serverSocket.accept();
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						ObjectInputStream objectInputStream = null;
						try {
							objectInputStream = new ObjectInputStream(socket.getInputStream());
							Command command = (Command) objectInputStream.readObject();
							System.out.println("Received: " + command);
							controller.command(command);
						} catch (Exception e) {
							throw new RuntimeException(e);
						} finally {
							try {
								if (objectInputStream != null)
									objectInputStream.close();
								if (socket != null && !socket.isClosed()) {
									if (socket.getOutputStream() != null)
										socket.getOutputStream().close();
									socket.close();
								}
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
					}
				});
				thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						Errors.report(new ServerException(e));
					}
				});
				thread.start();
			} catch (SocketTimeoutException e) {
				//
			} catch (Exception e) {
				Errors.report(e);
			}
		}
	}
}
