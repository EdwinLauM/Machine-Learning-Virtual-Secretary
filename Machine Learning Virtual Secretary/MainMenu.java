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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/***
 * Class that will show the user the first menu of the PCC Secretary
 * @author edwin
 *
 */
public class MainMenu extends JFrame {

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 500;

	private JLabel label;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JTextField signIn;
	private JTextField firstNameInput;
	private JTextField lastNameInput;
	private JTextField IdInput;
	private JTextField emailInput;

	private JRadioButton generalQuestion;
	private JRadioButton makeAppointment;
	private JComboBox courses;

	private ActionListener departmentChoice;
	private ActionListener signInID;
	private ActionListener signUpFirstName;
	private ActionListener signUpLastName;
	private ActionListener signUpID;
	private ActionListener signUpEmail;
	private ActionListener options;
	private ActionListener fillSignUp;
	private MouseListener clickContinue;

	private ButtonGroup optionsGroup;
	private JRadioButton questionButton;
	private JRadioButton appointmentButton;

	private JButton continueButton;

	private String userID;
	private String firstName;
	private String lastName;
	private String email;
	private String buttonGroupValue;
	

	private String CS = "Computer Science";
	private String Math = "Mathematics";
	private String Question = "Question";
	private String Appointment = "Appointment";

	private boolean infoFound = false;
	private boolean signInEntered = false;
	private boolean departmentChosen = false;
	private boolean computerScienceDepartment = false;
	private boolean mathematicsDepartment = false;
	private boolean optionsChosen = false;
	private boolean questionBox = false;
	private boolean appointmentBox = false;
	private boolean nextFrame = false;
	
	private boolean signUpFirstNameEntered = false;
	private boolean signUpLastNameEntered = false;
	private boolean signUpIDEntered = false;
	private boolean signUpEmailEntered = false;
	private boolean signUpEntered = false;
	private boolean IDisRepeated = false;

	private ComputerScienceQuestionFrame CSQuestionFrame;
	private ComputerScienceAppointmentFrame CSAppointmentFrame;
	private MathematicsQuestionFrame mathQuestionFrame;
	private MathematicsAppointment mathAppointmentFrame;
	
	private student User;
	private Department department;


	/***
	 * Constructor that will initialize all the listeners, objects, control panel and labels;
	 */
	public MainMenu()
	{

		createLabels();

		departmentChoice = new departmentListener(); //object of the department listener class
		clickContinue = new clickContinueListener(); //object of the click continue class
		signInID = new signInIDListener(); // object of the sign in id class
		signUpFirstName = new signUpFirstNameListener(); //object of the sign up first name class
		signUpLastName = new signUpLastNameListener(); //object of the sign up last name class
		signUpID = new signUpIDListener(); //objects of the sign up id class
		signUpEmail = new signUpEmailListener(); //object of the sign up email class
		options = new ButtonGroupListener(); //object of the button group (j radio button class)
		
		User = new student(); //creates object of the student class
		department = new Department(); //creates object of the department class

		createControlPanel(); //calls create control panel method
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Main Menu ");
		setVisible(true);


	}

	/***
	 * inner class that will check if the continue button is clicked
	 * If it is clicked it will call the method check sign up to check whether all the text fields where filled if they were going to sign up
	 * if the user sign in , department chosen and options chosen is completed, then it will close the current frame. 
	 * It checks if the CS department and question is chosen, then it will create an object that will create a computer science question frame
	 * if it was the appointment box then it will create an object that will create a computer science appointment frame
	 * @author edwin
	 *
	 */
	class clickContinueListener implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			checkSignUpFull(); //checks whether the user fully signed up
			
			if (signInEntered && departmentChosen && optionsChosen) //if user ID was found, the department was chosen and an option was chosen
			{
				setVisible(false);
				if (computerScienceDepartment && questionBox) //if the computer science department was chosen and the question box was chosen
				CSQuestionFrame = new ComputerScienceQuestionFrame(User, department); //open the computer science question frame
				
				if (computerScienceDepartment && appointmentBox) //if the computer science department was chosen and the appointment box was chosen
					CSAppointmentFrame = new ComputerScienceAppointmentFrame(User, department); // open the computer science appointment frame
				
				if(mathematicsDepartment && questionBox) //if the math department was chosen and the question box was chosen
					mathQuestionFrame = new MathematicsQuestionFrame(User, department); //open the math question frame
				
				if(mathematicsDepartment && appointmentBox) //if the math department was chosen and the appointment was chosen
					mathAppointmentFrame = new MathematicsAppointment(User, department); //open the math appointment frame
				
			}
			
