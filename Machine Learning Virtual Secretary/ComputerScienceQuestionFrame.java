import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Scanner;

/***
 * Class that will prompt the user to enter his question
 * @author edwin
 *
 */
public class ComputerScienceQuestionFrame extends JFrame{

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 300;

	private JLabel label1;
	private JLabel label2;
	private JTextField questionTextField;
	private JButton continueButton;

	private MouseListener continueListener;
	private ActionListener questionAskedListener;

	private String question;
	private String answer;

	private ArrayList<String> allQuestions;
	private ArrayList<Integer> ranks;
	private ArrayList<String> rankedQuestions;

	private boolean exactMatch = false;
	private boolean guess = false;
	private boolean notFound = false;
	

	private exactAnswerFrame exactMatchFrame;
	private guessAnswerFrame guessMatchFrame;
	
	private student User;
	private Question findQuestion;
	private Department department;

	/***
	 * Constructor that will initialize listeners, labels, content panel, 
	 * @param User object that contains the current student information
	 * @param department object that contains the department that was chosen
	 */
	public ComputerScienceQuestionFrame(student User, Department department)
	{
		createLabels();
		answer = "";
		question = "";
		
		this.User = User;
		this.department = department;
		
		questionAskedListener = new questionListener();
		continueListener = new continueButtonListener();

		createContentPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Computer Science Ask a Question");

		setVisible(true);
	}

	/***
	 * listener that will set the question entered in the text field to a string member variable
	 * @author edwin
	 *
	 */
	class questionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			question = questionTextField.getText().toLowerCase();

		}
	}
	
	/***
	 * listener that will allow the user to advance to the next frame, it will check for exact or guess match
	 * @author edwin
	 *
	 */
	class continueButtonListener implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			findQuestion = new Question(question); //initializes the object of findQuestion with the string of the current asked question
			
			try {
				findQuestion.checkExactMatch(); //calls the method that will check if the question has an exact match

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			if (findQuestion.getExactMatch()) //if it was an exact match
			{
				setVisible(false);
				exactMatchFrame = new exactAnswerFrame(findQuestion, User); //call the exact match frame that will output the final answer
			}

			if (!findQuestion.getExactMatch()) //if it wasn't an exact match
			{
				try {
					findQuestion.GuessMatchFindKeyWords(); //calls the method that will extract the possible questions the user asked
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}

				if (!findQuestion.getExactMatch() && findQuestion.getGuess()) //if the machine was able to get some possible questions
				{
					setVisible(false);
					guessMatchFrame = new guessAnswerFrame(findQuestion, User, department); //call the guess match frame that will give the user possible question options
				}

				if(!findQuestion.getGuess()) //if the machine couldn't guess, send an email
				{
					JOptionPane.showMessageDialog(new JFrame(), "Couldn't find possible matches for your question, email has been sent to the CS department");
					sendEmail();
					findQuestion.setGuess(true);
				}


			}

		}

		public void mouseReleased(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}


	}

	/***
	 * Method that will create the labels for the current frame
	 */
	public void createLabels()
	{
		label1 = new JLabel("Ask a question for the CS Department");
		label1.setFont(new Font("Ask a question for the CS Department", Font.ITALIC, 25));

		label2 = new JLabel("Please enter your question");
		label2.setFont(new Font("Please enter your question", Font.ITALIC, 18));
	}

	/***
	 * Method that will create the content panel of 2 rows and 1 column
	 */
	public void createContentPanel()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(2,1));

		contentPanel.add(createTopPanel());
		contentPanel.add(createLowerPanel());

		add(contentPanel);
	}

	/***
	 * Method that will create the top panel for the content panel
	 * @return the panel containing the title of the current frame
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.add(label1);

		return topPanel;
	}

	/***
	 * Method that will create the bottom panel with 2 columns 
	 * @return panel containing label prompting the user to enter the question, text field and continue button
	 */
	public JPanel createLowerPanel()
	{
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,2));
		lowerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Computer Science"));

		JPanel rightSidePanel = new JPanel();
		rightSidePanel.setLayout(new GridLayout(3,1));

		JPanel leftSidePanel = new JPanel();

		leftSidePanel.add(label2);

		JPanel topRightSidePanel = new JPanel();
		JPanel middleRightSidePanel = new JPanel();
		JPanel bottomRightSidePanel = new JPanel();

		continueButton = new JButton("Continue");
		continueButton.addMouseListener(continueListener);


		bottomRightSidePanel.add(continueButton);

		questionTextField = new JTextField();
		questionTextField.setPreferredSize(new Dimension(200,20));
		questionTextField.addActionListener(questionAskedListener);

		topRightSidePanel.add(questionTextField);

		rightSidePanel.add(topRightSidePanel);
		rightSidePanel.add(middleRightSidePanel);
		rightSidePanel.add(bottomRightSidePanel);

		lowerPanel.add(leftSidePanel);
		lowerPanel.add(rightSidePanel);



		return lowerPanel;
	}


	/***
	 * Method that will send an email if there was no exact match and any guesses
	 */
	public void sendEmail()
	{
		final String username = "edwinlau0903@gmail.com";
		final String password = "Finalproject1";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); //TLS
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(prop,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("edwinlau0903@gmail.com"));
			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse("edwinlau20@gmail.com") //change to macs@pasadena.edu
					);
			message.setSubject("Testing Gmail TLS");
			message.setText("Dear " + department.getDepartmentChosen() +  " Department,"
					+ "\n\n" + User.getUserID() + " " + User.getFirstName() + " " + User.getLastName() + " " + User.getEmail() + " asked " + question );

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

