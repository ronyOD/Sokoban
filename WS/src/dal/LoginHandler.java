package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
* <h1>LoginHandler</h1>
* This class gets/sets a solution in the db.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class LoginHandler {

	/**
	* This method gets a solution from the Db by the levelName.
	* @param connection.
	* @param levelName.
	* @return String.
	*/
	public String getSolution(Connection connection, String levelName){
		String solution = null;
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Solutions WHERE LevelName='" + levelName + "'");
			ResultSet rs = ps.executeQuery();
			rs.next();		
			solution = rs.getString(3);
			//solution = rs.toString();
							
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return solution;
	}
	/**
	* This method inserts a solution to the Db.
	* @param connection.
	* @param solution.
	* @param levelName.
	* @return Boolean.
	*/
	public boolean insertSolution(Connection connection, String solution, String levelName){
		try {
			String query = "INSERT INTO Solutions (LevelName, Solution) values (?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, levelName);
			ps.setString(2, solution);
			
			ps.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
