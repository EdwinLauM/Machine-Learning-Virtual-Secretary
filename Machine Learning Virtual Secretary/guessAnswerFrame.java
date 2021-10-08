import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import java.util.Properties;

/***
 * Class that creates the current guess frame
 * @author edwin
 *
 */
public class guessAnswerFrame extends JFrame{

	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 400;

	private ArrayList<String> possibleQuestions;

	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	
	private JTextField outputAnswer;
	
	private JList possibleQuestionsList;
	private JButton selectedQuestionButton;
	private JButton continueButton;

	private DefaultListModel listModel;

	private ActionListener getSelectedQuestion;
	
	private String NoneOfTheAbove;

	private String userQuestion;
	private String emailSent;
	private String initialQuestionAsked;
	
	private student User;
	private Question findQuestion;
	private Department department;

	/***
	 * Constructor that initializes the department, question and student object along with labels and content panel
	 * @param findQuestion object that has the ranked questions stored
	 * @param User objecct that contains the student information
	 * @param department that contains the department chosen and the faculty chosen
	 */
	public guessAnswerFrame(Question findQuestion, student User, Department department)
	{
		userQuestion = "";
		initialQuestionAsked = ""; 
		NoneOfTheAbove = "None of the Above";
		emailSent = "An email was sent to the Computer Science Department";

		this.findQuestion = findQuestion;
		this.possibleQuestions = findQuestion.getRankedQuestions();

		this.possibleQuestions.add(NoneOfTheAbove);
		
		this.User = User;
		this.department = department;
		
		
		getSelectedQuestion = new selectedQuestionListener();
		
		createLabels();

		createContentPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Computer Science Ask a Question");

		setVisible(true);
	}

