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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/***
 * Class that will allow the user to book an appointment after seeing the whole available shecule for a faculty 
 * @author edwin
 *
 */
public class MathematicsAvailableAppointmentsFrame extends JFrame {

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 400;

	private ArrayList<String> allAppointments;
	private ArrayList<String> infoToBeReplaced;

	private String newFaculty;
	private String freeTime;

	private JLabel label1;
	private JLabel label2;

	private JList allAvailableAppointments;
	private DefaultListModel listModel;

	private JButton bookButton;

	private ActionListener bookClick;

	private String chosenAppointment;
	
	private int indexOfAppointment;
	
	private boolean bookAppointmentSuccessful = false;
	
	private student User;
	private Department department;


	/***
	 * Constructor that will initialize the labels, click listener, content panel, department and user object, and the array containing all available appoiintments
	 * @param allAppointments array containing all available appointments
	 * @param department object that has the department chosen and the faculty chosen
	 * @param User object that contains all the students information
	 */
	public MathematicsAvailableAppointmentsFrame(ArrayList<String> allAppointments, Department department, student User)
	{

		this.allAppointments = allAppointments;
		this.department = department;
		this.User = User;
		infoToBeReplaced = new ArrayList<String>();
		newFaculty = "newFaculty";
		freeTime = "Free";

		bookClick = new bookButtonListener();
		
		
		createLabels();

		createContentPanel();

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mathematics Book an Appointment");

		setVisible(true);
	}

	/***
	 * Listener that will book an appointment based on the selection made 
	 * @author edwin
	 *
	 */
	class bookButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			chosenAppointment = (String) allAvailableAppointments.getSelectedValue();
			System.out.println(chosenAppointment);

			try {
				bookAppointment();
				bookAppointmentSuccessful = true;
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			System.out.println("index: " + indexOfAppointment);
			if (bookAppointmentSuccessful)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Appointment was booked!");
				sendEmail();
			}
		}
	}

	/***
	 * Method that will initialize all labels
	 */
	public void createLabels()
	{
		label1 = new JLabel("Available Apppointments");
		label1.setFont(new Font("Available Appointments", Font.ITALIC, 20));

		label2 = new JLabel("for " + department.getFaculty().getFacultyChosen());
		label2.setFont(new Font("for " + department.getFaculty().getFacultyChosen(), Font.ITALIC, 20));
	}

	/***
	 * Method that will create the current panel which contains two rows
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
	 * Panel that will be divided into two columns containing label 1 and label 2 and will be used for the first row of the content panel
	 * @return top panel for the content panel
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,1));

		JPanel topTopPanel = new JPanel();
		JPanel bottomTopPanel = new JPanel();
		bottomTopPanel.setPreferredSize(new Dimension(200,100));

		topTopPanel.add(label1);

		bottomTopPanel.add(label2);

		topPanel.add(topTopPanel);
		topPanel.add(bottomTopPanel);

		return topPanel;
	}
	
	/***
	 * Method that will have a scroll pane filled with a list for the user to see all the available appointments of that faculty member
	 * @return bottom panel of the content panel
	 */
	public JPanel createBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Available Appoitnments"));

		listModel = new DefaultListModel();

		for (int i = 0; i < allAppointments.size(); i ++)
		{
			listModel.addElement(allAppointments.get(i));
		}

		allAvailableAppointments = new JList(listModel);
		allAvailableAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allAvailableAppointments.setSelectedIndex(0);
		allAvailableAppointments.setVisibleRowCount(5);
		bookButton = new JButton("Select");
		bookButton.addActionListener(bookClick);

		JScrollPane appointmentScrollPane = new JScrollPane(allAvailableAppointments);

		bottomPanel.add(appointmentScrollPane);
		bottomPanel.add(bookButton);

		return bottomPanel;
	}

	/***
	 * Method that will book an appointment for the user based on the selection of the appointment
	 * @throws FileNotFoundException if the file is not found
	 */
	public void bookAppointment() throws FileNotFoundException
	{
		Scanner appointIn = new Scanner(new File("MathAppointments.txt"));
		int counter = 0;
		
		String currentLine;

		while (appointIn.hasNextLine())
		{
			currentLine = appointIn.nextLine(); //reads the whole current line
			infoToBeReplaced.add(currentLine); //Stores the whole appointments txt in this array
		}
		
		appointIn.close(); // closes the file
		
		Scanner appointIn2 = new Scanner(new File("MathAppointments.txt")); //opens the same file
		
		String currentLine2; 
		while (appointIn2.hasNextLine())
		{
			currentLine2 = appointIn2.nextLine(); //reads the whole current line
			counter++; //use counter to find the index of where the appointment was trying to be booked at 
			if (currentLine2.equals(department.getFaculty().getFacultyChosen())) //if the current line is equal to the faculty member the user chose
			{
				while(!currentLine2.substring(5).equals(chosenAppointment)) //while the current line is not equal to the appointment the user chose
				{
					currentLine2 = appointIn2.nextLine(); //move to the next line
					counter++; //add a counter
				}
				indexOfAppointment = counter; //if it is found then this is the index of the appointment
			}
		}
		appointIn2.close();
		
		replacingArrayList(); //method call
		writeOnFile(); //method call

	}
	
	/***
	 * Once the index of where the question is located is found, we will replace it with the users information and the appointment time
	 */
	public void replacingArrayList()
	{
		String replaceString = "" + User.getUserID() + " " + User.getFirstName() + " " + User.getLastName() + " " + User.getEmail() + " " + infoToBeReplaced.get(indexOfAppointment-1).substring(5); //creates a new string that will take the place of the appointment chosen 
		infoToBeReplaced.set(indexOfAppointment - 1, replaceString); //uses the index of where the appointment was found, replaces it with the student ID
		
	}
	
	/***
	 * Method that will write the new appointment schedule after the user has booked his appointment
	 * @throws FileNotFoundException if file is not found
	 */
	public void writeOnFile() throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(new File("MathAppointments.txt"));
		
		for (int i = 0; i < infoToBeReplaced.size(); i++)
		{
			writer.println(infoToBeReplaced.get(i));
		}
		writer.close();
	}
	
	/***
	 * Method that will send en email to the user and faculty member based with the students info and appointmnet
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
			message.addRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(User.getEmail()));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(department.getFaculty().getFacultyEmail())); //when presenting change to department.getFaculty().getFacultyEmail()
			message.setSubject("Testing Gmail TLS");
			message.setText("Dear " + department.getFaculty().getFacultyChosen()
					+ "\n\n" + User.getUserID() + " " + User.getFirstName() + " " + User.getLastName() + " " + User.getEmail() + " has booked an appointment for " + chosenAppointment ); 

					Transport.send(message);

					System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}





