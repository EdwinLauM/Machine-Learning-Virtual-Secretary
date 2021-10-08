
/***
 * Class that will store the faculty's name and email
 * @author edwin
 *
 */
public class facultyMember extends Faculty {
	
	private String FacultyMember;
	private String facultyEmail;
	
	public facultyMember()
	{
		FacultyMember = "";
		facultyEmail = "";
	}
	
	/***
	 * Method that sets the faculty member name
	 * @param FacultyMember containing the faculty member name
	 */
	public void setFacultyMember(String FacultyMember)
	{
		this.FacultyMember = FacultyMember;
	}
	
	/***
	 * Method that sets the faculty member email
	 * @param facultyEmail containing the faculty member email
	 */
	public void setFacultyEmail(String facultyEmail)
	{
		this.facultyEmail = facultyEmail;
	}
	
	/***
	 * Method that gets the faculty member
	 * @return faculty member name
	 */
	public String getFacultyMember()
	{
		return FacultyMember;
	}
	
	/***
	 * Method that gets the faculty member email
	 * @return faculty member email
	 */
	public String getFacultyEmail()
	{
		return facultyEmail;
	}

}
