import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VanUnitTests
{
	
	UnbalancedTree<Integer, String> testTree;
	UnbalancedTree<Integer, String> tree;
	
	@Before
	public void setUp() throws Exception
	{
		testTree = new UnbalancedTree<Integer, String>();
		tree = new UnbalancedTree<Integer, String>();
	}
	
	@Test
	public void vanTests() throws Warning
	{
		int id1 = tree.add(100, "A");
		int id2 = tree.add(75, "B");
		int id3 = tree.add(125, "C");
		int id4 = tree.add(60, "D");
		int id5 = tree.add(70, "E");
		int id6 = tree.add(45, "F");
		int id7 = tree.add(135, "G");
		int id8 = tree.add(80, "H");
		int id9 = tree.add(120, "I");
		int id10 = tree.add(143, "J");
		int id11 = tree.add(124, "K");
		int id12 = tree.add(136, "L");
		
		UnbalancedTree<Integer, String> tree1 = tree;
		
		assertEquals(12, tree1.getIdListSize());
		assertEquals(12, tree1.getTreeSize());
		
		// remove the root id1, new root is id9
		assertEquals("A", tree1.getValueByID(id1));
		assertEquals(tree1.getValueByID(id1), tree1.removeByID(id1));
		assertEquals(6, tree1.getPositionByID(id9));
		tree1 = resetTree();
		
		assertEquals(12, tree1.getIdListSize());
		assertEquals(12, tree1.getTreeSize());
		// remove id11 and id 12
		assertEquals(tree1.getValueByID(id11), tree1.removeByID(id11));
		assertEquals(tree1.getValueByID(id12), tree1.removeByID(id12));
		assertEquals(6, tree1.getPositionByID(id8));
		assertEquals(tree1.getValueByID(id10), tree1.removeMax());
		
		UnbalancedTree<Integer, String> tree2 = new UnbalancedTree<Integer, String>();
		
		int i1 = tree2.add(100, "A");
		int i2 = tree2.add(75, "B");
		int i3 = tree2.add(125, "C");
		int i4 = tree2.add(60, "D");
		
		// remove i4
		assertEquals(tree2.getValueByID(i4), tree2.removeByID(i4));
		tree2 = resetTree();
		
		//remove root
		assertEquals(tree2.getValueByID(id1), tree2.removeByID(id1));
	}
	
	@Test
	public void chrisTests() throws Warning
	{
		// todo
	}
	
	@Test
	public void veronicaTests2() throws Warning
	{
		int idForA = testTree.add(100, "a");
		assertEquals(1, idForA);
		assertEquals(1, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(1, testTree.getPositionByID(idForA));
		
		int idForB = testTree.add(60, "b");
		assertEquals(2, idForB);
		assertEquals(2, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(2, testTree.getPositionByID(idForB));
		
		int idForC = testTree.add(115, "c");
		assertEquals(3, idForC);
		assertEquals(3, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(1, testTree.getPositionByID(idForC));
		
		int idForD = testTree.add(10, "d");
		assertEquals(4, idForD);
		assertEquals(4, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(4, testTree.getPositionByID(idForD));
		
		int idForE = testTree.add(75, "e");
		assertEquals(5, idForE);
		assertEquals(5, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(3, testTree.getPositionByID(idForE));
		
		int idForF = testTree.add(200, "f");
		assertEquals(6, idForF);
		assertEquals(6, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(1, testTree.getPositionByID(idForF));
		
		int idForG = testTree.add(2, "g");
		assertEquals(7, idForG);
		assertEquals(7, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(7, testTree.getPositionByID(idForG));
		
		int idForH = testTree.add(50, "h");
		assertEquals(8, idForH);
		assertEquals(8, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(6, testTree.getPositionByID(idForH));
		
		int idForI = testTree.add(70, "i");
		assertEquals(9, idForI);
		assertEquals(9, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(5, testTree.getPositionByID(idForI));
		
		int idForJ = testTree.add(80, "j");
		assertEquals(10, idForJ);
		assertEquals(10, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(4, testTree.getPositionByID(idForJ));
		
		// start removing stuff
		testTree.removeByID(idForF);
//		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
//		assertEquals(4, testTree.getPositionByID(idForE));
		assertEquals(7, testTree.getPositionByID(idForH));
		assertEquals(9, testTree.getPositionByID(idForG));
		assertEquals(1, testTree.getPositionByID(idForC));
		assertEquals(2, testTree.getPositionByID(idForA));
		assertEquals(3, testTree.getPositionByID(idForJ));
	}
	
	public UnbalancedTree<Integer, String> resetTree()
	{
		UnbalancedTree<Integer, String> tree = new UnbalancedTree<Integer, String>();
		
		try
		{
			int id1 = tree.add(100, "A");
			int id2 = tree.add(75, "B");
			int id3 = tree.add(125, "C");
			int id4 = tree.add(60, "D");
			int id5 = tree.add(70, "E");
			int id6 = tree.add(45, "F");
			int id7 = tree.add(135, "G");
			int id8 = tree.add(80, "H");
			int id9 = tree.add(120, "I");
			int id10 = tree.add(143, "J");
			int id11 = tree.add(124, "K");
			int id12 = tree.add(136, "L");
		}
		catch (Warning e)
		{
			fail();
		}
		return tree;
	}
	
}
