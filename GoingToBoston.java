/*
Java™ Project: ICS4U
Package: boston
Class: GoingToBoston
Programmer: Shaan Banday.

Date Created: Tuesday, 8th February, 2021.
Date Completed: Thursday, 10th February, 2021. 

Description: Going to Boston is a dice game between two people. The number of rolls depends on the number of die available. In this 
Java™ version, the user plays against a computer, and there are 3 die, meaning only 3 rolls. The user will go first, rolling all three 
die, and keeping the highest one. The user then rolls again with the two die left, and again, keeps the highest one. Finally, the user 
rolls the last die and keeps whatever it lands on. The user's score for that round is the sum of the rolls. After the user rolls, the 
computer rolls with the exact same rules as the user. If at any point, more than one die roll on the same number, then one of the die 
is chosen to be kept, and the round continues. At the end of the round, whoever has the higher sum wins. The user has the option to 
play as many rounds as they want, and the score for each individual round is kept. At the end of the game, the user can choose to 
leave the game, and the console will display whether they won or lost.
*/
package boston; //Launch the class from this package named "boston"

//Import the Scanner Method. 
import java.util.Scanner; //This will be necessary for taking user inputs to proceed with the game.

public class GoingToBoston //The name of the class.
{ 
	
	public static void main(String[] args) throws InterruptedException //My main method throws an Interrupted Exception for a delay
	{
		//Declare and Initialize the Scanner
		Scanner takeInput = new Scanner(System.in); //This scanner is universal, and will take all types of user input.
		
		//Declare logic variables
		boolean gameOver = false; //Boolean to display state of game. Set it to "false" since the game is not over.
		boolean exitPrompt; //Boolean to display whether the user will exit the "Do you want to play again" prompt.
		boolean whosTurn; //Boolean to display who's turn it is. False is user's turn, true is computers turn. This will make sense later
		
		//Declare integer variables
		int userTurn; //Integer to show what turn the user is on. Can only be 1, 2, or 3. This resets after every round.
		int computerTurn; //Integer to show what turn the computer is on. Can only be 1, 2, or 3 and also resets every round.
		int userScore = 0; //The user's score (number of round won). At the start, it is initialized to 0.
		int computerScore = 0; //The computer's score (number of round won). At the start, it is initialized to 0.
		int userSum = 0; //The sum of the dice for the user. This updates after each roll and is initialized to 0.
		int computerSum = 0; //The sum of the dice for the computer. This updates after each roll and is initialized to 0.
		
		//Declare word-based variables
		String userPrompt; //The string taken from the user to determine whether to terminate the game.
		char yesOrNo; //That same string, but converted to a character. Meant to simplify later if-statements 
		
		//Declare delay constant
		final short DELAY_2 = 5000; //Delay between computer rolls. Gives enough time for user to read previous information.
		
		//Output to the user to introduce them to the game.
		System.out.println("Hello! You will be playing a game called Going to Boston against a computer. "
				+ "Press ENTER to play."); //Prompt user to press the ENTER key to start the game
		takeInput.nextLine(); //Take any input, and ENTER KEY.

		//Whole game is coded inside a while loop.
		while (!gameOver) //Keep running the loop, as long as the game is not over
		{
			//Initialize all previous integer variables
			userTurn = 1; //Set the turn-number for the user to 1. Every time a new round is started, this resets.
			computerTurn = 1; //Set the turn-number for the computer to 1. Every time a new round is started, this also resets.
			userSum = 0; //Set the sum of the user's die to 0. This resets every round.
			computerSum = 0; //Set the sum of the computer's die to 0. Again, this resets every round.
			
			//Initialize all previous boolean variables
			exitPrompt = false; //Set exitPrompt to false, so the user will be given the prompt to play again.
			whosTurn = false; //Set whosTurn to false, which means the user starts
			
			//User goes first
			while (userTurn <= 3) //Run the loop 3 times
			{
				System.out.println("\nPress ENTER for roll number " + userTurn + "."); //Prompt user to press ENTER and begin the roll
				takeInput.nextLine(); //Take any input, and ENTER key.
				
				userSum += executeTurn(userTurn, whosTurn); //Call an execution method by passing it the turn-number. Whatever is returned is added to the user's sum
				userTurn += 1; //After the roll is complete, add 1 to userTurn, to move on to the next roll.
			}
			
			//Message after user's three rolls
			System.out.println("Your score for this round was " + userSum + "."); //Display the user's score for this round.
			System.out.println("\n\nIt is now the computers turn. Press ENTER to proceed."); //Prompt the user to prepare for the computer to now go
			takeInput.nextLine(); //Take any input, and ENTER key.
			
			whosTurn = true;
			
			//Computer goes second
			while (computerTurn <=3) //Run the loop 3 times
			{
				System.out.println("\nThe computer will go for roll " + computerTurn + "."); //Tell the user that the computer is about to roll
				
				computerSum += executeTurn(computerTurn, whosTurn); //Call a similar execution method by passing it the turn-number. Return the highest roll and add it to the computer's sum
				computerTurn += 1; //After the roll is complete, add 1 to computerTurn, to move on to the next roll.
				Thread.sleep(DELAY_2); //5-second long delay between the rounds. Gives enough time for user to read previous information
			}
			
			//Message after computer's turn
			System.out.println("The computer's score for this round was " + computerSum + "."); //Display the computer's score for this round
			
			//Decisions
			if (userSum == computerSum) //If the sums are the same
			{
				System.out.println("\nYou scored the same as the computer. Therefore it was a draw."); //Display message for draw
			}
			else if (userSum > computerSum) //If the user's sum was greater than the computer sum
			{
				System.out.println("\nWINNER, WINNER, CHICKEN DINNER! You beat the computer!"); //Display message for user winning
				userScore += 1; //Increase the overall score for the the user by one
			}
			else //Otherwise, if the computer is winning
			{
				System.out.println("\nSorry! The computer has won. :-("); //Display message for computer winning
				computerScore +=1; //Increase the overall score by one
			}
			
			//Call a void method (command), which displays the overall scores.
			displayScore(userScore, computerScore); //Arguments which are passed are whatever the scores are
			
			//After everything, ask user if they want to play again. Keep the user in this while loop for an incorrect input
			while (!exitPrompt) //Run the loop until the user exits the prompt
			{
				try 
				{
					System.out.println("\nWould you like to go again? (Y/N):"); //Ask user if they would like to play again
					userPrompt = takeInput.nextLine(); //Take their input, and assign it to a string.
					yesOrNo = userPrompt.toUpperCase().charAt(0); //Set the character yesOrNo to the first index of the upper case of string, in case the user inputs 'yes' or 'no'
					
					//Decisions
					if (yesOrNo == 'Y') //If the user inputs 'Y' — meaning they want to play again
					{
						System.out.println("\nOkay, a new round will start shortly, "
								+ "but your overall scores for each round will be kept."); //Tell user that a new round will begin shortly 
						
						Thread.sleep(DELAY_2); //Large delay before formally resetting the round, which prepares the user
						exitPrompt = true; //Set exitPrompt to true, so the user can exit this prompt
					}
					else if (yesOrNo == 'N') //If the user inputs 'N' — meaning they do not want to play again
					{
						exitPrompt = true; //Set exitPrompt to true, so the user can exit this prompt
						gameOver = true; //Set gameOver to true, which will exit the larger while loop and terminate the game
					}
					else if (userPrompt.equals(""))
					{
						//do nothing
					}
					else //Otherwise, if the user inputs something else
					{
						System.out.println("\nSorry, you inputted something wrong. Let's try again."); //Keep them in the while loop
					}
				}
				catch (Exception e){
					System.out.println("At some point throughout the rolls, you pressed the ENTER key when you should't have. No worries");
				}
			}
			takeInput.close();
		}
		
		//Close the scanner
		
		//Display game over message and final scores
		System.out.println("The game is now over. The final scores are " + userScore + " for you and " 
				+ computerScore + " for the computer."); //Show the user's score compared to the computer's score
		
		//Decisions
		if (userScore == computerScore) //If the overall scores was the same
		{
			System.out.println("Therefore, it was an overall draw."); //Display that overall, it was a draw
		}
		else if (userScore > computerScore) //If the user's overall score was higher than the computer's
		{
			System.out.println("Therefore, you won."); //Display that overall, the user won
		}
		else //Otherwise, if the computer's overall score was higher than the user's
		{
			System.out.println("Therefore, the computer won."); //Display that overall, the computer won
		}
		
		//End message
		System.out.println("\nThank you for playing! Goodbye."); //Thank the user for playing
		
		//Program Terminated
	}
	
