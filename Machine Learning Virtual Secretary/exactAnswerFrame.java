import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseListener;

/***
 * Class that will provide the user with a exact answer frame containing the answer for his question
 * @author edwin
 *
 */
public class exactAnswerFrame extends JFrame {
	
	private static final int FRAME_WIDTH = 500
			;
	private static final int FRAME_HEIGHT = 300;
	
	
	private JLabel label1;
	private JLabel label2;
	private JTextField outputAnswer;
	
	private JButton exitButton;
	
	private MouseListener clickExit;
	
	private student User;
	private Question findQuestion;
	
	/***
	 * listener that will exit the program when he clicks on the exit object
	 * @author edwin
	 *
	 */
	class exitListener implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			System.exit(0);
		}
		
		public void mouseReleased(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}

	/***
	 * Constructor that will initialize the question and user object, labels, content panel and listeners
	 * @param findQuestion object that contains the question entered by the user
	 * @param User object that contains the students information
	 */
	public exactAnswerFrame(Question findQuestion , student User)
	{
		this.findQuestion = findQuestion;
		this.User = User;
		
		clickExit = new exitListener();
		
		createLabels();

		createContentPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Computer Science Ask a Question");

		setVisible(true);
	}
	
	/***
	 * method that will initialize all labels
	 */
	public void createLabels()
	{
		label1 = new JLabel("Answer: ");
		
		label2 = new JLabel("Exact match for your question!");
		label2.setFont(new Font("Exact match for your question!", Font.ITALIC, 20));
	}
	
	/***
	 * Method that will create the current frame panels with 3 rows
	 */
	public void createContentPanel()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(3,1));
		contentPanel.add(createTopPanel());
		contentPanel.add(createMiddlePanel());
		contentPanel.add(createBottomPanel());
		
		add(contentPanel);
	}
	
	/***
	 * Method that will create the top panel containing a label
	 * @return the top panel for the content panel
	 */
	public JPanel createTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.add(label2);
		
		return topPanel;
	}
	
	/***
	 * Method that will create the middle panel containing the answer of the user in a non editable text field
	 * @return middle panel of the content panel
	 */
	public JPanel createMiddlePanel()
	{
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Answer to question"));
		
		outputAnswer = new JTextField();
		outputAnswer.setPreferredSize(new Dimension(250,20));
		outputAnswer.setEditable(false);
		outputAnswer.setText(findQuestion.getAnswer());
		
		middlePanel.add(label1);
		middlePanel.add(outputAnswer);
		
		return middlePanel;
	}
	
	/***
	 * Method that will create the bottom panel containing an exit button
	 * @return bottom panel of the content panel
	 */
	public JPanel createBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		exitButton = new JButton("Exit");
		exitButton.addMouseListener(clickExit);
		
		bottomPanel.add(exitButton);
		
		return bottomPanel;
	}
	
}
