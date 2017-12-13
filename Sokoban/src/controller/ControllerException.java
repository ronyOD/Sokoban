package controller;

public class ControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6018768777897357017L;

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

}
