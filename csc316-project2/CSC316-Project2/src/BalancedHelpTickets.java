import java.util.Scanner;

/**
 * The main user interface for a program that allows the user to create new help tickets by priority, store them in a BST, and retrieve them by a given ID.
 * 
 * @author Veronica Alban <valban@ncsu.edu>
 * @author Van Duong <vtduong@ncsu.edu>
 * @author Bennett Lynch <bblynch2@ncsu.edu>
 * @author Chris Tran <hvtran@ncsu.edu>
 */
public class BalancedHelpTickets
{
	static PriorityQueueWithPositionQueries<Integer, HelpTicket> ticketList = new AVLTree<Integer, HelpTicket>();
	
	public static void main(String[] args)
	{
		// if (args.length == 0) {
		IndentPrinter indent = new IndentPrinter(System.out, "   ");
		indent.increaseIndent();
		
		HelpTickets tickets = new HelpTickets();
		// keeps track of ticket's id
		int id = 0;
		
		// boolean loop = true;
		while (true)
		{
			// System.out.print("Input: ");
			Scanner input = new Scanner(System.in);
			
			String com = input.next();
			if (!com.equals("+") && !com.equals("-") && !com.equals("*") && !com.equals("?"))
			{
				
				System.out.println(com);
				indent.println("Invalid command " + com);
				continue;
			}
			else if (com.equals("+"))
			{
				String num = input.next();
				int priority = 0;
				System.out.println(com + " " + num);
				try
				{
					priority = Integer.parseInt(num);
					int idNum = tickets.addTicket(priority, ++id);
					indent.println("id = " + idNum);
				}
				catch (Warning e)
				{
					indent.println("Warning: a ticket with priority " + priority + " is already in the queue");
				}
				catch (NumberFormatException e)
				{
					indent.println("Warning: priority " + num + " is not an integer");
				}
				
			}
			else if (com.equals("-"))
			{
				String num = input.next();
				int idNum = 0;
				System.out.println(com + " " + num);
				try
				{
					
					idNum = Integer.parseInt(num);
					int position = ticketList.queryPositionById(idNum);
					HelpTicket ticket = tickets.removeTicket(idNum);
					indent.println(ticket.getPriority() + ", pos = " + position);
					continue;
				}
				catch (Warning e)
				{
					indent.println("Warning: There is no ticket with id = " + idNum + " in the queue");
				}
				catch (NumberFormatException e)
				{
					indent.println("Warning: id " + num + " is not an integer");
				}
				
			}
			else if (com.equals("?"))
			{
				String num = input.next();
				int idNum = 0;
				System.out.println(com + " " + num);
				try
				{
					
					idNum = Integer.parseInt(num);
					int position = tickets.findPositionByID(idNum);
					indent.println("pos = " + position);
					continue;
				}
				catch (Warning e)
				{
					indent.println("Warning: There is no ticket with id = " + idNum + " in the queue");
				}
				catch (NumberFormatException e)
				{
					indent.println("Warning: id " + num + " is not an integer");
				}
				
			}
			else if (com.equals("*"))
			{
				System.out.println(com);
				try
				{
					HelpTicket ticket = tickets.removeHighestPriority();
					indent.println("id = " + ticket.getId() + ", " + ticket.getPriority());
					continue;
				}
				catch (Warning e)
				{
					indent.println("removal attempted when queue is empty");
					continue;
				}
			}
			// input.close();
		}
		
		// } else {
		// readFile(args[0]);
		// }
		
	}
	
	/**
	 * Adds a ticket with the given priority.
	 * 
	 * @param priority
	 *            the given priority
	 * @throws Warning
	 * @output id; id’s are consecutive positive integers: the first + operation results in id 1, the second 2, etc.
	 */
	protected int addTicket(int priority, int id) throws Warning
	{
		// Any attempt to insert a ticket with the same priority as one already in the queue should generate a warning
		try
		{
			return ticketList.addByKey(new Integer(priority), new HelpTicket(priority, id));
		}
		catch (Warning e)
		{
			throw new Warning("a ticket with priority " + priority + " is already in the queue");
		}
	}
	
	/**
	 * Removes a ticket with the given id.
	 * 
	 * @param id
	 * @throws Warning
	 * @output priority (same as when the ticket was inserted) and current position in the queue;
	 *         any valid integer can be used as a priority
	 */
	protected HelpTicket removeTicket(int id) throws Warning
	{
		try
		{
			return ticketList.removeById(id);
		}
		catch (Warning e)
		{
			// A warning should be generated if there is an attempt to remove a ticket that is not in the queue
			throw new Warning("there is no ticket with id = " + id + " in the queue");
		}
		
	}
	
	/**
	 * Removes the ticket with highest priority.
	 * 
	 * @throws Warning
	 * @output the id and priority of the ticket with highest priority
	 */
	protected HelpTicket removeHighestPriority() throws Warning
	{
		try
		{
			return (HelpTicket) ticketList.removeMaximumKey();
		}
		catch (Warning e)
		{
			throw new Warning("Warning: removal attempted when queue is empty");
		}
		
	}
	
	/**
	 * Find the position of the given id.
	 * 
	 * @param id
	 * @output current position in the queue of the ticket with the given id
	 */
	protected int findPositionByID(int id) throws Warning
	{
		return ticketList.queryPositionById(id);
	}
	
	public String printTree()
	{
		return ticketList.toString();
		
	}
	
}
