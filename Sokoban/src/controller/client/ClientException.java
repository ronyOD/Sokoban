package controller.client;

public class ClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597378546645393317L;

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

}
