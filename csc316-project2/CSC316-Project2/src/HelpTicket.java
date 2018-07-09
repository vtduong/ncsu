/**
 * Represents a single instance of a unique help ticket.
 * 
 * @author Veronica Alban <valban@ncsu.edu>
 * @author Van Duong <vtduong@ncsu.edu>
 * @author Bennett Lynch <bblynch2@ncsu.edu>
 * @author Chris Tran <hvtran@ncsu.edu>
 */
public class HelpTicket
{
	
	/** The unqiue id that was assigned to this ticket */
	private int id;
	
	/** The priority that was given to this ticket. */
	int priority;
	
	/**
	 * Constructor for HelpTicket.
	 * 
	 * @param priority
	 * @param id
	 */
	public HelpTicket(int priority, int id)
	{
		super();
		this.id = id;
		this.priority = priority;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getPriority()
	{
		return this.priority;
	}
	
}
