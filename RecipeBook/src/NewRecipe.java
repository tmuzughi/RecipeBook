import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class NewRecipe {

	private JFrame frame;
	private JTextField RecipeNameBox;
	private JTextField DifficultyBox;
	private JTextField TimeBox;
	private JButton BackButton;
	private JButton SubmitButton;
	private JButton ResetButton;
	private JTextArea IngredientsBox;
	private JTextArea DescriptionBox;
	private JTextArea InstructionsBox;
	private JLabel IngredientsLabel;
	private JLabel InstructionsLabel;
	private JLabel SummaryLabel;
	private JTextField CategoryBox;
	private JLabel CategoryLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		int count = args.length;
		int number;
		if (count > 0)
			number = Integer.parseInt(args[0]);
		else
			number = 1;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewRecipe window = new NewRecipe(number);
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
	public NewRecipe(int x) {
		initialize(x);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int x) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		RecipeNameBox = new JTextField();
		RecipeNameBox.setBounds(23, 25, 86, 20);
		frame.getContentPane().add(RecipeNameBox);
		RecipeNameBox.setColumns(10);
		
		JLabel RecipeLabel = new JLabel("Recipe Name");
		RecipeLabel.setBounds(25, 10, 86, 14);
		frame.getContentPane().add(RecipeLabel);
		
		DifficultyBox = new JTextField();
		DifficultyBox.setBounds(23, 66, 86, 20);
		frame.getContentPane().add(DifficultyBox);
		DifficultyBox.setColumns(10);
		
		TimeBox = new JTextField();
		TimeBox.setBounds(23, 110, 86, 20);
		frame.getContentPane().add(TimeBox);
		TimeBox.setColumns(10);
		
		JLabel DifficultyLabel = new JLabel("Difficulty");
		DifficultyLabel.setBounds(23, 51, 86, 14);
		frame.getContentPane().add(DifficultyLabel);
		
		JLabel TimeLabel = new JLabel("Time");
		TimeLabel.setBounds(23, 95, 86, 14);
		frame.getContentPane().add(TimeLabel);
		
		//BACK BUTTON
		BackButton = new JButton("Back");
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormTest.main(new String[] {Integer.toString(x)});
    			frame.setVisible(false);
			}
		});
		BackButton.setBounds(20, 227, 89, 23);
		frame.getContentPane().add(BackButton);
		
		IngredientsBox = new JTextArea();
		IngredientsBox.setBounds(138, 25, 275, 48);
		frame.getContentPane().add(IngredientsBox);
		
		DescriptionBox = new JTextArea();
		DescriptionBox.setBounds(138, 157, 275, 48);
		frame.getContentPane().add(DescriptionBox);
		
		InstructionsBox = new JTextArea();
		InstructionsBox.setBounds(138, 90, 275, 48);
		frame.getContentPane().add(InstructionsBox);
		
		IngredientsLabel = new JLabel("Ingredients");
		IngredientsLabel.setBounds(138, 10, 89, 14);
		frame.getContentPane().add(IngredientsLabel);
		
		InstructionsLabel = new JLabel("Instructions");
		InstructionsLabel.setBounds(138, 75, 76, 14);
		frame.getContentPane().add(InstructionsLabel);
		
		SummaryLabel = new JLabel("Description");
		SummaryLabel.setBounds(138, 142, 86, 14);
		frame.getContentPane().add(SummaryLabel);
		
		//RESET BUTTON
		ResetButton = new JButton("Reset");
		ResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecipeNameBox.setText("");
				DifficultyBox.setText("");
				TimeBox.setText("");
				DescriptionBox.setText("");
				InstructionsBox.setText("");
				IngredientsBox.setText("");
			}
		});
		ResetButton.setBounds(174, 227, 89, 23);
		frame.getContentPane().add(ResetButton);
		
		//SUBMIT BUTTON
		SubmitButton = new JButton("Submit");
		SubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check for recipe name in RecipeBook
				// Create a variable for the connection string.
		        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=RecipeBook;integratedSecurity=true";
		        
		        String recipeName = RecipeNameBox.getText();
		        String category = CategoryBox.getText();
		        String ingredients = IngredientsBox.getText();
		        String instructions = InstructionsBox.getText();
		        String description = DescriptionBox.getText();
		        String recipeTime = TimeBox.getText();
		        String difficulty = DifficultyBox.getText();
		        
		        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
		            String SQL = "select * from user" + x + "Recipes;";
		            
		            //insert into Recipes(RecipeName, Category, Difficulty, RecipeTime)\r\n" + 
            		//"values('Blob2', 'Restaurant', 'Medium', 5);
		            ResultSet rs = stmt.executeQuery(SQL);
		            
		            boolean verified = false;
		            // Iterate through the data in the result set and display it.
		            while (rs.next()) {		            
		            	
		            	String checkRN = rs.getString("RecipeName");
		            	if (checkRN.contentEquals(recipeName))
		            		verified = true;
		            	
		            }//end while
		            if (verified)
		            	System.out.println("Recipe already exists");
		            else {
		            	//do insert
		            		try (Connection con2 = DriverManager.getConnection(connectionUrl); Statement stmt2 = con.createStatement();) {
		            		
				            String SQL2 = "insert into user" + x + "Recipes "
				            		+ "(RecipeName, Category, Ingredients, RecipeTime, Difficulty, Summary, Steps) "
				            		+ "values ('" + recipeName + "', '" + category + "', '" + ingredients + "', '"
				            		+ recipeTime + "', '" + difficulty + "', '" + description + "', '" + instructions + "');";
				            try(ResultSet rs2 = stmt.executeQuery(SQL2);){
				            //also do nothing
				            	//userID = rs2.getInt("Id");
				            	
				            } catch (SQLException ex3) {
				            	//do nothing
				            }
		            	//head back to FormTest
				            FormTest.main(new String[] {Integer.toString(x)});
			    			frame.setVisible(false);
		            }
		          }
		        }
		        // Handle any errors that may have occurred.
		        catch (SQLException ex) {
		            ex.printStackTrace();
		        }
				
			}
		});
		SubmitButton.setBounds(324, 227, 89, 23);
		frame.getContentPane().add(SubmitButton);
		
		CategoryBox = new JTextField();
		CategoryBox.setBounds(23, 150, 86, 20);
		frame.getContentPane().add(CategoryBox);
		CategoryBox.setColumns(10);
		
		CategoryLabel = new JLabel("Category");
		CategoryLabel.setBounds(23, 135, 46, 14);
		frame.getContentPane().add(CategoryLabel);
	}

}
