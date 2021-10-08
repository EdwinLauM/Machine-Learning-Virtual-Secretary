import javax.swing.JFrame;
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
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/***
 * class that will show the user the computer science appointment frame
 * @author edwin
 *
 */
public class ComputerScienceAppointmentFrame extends JFrame {

	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 350;

	private JLabel label1;
	
	private ButtonGroup appointmentOptions;
	private ButtonGroup deanOption;
	private JRadioButton bookAppointmentButton;
	private JRadioButton retrieveAppointmentButton;
	
	private JButton continueButton;
	
	private JComboBox faculty;
	private JRadioButton dean;
	
	private ActionListener options;
	private ActionListener facultyChoice;
	private ActionListener chooseDean;
	private MouseListener clickContinue;
	
	private String newFaculty;
	private String appointmentOption;
	private String freeTime;
	
	private String bookString;
	private String seeString;
	private String deanChoice;
	
	private boolean book = false;
	private boolean see = false;
	private boolean facultyChosen = false;
	
	private int studentID;
	
	private ArrayList<String> allAppointments;
	
	private ComputerScienceAvaialableAppointmentsFrame seeAppointments;
	private ComputerScienceBookFrame bookAppointment;
	
	private student User; 
	private Department department;
	private Faculty facultyMember;
	
	/***
	 * Constructor that will initialize all the strings, the two objects passed, listeners, labels and content panel
	 * @param User object containing students information
	 * @param department object containing department and faculty chosen
	 */
	public ComputerScienceAppointmentFrame(student User, Department department)
	{
		newFaculty = "newFaculty";
		freeTime = "Free";
		appointmentOption = "";
		bookString = "Book";
		seeString = "Available";
		deanChoice = "";
		
		this.User = User;
		this.department = department;
		facultyMember = new Faculty();
		
		allAppointments = new ArrayList<String>();
		facultyChoice = new FacultyComboBoxListener();
		options = new appointmentOptionListener();
		clickContinue = new clickContinueListener();
		chooseDean = new deanOptionListener();
		
		createLabels();

		createContentPanel();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Computer Science Book an Appointment");

		setVisible(true);
	}
	
