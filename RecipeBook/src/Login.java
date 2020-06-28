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
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Error label
		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		ErrorLabel.setForeground(new Color(255, 0, 0));
		ErrorLabel.setBounds(117, 72, 209, 20);
		frame.getContentPane().add(ErrorLabel);
		
		submitButton = new JButton("Submit");
		submitButton.setForeground(new Color(0, 0, 0));
		submitButton.setBackground(new Color(245, 255, 250));
		submitButton.setBounds(161, 165, 89, 23);
		
		userTextField = new JTextField();
		userTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ErrorLabel.setText("");
			}
		});
		userTextField.setBounds(164, 103, 86, 20);
		userTextField.setColumns(10);
		
		userTextField.requestFocusInWindow();
		
		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ErrorLabel.setText("");
			}
		});
		passwordField.setBounds(164, 134, 86, 20);
		
		
		
		
		
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
		            	ErrorLabel.setText("Username or Password incorrect");
		            	//System.out.println("Username or Password incorrect");
		            
		        }
		        // Handle any errors that may have occurred.
		        catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
		});
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(84, 106, 70, 14);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(84, 137, 70, 14);
		
		resetButton = new JButton("Reset");
		resetButton.setForeground(new Color(0, 0, 0));
		resetButton.setBackground(new Color(245, 255, 250));
		resetButton.setBounds(161, 199, 89, 23);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userTextField.setText("");
				passwordField.setText("");
				ErrorLabel.setText("");
			}
		});
		
		createButton = new JButton("Create New User");
		createButton.setForeground(SystemColor.desktop);
		createButton.setBackground(new Color(245, 255, 250));
		createButton.setBounds(132, 25, 151, 31);
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				NewUser.main(new String[0]);
    			frame.setVisible(false);
				
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(userTextField);
		frame.getContentPane().add(createButton);
		frame.getContentPane().add(lblUser);
		
		frame.getContentPane().add(lblPassword);
		frame.getContentPane().add(passwordField);
		frame.getContentPane().add(submitButton);
		frame.getContentPane().add(resetButton);
		
		
		
		frame.setLocationRelativeTo(null);
	}//end initialize
}