			if (signUpEntered && departmentChosen && optionsChosen && !IDisRepeated)
			{
				try
				{
					storeNewUser(); //if the id is not repeated, and the user completed the whole sign up, store this information in the students_info txt
				}
				catch (IOException i)
				{
					System.out.println(i);
				}
				
				setVisible(false);
				if (computerScienceDepartment && questionBox) // if the computer science department was chosen and the question box was chosen
					CSQuestionFrame = new ComputerScienceQuestionFrame(User, department);
					
					if (computerScienceDepartment && appointmentBox)  //if the computer science department was chosen and the appointment box was chosen
						CSAppointmentFrame = new ComputerScienceAppointmentFrame(User, department); // open the computer science appointment frame
					
					if(mathematicsDepartment && questionBox) //if the math department was chosen and the question box was chosen
						mathQuestionFrame = new MathematicsQuestionFrame(User, department); //open the math question frame
					
					if(mathematicsDepartment && appointmentBox) //if the math department was chosen and the appointment was chosen
						mathAppointmentFrame = new MathematicsAppointment(User, department); //open the math appointment frame
					
				
			}

		}

		public void mouseReleased(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}

	/***
	 * listener that will set the user id to the user object
	 * @author edwin
	 *
	 */
	class signInIDListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			User.setUserID(signIn.getText());
			System.out.println(User.getUserID());
			try {
				readStudentInfo();
				
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			
			firstNameInput.setText(User.getFirstName());
			firstNameInput.setEditable(false);
			lastNameInput.setText(User.getLastName());
			lastNameInput.setEditable(false);
			IdInput.setText(User.getUserID());
			IdInput.setEditable(false);
			emailInput.setText(User.getEmail());
			emailInput.setEditable(false);

			
			if (infoFound == false)
			{
				JOptionPane.showMessageDialog(new JFrame(), "We couldn't find your student ID");
			}
			else
				signInEntered = true;
		}
	}

	/***
	 * listener that will set the first name to the user object
	 * @author edwin
	 */
	class signUpFirstNameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			User.setFirstName(firstNameInput.getText());
			signUpFirstNameEntered = true;
			System.out.println(User.getFirstName());
		}
	}

	/***
	 * listener that will set the last name to the user object
	 * @author edwin
	 *
	 */
	class signUpLastNameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			User.setLastName(lastNameInput.getText());
			signUpLastNameEntered = true;
			System.out.println(User.getLastName());

		}
	}

	/***
	 * listener that will set the new ID to the user object
	 * @author edwin
	 *
	 */
	class signUpIDListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			User.setUserID(IdInput.getText());
			signUpIDEntered = true;
			try {
				checkSignUpID(); //calls the method sign up id that will check if the inputted ID already exists
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			
			if(IDisRepeated)
				JOptionPane.showMessageDialog(new JFrame(), "User ID exists! Please use another");
			System.out.println(User.getUserID());
		}
	}

	/***
	 * listener that will set the email to the user object
	 * @author edwin
	 *
	 */
	class signUpEmailListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			User.setEmail(emailInput.getText());
			signUpEmailEntered = true;
			System.out.println(User.getEmail());
		}
	}

	/***
	 * listener that will set the department chosen by the user to the department object
	 * @author edwin
	 *
	 */
	class departmentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			department.setDepartmentChosen((String) courses.getSelectedItem());
			setComboBoxBoolean(); //method that will check what department was chosen
		}
	}

	/***
	 * listener that will get the  
	 * @author edwin
	 *
	 */
	class ButtonGroupListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			buttonGroupValue = optionsGroup.getSelection().getActionCommand();
			setRadioButtonBoolean();
		}
	}

	/***
	 * Method that will create a panel with a grid layout of 4 rows and and 1 column
	 */
	public void createControlPanel()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(4,1));

		contentPanel.add(createTopPanel());
		contentPanel.add(createTopSecondPanel());
		contentPanel.add(createThirdPanel());
		contentPanel.add(createFourthPanel());
		add(contentPanel);
	}

	/***
	 * Method that will initialize all labels we will be using in the current frame
	 */
	public void createLabels()
	{

		label = new JLabel("Virtual PCC Secretary");
		label.setFont(new Font("Virtual PCC Secretary", Font.ITALIC, 25));
		label.repaint();
		label2 = new JLabel("Please enter your student ID: ");

		label3 = new JLabel("First Name: ");
		label4 = new JLabel("Last Name: ");
		label5 = new JLabel("ID: ");
		label6 = new JLabel("Email: ");
		label7 = new JLabel("Please select the department: ");

		label8 = new JLabel("Please select an option");


	}

	/***
	 * Creates a panel with 2 rows and 1 column. The first row will have the title, the second row will have a label prompting the user to enter his ID
	 * @return the first row of the content panel
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,1,0,0));

		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();

		northPanel.add(label);
		northPanel.add(new JLabel("                                                                                                             "));

		signIn = new JTextField();
		signIn.setPreferredSize(new Dimension(200,20));
		signIn.addActionListener(signInID);

		southPanel.add(label2);
		southPanel.add(new JLabel("                       "));
		southPanel.add(signIn);
		southPanel.setBorder(new TitledBorder(new EtchedBorder(), "Sign in"));


		topPanel.add(northPanel);
		topPanel.add(southPanel);

		return topPanel;

	}

	/***
	 * Creates a panel with 4 rows and 1 column that will prompt the user to enter all the sign up info needed
	 * @return second row of the content panel
	 */
	public JPanel createTopSecondPanel()
	{
		JPanel topSecondPanel = new JPanel();
		topSecondPanel.setLayout(new GridLayout(4,1));

		firstNameInput = new JTextField();
		firstNameInput.setPreferredSize(new Dimension(200,20));
		firstNameInput.addActionListener(signUpFirstName);


		lastNameInput = new JTextField();
		lastNameInput.setPreferredSize(new Dimension(199,20));
		lastNameInput.addActionListener(signUpLastName);

		IdInput = new JTextField();
		IdInput.setPreferredSize(new Dimension(200, 20));
		IdInput.addActionListener(signUpID);

		emailInput = new JTextField();
		emailInput.setPreferredSize(new Dimension(200, 20));
		emailInput.addActionListener(signUpEmail);

		JPanel firstName = new JPanel();
		JPanel lastName = new JPanel();
		JPanel ID = new JPanel();
		JPanel email = new JPanel();

		firstName.add(label3);
		firstName.add(new JLabel("                                                      "));
		firstName.add(firstNameInput);

		lastName.add(label4);
		lastName.add(new JLabel("                                                      "));
		lastName.add(lastNameInput);

		ID.add(label5);
		ID.add(new JLabel("                                                                     "));
		ID.add(IdInput);

		email.add(label6);
		email.add(new JLabel("                                                               "));
		email.add(emailInput);

		topSecondPanel.setBorder(new TitledBorder(new EtchedBorder(), "Sign up"));
		topSecondPanel.add(firstName);
		topSecondPanel.add(lastName);
		topSecondPanel.add(ID);
		topSecondPanel.add(email);

		return topSecondPanel;

	}

	/***
	 * Creates a panel of 2 columns. The left column will contain a label prompting the user to select a department
	 * The right column will contain a combo box with the list of departments available
	 * @return the third row of the content panel
	 */
	public JPanel createThirdPanel()
	{
		JPanel thirdPanel = new JPanel();
		thirdPanel.setLayout(new GridLayout(1,2, 0, 0));

		JPanel labelPanel = new JPanel();
		JPanel comboPanel = new JPanel();

		labelPanel.add(label7);
		comboPanel.add(createComboBox());


		thirdPanel.setBorder(new TitledBorder(new EtchedBorder(), "Department"));
		thirdPanel.add(labelPanel);
		thirdPanel.add(comboPanel);

		return thirdPanel;
	}

	/***
	 * Creates a panel of 2 columns. The left column will contain a a group of radio buttons. appointment or question
	 * The right column is jut a button that will allow the user to continue to the next frame
	 * @return the last row of the content panel
	 */
	public JPanel createFourthPanel()
	{
		JPanel fourthPanel = new JPanel();
		fourthPanel.setLayout(new GridLayout(1,2));

		JPanel leftSide = new JPanel();
		JPanel topLeftSide = new JPanel();
		JPanel bottomLeftSide = new JPanel();


		leftSide.setLayout(new GridLayout(2,1));
		leftSide.setBorder(new TitledBorder(new EtchedBorder(), "Options"));
		topLeftSide.add(label8);
		bottomLeftSide.add(createRadioButton());

		leftSide.add(topLeftSide);
		leftSide.add(bottomLeftSide);

		JPanel rightSide = new JPanel();

		rightSide.setBorder(new TitledBorder(new EtchedBorder(), "Continue"));
		rightSide.add(createContinueButton());

		fourthPanel.add(leftSide);
		fourthPanel.add(rightSide);


		return fourthPanel;
	}

	/***
	 * Creates the continue button for the user to advance frame
	 * @return a panel that will contain the continue button
	 */
	public JPanel createContinueButton()
	{
		JPanel panel = new JPanel();

		continueButton = new JButton("Continue");
		continueButton.addMouseListener(clickContinue);

		panel.add(continueButton);

		return panel;
	}

	/***
	 * method that will create the radio buttons that the user will use to choose his option
	 * @return a panel that will contain a button group of radio buttons
	 */
	public JPanel createRadioButton()
	{
		questionButton = new JRadioButton("Question");
		questionButton.setActionCommand("Question");
		questionButton.addActionListener(options);

		appointmentButton = new JRadioButton("Appointment");
		appointmentButton.setActionCommand("Appointment");
		appointmentButton.addActionListener(options);


		optionsGroup = new ButtonGroup();
		optionsGroup.add(questionButton);
		optionsGroup.add(appointmentButton);


		JPanel panel = new JPanel();
		panel.add(questionButton);
		panel.add(appointmentButton);

		return panel;
	}

	/***
	 * Creates a combo box that will contain all the departments 
	 * @return a combo box with the departments
	 */
	public JComboBox createComboBox()
	{
		courses = new JComboBox();
		courses.setPreferredSize(new Dimension(150,30));

		courses.addItem("Computer Science");
		courses.addItem("Mathematics");
		courses.setEditable(false);
		courses.addActionListener(departmentChoice);

		return courses;
	}

	/***
	 * Method that will check if the user sign in id exists
	 * @throws FileNotFoundException if the file is not found
	 */
	public void readStudentInfo() throws FileNotFoundException
	{

		Scanner in = new Scanner(new File("students_information.txt"));

		while (in.hasNextLine()) 
		{
			String userID = in.next();
			String firstName = in.next();
			String lastName = in.next();
			String email = in.next();

			if (Integer.parseInt(userID) == (Integer.parseInt(User.getUserID()))) //if the userID read is the same as the user stored in the student 
			{
				User.setUserID(userID);
				User.setFirstName(firstName);
				User.setLastName(lastName);
				User.setEmail(email);
				infoFound = true; //if all the information was found then set this to true
				break;
			}
			else
			{
				infoFound = false; //if it wans't found then its false
			}

//			in.nextLine();

		}
		in.close();


	}
	
	/***
	 * Method that will check what department was selected
	 */
	public void setComboBoxBoolean()
	{
		if (department.getDepartmentChosen().equals(CS))
			computerScienceDepartment = true;
		else
			mathematicsDepartment = true;

		departmentChosen = true;
	}

	/***
	 * Method that will check what option was selected
	 */
	public void setRadioButtonBoolean()
	{
		if (buttonGroupValue.equals(Question))
		{
			optionsChosen = true;
			questionBox = true;
		}
		else 
		{
			optionsChosen = true;
			appointmentBox = true;
		}
	}
	
	/***
	 * Method that will check if all the fields for the sign up where inputted
	 */
	public void checkSignUpFull()
	{
		if (signUpFirstNameEntered && signUpLastNameEntered && signUpIDEntered && signUpEmailEntered)
		{
			signUpEntered = true;
		}
	}

	/***
	 * Method that will append the new user to the students information file
	 * @throws IOException if the file is not found
	 */
	public void storeNewUser() throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter("students_information.txt", true));
		bw.write("\n" + User.getUserID() + " " + User.getFirstName() + " " + User.getLastName() + " " + User.getEmail());
		bw.close();
	}

	/***
	 * Method that will check if the sign up id exists in the file
	 * @throws FileNotFoundException if the file is not found
	 */
	public void checkSignUpID() throws FileNotFoundException
	{
		Scanner infoIn = new Scanner(new File("students_information.txt"));
		
		
		while (infoIn.hasNextLine())
		{
			String extractID = infoIn.next();
			if (extractID.equals(User.getUserID()))
			{
				IDisRepeated = true;
				break;
			}
			else
				IDisRepeated = false;
				
			
			infoIn.nextLine();
		}
		infoIn.close();
	}
}	