	/***
	 * listener that will store the faculty member chosen in the faculty member object which is inside the department object
	 * @author edwin
	 *
	 */
	class FacultyComboBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			facultyMember.setFaculty((String) faculty.getSelectedItem());
			department.setFaculty(facultyMember);
			try {
				storeFacultyEmail();
			} catch (FileNotFoundException e1) {
				System.out.println(e1);
			}
			try {
				readAppointments();//method call
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			facultyChosen = true; //a faculty member was chosen, therefore we set it equal to true
		}
	}
	
	/***
	 * Listener that will get the selection of what appointment was chosen
	 * @author edwin
	 *
	 */
	class appointmentOptionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			appointmentOption = appointmentOptions.getSelection().getActionCommand();
			checkAppointmentSelection(); //check if it was see appointments or book appointments (method call)
		}
	}
	
	/***
	 * Listener that will store the dean option choice to the faculty object which is inside the department object
	 * @author edwin
	 *
	 */
	class deanOptionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			facultyMember.setFaculty(deanOption.getSelection().getActionCommand());
			department.setFaculty(facultyMember);
			try {
				storeFacultyEmail();
			} catch (FileNotFoundException e1) {
				System.out.println(e1);
			}
			
			try {
				readAppointments(); //method call to find what appointment was chosen
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			facultyChosen = true; //a faculty member was chosen, therefore we set it equal to true
		}
	}

	/***
	 * continue button listener that will send the user to a new frame depending on his selection
	 * @author edwin
	 *
	 */
	class clickContinueListener implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			if(see && facultyChosen)
			{
				setVisible(false);
				seeAppointments = new ComputerScienceAvaialableAppointmentsFrame(allAppointments, department, User);
			}
			
			if (book && facultyChosen)
			{
				setVisible(false);
				bookAppointment = new ComputerScienceBookFrame(department , User);
				
			}
		}
		
		public void mouseReleased(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
	
	/***
	 * Method that will initialize all the labels
	 */
	public void createLabels()
	{
		label1 = new JLabel("Please choose an option for your appointment");
		label1.setFont(new Font("Please choose an option for your appointment", Font.ITALIC, 20));

	}

	/***
	 * Method that will fill the current frame with a layout of two rows and one column
	 */
	public void createContentPanel()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(2,1));
		contentPanel.setBorder(new TitledBorder(new EtchedBorder(), "Appointment Options"));
	
		contentPanel.add(createTopPanel());
		contentPanel.add(createBottomPanel());

		add(contentPanel);
	}

	/***
	 * Method that will create the top panel with label1 (title of the frame)
	 * @return the top panel (first row of the content panel)
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();

		topPanel.add(label1);

		return topPanel;
	}
	
	/***
	 * bottom panel that will be divided into two columns, left column will have the book or see appointment option, the right column will have the faculty member options 
	 * @return the bottom panel of the content panel (second row of the content panel)
	 */
	public JPanel createBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));
		
		JPanel bottomLeftPanel = new JPanel();
		bottomLeftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Book or Retrieve Appointments"));
		
		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(3,2));
		bottomRightPanel.setBorder(new TitledBorder(new EtchedBorder(), "Faculty Member or Dean"));
		
		JPanel topLeftBottomRightPanel = new JPanel();
		JPanel topRightBottomRightPanel = new JPanel();
		
		bottomRightPanel.add(topLeftBottomRightPanel);
		bottomRightPanel.add(topRightBottomRightPanel);
		bottomRightPanel.add(createFacultyComboBox());
		bottomRightPanel.add(createDeanOption());
			
		bottomLeftPanel.add(createAppointmentOptions());
		
		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);
		
		
		return bottomPanel;
	}
	
	/***
	 * Method that will create the appointment radio button group along with the continue button
	 * @return panel contianing the button groups and continue button
	 */
	public JPanel createAppointmentOptions()
	{		
		bookAppointmentButton = new JRadioButton("Book Appointment");
		bookAppointmentButton.setActionCommand(bookString);
		bookAppointmentButton.addActionListener(options);
		retrieveAppointmentButton =  new JRadioButton("See Appointments");
		retrieveAppointmentButton.setActionCommand(seeString);
		retrieveAppointmentButton.addActionListener(options);

		appointmentOptions = new ButtonGroup();
		appointmentOptions.add(bookAppointmentButton);
		appointmentOptions.add(retrieveAppointmentButton);
		
		continueButton = new JButton("Continue");
		continueButton.addMouseListener(clickContinue);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		JPanel buttonGroupPanel = new JPanel();
		buttonGroupPanel.add(bookAppointmentButton);
		buttonGroupPanel.add(retrieveAppointmentButton);
		
		JPanel continueButtonPanel = new JPanel();
		continueButtonPanel.add(continueButton);
		
		panel.add(buttonGroupPanel);
		panel.add(continueButtonPanel);
		
		return panel;
	}
	
	/***
	 * Method that will create the dean radio button group
	 * @return panel containing the dean's radio button
	 */
	public JPanel createDeanOption()
	{
		dean = new JRadioButton("Dean");
		dean.setActionCommand("Carrie Starbird");
		dean.addActionListener(chooseDean);
		
		deanOption = new ButtonGroup();
		deanOption.add(dean);
		
		JPanel deanPanel = new JPanel();
		deanPanel.add(dean);
		return deanPanel;
		
	}
	
	/***
	 * Method that will create the combo box for the faculty members
	 * @return the combo box
	 */
	public JComboBox createFacultyComboBox()
	{
		faculty = new JComboBox();
		faculty.setPreferredSize(new Dimension(150,30));

		faculty.addItem("Jamal Ashraf");
		faculty.addItem("Dave Smith");
		faculty.addItem("Sassan Barkeshli");
		faculty.setEditable(false);
		faculty.addActionListener(facultyChoice);

		return faculty;
	}

	/***
	 * Method that will store all available appointments for that faculty member chosen
	 * @throws FileNotFoundException if the file is not found
	 */
	public void readAppointments() throws FileNotFoundException
	{
		Scanner appointIn = new Scanner(new File("CSappointments.txt"));
		
		while(appointIn.hasNextLine())
		{
			String currentLine = appointIn.nextLine(); //reads the current full line in the appointments.txt file 
			if (currentLine.equals(department.getFaculty().getFacultyChosen())) //if the current line includes the name of the faculty chosen
			{
				while(!currentLine.equals(newFaculty)) //if the current line is not equal to the string newFaculty
				{
					currentLine = appointIn.nextLine(); //read the next line
					if (currentLine.substring(0,4).equals(freeTime)) //if the current line string index from 0 4 is equal to the string freeTime
					{
					allAppointments.add(currentLine.substring(5)); //there is a free appointment space (add to array list)
					}
				}
			}
		}
		appointIn.close();
	}
	
	/***
	 * Method that will evaluate which appointment option was chosen and sets a boolean to true accordingly
	 */
	public void checkAppointmentSelection()
	{
		if (appointmentOption.equals(bookString)) //if the appointment option is to book an apppointment
		{
			book = true;
		}
		else //if the appointment option is to see all available appointments
			see = true;
	}
	
	/***
	 * Method that will store the email of the current faculty
	 * @throws FileNotFoundException if file not found
	 */
	public void storeFacultyEmail() throws FileNotFoundException
	{
		Scanner facultyIn = new Scanner(new File("facultyInformation.txt"));
		while (facultyIn.hasNextLine())
		{
			String facultyFullName = facultyIn.next() + " " + facultyIn.next();
			String facultyEmail = facultyIn.nextLine().trim();
			if (facultyFullName.contains(facultyMember.getFacultyChosen()))
			{
				facultyMember.setFacultyEmail(facultyEmail);
				break;
			}
		}
		facultyIn.close();
	}



}
