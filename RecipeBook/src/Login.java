import java.awt.EventQueue;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class Login {

	private JFrame frame;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JButton submitButton;
	private JButton resetButton;
	private JButton createButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//end main

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userTextField = new JTextField();
		userTextField.setBounds(164, 103, 86, 20);
		frame.getContentPane().add(userTextField);
		userTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(164, 134, 86, 20);
		frame.getContentPane().add(passwordField);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				String user;
				user = userTextField.getText();
				//System.out.println(user);
				
				
				
				String password;
				password = passwordField.getText();
				//says getText() is deprecated for JPasswordField but seems to work
				//System.out.println(password);
				
				int userID;
				
				
				
				// Create a variable for the connection string.
		        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=RecipeBook;integratedSecurity=true";

		        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
		            String SQL = "select * from userLogin;";
		            
		            //insert into Recipes(RecipeName, Category, Difficulty, RecipeTime)\r\n" + 
            		//"values('Blob2', 'Restaurant', 'Medium', 5);
		            ResultSet rs = stmt.executeQuery(SQL);

		            boolean verified = false;
		            // Iterate through the data in the result set and display it.
		            while (rs.next()) {
		            	String checkUser = rs.getString("userName");
		            	String checkPass = rs.getString("userPass");
		            	
		            	//if (check.equals("user1"))
		                //System.out.println(rs.getString("userName") + " " + rs.getString("userPass"));
		            	
		            	if (checkUser.equals(user)) {
		            		if (checkPass.equals(password)) {
			            		//we have verified that the entered user matches the entered password
		            			verified = true;
		            			System.out.println("Login info verified.");
		            			userID = rs.getInt("Id");
		            			FormTest.main(new String[] {Integer.toString(userID)});
		            			frame.setVisible(false);
		            			//FormTest jfrm2= new FormTest();
		                        //jfrm2.setSize(270, 160); 
		                        //jfrm2.setVisible(true);
		                        
			            	}
		            	}
		            	
		            }//end while
		            if (!verified)
		            	System.out.println("Username or Password incorrect");
		            
		        }
		        // Handle any errors that may have occurred.
		        catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
		});//end action listener
		
		
		
		submitButton.setBounds(161, 165, 89, 23);
		frame.getContentPane().add(submitButton);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(84, 106, 70, 14);
		frame.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(84, 137, 70, 14);
		frame.getContentPane().add(lblPassword);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userTextField.setText("");
				passwordField.setText("");
				
			}
		});
		resetButton.setBounds(161, 199, 89, 23);
		frame.getContentPane().add(resetButton);
		
		createButton = new JButton("Create New User");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				NewUser.main(new String[0]);
    			frame.setVisible(false);
				
			}
		});
		createButton.setBounds(132, 25, 151, 31);
		frame.getContentPane().add(createButton);
	}//end initialize
}