	private static int executeTurn(int d, boolean checkTurn) 
	{
		/*
		 * Private method for all the logic of a roll. The parameter it takes is the turn-number (1, 2, or 3) and whether the computer
		 * or user is going. The turn number determines the number of rolls and number of die passed to other methods, and the boolean
		 * determines the message displayed to the user. 
		 */
		
		//Initialize all variables
		int die1; //Integer representing the value of the first die
		int die2; //Integer representing the value of the second die
		int die3; //Integer representing the value of the third die
		int maximum; //Integer representing the value of the maximum of the three die
		
		//Initialize delay constant
		final short DELAY_1 = 1500; //Delay while rolling. Mimics the fact that people shake the die in their hand before releasing.
		
		//Decisions
		if (d == 1) //If my main method passes it 1, which means the user or computer is on roll one
		{
			System.out.println("\nRolling... please wait."); //Message to simulate that it takes some time to roll
			
			//try and catch statement
			try //Try to implement a delay
			{
				Thread.sleep(DELAY_1); //Delay for 1 and a half seconds for the roll of the die.
			}
			catch (InterruptedException e){	//catch any exception
			}
			
			//Roll first die
			die1 = rollDie(); //Call the private method roleRie and whatever value it returns, assign that to die1
			drawDie (die1); //Call the private method drawDie and draw whatever the roll was
			
			//Roll second die
			die2 = rollDie(); //Call the private method roleRie and whatever value it returns, assign that to die2
			drawDie (die2); //Call the private method drawDie and draw whatever the roll was
			
			//Roll second die
			die3 = rollDie(); //Call the private method roleRie and whatever value it returns, assign that to die3
			drawDie (die3); //Call the private method drawDie and draw whatever the roll was
				
			maximum = findMax(die1, die2, die3); //Call the private method findMax and pass it the value of all three rolls to find the highest
			
			//Nested Decisions
			if (!checkTurn) //If the user was going, there is a specific message
			{
				System.out.println("You rolled a " + die1 + ", " + die2 + ", and " + die3 + "."); //Display the values of the three rolls
				System.out.println("Since " + maximum + " is the highest number out of the three, that is what you will keep."); //Tell user what they are going to keep
			}
			else //If the computer was going, there is a specific message 
			{
				System.out.println("The computer rolled a " + die1 + ", " + die2 + ", and " + die3 + "."); //Display the values of the three rolls
				System.out.println("Since " + maximum + " is the highest number out of the three, that is what the computer will keep."); //Tell the user what the computer will keep
			}
			
			return maximum; //Return the maximum to the main method, so it can add it to the user's or computer's sum
		}	
		
		else if (d == 2) //If my main method passes it 2, which means the user or computer is on roll two
		{
			System.out.println("\nRolling... please wait."); //Message to simulate that it takes some time to roll
			
			//try and catch statement
			try //Try to implement a delay
			{
				Thread.sleep(DELAY_1); //Delay for 1 and a half seconds
			}
			catch (InterruptedException e){	//catch any exception
			}
			
			//First die has already been rolled
			
			//Roll second die
			die2 = rollDie(); //Call the private method roleRie and whatever value it returns, assign that to die2
			drawDie (die2); //Call the private method drawDie and draw whatever the roll was
			
			//Roll third die
			die3 = rollDie(); //Call the private method roleRie and whatever value it returns, assign that to die3
			drawDie (die3); //Call the private method drawDie and draw whatever the roll was
				
			maximum = findMax(0, die2, die3); //Call the private method findMax and pass it the value of all three rolls to find the highest
		
			//Nested Decisions
			if (!checkTurn) //If the user was going, there is a specific message
			{
				System.out.println("You rolled a " + die2 + " and " + die3 + "."); //Display the values of the two rolls
				System.out.println("Since " + maximum + " is the highest number out of the two, that is what you will keep."); //Tell user what they are going to keep
			}
			else //If the computer was going, there is a specific message 
			{
				System.out.println("The computer rolled a " + die2 + " and " + die3 + "."); //Display the values of the two rolls
				System.out.println("Since " + maximum + " is the highest number out of the two, that is what the computer will keep."); //Tell the user what the computer will keep
			}
			
			return maximum; //Return the maximum of the two to the main method, so it can add it to the user's sum
		}
		
		else //Otherwise, my main method passes it 3, which means the user or computer is on the last roll
		{
			System.out.println("\nRolling... please wait."); //Message to simulate that it takes some time to roll
			
			//try and catch statement
			try //Try to implement a delay
			{
				Thread.sleep(DELAY_1); //Delay for 1 and a half seconds
			}
			catch (InterruptedException e){	//catch any exception
			}
			
			//First and Second die has already been rolled
			
			//Roll third die
			die3 = rollDie(); //Call the private method roleRie and whatever value it returns, assign that to die3
			drawDie (die3); //Call the private method drawDie and draw whatever the roll was
			
			//Nested Decisions
			if (!checkTurn) //If the user was going, there is a specific message
			{
				System.out.println("You rolled a " + die3 + "."); //Display the values of the last roll
				System.out.println("Since this was your last turn, " + die3 + " is what you will keep."); //Tell user they are going to keep whatever the roll was
			}
			else //If the computer was going, there is a specific message 
			{
				System.out.println("The computer rolled a " + die3 + "."); //Display the value of the last roll
				System.out.println("Since this was the computer's last turn, " + die3 + " is what it will keep."); //Tell the user that this is what the computer will keep
			}
			
			return die3; //Return the die3 to the main method, so it can add it to the user's sum. Maximum does not need to be found
		}
	}
	
