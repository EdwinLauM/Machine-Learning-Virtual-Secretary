/***
 * Class that will contain the faculties information
 * @author edwin
 *
 */
public class Faculty {

	private String facultyChosen;
	private String facultyEmail;
	
	/***
	 * Constructor that will initialize facultyChosen to an empty string
	 */
	public Faculty()
	{
		facultyChosen = "";
		facultyEmail = "";
	}
	
	/***
	 * Method that will set the faculty members name
	 * @param facultyChosen parameter containing the faculty's name
	 */
	public void setFaculty(String facultyChosen)
	{
		this.facultyChosen = facultyChosen;
	}
	
	/***
	 * Method that will retrieve the faculty's name
	 * @return faculty's chosen name
	 */
	public String getFacultyChosen()
	{
		return facultyChosen;
	}
	
	/***
	 * Method that will set the faculty's email
	 * @param facultyEmail parameter containing the faculty's email
	 */
	public void setFacultyEmail(String facultyEmail)
	{
		this.facultyEmail = facultyEmail;
	}
	
	/***
	 * Method that will retrieve the faculty's email
	 * @return faculty's email
	 */
	public String getFacultyEmail()
	{
		return facultyEmail;
	}
	

	
	
}
