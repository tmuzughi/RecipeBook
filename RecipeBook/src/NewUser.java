import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewUser {

	private JFrame frame;
	private JTextField userTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUser window = new NewUser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		ErrorLabel.setForeground(new Color(255, 0, 0));
		ErrorLabel.setBounds(160, 31, 124, 14);
		frame.getContentPane().add(ErrorLabel);
		
		userTextField = new JTextField();
		userTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ErrorLabel.setText("");
			}
		});
		userTextField.setBounds(168, 80, 86, 20);
		frame.getContentPane().add(userTextField);
		userTextField.setColumns(10);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setBackground(new Color(245, 255, 250));
		
		frame.setLocationRelativeTo(null);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String user;
				user = userTextField.getText();										
				
				String password;
				password = passwordField.getText();
				
				int userID = 0;
				
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
		            	userID = rs.getInt("Id");
		            	String checkUser = rs.getString("userName");
		            	if (checkUser.contentEquals(user))
		            		verified = true;
		            	
		            }//end while
		            if (verified)
		            	ErrorLabel.setText("User name taken");
		            else {
		            	//do stuff
		            	try (Connection con2 = DriverManager.getConnection(connectionUrl); Statement stmt2 = con.createStatement();) {
		            		
				            String SQL2 = "insert into userLogin\r\n" + 
				            		"(userName, userPass)\r\n" + 
				            		"values('" + user + "', '" + password + "');";
				            try(ResultSet rs2 = stmt.executeQuery(SQL2);){
				            //also do nothing
				            	//userID = rs2.getInt("Id");
				            	
				            } catch (SQLException ex3) {
				            	//do nothing
				            }
				            
				            //create user table
				            userID++;
				            try (Connection con4 = DriverManager.getConnection(connectionUrl); Statement stmt4 = con.createStatement();) {
			            		
					            String SQL4 = "create table user" + userID + "Recipes\r\n" + 
					            		"( Id int Primary Key identity(1,1),\r\n" + 
					            		"RecipeName varchar(50),\r\n" + 
					            		"Category varchar(50),\r\n" + 
					            		"Ingredients varchar(1000),\r\n" + 
					            		"RecipeTime int,\r\n" + 
					            		"Difficulty varchar(50),\r\n" + 
					            		"Summary varchar(1000),\r\n" + 
					            		"Steps varchar(1000),\r\n" + 
					            		"ImgPath varchar(300)\r\n" + 
					            		");";
					            try(ResultSet rs4 = stmt.executeQuery(SQL4);){
					            //also do nothing
					            } catch (SQLException ex5) {
					            	//do nothing
					            }
					            //go back to login
					            //Login.main(new String[0]);
				    			//frame.setVisible(false);
			            	}//end inner inner try
					        // Handle any errors that may have occurred.
					        catch (SQLException ex) {
					            ex.printStackTrace();
					        }//end inner inner catch
				            
				            
				            //go back to login
				            Login.main(new String[0]);
			    			frame.setVisible(false);
		            	}
				        // Handle any errors that may have occurred.
				        catch (SQLException ex) {
				            ex.printStackTrace();
				        }
		            }//end else
		            
		        }
		        // Handle any errors that may have occurred.
		        catch (SQLException ex) {
		            ex.printStackTrace();
		        }
				
			}
		});//end action listener
		
		submitButton.setBounds(165, 163, 89, 23);
		frame.getContentPane().add(submitButton);
		
		JButton resetButton = new JButton("Reset");
		resetButton.setBackground(new Color(245, 255, 250));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userTextField.setText("");
				passwordField.setText("");
				ErrorLabel.setText("");
			}
		});
		resetButton.setBounds(165, 197, 89, 23);
		frame.getContentPane().add(resetButton);
		
		JLabel lblEnterUserName = new JLabel("Enter User Name");
		lblEnterUserName.setBounds(167, 61, 112, 14);
		frame.getContentPane().add(lblEnterUserName);
		
		JLabel lblEnterPassword = new JLabel("Enter Password");
		lblEnterPassword.setBounds(168, 111, 112, 14);
		frame.getContentPane().add(lblEnterPassword);
		
		JButton backButton = new JButton("Back");
		backButton.setBackground(new Color(245, 255, 250));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Login.main(new String[0]);
    			frame.setVisible(false);
				
			}
		});
		backButton.setBounds(332, 27, 64, 23);
		frame.getContentPane().add(backButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(168, 132, 86, 20);
		frame.getContentPane().add(passwordField);
		
		
	}
}
