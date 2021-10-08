/***
 * class that will store the department chosen, it will also contain an object of type faculty
 * @author edwin
 *
 */
public class Department {
	
	private String departmentChosen;
	
	private Faculty faculty;
	
	/***
	 * Constructor that will initialzie the string depratment chosen to an empty string
	 */
	public Department()
	{
		departmentChosen = "";
	}
	
	/***
	 * Method that will set the department chosen
	 * @param departmentChosen parameter used to set the department chosen
	 */
	public void setDepartmentChosen(String departmentChosen)
	{
		this.departmentChosen = departmentChosen;
	}
	
	/***
	 * Method that will provide the machine with the department chosen
	 * @return the department chosen by the user
	 */
	public String getDepartmentChosen()
	{
		return departmentChosen;
	}
	
	/***
	 * Method that will set an object of type faculty to the faculty member in this department
	 * @param faculty parameter that will be used to set the faculty object 
	 */
	public void setFaculty(Faculty faculty)
	{
		this.faculty = faculty;
	}
	
	/***
	 * Method that will provide the machine with the faculty information
	 * @return faculty object
	 */
	public Faculty getFaculty()
	{
		return faculty;
	}

	

}
