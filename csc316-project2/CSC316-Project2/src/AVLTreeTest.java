import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest
{
	
	AVLTree<Integer, String> tree;
	
	@Before
	public void setUp() throws Exception
	{
		tree = new AVLTree<Integer, String>();
	}
	
	@Test
	public void basicTests() throws Warning
	{
		// Add a single node
		int idFor100 = tree.addByKey(100, "Entry 100");
		assertEquals(1, idFor100);
		assertEquals(1, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 100"));
		
		assertEquals(1, tree.queryPositionById(idFor100));
		
		// Add a 2nd node
		int idFor75 = tree.addByKey(75, "Entry 75");
		assertEquals(2, idFor75);
		assertEquals(2, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 100"));
		
		assertEquals(1, tree.queryPositionById(idFor100));
		assertEquals(2, tree.queryPositionById(idFor75));
		
		// Add a 3rd node
		int idFor125 = tree.addByKey(125, "Entry 125");
		assertEquals(3, idFor125);
		assertEquals(3, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 100"));
		
		assertEquals(1, tree.queryPositionById(idFor125));
		assertEquals(2, tree.queryPositionById(idFor100));
		assertEquals(3, tree.queryPositionById(idFor75));
		
		// Try to add a duplicate key
		try
		{
			idFor125 = tree.addByKey(125, "Entry 125");
			fail();
		}
		catch (Exception e)
		{
			assertEquals(3, tree.getTreeSize());
			assertEquals(tree.getTreeSize(), tree.getIdListSize());
			assertTrue(tree.getRootValue().equals("Entry 100"));
			
			assertEquals(1, tree.queryPositionById(idFor125));
			assertEquals(2, tree.queryPositionById(idFor100));
			assertEquals(3, tree.queryPositionById(idFor75));
		}
		
		// Remove the max (125,c)
		// tree.listNodes();
		tree.removeMaximumKey();
		
		assertEquals(2, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 100"));
		
		assertEquals(1, tree.queryPositionById(idFor100));
		assertEquals(2, tree.queryPositionById(idFor75));
		
		// Add 125 back and make sure the id was incremented by 1
		int idForD = tree.addByKey(125, "Entry 125 (Second)");
		assertEquals(4, idForD);
		assertEquals(3, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 100"));
		
		assertEquals(1, tree.queryPositionById(idForD));
		assertEquals(2, tree.queryPositionById(idFor100));
		assertEquals(3, tree.queryPositionById(idFor75));
		
		// Remove 125, but this time by id rather than max
		tree.removeById(idForD);
		assertEquals(2, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 100"));
		
		assertEquals(1, tree.queryPositionById(idFor100));
		assertEquals(2, tree.queryPositionById(idFor75));
		
		// tree.listNodes();
		
		// The tree should now have root 100-a and a left-child of 75-b
		// If we remove 100-a, 75-b should be moved to root
		tree.removeById(idFor100);
		
		// tree.listNodes();
		
		assertEquals(1, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertTrue(tree.getRootValue().equals("Entry 75"));
		
		assertEquals(1, tree.queryPositionById(idFor75));
		
		// Remove the max (which is the only node left: the root)
		tree.removeMaximumKey();
		assertEquals(0, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		assertNull(tree.getRootValue());
	}
	
	@Test
	public void advancedTests() throws Warning
	{
		int idFor100 = tree.addByKey(100, "Entry 100");
		int idFor50 = tree.addByKey(50, "Entry 50");
		int idFor150 = tree.addByKey(150, "Entry 150");
		int idFor125 = tree.addByKey(125, "Entry 125");
		int idFor175 = tree.addByKey(175, "Entry 175");
		int idFor25 = tree.addByKey(25, "Entry 25");
		int idFor75 = tree.addByKey(75, "Entry 75");
		int idFor170 = tree.addByKey(170, "Entry 170");
		int idFor180 = tree.addByKey(180, "Entry 180");
		int idFor70 = tree.addByKey(70, "Entry 70");
		int idFor80 = tree.addByKey(80, "Entry 80");
		int idFor120 = tree.addByKey(120, "Entry 120");
		int idFor130 = tree.addByKey(130, "Entry 130");
		int idFor20 = tree.addByKey(20, "Entry 20");
		int idFor30 = tree.addByKey(30, "Entry 30");
		
		// Make sure all of the ids are correct
		assertEquals(1, idFor100);
		assertEquals(2, idFor50);
		assertEquals(3, idFor150);
		assertEquals(4, idFor125);
		assertEquals(5, idFor175);
		assertEquals(6, idFor25);
		assertEquals(7, idFor75);
		assertEquals(8, idFor170);
		assertEquals(9, idFor180);
		assertEquals(10, idFor70);
		assertEquals(11, idFor80);
		assertEquals(12, idFor120);
		assertEquals(13, idFor130);
		assertEquals(14, idFor20);
		assertEquals(15, idFor30);
		
		// Make sure the tree was built to the right size
		assertEquals(15, tree.getTreeSize());
		assertEquals(15, tree.getIdListSize());
		
		// Test positions
		assertEquals(1, tree.queryPositionById(idFor180));
		assertEquals(2, tree.queryPositionById(idFor175));
		assertEquals(3, tree.queryPositionById(idFor170));
		assertEquals(4, tree.queryPositionById(idFor150));
		assertEquals(5, tree.queryPositionById(idFor130));
		assertEquals(6, tree.queryPositionById(idFor125));
		assertEquals(7, tree.queryPositionById(idFor120));
		assertEquals(8, tree.queryPositionById(idFor100));
		assertEquals(9, tree.queryPositionById(idFor80));
		assertEquals(10, tree.queryPositionById(idFor75));
		assertEquals(11, tree.queryPositionById(idFor70));
		assertEquals(12, tree.queryPositionById(idFor50));
		assertEquals(13, tree.queryPositionById(idFor30));
		assertEquals(14, tree.queryPositionById(idFor25));
		assertEquals(15, tree.queryPositionById(idFor20));
		
		// Test removal of a node with no children
		assertTrue(tree.removeById(idFor30).equals("Entry 30"));
		try
		{
			tree.queryPositionById(idFor30);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(14, tree.getTreeSize());
			assertEquals(14, tree.getIdListSize());
			assertEquals(13, tree.queryPositionById(idFor25));
			assertEquals(14, tree.queryPositionById(idFor20));
		}
		
		// Test removal of a node with 1 immediate child but no more
		assertTrue(tree.removeById(idFor25).equals("Entry 25"));
		try
		{
			tree.queryPositionById(idFor25);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(13, tree.getTreeSize());
			assertEquals(13, tree.getIdListSize());
			assertEquals(13, tree.queryPositionById(idFor20));
		}
		
		// Test removal of a node with 1 immediate child and exactly 1 more sub-child
		// First cut 20 and 70
		assertTrue(tree.removeById(idFor20).equals("Entry 20"));
		try
		{
			tree.queryPositionById(idFor20);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(12, tree.getTreeSize());
			assertEquals(12, tree.getIdListSize());
			assertEquals(9, tree.queryPositionById(idFor80));
			assertEquals(10, tree.queryPositionById(idFor75));
			assertEquals(11, tree.queryPositionById(idFor70));
			assertEquals(12, tree.queryPositionById(idFor50));
		}
		
		assertTrue(tree.removeById(idFor70).equals("Entry 70"));
		try
		{
			tree.queryPositionById(idFor70);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(11, tree.getTreeSize());
			assertEquals(11, tree.getIdListSize());
			assertEquals(9, tree.queryPositionById(idFor80));
			assertEquals(10, tree.queryPositionById(idFor75));
			assertEquals(11, tree.queryPositionById(idFor50));
		}
		
		// Now test the desired condition
		// tree.listNodes();
		assertTrue(tree.removeById(idFor80).equals("Entry 80"));
		// tree.listNodes();
		try
		{
			tree.queryPositionById(idFor80);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(10, tree.getTreeSize());
			assertEquals(10, tree.getIdListSize());
			assertEquals(9, tree.queryPositionById(idFor75));
			assertEquals(10, tree.queryPositionById(idFor50));
		}
		
		// tree.listNodes();
		// Test removal of a node with 2 immediate children but no more
		assertTrue(tree.removeById(idFor125).equals("Entry 125"));
		// tree.listNodes();
		try
		{
			tree.queryPositionById(idFor125);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(9, tree.getTreeSize());
			assertEquals(9, tree.getIdListSize());
			
			assertEquals(9, tree.queryPositionById(idFor50));
			assertEquals(8, tree.queryPositionById(idFor75));
			assertEquals(7, tree.queryPositionById(idFor100));
			assertEquals(6, tree.queryPositionById(idFor120));
			assertEquals(5, tree.queryPositionById(idFor130));
			assertEquals(4, tree.queryPositionById(idFor150));
			assertEquals(3, tree.queryPositionById(idFor170));
			assertEquals(2, tree.queryPositionById(idFor175));
			assertEquals(1, tree.queryPositionById(idFor180));
		}
		
		// Test removal of a node with 2 immediate children while being left heavy
		int idFor131 = tree.addByKey(131, "Entry 131");
		assertEquals(10, tree.getTreeSize());
		assertEquals(10, tree.getIdListSize());
		
		assertEquals(10, tree.queryPositionById(idFor50));
		assertEquals(9, tree.queryPositionById(idFor75));
		assertEquals(8, tree.queryPositionById(idFor100));
		assertEquals(7, tree.queryPositionById(idFor120));
		assertEquals(6, tree.queryPositionById(idFor130));
		assertEquals(5, tree.queryPositionById(idFor131));
		assertEquals(4, tree.queryPositionById(idFor150));
		assertEquals(3, tree.queryPositionById(idFor170));
		assertEquals(2, tree.queryPositionById(idFor175));
		assertEquals(1, tree.queryPositionById(idFor180));
		
		assertTrue(tree.removeById(idFor150).equals("Entry 150"));
		try
		{
			tree.queryPositionById(idFor150);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(9, tree.getTreeSize());
			assertEquals(9, tree.getIdListSize());
			
			assertEquals(9, tree.queryPositionById(idFor50));
			assertEquals(8, tree.queryPositionById(idFor75));
			assertEquals(7, tree.queryPositionById(idFor100));
			assertEquals(6, tree.queryPositionById(idFor120));
			assertEquals(5, tree.queryPositionById(idFor130));
			assertEquals(4, tree.queryPositionById(idFor131));
			// assertEquals(4, tree.queryPositionById(idFor150));
			assertEquals(3, tree.queryPositionById(idFor170));
			assertEquals(2, tree.queryPositionById(idFor175));
			assertEquals(1, tree.queryPositionById(idFor180));
		}
		
		// Test removal of a node with 2 immediate children while being right heavy
		assertTrue(tree.removeById(idFor131).equals("Entry 131"));
		try
		{
			tree.queryPositionById(idFor131);
			fail();
		}
		catch (Exception e)
		{
			assertEquals(8, tree.getTreeSize());
			assertEquals(8, tree.getIdListSize());
			
			assertEquals(8, tree.queryPositionById(idFor50));
			assertEquals(7, tree.queryPositionById(idFor75));
			assertEquals(6, tree.queryPositionById(idFor100));
			assertEquals(5, tree.queryPositionById(idFor120));
			assertEquals(4, tree.queryPositionById(idFor130));
			// assertEquals(4, tree.queryPositionById(idFor131));
			// assertEquals(4, tree.queryPositionById(idFor150));
			assertEquals(3, tree.queryPositionById(idFor170));
			assertEquals(2, tree.queryPositionById(idFor175));
			assertEquals(1, tree.queryPositionById(idFor180));
		}
		
		// Test removal of root with 2 children + subchildren
		// tree.listNodes();
		assertTrue(tree.removeById(idFor100).equals("Entry 100"));
		
		try
		{
			tree.queryPositionById(idFor100);
			fail();
		}
		catch (Exception e)
		{
			assertEquals("Entry 120", tree.getRootValue());
			
			// tree.listNodes();
			
			assertEquals(7, tree.getTreeSize());
			assertEquals(7, tree.getIdListSize());
			
			assertEquals(7, tree.queryPositionById(idFor50));
			assertEquals(6, tree.queryPositionById(idFor75));
			// assertEquals(6, tree.queryPositionById(idFor100));
			assertEquals(5, tree.queryPositionById(idFor120));
			assertEquals(4, tree.queryPositionById(idFor130));
			// assertEquals(4, tree.queryPositionById(idFor131));
			// assertEquals(4, tree.queryPositionById(idFor150));
			assertEquals(3, tree.queryPositionById(idFor170));
			assertEquals(2, tree.queryPositionById(idFor175));
			assertEquals(1, tree.queryPositionById(idFor180));
		}
		
		// Test removal of root with 2 children + no subchildren
		assertTrue(tree.removeById(idFor75).equals("Entry 75"));
		assertTrue(tree.removeById(idFor130).equals("Entry 130"));
		assertTrue(tree.removeById(idFor175).equals("Entry 175"));
		assertTrue(tree.removeById(idFor180).equals("Entry 180"));
		
		assertEquals(3, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		
		assertEquals(1, tree.queryPositionById(idFor170));
		assertEquals(2, tree.queryPositionById(idFor120));
		assertEquals(3, tree.queryPositionById(idFor50));
		
		assertTrue(tree.removeById(idFor120).equals("Entry 120"));
		
		assertEquals(2, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		
		assertEquals(1, tree.queryPositionById(idFor170));
		assertEquals(2, tree.queryPositionById(idFor50));
		
		// Test removal of a root with only 1 child
		assertTrue(tree.removeById(idFor50).equals("Entry 50"));
		
		assertEquals(1, tree.getTreeSize());
		assertEquals(tree.getTreeSize(), tree.getIdListSize());
		
		assertEquals(1, tree.queryPositionById(idFor170));
		
		// Test removal of a root with no children
		assertTrue(tree.removeById(idFor170).equals("Entry 170"));
		
		try
		{
			tree.removeMaximumKey();
			fail();
		}
		catch (Exception e)
		{
			assertEquals(0, tree.getTreeSize());
			assertEquals(tree.getTreeSize(), tree.getIdListSize());
		}
		
		// Make sure we can still add a node after clearing out the tree
		int SECONDidFor100 = tree.addByKey(100, "Entry 100");
		int SECONDidFor50 = tree.addByKey(50, "Entry 50");
		int SECONDidFor150 = tree.addByKey(150, "Entry 150");
		int SECONDidFor125 = tree.addByKey(125, "Entry 125");
		int SECONDidFor175 = tree.addByKey(175, "Entry 175");
		int SECONDidFor25 = tree.addByKey(25, "Entry 25");
		int SECONDidFor75 = tree.addByKey(75, "Entry 75");
		int SECONDidFor170 = tree.addByKey(170, "Entry 170");
		int SECONDidFor180 = tree.addByKey(180, "Entry 180");
		int SECONDidFor70 = tree.addByKey(70, "Entry 70");
		int SECONDidFor80 = tree.addByKey(80, "Entry 80");
		int SECONDidFor120 = tree.addByKey(120, "Entry 120");
		int SECONDidFor130 = tree.addByKey(130, "Entry 130");
		int SECONDidFor20 = tree.addByKey(20, "Entry 20");
		int SECONDidFor30 = tree.addByKey(30, "Entry 30");
		
		// Make sure all of the ids are correct
		assertEquals(17, SECONDidFor100);
		assertEquals(18, SECONDidFor50);
		assertEquals(19, SECONDidFor150);
		assertEquals(20, SECONDidFor125);
		assertEquals(21, SECONDidFor175);
		assertEquals(22, SECONDidFor25);
		assertEquals(23, SECONDidFor75);
		assertEquals(24, SECONDidFor170);
		assertEquals(25, SECONDidFor180);
		assertEquals(26, SECONDidFor70);
		assertEquals(27, SECONDidFor80);
		assertEquals(28, SECONDidFor120);
		assertEquals(29, SECONDidFor130);
		assertEquals(30, SECONDidFor20);
		assertEquals(31, SECONDidFor30);
		
		// Make sure the tree was built to the right size
		assertEquals(15, tree.getTreeSize());
		assertEquals(15, tree.getIdListSize());
		
		// Test positions
		assertEquals(1, tree.queryPositionById(SECONDidFor180));
		assertEquals(2, tree.queryPositionById(SECONDidFor175));
		assertEquals(3, tree.queryPositionById(SECONDidFor170));
		assertEquals(4, tree.queryPositionById(SECONDidFor150));
		assertEquals(5, tree.queryPositionById(SECONDidFor130));
		assertEquals(6, tree.queryPositionById(SECONDidFor125));
		assertEquals(7, tree.queryPositionById(SECONDidFor120));
		assertEquals(8, tree.queryPositionById(SECONDidFor100));
		assertEquals(9, tree.queryPositionById(SECONDidFor80));
		assertEquals(10, tree.queryPositionById(SECONDidFor75));
		assertEquals(11, tree.queryPositionById(SECONDidFor70));
		assertEquals(12, tree.queryPositionById(SECONDidFor50));
		assertEquals(13, tree.queryPositionById(SECONDidFor30));
		assertEquals(14, tree.queryPositionById(SECONDidFor25));
		assertEquals(15, tree.queryPositionById(SECONDidFor20));
		
	}
	
	@Test
	public void AVLspecificTests() throws Warning
	{
		// Try to create a very unbalanced (linear) tree
		
		int idFor20 = tree.addByKey(20, "Entry 20");
		int idFor25 = tree.addByKey(25, "Entry 25");
		int idFor30 = tree.addByKey(30, "Entry 30");
		int idFor50 = tree.addByKey(50, "Entry 50");
		int idFor70 = tree.addByKey(70, "Entry 70");
		int idFor75 = tree.addByKey(75, "Entry 75");
		int idFor80 = tree.addByKey(80, "Entry 80");
		int idFor100 = tree.addByKey(100, "Entry 100");
		int idFor120 = tree.addByKey(120, "Entry 120");
		int idFor125 = tree.addByKey(125, "Entry 125");
		int idFor130 = tree.addByKey(130, "Entry 130");
		int idFor150 = tree.addByKey(150, "Entry 150");
		int idFor170 = tree.addByKey(170, "Entry 170");
		int idFor175 = tree.addByKey(175, "Entry 175");
		int idFor180 = tree.addByKey(180, "Entry 180");
		
		tree.listNodes();
		
		// Try to create an unbalanced tree by deletion
		assertTrue(tree.removeById(idFor170).equals("Entry 170"));
		assertTrue(tree.removeById(idFor180).equals("Entry 180"));
		assertTrue(tree.removeById(idFor175).equals("Entry 175"));
		assertTrue(tree.removeById(idFor120).equals("Entry 120"));
		assertTrue(tree.removeById(idFor130).equals("Entry 130"));
		assertTrue(tree.removeById(idFor125).equals("Entry 125"));
		
		tree.listNodes();
		
	}
	
}
