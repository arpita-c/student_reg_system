package student_reg;

import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

/* Connection Class is used 
 * for creating a connection
 * with the Oracle Database.
 * The Class has four method
 * a> Create the Connection.
 * b> Close the Connection.
 * c> Get the Connection Object.
 * d> Set the Connection Object. 
 */

public class Connection {
	
	private final String driver="oracle.jdbc.driver.OracleDriver";
	//private final String url="jdbc:oracle:thin:@localhost:1521:xe";
	private final String url="jdbc:oracle:thin:@<host name>:<portno>:<database>";
	private final String user=<username>;
	private final String pwd=<password>;
	private java.sql.Connection connectionObj=null;
	
	// Create the connection to the database 
	public java.sql.Connection createConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName(driver);
		connectionObj = DriverManager.getConnection(url,user,pwd);	
		System.out.println("Connection Craeted");
		setConnection(connectionObj);
		return connectionObj;
	}

	// Close the connection
	public void closeConnection() throws SQLException
	{
		System.out.println("Connection Closed");
		connectionObj.close();
	}
	
	// Get Connection Object
	public java.sql.Connection getConnection()
	{
		return connectionObj;
	}

	//Set the connection Object
	public void setConnection(java.sql.Connection conn) 
	{
		this.connectionObj = conn;
	}


}
