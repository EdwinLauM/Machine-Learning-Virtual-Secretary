import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/***
 * Class that will check if the question is exact match or guess or non
 * @author edwin
 *
 */
public class Question {
	
	
	private String questionAsked;
	private String answer;
	
	private ArrayList<String> allQuestions;
	private ArrayList<Integer> ranks;
	private ArrayList<String> rankedQuestions;


	
	private boolean guess;
	private boolean exactMatch;
	
	/***
	 * Construcor that initializes all booleans to false, and strings to empty strings, an array list that will contain all the final ranked questions, and the quesiton asked by the user
	 * @param questionAsked by the user
	 */
	public Question(String questionAsked)
	{
		this.questionAsked = questionAsked;
		rankedQuestions = new ArrayList<String>();
		answer = "";
		exactMatch = false;
		guess = false;
	}

	/***
	 * Method that will check if the question is an exact match
	 * @throws FileNotFoundException if file not found
	 */
	public void checkExactMatch() throws FileNotFoundException
	{
		Scanner questionIn = new Scanner(new File("questions.txt"));
		String answerIndex = "";
		String line = "";

		while (questionIn.hasNextLine())
		{
			answerIndex = "" + questionIn.nextInt(); //reads the first integer of each line and converts it into a string
			line = questionIn.nextLine().trim().toLowerCase(); //reads the rest of the line as a string, trims it and makes it lower case
			
			if (line.equals(questionAsked)) //if the rest of the line is equal to the question inputted
			{
				findExactMatchAnswer(answerIndex); //call the method
				break; //exits the while loop because exact match has been found
			}
		}

		questionIn.close(); //closes the scanner
	}
	
	/***
	 * Method that will then find the exact match for his answer
	 * @param answerIndex the index of where the question was found
	 * @throws FileNotFoundException if file not found
	 */
	public void findExactMatchAnswer(String answerIndex) throws FileNotFoundException
	{
		Scanner answerIn = new Scanner(new File("answers.txt")); 
		
		String index = "";
		String currentLine = "";

		while(answerIn.hasNextLine())
		{
			index = "" + answerIn.nextInt(); //reads the first integer and converts it into a string 
			 currentLine = answerIn.nextLine(); //reads the rest of the line
			
			if (index.equals(answerIndex)) //if the current index is equal to the index of the question
			{
				answer = currentLine; //sets the answer to the current line
				exactMatch = true; // exact match has been found
				break;
			}
			else
				exactMatch = false;
		}
		answerIn.close();
	}
	
	/***
	 * Method that will find the keywords, and check for each question if they have them
	 * @throws FileNotFoundException if file not found
	 */
	public void GuessMatchFindKeyWords() throws FileNotFoundException
	{
		Scanner uselessIn = new Scanner(new File("useless_words.txt"));

		String[] tempQuestion = questionAsked.split(" "); //Splits the question asked into an array of type string
		ArrayList<String> finalKeyWords = new ArrayList<String>(); //Array List that will store only the key words
		ArrayList<String> uselessWords = new ArrayList<String>(); //Array List that will store all the useless words from a file

		while (uselessIn.hasNextLine()) 
		{
			String currentLine = uselessIn.nextLine(); //Loop that will store all the useless words from the useless_words file
			uselessWords.add(currentLine); //adds it to the array list
		}
		uselessIn.close();


		for (int i = 0; i < tempQuestion.length; i++)
		{
			for (int j = 0; j < uselessWords.size(); j++)
			{
				if (uselessWords.get(j).equals(tempQuestion[i])) //loop that will check if any of the useless words are present in the array that stored the question
				{
					tempQuestion[i] = ""; //if yes, replace it with an empty string
				}
			}
		}

		for (int i = 0; i < tempQuestion.length; i++)
		{
			if (tempQuestion[i] != "")
			{
				finalKeyWords.add(tempQuestion[i]); //if the array is not equal to an empty string than add that key word to the key word array list
			}
		}

		RetrieveAllCommonQuestions(finalKeyWords); //method call that will get the final ranked questions

	}

	/***
	 * Method that will compare the final keywords with each question and retrieve the final ranked guess questions
	 * @param finalKeyWords list containing all the final key words
	 * @throws FileNotFoundException if file not found
	 */
	public void RetrieveAllCommonQuestions(ArrayList<String> finalKeyWords) throws FileNotFoundException
	{
		Scanner questionIn = new Scanner(new File("questions.txt"));
		
		allQuestions = new ArrayList<String>();
		ranks = new ArrayList<Integer>();
		rankedQuestions = new ArrayList<String>();

		while (questionIn.hasNextLine())
		{
			String startingIndex = "" + questionIn.nextInt();
			String currentLine = questionIn.nextLine().toLowerCase().trim(); //trims and lower cases the line
			allQuestions.add(currentLine); //store the whole question file in an array list
		}
		questionIn.close();

		for (int i  = 0; i < allQuestions.size(); i++) //for loop that runs  until the array list with all the questions stored end
		{
			int counter = 0;
			for (int j = 0; j < finalKeyWords.size(); j ++) //for loop that runs until the array list with all the key words stored
			{
				if (allQuestions.get(i).contains(finalKeyWords.get(j))) // if the question contains any of the key words 
				{
					counter++; //add a counter
				}
			}
			ranks.add(counter); //store the counter in an array list holding the ranks
		}
		
		int max = ranks.get(0); //set the max value to the first index value of the ranks array list
		
		int noGuessCounter = 0;
		 
		int maxIndex = 0;

		for (int i  = 0; i < 4; i ++) //for loop that will take the highest 4 ranked questions
		{
			for (int j = 0; j < ranks.size(); j ++) //for loop that will go through the size of the rank array list
			{
				if (ranks.get(j) >= max) //it's going to set the max value of the array list ranks
				{
					max = ranks.get(j);
					maxIndex = j; //we store the index of the max value 
				}

			}
			if (max == 0)
			{
				noGuessCounter++;
			}
			rankedQuestions.add(allQuestions.get(maxIndex)); // find the question in the array list that stores all questions with the max index, store it in the ranked questions array list
			ranks.remove(maxIndex); //remove the value from the rank array list
			allQuestions.remove(maxIndex); //remove the question from the all question array list
			max = ranks.get(0); //set the max to the start of the ranks array list 
			
		}
		
		if (noGuessCounter == 4) 
			guess = false;
		else
			guess = true;
		

	}
	
	/***
	 * Method that will return the current question asked
	 * @return question asked by the user
	 */
	public String getQuestionAsked()
	{
		return questionAsked;
	}
	
	/***
	 * Method that set the answer of the user
	 * @param answer containing the final answer
	 */
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
	
	/***
	 * If the machine was able to indentify an exact match or not
	 * @return true or false for exact match
	 */
	public boolean getExactMatch()
	{
		return exactMatch;
	}
	
	/***
	 * Mehtod that gets the final answer of his question
	 * @return answer for the students quesiton
	 */
	public String getAnswer()
	{
		return answer;
	}
	
	/***
	 * Method that will see if the machine was able to guess the question
	 * @return true or false if guessed
	 */
	public boolean getGuess()
	{
		return guess;
	}
	
	/***
	 * Method that will set true or false if the machine was able to guess questions
	 * @param guess containing true or false for guess
	 */
	public void setGuess(boolean guess)
	{
		this.guess = guess;
	}
	
	/***
	 * Method that gets the final array containing all ranked questions
	 * @return array with all ranked questions
	 */
	public ArrayList<String> getRankedQuestions()
	{
		return rankedQuestions;
	}
}