	private static void displayScore(int scrUser, int scrComp) //Private void method (command), which displays the overall score
	{
		/*
		 * This method takes the parameter of the user's and computer's overall score and compares them. It is invoked by the main 
		 * method. It outputs to the console but, it does not return any value back to the main method. Therefore, this method would
		 * be called a command in other programming languages.
		 */
		
		//Decisions 
		if (scrUser == scrComp) //If the user's current score is the same as the computer's
		{
			System.out.println("\nThe score is " + scrUser + " for you and " + scrComp + " for the computer. "
					+ "\nRight now, you are tied with the computer."); //Tell the user of that they are currently tied with the computer
		}
		
		else if (scrUser > scrComp) //If the user's current score is more the computer's
		{
			System.out.println("\nThe score is " + scrUser + " for you and " + scrComp + " for the computer. "
					+ "\nRight now, you are winning."); //Tell the user they are currently winning
		}
		
		else //Otherwise, if the computer's current score is more than the user's
		{
			System.out.println("\nThe score is " + scrUser + " for you and " + scrComp + " for the computer. "
					+ "\nRight now, you are losing."); //Tell the user they are currently using
		}
	}
	
	private static int rollDie() //Private method which is invoked for every single dice roll. When there are 3 die, it is invoked 3 times, etc. 
	{
		return (int) ((Math.random()*6) + 1); //Return a random generated number between 1 and 6, to represent the 6 sides of a die
	}

