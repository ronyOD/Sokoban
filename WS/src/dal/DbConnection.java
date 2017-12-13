package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
* <h1>DbConnection</h1>
* This class configure the Db Connection.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class DbConnection {
	/**
	* This method gets the Db Connection.
	* @return Connection.
	*/
	public Connection getConnection(){
		try {
			Connection connection = null;
			String connectionURL = "jdbc:mysql://localhost:3306/MyDb?autoReconnect=true&useSSL=false";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "root");
			
			return connection;
			
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
