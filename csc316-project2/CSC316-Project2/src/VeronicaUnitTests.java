import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class VeronicaUnitTests
{
	
	UnbalancedTree<Integer, String> testTree;
	
	@Before
	public void setUp() throws Exception
	{
		testTree = new UnbalancedTree<Integer, String>();
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
		
		// check to make sure that they are in the order they should be
		assertEquals(4, testTree.getPositionByID(idForJ));
		assertEquals(3, testTree.getPositionByID(idForA));
		assertEquals(7, testTree.getPositionByID(idForB));
		assertEquals(2, testTree.getPositionByID(idForC));
		assertEquals(9, testTree.getPositionByID(idForD));
		assertEquals(5, testTree.getPositionByID(idForE));
		assertEquals(1, testTree.getPositionByID(idForF));
		assertEquals(10, testTree.getPositionByID(idForG));
		assertEquals(8, testTree.getPositionByID(idForH));
		assertEquals(6, testTree.getPositionByID(idForI));
		
		// start removing stuff
		testTree.removeByID(idForF);
		//testTree.removeMax();
		assertEquals(9, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		assertEquals(4, testTree.getPositionByID(idForE));
		assertEquals(7, testTree.getPositionByID(idForH));
		assertEquals(9, testTree.getPositionByID(idForG));
		assertEquals(1, testTree.getPositionByID(idForC));
		assertEquals(2, testTree.getPositionByID(idForA));
		assertEquals(3, testTree.getPositionByID(idForJ));
		
	}
	
	@Test
	public void veronicaTests() throws Warning
	{
		// build my tree and check correct position on the way
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
		
		int idForK = testTree.add(12, "k");
		assertEquals(11, idForK);
		assertEquals(11, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(9, testTree.getPositionByID(idForK));
		
		int idForL = testTree.add(55, "l");
		assertEquals(12, idForL);
		assertEquals(12, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(8, testTree.getPositionByID(idForL));
		
		int idForM = testTree.add(11, "m");
		assertEquals(13, idForM);
		assertEquals(13, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(11, testTree.getPositionByID(idForM));
		
		int idForN = testTree.add(15, "n");
		assertEquals(14, idForN);
		assertEquals(14, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(10, testTree.getPositionByID(idForN));
		
		int idForO = testTree.add(14, "o");
		assertEquals(15, idForO);
		assertEquals(15, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(11, testTree.getPositionByID(idForO));
		
		int idForP = testTree.add(45, "p");
		assertEquals(16, idForP);
		assertEquals(16, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(10, testTree.getPositionByID(idForP));
		
		int idForQ = testTree.add(31, "q");
		assertEquals(17, idForQ);
		assertEquals(17, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(11, testTree.getPositionByID(idForQ));
		
		int idForR = testTree.add(20, "r");
		assertEquals(18, idForR);
		assertEquals(18, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(12, testTree.getPositionByID(idForR));
		
		int idForS = testTree.add(35, "s");
		assertEquals(19, idForS);
		assertEquals(19, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(11, testTree.getPositionByID(idForS));
		
		int idForT = testTree.add(34, "t");
		assertEquals(20, idForT);
		assertEquals(20, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(12, testTree.getPositionByID(idForT));
		
		int idForU = testTree.add(37, "u");
		assertEquals(21, idForU);
		assertEquals(21, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(11, testTree.getPositionByID(idForU));
		
		int idForV = testTree.add(29, "v");
		assertEquals(22, idForV);
		assertEquals(22, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(15, testTree.getPositionByID(idForV));
		
		int idForW = testTree.add(25, "w");
		assertEquals(23, idForW);
		assertEquals(23, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(16, testTree.getPositionByID(idForW));
		
		int idForX = testTree.add(30, "x");
		assertEquals(24, idForX);
		assertEquals(24, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(15, testTree.getPositionByID(idForX));
		
		int idForY = testTree.add(26, "y");
		assertEquals(25, idForY);
		assertEquals(25, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("a"));
		
		assertEquals(17, testTree.getPositionByID(idForY));
		
		// start removing nodes and see what happens
		
		// remove the root node
		testTree.removeByID(idForA);
		assertEquals(24, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(3, testTree.getPositionByID(idForJ));
		
		// remove a node that has no immediate children
		testTree.removeByID(idForG);
		assertEquals(23, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(23, testTree.getPositionByID(idForD));
		
		testTree.removeByID(idForR);
		assertEquals(22, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(15, testTree.getPositionByID(idForV));
		
		testTree.removeByID(idForQ);
		assertEquals(21, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(13, testTree.getPositionByID(idForX));
		assertEquals(14, testTree.getPositionByID(idForV));
		assertEquals(16, testTree.getPositionByID(idForW));
		assertEquals(15, testTree.getPositionByID(idForY));
		
		testTree.removeByID(idForK);
		assertEquals(20, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(18, testTree.getPositionByID(idForO));
		assertEquals(17, testTree.getPositionByID(idForN));
		assertEquals(9, testTree.getPositionByID(idForP));
		assertEquals(13, testTree.getPositionByID(idForX));
		
		testTree.removeByID(idForO);
		assertEquals(19, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(17, testTree.getPositionByID(idForN));
		assertEquals(9, testTree.getPositionByID(idForP));
		assertEquals(13, testTree.getPositionByID(idForX));
		assertEquals(11, testTree.getPositionByID(idForS));
		
		int idForZ = testTree.add(46, "z");
		assertEquals(26, idForZ);
		assertEquals(20, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		
		assertEquals(9, testTree.getPositionByID(idForZ));
		
		testTree.removeByID(idForN);
		assertEquals(19, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(16, testTree.getPositionByID(idForY));
		assertEquals(10, testTree.getPositionByID(idForP));
		assertEquals(14, testTree.getPositionByID(idForX));
		assertEquals(12, testTree.getPositionByID(idForS));
		assertEquals(17, testTree.getPositionByID(idForW));
		
		testTree.listNodes();
		
		testTree.removeByID(idForF);
		
		testTree.listNodes();

		/**assertEquals(18, testTree.getTreeSize());
		assertEquals(testTree.getTreeSize(), testTree.getIdListSize());
		assertTrue(testTree.getRootValue().equals("j"));
		assertEquals(1, testTree.getPositionByID(idForC));
		assertEquals(9, testTree.getPositionByID(idForP));
		assertEquals(13, testTree.getPositionByID(idForX));
		assertEquals(11, testTree.getPositionByID(idForS));
		assertEquals(16, testTree.getPositionByID(idForW));
		assertEquals(2, testTree.getPositionByID(idForJ));*/
	}
	
}
