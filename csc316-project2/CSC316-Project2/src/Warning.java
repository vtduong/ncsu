/**
 * A custom exception that represents a warning message for our help ticket tool.
 * 
 * @author Veronica Alban <valban@ncsu.edu>
 * @author Van Duong <vtduong@ncsu.edu>
 * @author Bennett Lynch <bblynch2@ncsu.edu>
 * @author Chris Tran <hvtran@ncsu.edu>
 */
public class Warning extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public Warning()
	{
		super();
	}
	
	public Warning(String arg0)
	{
		super(arg0);
	}
	
	public Warning(Throwable arg0)
	{
		super(arg0);
	}
	
	public Warning(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}
	
}
