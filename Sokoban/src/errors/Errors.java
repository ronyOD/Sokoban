package errors;

public class Errors {
	public static void report(Exception e) {
		System.err.println("Error: " + e.getMessage());
	}
}
