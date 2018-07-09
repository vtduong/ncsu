import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ChrisUnitTests
{
	
	BinarySearchTree<Integer, String> tree;
	
	@Before
	public void setUp() throws Exception
	{
		tree = new BinarySearchTree<Integer, String>();
	}
	
	// basic tests -addByKey -getIdListSize -getTreeSize -getRootValue -removeMaximumKey
	@Test
	public void basicTests() throws Warning
	{
		assertEquals(0, tree.getIdListSize());
		assertEquals(0, tree.getTreeSize());
		
		// Adding first node
		int id1 = tree.addByKey(25, "root");
		assertTrue(tree.getRootValue().equals("1"));
		assertEquals(1, tree.getIdListSize());
		assertEquals(1, tree.getTreeSize());
		
		// Adding left and right node
		int id2 = tree.addByKey(20, "left");
		assertEquals(2, tree.getTreeSize());
		
		int id3 = tree.addByKey(30, "right");
		assertEquals(3, tree.getTreeSize());
		
		// Adding duplicate key
		try
		{
			int id4 = tree.addByKey(30, "dupe");
			fail();
		}
		catch (IllegalStateException e)
		{
			assertEquals(3, tree.getTreeSize());
		}
		
		// Adding null key
		try
		{
			int id4 = tree.addByKey(null, "null");
			fail();
		}
		catch (IllegalArgumentException e)
		{
			assertEquals(3, tree.getTreeSize());
		}
		
		// Testing queryPositionById
		assertTrue(tree.queryPositionById(id1) > tree.queryPositionById(id2) &&
				tree.queryPositionById(id3) > tree.queryPositionById(id1));
		
		// Testing removeMaximumKey
		try
		{
			assertTrue(tree.removeMaximumKey().equals("right"));
			assertEquals(2, tree.getTreeSize());
		}
		catch (Warning e)
		{
			fail();
		}
		
	}
	
	// Advanced tests for -removeById -swapNodes -deleteNodes
	@Test
	public void advancedTest() throws Warning
	{
		int id1 = tree.addByKey(100, "1");
		int id2 = tree.addByKey(75, "2");
		int id3 = tree.addByKey(150, "3");
		int id4 = tree.addByKey(70, "4");
		int id5 = tree.addByKey(80, "5");
		int id6 = tree.addByKey(170, "6");
		int id7 = tree.addByKey(50, "7");
		int id8 = tree.addByKey(71, "8");
		int id9 = tree.addByKey(76, "9");
		int id10 = tree.addByKey(200, "10");
		int id11 = tree.addByKey(30, "11");
		int id12 = tree.addByKey(60, "12");
		int id13 = tree.addByKey(175, "13");
		int id14 = tree.addByKey(205, "14");
		int id15 = tree.addByKey(61, "15");
		int id16 = tree.addByKey(174, "16");
		int id17 = tree.addByKey(203, "17");
		int id18 = tree.addByKey(207, "18");
		int id19 = tree.addByKey(65, "19");
		int id20 = tree.addByKey(171, "20");
		int id21 = tree.addByKey(202, "21");
		int id22 = tree.addByKey(204, "22");
		int id23 = tree.addByKey(62, "23");
		int id24 = tree.addByKey(172, "24");
	}
	
}
