/***
 * Class that contains all the students information
 * @author edwin
 *
 */
public class student {

	private String userID;
	private String firstName;
	private String lastName;
	private String email;
	
	/***
	 * Constructor that will initialize all the information to empty strings 
	 */
	public student()
	{
		userID = "";
		firstName = "";
		lastName = "";
		email = "";
	}
	
	/***
	 * Method that sets the user id
	 * @param userID containing the users id
	 */
	public void setUserID(String userID)
	{
		this.userID = userID;
	}
	
	/***
	 * Method that sets the first name
	 * @param firstName containing the students first name
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	/***
	 * Method that sets the last name
	 * @param lastName containing the students last name
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	/***
	 * Method that sets the email
	 * @param email containing the students email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/***
	 * Method that gets the users id
	 * @return the users id
	 */
	public String getUserID()
	{
		return userID;
	}
	
	/***
	 * Method that gets the users first name
	 * @return users first name
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/***
	 * Method that gets the users last name
	 * @return users last name
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/***
	 * Method that gets the users email
	 * @return users email
	 */
	public String getEmail()
	{
		return email;
	}
}
