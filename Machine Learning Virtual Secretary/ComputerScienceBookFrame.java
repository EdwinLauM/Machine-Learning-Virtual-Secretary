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
 * Class that will book an appointment based on the students input
 * @author edwin
 *
 */
public class ComputerScienceBookFrame extends JFrame{
	
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 400;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	
	private JTextField appointment;
	
	private ArrayList<String> infoToBeReplaced;

	
	private String bookedAppointment;
	private String newFaculty;
	
	private int indexOfAppointment;
	
	private boolean appointmentFound;
	
	
	private ActionListener appointmentEntry;
	
	private student User;
	private Department department;
	
	/***
	 * Constructor that will initialize the department and student object, store the all appointments array, strings, listeners, labels and content panel
	 * @param allAppointments array containing all available appointments
	 * @param department object containing the department and faculty chosen
	 * @param User object containing the students information
	 */
	public ComputerScienceBookFrame(Department department , student User)
	{
		
		this.department = department;
		this.User = User;
		indexOfAppointment = 0;
		appointmentFound = true;
		newFaculty = "newFaculty";
		

		appointmentEntry = new textFieldListener();
		
		System.out.println(department.getFaculty().getFacultyEmail());
		
		createLabels();

		createContentPanel();

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Computer Science Book an Appointment");

		setVisible(true);
	}
	
	/***
	 * Listener that will book an appointment based on his input (if available)
	 * @author edwin
	 *
	 */
	class textFieldListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			bookedAppointment = appointment.getText();
			try {
				bookAppointment();
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			System.out.println(indexOfAppointment);
			if (!appointmentFound)
				JOptionPane.showMessageDialog(new JFrame(), "There exists no appointments");
			else
			{
				JOptionPane.showMessageDialog(new JFrame(), "Appointment booked! An email was sent to the faculty and you");
				sendEmail();
			}
				
			
		}
	}
	
	/***
	 * Method that will initialize all labels
	 */
	public void createLabels()
	{
		label1 = new JLabel("Enter an Appointment, example: MM/DD/YY 8:01-9:00");
		
		label2 = new JLabel("Book an Appointment");
		label2.setFont(new Font("Book an Appointment", Font.ITALIC, 20));
		
		label3 = new JLabel("for " + department.getFaculty().getFacultyChosen());
		label3.setFont(new Font("for " + department.getFaculty().getFacultyChosen(), Font.ITALIC, 20));
		
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
	 * Method that will create the top panel of the control panel, layout of 2 rows and each has a label in it
	 * @return top panel for the control panel
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,1));
		
		JPanel topTopPanel = new JPanel();
		topTopPanel.add(label2);
		
		JPanel bottomTopPanel = new JPanel();
		bottomTopPanel.add(label3);
		
		topPanel.add(topTopPanel);
		topPanel.add(bottomTopPanel);
		return topPanel;
	}
	
	/***
	 * Method that will create the bottom panel of the content panel, layout of 2 rows. Contains a label prompting the user to enter his appointment and a text field
	 * @return bottom Panel of the content panel
	 */
	public JPanel createBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Enter an appointment"));
		bottomPanel.setLayout(new GridLayout(2,1));
		
		JPanel topBottomPanel = new JPanel();
		topBottomPanel.add(label1);
		
		JPanel bottomBottomPanel = new JPanel();
		appointment = new JTextField();
		appointment.setPreferredSize(new Dimension(200,20));
		appointment.addActionListener(appointmentEntry);
		
		bottomBottomPanel.add(appointment);
		
		
		bottomPanel.add(topBottomPanel);
		bottomPanel.add(bottomBottomPanel);
		
		return bottomPanel;
		
	}
	
	/***
	 * Method that will book an appointment if available
	 * @throws FileNotFoundException if file not found
	 */
	public void bookAppointment() throws FileNotFoundException
	{
		Scanner appointIn = new Scanner(new File("CSappointments.txt"));
		int counter = 0;
		infoToBeReplaced = new ArrayList<String>(); //array list containing all the appointments in the text file

		String currentLine;

		while (appointIn.hasNextLine())
		{
			currentLine = appointIn.nextLine(); //reads the whole current line
			infoToBeReplaced.add(currentLine); //Stores the whole appointments txt in this array
		}
		appointIn.close(); // closes the file
		
		Scanner appointIn2 = new Scanner(new File("CSappointments.txt")); //opens the same file
		
		String currentLine2; 

		while (appointIn2.hasNextLine())
		{
			currentLine2 = appointIn2.nextLine(); //reads the whole current line
			counter++; //use counter to find the index of where the appointment was trying to be booked at 
			if (currentLine2.equals(department.getFaculty().getFacultyChosen())) //if the current line is equal to the faculty member the user chose
			{
				while(!currentLine2.substring(5).equals(bookedAppointment)) //while the current line is not equal to the appointment the user entered
				{
					currentLine2 = appointIn2.nextLine(); //move to the next line
					counter++; //add a counter
					if (currentLine2.equals(newFaculty))
					{
						appointmentFound = false;
						break;
					}
					else
						appointmentFound = true;
				}
				indexOfAppointment = counter; //if it is found then this is the index of the appointment
			}
		}
		appointIn2.close();
		
		if (appointmentFound)
		{
		replacingArrayList(); //method call
		writeOnFile(); //method call
		}

	}
	
	/***
	 * Method that will replace the current appointment selected with the users information and appointment time
	 */
	public void replacingArrayList()
	{
		String replaceString = "" + User.getUserID() + " " + User.getFirstName() + " " + User.getLastName() + " " + User.getEmail() + " " + infoToBeReplaced.get(indexOfAppointment-1).substring(5); //creates a new string that will take the place of the appointment chosen 
		infoToBeReplaced.set(indexOfAppointment - 1, replaceString); //uses the index of where the appointment was found, replaces it with the student ID
		
	}
	
	/***
	 * Method that will write the whole new appointment list after the user has scheduled an appointment
	 * @throws FileNotFoundException if file not found
	 */
	public void writeOnFile() throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(new File("CSappointments.txt"));
		
		for (int i = 0; i < infoToBeReplaced.size(); i++)
		{
			writer.println(infoToBeReplaced.get(i));
		}
		writer.close();
	}
	
	/***
	 * Send an email to the student and the faculty email with the appointment scheduled and the students information
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
			message.setText("Dear " + department.getFaculty().getFacultyChosen()
					+ "\n\n" + User.getUserID() + " " + User.getFirstName() + " " + User.getLastName() + " " + User.getEmail() + " has booked an appointment for " + bookedAppointment ); 

					Transport.send(message);

					System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
}

