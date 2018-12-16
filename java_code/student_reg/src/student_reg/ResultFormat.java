package student_reg;

import java.sql.ResultSet;

/* ResultFormat class is 
 * used for handling 
 * Stored Procedure Output format.
 * The class contains two variables
 * a> Resultset Object => 
 * Contains the recordset from the table
 * b>Message variable:
 * Contains the Stored Procedure generated Message. 	
 */

public class ResultFormat {
	
	/* Declaration of variables */
	private String message=null;
	private ResultSet res=null;
	
	// Constructor Define for initialization
	public ResultFormat(String message,ResultSet res )
	{
		this.message=message;
		this.res=res;
		
	}
	
	// get Message Variable
	public String getMessage()
	{
		return this.message;	
	}
	
	// Get ResultSet Object
	public ResultSet getRes()
	{
		return this.res;	
	}

}