	/***
	 * Listener that will output the final answer to the user or send an email if none of the above is selected
	 * @author edwin
	 *
	 */
	class selectedQuestionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			userQuestion = (String) possibleQuestionsList.getSelectedValue();
			System.out.println(userQuestion);
			if (!userQuestion.equals(possibleQuestions.get(4)))
			{
				try {
					checkSelectedAnswer();
				} catch (FileNotFoundException e) {
					System.out.println(e);
				}
				outputAnswer.setText(findQuestion.getAnswer());
			}
			else
			{
				outputAnswer.setText(emailSent);
				sendEmail();
			}

		}
	}
	
	/***
	 * Mehtod that will initialize all labels
	 */
	public void createLabels()
	{
		label1 = new JLabel("    FAQ");
		label1.setFont(new Font("    FAQ", Font.ITALIC, 20));

		label2 = new JLabel("<html> Set of possible<br>questions</html>"); //adds a new line
		label2.setFont(new Font("<html>Set of possible<br>questions</html>", Font.BOLD, 15));
		
		label3 = new JLabel("Your answer:");
	}

	/***
	 * Method that will create the current frame with a layout of 2 rows
	 */
	public void createContentPanel()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(2,1));
		contentPanel.add(createTopPanel());
		contentPanel.add(createBottomPanel());

		add(contentPanel);
	}

	/***
	 * Method that will create the top panel with 2 rows and 3 columns. It will contain 2 labels
	 * @return top panel of content panel
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,3));
		topPanel.setBorder(new TitledBorder(new EtchedBorder(), "Welcome to the FAQ Forum"));

		topPanel.add(new JPanel());
		topPanel.add(label1);
		topPanel.add(new JPanel());
		topPanel.add(new JPanel());
		topPanel.add(label2);

		return topPanel;
	}

	/***
	 * Method that will create the bottom panel of the content panel with 1 row and 2 columns. Contains a list of all ranked questions and a selected button and text field
	 * @return the bottom panel for the content panel
	 */
	public JPanel createBottomPanel()
	{
		JPanel bottomPanel = new JPanel();

		bottomPanel.setLayout(new GridLayout(1,2, 2,0));
		
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Possible Questions"));
		
		listModel = new DefaultListModel();
		for (int i = 0; i < possibleQuestions.size(); i ++)
		{
			listModel.addElement(possibleQuestions.get(i));
		}

		possibleQuestionsList = new JList(listModel);
		possibleQuestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		possibleQuestionsList.setSelectedIndex(0);
		possibleQuestionsList.setVisibleRowCount(5);

		selectedQuestionButton = new JButton("Select");
		selectedQuestionButton.addActionListener(getSelectedQuestion);
		
		JScrollPane listScrollPane = new JScrollPane(possibleQuestionsList);
		
		leftBottomPanel.add(listScrollPane);
		
		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Final Answer"));
		rightBottomPanel.setLayout(new GridLayout(2,1));
		
		JPanel topRightBottomPanel = new JPanel();
		topRightBottomPanel.setLayout(new GridLayout(1,2));
		JPanel leftTopRightBottomPanel = new JPanel();
		
		leftTopRightBottomPanel.add(selectedQuestionButton);
		topRightBottomPanel.add(leftTopRightBottomPanel);
		topRightBottomPanel.add(new JPanel());
		
		JPanel bottomRightBottomPanel = new JPanel();
		
		outputAnswer = new JTextField();
		outputAnswer.setPreferredSize(new Dimension(275,20));
		outputAnswer.setEditable(false);
		
		bottomRightBottomPanel.add(label3);
		bottomRightBottomPanel.add(outputAnswer);
		
		rightBottomPanel.add(topRightBottomPanel);
		rightBottomPanel.add(bottomRightBottomPanel);

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);

		return bottomPanel;
	}

	/***
	 * Method that checks if the selected question from the list has an answer and stores its index
	 * @throws FileNotFoundException if file not found
	 */
	public void checkSelectedAnswer() throws FileNotFoundException
	{
		Scanner questionIn = new Scanner(new File("questions.txt"));
		String answerIndex = "";

		while (questionIn.hasNextLine())
		{
			answerIndex = "" +  questionIn.nextInt();
			String currentLine = questionIn.nextLine().toLowerCase().trim();
			
			if (currentLine.equals(userQuestion)) //If the current line is equal to the question the user selected
			{
				findAnswer(answerIndex); //method call
				try {
					MachineLearn(answerIndex);
				} catch (IOException e) {
					System.out.println(e);
				}
				break;
			}
		}

		questionIn.close();
	}

	/***
	 * Method that will retrieve the final answer
	 * @param answerIndex index of where the question was found
	 * @throws FileNotFoundException if file not found
	 */
	public void findAnswer(String answerIndex) throws FileNotFoundException
	{
		Scanner answerIn = new Scanner(new File("answers.txt"));
		
		String answer = "";
		
		while (answerIn.hasNextLine())
		{
			answer = "" + answerIn.nextInt();
			String currentLine = answerIn.nextLine().trim();
			if (answer.equals(answerIndex)) //if the current answer index is equal to the answer index extracted in the question file
			{
				findQuestion.setAnswer(currentLine); //set the answer of the question to the current line
				
			}
		}
		answerIn.close();
	}
	
	/***
	 * Method that will add the current question to the questions file if the user chose an answer through in the possible ranked questions 
	 * @param answerIndex index where the question will belong to
	 * @throws IOException if file not found
	 */
	public void MachineLearn(String answerIndex) throws IOException
	{		
		BufferedWriter bw = new BufferedWriter(new FileWriter("questions.txt", true));
		bw.write("\n" + answerIndex + " " + findQuestion.getQuestionAsked());
		bw.close();
	}

	/***
	 * sends an email
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
					InternetAddress.parse(User.getEmail()) //change to macs@pasadena.edu
					
					);
			message.setSubject("Testing Gmail TLS");
			message.setText("Dear " + department.getDepartmentChosen() + " Department,"
					+ "\n\n" + User.getUserID() + " " + User.getFirstName() +  " " + User.getLastName() + " " + User.getEmail() + " asked " + findQuestion.getQuestionAsked() );

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}




