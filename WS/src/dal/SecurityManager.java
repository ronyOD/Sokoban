package dal;

import java.sql.Connection;
/**
* <h1>SecurityManager</h1>
* This class provides an easy and secure access to the Db.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class SecurityManager {
	/**
	* This method gets a solution from the Db by the levelName.
	* @param levelName.
	* @return String.
	*/
	public String getSolution(String levelName){
		String solution = null;
		try {
			DbConnection database = new DbConnection();
			Connection connection = database.getConnection();
			LoginHandler loginHandler = new LoginHandler();
			solution = loginHandler.getSolution(connection, levelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solution; 
	}
	/**
	* This method inserts a solution to the Db.
	* @param solution.
	* @param levelName.
	* @return Boolean.
	*/
	public boolean insertSolution(String solution, String levelName){
		try {
			DbConnection database = new DbConnection();
			Connection connection = database.getConnection();
			LoginHandler loginHandler = new LoginHandler();
			return loginHandler.insertSolution(connection, solution, levelName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
