
public class staff extends Faculty{
	
	private String staffName;
	private String staffEmail;
	
	public staff()
	{
		staffName = "";
		staffEmail = "";
	}
	
	public void setEmail(String staffEmail)
	{
		this.staffEmail = staffEmail;
	}
	
	public void setName(String staffName)
	{
		this.staffName = staffName;
	}
	
	public String getEmail()
	{
		return staffEmail;
	}
	
	public String getName()
	{
		return staffName;
	}

}
