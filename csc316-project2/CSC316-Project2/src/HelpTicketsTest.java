import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HelpTicketsTest
{
	private HelpTickets help;
	
	// private UnbalancedTree<Integer, HelpTicket> tree;
	@Before
	public void SetUp()
	{
		help = new HelpTickets();
		// tree = new UnbalancedTree<Integer, HelpTicket>();
	}
	
	@Test
	public void testAddTicket()
	{
		// add a node with prio 100 and one with prio 75
		int id1 = 100;
		int id2 = 75;
		int id3 = 125;
		int id4 = 60;
		int id5 = 70;
		int id6 = 45;
		int id7 = 120;
		int id8 = 135;
		int id9 = 143;
		int id10 = 136;
		// try
		// {
		// assertEquals(help.addTicket(100, id1), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(75, id2), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(125, id3), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(60, id4), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(70, id5), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(45, id6), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(120, id7), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(135, id8), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(143, id9), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(136, id10), help.getHelpTickets().getIdListSize());
		// }
		// catch (Warning e)
		// {
		// System.out.printf("The ticket with id = %d already exists\n", id1);
		// }
		
		try
		{
			assertEquals(2, help.findPositionByID(10));
		}
		catch (Warning e)
		{
			System.out.printf("There is no ticket with id = %d.", id1);
		}
	}
	
	@Test
	public void testRemoveById()
	{
		
	}
	
	private void reset()
	{
		help = new HelpTickets();
		int id1 = 100;
		int id2 = 75;
		int id3 = 125;
		int id4 = 60;
		int id5 = 70;
		int id6 = 45;
		int id7 = 120;
		int id8 = 135;
		int id9 = 143;
		int id10 = 136;
		// try {
		// assertEquals(help.addTicket(100, id1), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(75, id2), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(125, id3), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(60, id4), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(70, id5), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(45, id6), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(120, id7), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(135, id8), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(143, id9), help.getHelpTickets().getIdListSize());
		// assertEquals(help.addTicket(136, id10), help.getHelpTickets().getIdListSize());
		// } catch (Warning e) {
		// fail();
		// }
	}
}
