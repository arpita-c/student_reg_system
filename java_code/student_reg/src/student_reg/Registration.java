package student_reg;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/* Registration class is 
 * user for the creation 
 * of GUI interface
 * using Java Swing Component.
 */

public class Registration {
	
	/* Declaration of variables */
	private JFrame mainFrame;
	public JTable table;
	private StudentRegUtilities studentregutilitesObj;
	private ResultSet res=null;
	private JTable datatable;
	
	
	/* 
	 * Constructor function which will create an object of StudentRegUtilities 
	 * to communicate with the database handler function
	*/
	public Registration() throws ClassNotFoundException, SQLException
	{
		studentregutilitesObj =new StudentRegUtilities();
		prepareGUI();	
	}
	
	
	
	/* Get TA Information GUI Interface */
	private  void get_ta_table()
	{
		JFrame inputFrame = new JFrame("Get TA Info");
		inputFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				inputFrame.dispose();
				mainFrame.setVisible(true);
			}
		});

		inputFrame.setBounds(100, 100, 400, 250);
		inputFrame.setVisible(true);
		inputFrame.setSize(600, 400);
		inputFrame.setLocation(530, 300);

		JPanel panel = new JPanel();
		inputFrame.add(panel);

		JLabel classid_lbl = new JLabel("ClassID:");
		classid_lbl.setVisible(true);
		panel.add(classid_lbl);
		
		JTextField classid_textfield = new JTextField();
		classid_textfield.setBounds(160, 65, 90, 20);
		panel.add(classid_textfield);
		classid_textfield.setColumns(15);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(60, 150, 89, 23);
		panel.add(btnSubmit);
				
		JButton homebutton = new JButton("Home");
		homebutton.setBackground(Color.BLUE);
		homebutton.setForeground(Color.MAGENTA);
		homebutton.setBounds(200, 150, 89, 23);
		panel.add(homebutton);

		datatable=new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		//scrollPane.setBounds(97, 72, 218, 136);
		//scrollPane.setBounds(300, 15, 218, 136);
		datatable.setVisible(false);
		panel.add(scrollPane);
		scrollPane.setViewportView(datatable);
		scrollPane.setPreferredSize(new Dimension(200,100));
		
		btnSubmit.addActionListener(ae ->
		{
			String classid=classid_textfield.getText();
			try 
			{
				ResultFormat resformat=studentregutilitesObj.getTAInfo(classid);
				if(resformat.getMessage()!=null)
				{
					datatable.setVisible(false);	
					JOptionPane.showMessageDialog(inputFrame,resformat.getMessage());	
				}
				
				else
				{
					datatable.setVisible(true);
					datatable.setModel(buildTableModel(resformat.getRes()));
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		homebutton.addActionListener(ae ->
		{
			prepareGUI();
			inputFrame.setVisible(false);
		});
		
	}
	
	
	
	/* Get Prerequisite Courses GUI Interface */
	private  void get_prereq_courses()
	
	{
		JFrame inputFrame = new JFrame("Prerequisite Courses");
		inputFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				inputFrame.dispose();
				mainFrame.setVisible(true);
			}
		});

		inputFrame.setBounds(100, 100, 400, 250);
		inputFrame.setVisible(true);
		inputFrame.setSize(600, 400);
		//inputFrame.setLocation(430, 100);
		inputFrame.setLocation(530, 300);

		JPanel panel = new JPanel();
		inputFrame.add(panel);

		JLabel dept_code_lbl = new JLabel("Dept Code:");
		dept_code_lbl.setBounds(65, 68, 90, 14);
		panel.add(dept_code_lbl);
		
		JTextField deptcode_textfield = new JTextField();
		deptcode_textfield.setBounds(160, 65, 90, 20);
		panel.add(deptcode_textfield);
		deptcode_textfield.setColumns(10);
		
		JLabel courseno_lbl = new JLabel("Course#:");
		courseno_lbl.setBounds(65, 115, 90, 14);
		panel.add(courseno_lbl);

		JTextField courseno_textfield = new JTextField();
		courseno_textfield.setBounds(160, 112, 90, 20);
		panel.add(courseno_textfield);
		courseno_textfield.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(60, 200, 89, 23);
		panel.add(btnSubmit);
				
		JButton homebutton = new JButton("Home");
		homebutton.setBackground(Color.BLUE);
		homebutton.setForeground(Color.MAGENTA);
		homebutton.setBounds(200, 200, 89, 23);
		panel.add(homebutton);

		datatable=new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		//scrollPane.setBounds(97, 72, 218, 136);
		//scrollPane.setBounds(300, 15, 218, 136);
		datatable.setVisible(false);
		panel.add(scrollPane);
		scrollPane.setViewportView(datatable);
		scrollPane.setPreferredSize(new Dimension(200,300));
		
		btnSubmit.addActionListener(ae ->
		{
			String dept_code=deptcode_textfield.getText();
			int courseno= Integer.parseInt(courseno_textfield.getText());
			
			try 
			{
				ResultFormat resformat=studentregutilitesObj.getPrereqsiteCourse(dept_code, courseno);
				if(resformat.getMessage()!=null)
				{
					datatable.setVisible(false);	
					JOptionPane.showMessageDialog(inputFrame,resformat.getMessage());	
				}
				
				else
				{
					datatable.setVisible(true);
					datatable.setModel(buildTableModel(resformat.getRes()));
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		homebutton.addActionListener(ae ->
		{
			prepareGUI();
			inputFrame.setVisible(false);
		});
		
	}
	
	/* Fetch Record information of each database Table - GUI Interface*/
	private void show_table()
	{

		JFrame inputFrame = new JFrame("Show table");
		inputFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				inputFrame.dispose();
				mainFrame.setVisible(true);

			}
		});

		inputFrame.setBounds(100, 100, 400, 250);
		//inputFrame.getContentPane().setLayout(null);
		inputFrame.setVisible(true);
		//inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFrame.setSize(600, 700);
		//inputFrame.setLocation(430, 100);
		inputFrame.setLocation(530, 300);

		JPanel panel = new JPanel();
		inputFrame.add(panel);

		JLabel lbl = new JLabel("Table Name:");
		
		//lbl.setBounds(65, 68, 90, 14);
		lbl.setVisible(true);
		panel.add(lbl);

		String[] choices = {"Students", "TAs", "Courses", "Classes", "Enrollments",
				"Prerequisites", "Logs"};
		final JComboBox<String> cb = new JComboBox<>(choices);
		cb.setBounds(160, 65, 90, 20);
		cb.setVisible(true);
		panel.add(cb);
		
		JButton submit = new JButton("Submit");
		submit.setBackground(Color.BLUE);
		submit.setForeground(Color.MAGENTA);
		submit.setBounds(60, 150, 89, 23);
		panel.add(submit);
		

		JButton home = new JButton("Home");
		home.setBackground(Color.BLUE);
		home.setForeground(Color.MAGENTA);
		home.setBounds(200, 150, 89, 23);
		panel.add(home);
		
		datatable=new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		//scrollPane.setBounds(97, 72, 218, 136);
		//scrollPane.setBounds(300, 15, 218, 136);
		datatable.setVisible(false);
		panel.add(scrollPane);
		
		scrollPane.setViewportView(datatable);
		scrollPane.setPreferredSize(new Dimension(500,500));
		submit.addActionListener(ae ->
		{

			// get the dropdown selected value
			String selectedTable = (String)cb.getSelectedItem();
			
			if(selectedTable.equalsIgnoreCase("Students"))
				try {
					ResultFormat resformat=studentregutilitesObj.getStudentTable();
					res=resformat.getRes();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(selectedTable.equalsIgnoreCase("TAs"))
				try {
					ResultFormat resformat=studentregutilitesObj.getTasTable();
					res=resformat.getRes();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			if(selectedTable.equalsIgnoreCase("Courses"))
				try {
					ResultFormat resformat=studentregutilitesObj.getCoursesTable();
					res=resformat.getRes();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			if(selectedTable.equalsIgnoreCase("Classes"))
				try {
					ResultFormat resformat=studentregutilitesObj.getClassesTable();
					res=resformat.getRes();
					//res=studentregutilitesObj.getClassesTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			if(selectedTable.equalsIgnoreCase("Enrollments"))
				try {
					ResultFormat resformat=studentregutilitesObj.getEnrollmentsTable();
					res=resformat.getRes();
					//res=studentregutilitesObj.getEnrollmentsTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			if(selectedTable.equalsIgnoreCase("Prerequisites"))
				try {
					ResultFormat resformat=studentregutilitesObj.getPrerequisitesTable();
					res=resformat.getRes();
					//res=studentregutilitesObj.getPrerequisitesTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			if(selectedTable.equalsIgnoreCase("Logs"))
				try {
					ResultFormat resformat=studentregutilitesObj.getLogsTable();
					res=resformat.getRes();
					//res=studentregutilitesObj.getLogsTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			//Bound the database Result to the data Table
			try {
			//	JOptionPane.showMessageDialog(inputFrame, "hi");
				if(res!=null)	
				{
					datatable.setVisible(true);
					datatable.setModel(buildTableModel(res));
				}
				else
					JOptionPane.showMessageDialog(inputFrame, "No Record found");	
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

			
		
		home.addActionListener(ae ->
		{

			prepareGUI();
			inputFrame.setVisible(false);

		});
}
	
	
	
	// Build Custom Database table interface and Bind with the Swing Datatable 
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    	return new DefaultTableModel(data, columnNames);

	}

	
	
	// Add student Enrollment - GUI interface
	private void enroll_class()
	{
		JFrame inputFrame = new JFrame("Add Enrollment");
		inputFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				inputFrame.dispose();
				mainFrame.setVisible(true);

			}
		});

		inputFrame.setBounds(100, 100, 400, 250);
		inputFrame.setSize(700, 500);
		//inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFrame.getContentPane().setLayout(null);
		inputFrame.setVisible(true);
		inputFrame.setSize(600, 700);
		//inputFrame.setLocation(430, 100);
		inputFrame.setLocation(530, 300);

		JLabel lblbno = new JLabel("Student  B#:");
		lblbno.setBounds(65, 68, 90, 14);
		inputFrame.getContentPane().add(lblbno);

		JTextField bnumber = new JTextField();
		bnumber.setBounds(160, 65, 90, 20);
		inputFrame.getContentPane().add(bnumber);
		bnumber.setColumns(15);

		JLabel lblclsid = new JLabel("Class ID:");
		lblclsid.setBounds(65, 115, 90, 14);
		inputFrame.getContentPane().add(lblclsid);

		JTextField classid = new JTextField();
		classid.setBounds(160, 112, 90, 20);
		inputFrame.getContentPane().add(classid);
		classid.setColumns(15);


		JButton btnSubmit = new JButton("submit");

		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(60, 150, 89, 23);
		inputFrame.getContentPane().add(btnSubmit);


		btnSubmit.addActionListener(ae ->
		{
			String bno= bnumber.getText();
			String cid= classid.getText();
			ResultFormat resformat = null;
			try {
				resformat = studentregutilitesObj.insertEnrollment(bno, cid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String op=resformat.getMessage();
			JOptionPane.showMessageDialog(inputFrame, op);

		});

		JButton homebutton = new JButton("Home");
		homebutton.setBackground(Color.BLUE);
		homebutton.setForeground(Color.MAGENTA);
		homebutton.setBounds(200, 150, 89, 23);
		inputFrame.getContentPane().add(homebutton);

		homebutton.addActionListener(ae ->
		{

			prepareGUI();
			inputFrame.setVisible(false);

		});

	}

	
	// Drop Student Enrollment - GUI Interface
	private void drop_class()
	{
		JFrame inputFrame = new JFrame("Drop Enrollment");

		inputFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				inputFrame.dispose();
				mainFrame.setVisible(true);

			}
		});

		inputFrame.setBounds(100, 100, 400, 250);
		inputFrame.getContentPane().setLayout(null);
		inputFrame.setVisible(true);
		inputFrame.setSize(600,700);
		inputFrame.setLocation(530, 300);
	
		JLabel lblbno = new JLabel("Student  B#:");
		lblbno.setBounds(65, 68, 90, 14);
		inputFrame.getContentPane().add(lblbno);

		JTextField bnumber = new JTextField();
		bnumber.setBounds(160, 65, 90, 20);
		inputFrame.getContentPane().add(bnumber);
		bnumber.setColumns(15);

		JLabel lblclsid = new JLabel("Class ID:");
		lblclsid.setBounds(65, 115, 90, 14);
		inputFrame.getContentPane().add(lblclsid);

		JTextField classid = new JTextField();
		classid.setBounds(160, 112, 90, 20);
		inputFrame.getContentPane().add(classid);
		classid.setColumns(15);

		JButton btnSubmit = new JButton("submit");

		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(60, 150, 89, 23);
		inputFrame.getContentPane().add(btnSubmit);


		btnSubmit.addActionListener(ae ->
		{
			String bno= bnumber.getText();
			String cid= classid.getText();
			ResultFormat resformat = null;
			try {
				resformat = studentregutilitesObj.dropEnrollment(bno, cid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String op=resformat.getMessage();
			JOptionPane.showMessageDialog(inputFrame, op);
		});

		JButton homebutton = new JButton("Home");
		homebutton.setBackground(Color.BLUE);
		homebutton.setForeground(Color.MAGENTA);
		homebutton.setBounds(200, 150, 89, 23);
		inputFrame.getContentPane().add(homebutton);

		homebutton.addActionListener(ae ->
		{

			prepareGUI();
			inputFrame.setVisible(false);

		});


	}

	

	
	// Delete Student from the database - GUI interface
	private void delete_student()
	{
		JFrame inputFrame = new JFrame("Delete Student Record");
		inputFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				inputFrame.dispose();
				mainFrame.setVisible(true);

			}
		});

		inputFrame.setBounds(100, 100, 400, 250);
		inputFrame.getContentPane().setLayout(null);
		inputFrame.setVisible(true);
		inputFrame.setSize(600,700);
		inputFrame.setLocation(530, 300);

		JLabel lblbno = new JLabel("Student  B#:");
		lblbno.setBounds(65, 68, 90, 14);
		inputFrame.getContentPane().add(lblbno);

		JTextField bnumber = new JTextField();
		bnumber.setBounds(160, 65, 90, 20);
		inputFrame.getContentPane().add(bnumber);
		bnumber.setColumns(15);


		JButton btnSubmit = new JButton("submit");

		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(60, 150, 89, 23);
		inputFrame.getContentPane().add(btnSubmit);


		btnSubmit.addActionListener(ae ->
		{
			String bno= bnumber.getText();
			ResultFormat resformat = null;
			try {
				resformat = studentregutilitesObj.deleteStudent(bno);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String op=resformat.getMessage();
			JOptionPane.showMessageDialog(inputFrame, op);

		});

		JButton homebutton = new JButton("Home");
		homebutton.setBackground(Color.BLUE);
		homebutton.setForeground(Color.MAGENTA);
		homebutton.setBounds(200, 150, 89, 23);
		inputFrame.getContentPane().add(homebutton);

		homebutton.addActionListener(ae ->
		{

			prepareGUI();
			inputFrame.setVisible(false);

		});


	}

	
	
	// GUI Home interface
	private void prepareGUI()
	{
		mainFrame = new JFrame("Student Registration System");
		mainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				try {
					studentregutilitesObj.closeDBConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainFrame.dispose();

			}
		});
		
		//mainFrame.setSize(300, 300);
		mainFrame.setLocation(530, 300);
		Box box = Box.createVerticalBox();
		
		JPanel controlpanel=new JPanel();
		controlpanel.setLayout(new FlowLayout());

		JLabel label=new JLabel("Student Registration Window");
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setFont(new Font("Serif", Font.PLAIN, 20));
		
		controlpanel.add(label);
		controlpanel.add(new JLabel(""));
		
		JButton show_table = new JButton("Show Tables");
		show_table.setPreferredSize(new Dimension(300, 30));
		show_table.setForeground(Color.BLUE);
		show_table.setForeground(Color.MAGENTA);
		show_table.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(show_table);

		show_table.addActionListener(ae ->
		{

			this.show_table();
			mainFrame.setVisible(false);
		});

		show_table.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				show_table.setBackground((new Color(204,229,255)));

			}
			public void mouseExited(MouseEvent evt)
			{
				show_table.setBackground(UIManager.getColor("control"));
			}
		});

		box.add(Box.createVerticalStrut(10));
		
		JButton get_ta = new JButton("Get TA Info");
		get_ta.setPreferredSize(new Dimension(300, 30));
		get_ta.setForeground(Color.BLUE);
		get_ta.setForeground(Color.MAGENTA);
		get_ta.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(get_ta);

		get_ta.addActionListener(ae ->
		{

			this.get_ta_table();
			mainFrame.setVisible(false);
		});

		get_ta.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				get_ta.setBackground((new Color(204,229,255)));

			}
			public void mouseExited(MouseEvent evt)
			{
				get_ta.setBackground(UIManager.getColor("control"));
			}
		});

		
		box.add(Box.createVerticalStrut(10));
		
		JButton pre_req = new JButton("Prerequisite Courses");
		pre_req.setPreferredSize(new Dimension(300, 30));
		pre_req.setForeground(Color.BLUE);
		pre_req.setForeground(Color.MAGENTA);
		pre_req.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(pre_req);

		pre_req.addActionListener(ae ->
		{
			this.get_prereq_courses();
			mainFrame.setVisible(false);
		});

		pre_req.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				pre_req.setBackground((new Color(204,229,255)));

			}
			public void mouseExited(MouseEvent evt)
			{
				pre_req.setBackground(UIManager.getColor("control"));
			}
		});

		box.add(Box.createVerticalStrut(10));
		
		JButton add_enroll = new JButton("Add Enrollment");
		add_enroll.setPreferredSize(new Dimension(300, 30));
		add_enroll.setForeground(Color.BLUE);
		add_enroll.setForeground(Color.MAGENTA);
		add_enroll.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(add_enroll);
		add_enroll.addActionListener(ae ->
		{

			this.enroll_class();
			mainFrame.setVisible(false);

		});
		
		add_enroll.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				add_enroll.setBackground((new Color(204,229,255)));

			}
			public void mouseExited(MouseEvent evt)
			{
				add_enroll.setBackground(UIManager.getColor("control"));
			}
		});

		box.add(Box.createVerticalStrut(10));

		JButton drop_enroll = new JButton("Drop Enrollment");
		drop_enroll.setForeground(Color.BLUE);
		drop_enroll.setForeground(Color.MAGENTA);
		drop_enroll.setPreferredSize(new Dimension(300, 30));
		drop_enroll.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(drop_enroll);
		drop_enroll.addActionListener(ae ->
		{
			this.drop_class();
			mainFrame.setVisible(false);

		});

		drop_enroll.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				drop_enroll.setBackground((new Color(204,229,255)));

			}
			public void mouseExited(MouseEvent evt)
			{
				drop_enroll.setBackground(UIManager.getColor("control"));
			}
		});

		box.add(Box.createVerticalStrut(10));
		

		JButton delete_stud = new JButton("Delete Student");
		delete_stud.setForeground(Color.BLUE);
		delete_stud.setForeground(Color.MAGENTA);
		delete_stud.setPreferredSize(new Dimension(300, 30));
		delete_stud.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(delete_stud);
		delete_stud.addActionListener(ae ->
		{
			this.delete_student();
			mainFrame.setVisible(false);

		});
		delete_stud.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				delete_stud.setBackground((new Color(204,229,255)));
			}
			public void mouseExited(MouseEvent evt)
			{
				delete_stud.setBackground(UIManager.getColor("control"));
			}
		});


		box.add(Box.createVerticalStrut(10));

		
		JButton exitButton = new JButton("Exit");
		exitButton.setForeground(Color.BLUE);
		exitButton.setForeground(Color.MAGENTA);
		exitButton.setPreferredSize(new Dimension(300, 30));
		exitButton.setFont(new Font("Sans Serif", Font.BOLD, 15));
		controlpanel.add(exitButton);
		exitButton.addActionListener(ae ->
		{
			try {
				studentregutilitesObj.closeDBConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainFrame.dispose();

		});
		exitButton.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent evt)
			{
				exitButton.setBackground((new Color(204,229,255)));
			}
			public void mouseExited(MouseEvent evt)
			{
				exitButton.setBackground(UIManager.getColor("control"));
			}
		});

		box.add(controlpanel);
		box.add(Box.createVerticalStrut(10));

		mainFrame.add(box, BorderLayout.CENTER);
		mainFrame.setSize(560, 550);

		mainFrame.setVisible(true);

	}

	
	// main function 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Registration reg= new Registration();
		
		

	}

}
