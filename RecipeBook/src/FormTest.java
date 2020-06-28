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
import java.awt.SystemColor;
import java.awt.Color;

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
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
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
		newRecipeButton.setBackground(new Color(245, 255, 250));
		newRecipeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewRecipe.main(new String[] {Integer.toString(x)});
    			frame.setVisible(false);
			}
		});
		newRecipeButton.setBounds(323, 225, 101, 23);
		frame.getContentPane().add(newRecipeButton);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(new Color(245, 255, 250));
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
				nextButton.setBackground(new Color(245, 255, 250));
				nextButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int counter = 0;
						for (int i = 0; i < recipeLength[0]; i++) {
							if (recipes[i].equals(recipeName.getText())) {
								counter = (i + 1);
								break;
							}
						}
						if (counter != recipeLength[0]) {
						recipeName.setText(recipes[counter]);
						summary.setText(summaries[counter]);
						instructions.setText(steps[counter]);
						ingredients.setText(ingredientsArray[counter]);
						Difficulty.setText(difficulties[counter]);
						RecipeTime.setText(times[counter]);
						}
					}
				});
				nextButton.setBounds(310, 12, 89, 23);
				frame.getContentPane().add(nextButton);
				
				//BACK BUTTON
				JButton backButton = new JButton("Back");
				backButton.setBackground(new Color(245, 255, 250));
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
				
				//DELETE BUTTON
				JButton DeleteButton = new JButton("Delete");
				DeleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//DELETE
						try (Connection con2 = DriverManager.getConnection(connectionUrl); Statement stmt2 = con2.createStatement();) {
				            String SQL2 = "delete from user" + x + "Recipes "
				            		+ "where RecipeName='" + recipeName.getText() + "';";
				            
				            //insert into Recipes(RecipeName, Category, Difficulty, RecipeTime)\r\n" + 
				    		//"values('Blob2', 'Restaurant', 'Medium', 5);
				            try (ResultSet rs1 = stmt2.executeQuery(SQL2);){
				            	//do nothing
				            } catch (SQLException ex2) {
				            	//do more nothing
				            }
				            //refresh page
				            FormTest.main(new String[] {Integer.toString(x)});
	            			frame.setVisible(false);
				        }
				        // Handle any errors that may have occurred.
				        catch (SQLException ex1) {
				            ex1.printStackTrace();
				        }
					}
				});
				DeleteButton.setForeground(new Color(245, 255, 250));
				DeleteButton.setBackground(new Color(128, 0, 0));
				DeleteButton.setBounds(119, 225, 89, 23);
				frame.getContentPane().add(DeleteButton);
				
				JButton EditButton = new JButton("Edit");
				EditButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//EDIT
						try (Connection con2 = DriverManager.getConnection(connectionUrl); Statement stmt2 = con2.createStatement();) {
				            String SQL2 = "update user" + x + "Recipes "
				            		+ "set Ingredients='" + ingredients.getText() + "', "
				            		+ "RecipeTime='" + RecipeTime.getText() + "', "
				            		+ "Difficulty='" + Difficulty.getText() + "', "
				            		+ "Summary='" + summary.getText() + "', "
				            		+ "Steps='" + instructions.getText() + "' "
				            		+ "where RecipeName='" + recipeName.getText() + "';";
				            
				            //insert into Recipes(RecipeName, Category, Difficulty, RecipeTime)\r\n" + 
				    		//"values('Blob2', 'Restaurant', 'Medium', 5);
				            try (ResultSet rs1 = stmt2.executeQuery(SQL2);){
				            	//do nothing
				            } catch (SQLException ex2) {
				            	//do more nothing
				            }
				            //refresh page
				            FormTest.main(new String[] {Integer.toString(x)});
	            			frame.setVisible(false);
				        }
				        // Handle any errors that may have occurred.
				        catch (SQLException ex1) {
				            ex1.printStackTrace();
				        }
					}
				});
				EditButton.setBackground(new Color(245, 255, 250));
				EditButton.setBounds(233, 225, 67, 23);
				frame.getContentPane().add(EditButton);
				frame.setLocationRelativeTo(null);
				
	}
}
