package student_reg;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Formatter;

import oracle.jdbc.OracleTypes;

/* StudentRegUtilities  Class is used for 
 * a> Create the connection to the database
 * b> Fetch all table records from the DB.
 * c> Delete a student from JDBC to database
 * d> Add Student enrollment from JDBC to database
 * e> Drop Student enrollment from JDBC to database
 * f> Close the DB connection
 */

public class StudentRegUtilities {

	/* Declaration of all variables */
	private ResultSet res = null;
	private java.sql.Connection connection=null;
	private  String showStudents=null;
	private  String showClasses=null;
	private  String showCourses=null;
	private  String showEnrollments=null;
	private  String showTas=null;
	private  String showPrerequisites=null;
	private  String showLogs=null;
	private  String getTAInfo=null;
	private String getPrereqsiteCourses=null;
	private Connection obj=null;
	private String delete_student=null;
	private String drop_enrollment=null;
	private String insert_enrollment=null;
	
	
	
	/* Call the Connection Object to make connection with the Database */
	public StudentRegUtilities() throws ClassNotFoundException, SQLException
	{
		//Call the connection Object
		obj=new Connection();
		connection=obj.createConnection();
	}	
	
	/* Call the Connection Object with the Database */
	public void closeDBConnection() throws SQLException
	{
		if(res!=null)
			res.close();
		else
			res=null;
		if(obj.getConnection()!=null)
			obj.closeConnection();
	}
	
	
	/* Get Students Table Record */
	public ResultFormat getStudentTable() throws SQLException
	{
		showStudents="{call student_reg_package.show_students_ref(?)}";
		res=null;
		CallableStatement callStat = connection.prepareCall(showStudents);
		callStat.registerOutParameter(1, OracleTypes.CURSOR);
		callStat.execute();
		res = (ResultSet) callStat.getObject(1);
		callStat=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	
	}

	/* Get Classes Table Record */
	public ResultFormat getClassesTable() throws SQLException 
	{
		showClasses="{call student_reg_package.show_classes_ref(?)}";
		res=null;
		CallableStatement callStat = connection.prepareCall(showClasses);
		callStat.registerOutParameter(1, OracleTypes.CURSOR);
		callStat.execute();
		res = (ResultSet) callStat.getObject(1);
		callStat=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	
	}

	/* Get Enrollments Table Record */
	public ResultFormat getEnrollmentsTable() throws SQLException 
	{
		showEnrollments="{call student_reg_package.show_enrollments_ref(?)}";
		res=null;
		CallableStatement callStat = connection.prepareCall(showEnrollments);
		callStat.registerOutParameter(1, OracleTypes.CURSOR);
		callStat.execute();
		res = (ResultSet) callStat.getObject(1);
		callStat=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	
	}


	/* Get Courses Table Record */
	public ResultFormat getCoursesTable() throws SQLException 
	{
		showCourses="{call student_reg_package.show_courses_ref(?)}";
		res=null;
		CallableStatement callStat = connection.prepareCall(showCourses);
		callStat.registerOutParameter(1, OracleTypes.CURSOR);
		callStat.execute();
		res = (ResultSet) callStat.getObject(1);
		callStat=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	
	}

	
	/* Get Ta's Table Record */
	public ResultFormat getTasTable() throws SQLException 
	{
		showTas="{call student_reg_package.show_tas_ref(?)}";
		res=null;
		CallableStatement callStat = connection.prepareCall(showTas);
		callStat.registerOutParameter(1, OracleTypes.CURSOR);
		callStat.execute();
		res = (ResultSet) callStat.getObject(1);
		callStat=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	
	}
	
	
	/* Get getPrerequisitesTable Table Record */
	public ResultFormat getPrerequisitesTable() throws SQLException 
	{
		showPrerequisites="{call student_reg_package.show_prerequisites_ref(?)}";
		res=null;
		CallableStatement callStat = connection.prepareCall(showPrerequisites);
		callStat.registerOutParameter(1, OracleTypes.CURSOR);
		callStat.execute();
		res = (ResultSet) callStat.getObject(1);
		callStat=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	
	}
	
	
	/* Get Logs Table Record */
	public ResultFormat getLogsTable() throws SQLException 
	{
		showLogs="{call student_reg_package.show_logs_ref(?)}";
		res=null;
		CallableStatement cs = connection.prepareCall(showLogs);
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		res = (ResultSet) cs.getObject(1);
		cs=null;
		ResultFormat resformat=null;
		resformat=new ResultFormat(null,res);
		return resformat;
	}
	
