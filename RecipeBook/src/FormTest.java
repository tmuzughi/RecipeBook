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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class FormTest{

	private JFrame frame;
	private JTextField Difficulty;
	private JTextField RecipeTime;

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
					FormTest window = new FormTest(number);
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
	public FormTest(int x) {
		initialize(x);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int x) {
		//System.out.println(x);
		String[] recipes = new String[100];
		String[] difficulties = new String[100];
		String[] times = new String[100];
		String[] summaries = new String[100];
		String[] ingredientsArray = new String[100];
		String[] steps = new String[100];
		int[] recipeLength = new int[1];
		
		// Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=RecipeBook;integratedSecurity=true";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "select * from user" + x + "Recipes;";
            
            //insert into Recipes(RecipeName, Category, Difficulty, RecipeTime)\r\n" + 
    		//"values('Blob2', 'Restaurant', 'Medium', 5);
            ResultSet rs = stmt.executeQuery(SQL);

            boolean verified = false;
            int i = 0;
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
            	//insert all recipe names into array
            	String currentRecipe = rs.getString("RecipeName");
            	recipes[i] = currentRecipe;
            	String currentDifficulty = rs.getString("Difficulty");
            	difficulties[i] = currentDifficulty;
            	String currentTime = rs.getString("RecipeTime");
            	times[i] = currentTime;
            	String currentSummary = rs.getString("Summary");
            	summaries[i] = currentSummary;
            	String currentIngredients = rs.getString("Ingredients");
            	ingredientsArray[i] = currentIngredients;
            	String currentSteps= rs.getString("Steps");
            	steps[i] = currentSteps;
            	i++;
            	
            }//end while
          recipeLength[0] = i;
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//RECIPE NAME
		JTextArea recipeName = new JTextArea();
		recipeName.setText(recipes[0]);
		recipeName.setEditable(false);
		recipeName.setBounds(154, 11, 129, 22);
		frame.getContentPane().add(recipeName);
		
		
		
		//SUMMARY
		JTextArea summary = new JTextArea();
		summary.setLineWrap(true);
		summary.setText(summaries[0]);
		summary.setBounds(37, 64, 214, 111);
		frame.getContentPane().add(summary);
		
		//INSTRUCTIONS
		JTextArea instructions = new JTextArea();
		instructions.setLineWrap(true);
		instructions.setText(steps[0]);
		instructions.setBounds(270, 134, 129, 80);
		frame.getContentPane().add(instructions);
		
		//INGREDIENTS
		JTextArea ingredients = new JTextArea();
		ingredients.setLineWrap(true);
		ingredients.setText(ingredientsArray[0]);
		ingredients.setBounds(261, 64, 129, 58);
		frame.getContentPane().add(ingredients);
		
		JButton newRecipeButton = new JButton("New Recipe");
		newRecipeButton.setBounds(280, 225, 119, 23);
		frame.getContentPane().add(newRecipeButton);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(new String[0]);
    			frame.setVisible(false);
			}
		});
		logoutButton.setBounds(10, 225, 89, 23);
		frame.getContentPane().add(logoutButton);
		
		//DIFFICULTY
		Difficulty = new JTextField();
		Difficulty.setText(difficulties[0]);
		Difficulty.setBounds(37, 196, 86, 20);
		frame.getContentPane().add(Difficulty);
		Difficulty.setColumns(10);
		
		//TIME
		RecipeTime = new JTextField();
		RecipeTime.setText(times[0]);
		RecipeTime.setBounds(165, 196, 86, 20);
		frame.getContentPane().add(RecipeTime);
		RecipeTime.setColumns(10);
		
		JLabel DifficultyLabel = new JLabel("Difficulty");
		DifficultyLabel.setBounds(55, 180, 67, 14);
		frame.getContentPane().add(DifficultyLabel);
		
		JLabel TimeLabel = new JLabel("Time");
		TimeLabel.setBounds(192, 180, 51, 14);
		frame.getContentPane().add(TimeLabel);
		
		//NEXT BUTTON
				JButton nextButton = new JButton("Next");
				nextButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int counter = 0;
						for (int i = 0; i < recipeLength[0]; i++) {
							if (recipes[i].equals(recipeName.getText())) {
								counter = (i + 1);
								break;
							}
						}
						if (counter != recipeLength[0])
						recipeName.setText(recipes[counter]);
						summary.setText(summaries[counter]);
						instructions.setText(steps[counter]);
						ingredients.setText(ingredientsArray[counter]);
						Difficulty.setText(difficulties[counter]);
						RecipeTime.setText(times[counter]);
						
					}
				});
				nextButton.setBounds(310, 12, 89, 23);
				frame.getContentPane().add(nextButton);
				
				//BACK BUTTON
				JButton backButton = new JButton("Back");
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int counter = recipeLength[0];
						for (int i = counter - 1; i > 0; i--) {
							if (recipes[i].equals(recipeName.getText())) {
								counter = (i - 1);
								break;
							}
						}
						if (counter < 0 || counter == recipeLength[0])
							counter = 0;
						//if (counter >= 0)
						//System.out.println(counter);
						recipeName.setText(recipes[counter]);
						summary.setText(summaries[counter]);
						instructions.setText(steps[counter]);
						ingredients.setText(ingredientsArray[counter]);
						Difficulty.setText(difficulties[counter]);
						RecipeTime.setText(times[counter]);
					}
				});
				backButton.setBounds(37, 12, 89, 23);
				frame.getContentPane().add(backButton);
	}
}
