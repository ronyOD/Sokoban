package controller.server;

public class ServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597378546645393317L;

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

}