	private static void drawDie(int i) //Private void method (command) takes the number (1, 2, 3, 4, 5, or 6) and prints it to the console
	{
		//try and catch statement
		try //Try to implement a delay between the drawing of all the dice
		{
			Thread.sleep(450); //This delay must be very short (0.45 seconds) so as to not slow the program so much, but to also give the user separation from the dice
		}
		catch (InterruptedException e){	//catch any exception
		}
		
		//Switch Statement 
		switch (i) //Depending on the parameter for i passed by the main method, different things may be drawn
		{
		case 1: //If i is 1
			System.out.println("  _________\n |         |\n |         |\n |    o    |\n |         |\n |_________|\n"); //Draw the picture of a die with the face of 1
			break; //Break this case, so 1 only prints what is above
			
		case 2: //If i is 2
			System.out.println("  _________\n |         |\n |       o |\n |         |\n | o       |\n |_________|\n"); //Draw the picture of a die with the face of 2
			break;//Break this case, so 2 only prints what is above
			
		case 3: //If i is 3
			System.out.println("  _________\n |         |\n |       o |\n |    o    |\n | o       |\n |_________|\n"); //Draw the picture of a die with the face of 3
			break; //Break this case, so 3 only prints what is above
			
		case 4: //If i is 4
			System.out.println("  _________\n |         |\n | o     o |\n |         |\n | o     o |\n |_________|\n"); //Draw the picture of a die with the face of 4
			break; //Break this case, so 4 only prints what is above
			
		case 5: //If i is 5
			System.out.println("  _________\n |         |\n | o     o |\n |    o    |\n | o     o |\n |_________|\n"); //Draw the picture of a die with the face of 5
			break; //Break this case, so 5 only prints what is above
			
		case 6: //If i is 6
			System.out.println("  _________\n |         |\n | o     o |\n | o     o |\n | o     o |\n |_________|\n"); //Draw the picture of a die with the face of 6
			break; //Break this case, so 6 only prints what is above, and nothing below will be affected
		}
	}
	
	private static int findMax(int a, int b, int c) //Private method which takes three numbers sent by the execution method, and finds the maximum
	{
		return Math.max(Math.max(a, b), c); //Find the maximum of the first roll and the second roll. Then find the maximum between that and the third roll, and return
	}
} //End of class