	/* Get Prereqsites Table Record */
	public ResultFormat getPrereqsiteCourse(String dept_code, int course_no ) throws SQLException
	{
		res = null;
		getPrereqsiteCourses="{call student_reg_package.getPreRequisiteCourses_ref(?,?,?,?)}";
		CallableStatement callStat = connection.prepareCall(getPrereqsiteCourses);
		callStat.setString(1, dept_code);
		callStat.setInt(2, course_no);
		callStat.registerOutParameter(3, OracleTypes.VARCHAR);
		callStat.registerOutParameter(4, OracleTypes.CURSOR);
		callStat.execute();
		
		ResultFormat resformat=null;
		String message = callStat.getString(3);
		if(message==null)
		{
			res = (ResultSet) callStat.getObject(4);
			// Create a ResultFormat Object to store the output
			resformat=new ResultFormat(message,res);
		}		
		else
			resformat=new ResultFormat(message,null);
		callStat = null;
		return resformat;
		
	}

	/*Get TA Info */
	public ResultFormat getTAInfo(String classid) throws SQLException
	{
		res = null;
		getTAInfo="{call student_reg_package.getTAInfo_ref(?,?,?)}";
		CallableStatement callStat = connection.prepareCall(getTAInfo);
		callStat.setString(1, classid);
		callStat.registerOutParameter(2, OracleTypes.VARCHAR);
		callStat.registerOutParameter(3, OracleTypes.CURSOR);
		callStat.execute();
		
		ResultFormat resformat=null;
		String message = callStat.getString(2);
		if(message==null)
		{
			res = (ResultSet) callStat.getObject(3);
			// Create a ResultFormat Object to store the output
			resformat=new ResultFormat(message,res);
		}		
		else
			resformat=new ResultFormat(message,null);
		callStat = null;
		return resformat;
	}
	
	
	/* Delete the student from the system */
	public ResultFormat deleteStudent(String bnumber) throws SQLException
	{
		res = null;
		delete_student="{call student_reg_package.delete_student_ref(?,?)}";
		CallableStatement callStat = connection.prepareCall(delete_student);
		callStat.setString(1, bnumber);
		callStat.registerOutParameter(2, OracleTypes.VARCHAR);
		callStat.execute();
		
		ResultFormat resformat=null;
		String message = callStat.getString(2);
		resformat=new ResultFormat(message,null);
		callStat = null;
		return resformat;
	}
	
	
	/* Drop Enrollment for a student */
	public ResultFormat dropEnrollment(String bnumber,String classid) throws SQLException
	{
		res = null;
		drop_enrollment="{call student_reg_package.drop_student_enrollment_ref(?,?,?)}";
		CallableStatement callStat = connection.prepareCall(drop_enrollment);
		callStat.setString(1, bnumber);
		callStat.setString(2, classid);
		callStat.registerOutParameter(3, OracleTypes.VARCHAR);
		callStat.execute();
		
		ResultFormat resformat=null;
		String message = callStat.getString(3);
		resformat=new ResultFormat(message,null);
		callStat = null;
		return resformat;
	}
	
	/* Add Enrollment for a student */
	public ResultFormat insertEnrollment(String bnumber,String classid) throws SQLException
	{
		res = null;
		insert_enrollment="{call student_reg_package.insert_student_enrollment_ref(?,?,?)}";
		CallableStatement callStat = connection.prepareCall(insert_enrollment);
		callStat.setString(1, bnumber);
		callStat.setString(2, classid);
		callStat.registerOutParameter(3, OracleTypes.VARCHAR);
		callStat.execute();
		
		ResultFormat resformat=null;
		String message = callStat.getString(3);
		resformat=new ResultFormat(message,null);
		callStat = null;
		return resformat;
	}
	
}
