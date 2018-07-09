import java.util.TreeMap;

/**
 * A custom implementation of an AVL tree. Lookup methods will always be order of logn, even in the worst case.
 * Supports generic key <K> (that must implement the Comparable interface) and generic value <V>.
 * This tree implementation differs from normal trees in that each new entry will be assigned a unique ID for external tracking.
 * No duplicate keys are allowed.
 * 
 * @author Veronica Alban <valban@ncsu.edu>
 * @author Van Duong <vtduong@ncsu.edu>
 * @author Bennett Lynch <bblynch2@ncsu.edu>
 * @author Chris Tran <hvtran@ncsu.edu>
 */
public class AVLTree<K extends Comparable<K>, V> implements PriorityQueueWithPositionQueries<K, V>
{
	/** The overall root of the tree. This starts off non-null itself but with a null key/value. */
	private Node root;
	
	/** Each new node is assigned a unique ID */
	private int lastIdAssigned;
	
	/** A separate list used solely to lookup items by their id (and find their corresponding priority) in log(n) time */
	TreeMap<Integer, K> idLookupList;
	
	/** An inner class used to represent a single node. */
	private class Node
	{
		/** How the node will be compared to other nodes for sorting */
		public K key;
		
		/** The generic contents of the node */
		public V value;
		
		/** The unique ID assigned to this node. */
		public int id;
		
		/** The number of children this node has. */
		public int numLeftDescendants, numRightDescendants;
		
		/** Height of a node is the longest path to a leaf (or the max height of its children + 1) */
		public int height = -1;
		
		/** Pointers to the left/right children (may be null). */
		public Node parent, left, right;
		
		/** Constructor creates a dummy node. Requires a pointer to parent (may be null in case of the root) */
		Node(Node parent)
		{
			this.parent = parent;
		}
		
		/** Determines if the given node is a left child of its parent. Returns true if left child, returns false if right child OR the node is the root. */
		public boolean isLeftChild()
		{
			if (this == root)
				return false;
			
			return parent.left == this;
		}
		
		/** Determines if the given node is a right child of its parent. Returns true if right child, returns false if left child OR the node is the root. */
		public boolean isRightChild()
		{
			if (this == root)
				return false;
			
			return parent.right == this;
		}
	}
	
	/**
	 * Constructor to create a new, empty AVL Tree (no parameters allowed).
	 */
	AVLTree()
	{
		lastIdAssigned = 0;
		idLookupList = new TreeMap<Integer, K>();
		
		// Start the list with an empty root (which is a dummy node but has no further dummy children)
		root = new Node(null);
	}
	
	/**
	 * Converts a given id into a key that can be used to search the tree with.
	 * If the id does not exist, an exception is thrown.
	 * 
	 * @param id
	 *            the unique id key assigned to the user
	 * @return the key that may then be used for lookups
	 * @throws Warning
	 *             if the id does not exist
	 */
	private K convertIdToKey(int id)
	{
		// Determine what the associated key (e.g., priority) is with the given id
		K key = idLookupList.get(id);
		
		// If the id does not exist in the internal tracking list, throw an exception
		if (key == null)
			throw new IllegalArgumentException("given id does not exist");
		
		return key;
	}
	
	/**
	 * Private helper method to delete a node from the tree.
	 * If the node had no children, then one of its dummy leaves are attached to the node's parent.
	 * If the node has one child, then that child is moved into the deleted node's position.
	 * If the node had two children, then a node from whichever sub-tree is heavier is moved into the deleted node's position.
	 * 
	 * @param n
	 *            node to delete
	 */
	private void deleteNode(Node n)
	{
		// First get a count of how many (non-dummy) immediate children the node has
		int numImmediateChildren = ((n.left.value != null) ? 1 : 0) + ((n.right.value != null) ? 1 : 0);
		
		switch (numImmediateChildren)
		{
		case 0: // The node has no children whatsoever (only 2 dummy nodes), so simply replace it with its left dummy node
			
			if (n.isLeftChild())
			{
				n.parent.left = n.left;
				n.left.parent = n.parent;
			}
			else if (n.isRightChild())
			{
				n.parent.right = n.left;
				n.left.parent = n.parent;
			}
			else
				root = new Node(null);
			
			idLookupList.remove(n.id);
			break;
		
		case 1: // The node has one immediate child (and that child may or may not have further children)
			
			if (n.left.value != null)
			{
				if (n.isLeftChild())
					n.parent.left = n.left;
				else if (n.isRightChild())
					n.parent.right = n.left;
				else
					root = n.left;
				
				n.left.parent = n.parent;
			}
			
			else
			// n.right.value != null)
			{
				if (n.isLeftChild())
					n.parent.left = n.right;
				else if (n.isRightChild())
					n.parent.right = n.right;
				else
					root = n.right;
				
				n.right.parent = n.parent;
			}
			
			idLookupList.remove(n.id);
			break;
		
		case 2: // The node has two immediate children (which may or may not have further children)
			
			// We must pick either the (right-most child of the left subtree) or the (left-most child of the right subtree)
			// and replace the node-to-be-removed with it.
			// This BST is not required to remain balanced, but since it is a trivial task, we will still pick from the heavier of the two trees.
			
			Node nodeToMove;
			if (n.numLeftDescendants >= n.numRightDescendants)
			{
				n.numLeftDescendants--;
				nodeToMove = n.left;
				while (nodeToMove.right.value != null)
				{
					nodeToMove.numRightDescendants--;
					nodeToMove = nodeToMove.right;
				}
			}
			else
			{
				n.numRightDescendants--;
				nodeToMove = n.right;
				while (nodeToMove.left.value != null)
				{
					nodeToMove.numLeftDescendants--;
					nodeToMove = nodeToMove.left;
				}
			}
			
			// Swap the 2 nodes
			swapNodes(n, nodeToMove);
			
			// N has now been swapped, but it may still have children (e.g., has a left child after finding right-most child in subtree)
			// Remove n by recursively calling this same method and letting case 0 or 1 handle it
			deleteNode(n);
		}
	}
	
	/** Swaps two nodes by updating their pointers and the pointers of all nodes adjacent to them. */
	private void swapNodes(Node a, Node b)
	{
		// Store temporary copies of the data associated with a
		Node aParentWas = a.parent;
		Node aLeftWas = a.left;
		Node aRightWas = a.right;
		int aNumLeftDescendantsWas = a.numLeftDescendants;
		int aNumRightDescendantsWas = a.numRightDescendants;
		int aHeightWas = a.height;
		boolean aWasLeftChild = a.isLeftChild();
		boolean aWasRightChild = a.isRightChild();
		
		// Store temp copies of whether p was a left/right child (for when we change a's pters)
		boolean bWasLeftChild = b.isLeftChild();
		boolean bWasRightChild = b.isRightChild();
		
		// Tell a's 3 pointers to point to where b is currently pointing (while making sure a never points to itself)
		
		if (b.parent == a)
			a.parent = b;
		else
			a.parent = b.parent;
		
		if (b.left == a)
			a.left = b;
		else
			a.left = b.left;
		
		if (b.right == a)
			a.right = b;
		else
			a.right = b.right;
		
		a.numLeftDescendants = b.numLeftDescendants;
		a.numRightDescendants = b.numRightDescendants;
		a.height = b.height;
		
		// Up to 3 pointers (1 child and 2 parents) are pointing at b and we want them to point at a instead (while making sure a never points to itself)
		
		if (!bWasLeftChild && !bWasRightChild)
			root = a;
		else if (bWasLeftChild && b.parent != a)
			b.parent.left = a;
		else if (bWasRightChild && b.parent != a)
			b.parent.right = a;
		
		if (b.left != null && b.left != a)
			b.left.parent = a;
		if (b.right != null && b.right != a)
			b.right.parent = a;
		
		// Tell b's 3 pointers to point to where a WAS pointing (while making sure b never points to itself)
		
		if (aParentWas == b)
			b.parent = a;
		else
			b.parent = aParentWas;
		
		if (aLeftWas == b)
			b.left = a;
		else
			b.left = aLeftWas;
		
		if (aRightWas == b)
			b.right = a;
		else
			b.right = aRightWas;
		
		b.numLeftDescendants = aNumLeftDescendantsWas;
		b.numRightDescendants = aNumRightDescendantsWas;
		b.height = aHeightWas;
		
		// Up to 3 pointers (1 child and 2 parents) are pointing at a and we want them to point at b instead (while making sure b never points to itself)
		
		if (!aWasLeftChild && !aWasRightChild)
			root = b;
		else if (aWasLeftChild && aParentWas != b)
			aParentWas.left = b;
		else if (aWasRightChild && aParentWas != b)
			aParentWas.right = b;
		
		if (aLeftWas != b)
			aLeftWas.parent = b;
		if (aRightWas != b)
			aRightWas.parent = b;
	}
	
	/**
	 * Add a new item to the tree that is to be sorted by <K> and stores the contents of <V>.
	 * 
	 * @param keyToAdd
	 *            how the node will be compared to other nodes for sorting
	 * @param valueToAdd
	 *            the contents to be stored in the node
	 * @return the unique id assigned to this node
	 * @throws Warning
	 *             if the key is null or already exists
	 */
	public int addByKey(K keyToAdd, V valueToAdd)
	{
		if (keyToAdd == null)
			throw new IllegalArgumentException("key to add cannot be null");
		
		// Find a suitable place to insert the new key (with no duplicates allowed)
		Node current = root;
		while (current.value != null)
		{
			if (keyToAdd.compareTo(current.key) < 0)
			{
				current.numLeftDescendants++;
				current = current.left;
			}
			else if (keyToAdd.compareTo(current.key) > 0)
			{
				current.numRightDescendants++;
				current = current.right;
			}
			else
			{
				// Duplicate entry detected, unfortunately we must backtrack to the root to decrement all the counts
				while (current != root)
				{
					if (current.isLeftChild())
						current.parent.numLeftDescendants--;
					else
						current.parent.numRightDescendants--;
					current = current.parent;
				}
				throw new IllegalStateException("cannot have duplicate keys");
			}
		}
		
		// current should now be on a dummy node with empty key/value
		
		// Assign the given values
		current.key = keyToAdd;
		current.value = valueToAdd;
		
		// Create two new dummy node children (pointing upwards to current)
		current.left = new Node(current);
		current.right = new Node(current);
		
		// Assign and return a newly created unique id
		idLookupList.put(++lastIdAssigned, keyToAdd);
		current.id = lastIdAssigned;
		
		// Update the node's height (which will update its parents recursively)
		updateHeights(current);
		
		// Check AVL property
		checkBalance(current);
		
		return lastIdAssigned;
	}
	
	/** Updates the height of the given node and all of its parents */
	private void updateHeights(Node n)
	{
		if (n == null)
			return;
		
		// We must call this upwards after insertion, because we don't know (for example) if the height of the root will change or not at first
		
		// int heightWas = n.height;
		
		// Height of a node is the longest path to a leaf (or the max height of its children + 1)
		n.height = Math.max(n.left.height, n.right.height) + 1;
		
		// If the height did change, and the node does have a parent, call itself recursively
		// if (n.height != heightWas && n.parent != null)
		updateHeights(n.parent);
	}
	
	/** Checks and restores the AVL balance to the tree (where the given node is the bottom of a list of 3) */
	private void checkBalance(Node n)
	{
		// Trees must have at least 3 rows to become unbalanced
		if (n.parent == null || n.parent.parent == null)
			return;
		
		if (Math.abs(Math.max(0, n.parent.parent.left.height) - Math.max(0, n.parent.parent.right.height)) > 1)
		{
			// Tree is unbalanced, we have 4 cases to look for (LR, LL, RL, RR)
			// The worst cases are when it creates a zig-zag pattern, so address those first
			// See: http://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/AVL_Tree_Rebalancing.svg/2000px-AVL_Tree_Rebalancing.svg.png
			
			if (n.isRightChild() && n.parent.isLeftChild())
			{
				// Left-Right Case
				Node nParentWas = n.parent;
				
				// Put 4 into 3's old position, while temporarily detaching 3
				n.parent = nParentWas.parent;
				nParentWas.parent.left = n;
				
				// Move 'b' from 4 to 3
				nParentWas.right = n.left;
				n.left.parent = nParentWas;
				
				// Reattach 3
				n.left = nParentWas;
				nParentWas.parent = n;
				
				// Update children count/heights
				if (nParentWas.right.value == null)
					nParentWas.numRightDescendants = 0;
				else
					nParentWas.numRightDescendants = 1 + nParentWas.right.numLeftDescendants + nParentWas.right.numRightDescendants;
				
				n.numLeftDescendants = 1 + nParentWas.numLeftDescendants + nParentWas.numRightDescendants;
				
				updateHeights(nParentWas);
				updateHeights(n);
				
				// Point n to the bottom node for consistency in LL/RR cases
				n = n.left;
			}
			else if (n.isLeftChild() && n.parent.isRightChild())
			{
				// Right-Left Case
				Node nParentWas = n.parent;
				
				// Put 4 into 5's old position, while temporarily disjointing 5
				n.parent = nParentWas.parent;
				nParentWas.parent.right = n;
				
				// Move 'c' from 4 to 5
				nParentWas.left = n.right;
				n.right.parent = nParentWas;
				
				// Reattach 5
				n.right = nParentWas;
				nParentWas.parent = n;
				
				// Update children count/heights
				if (nParentWas.left.value == null)
					nParentWas.numLeftDescendants = 0;
				else
					nParentWas.numLeftDescendants = 1 + nParentWas.left.numLeftDescendants + nParentWas.left.numRightDescendants;
				
				updateHeights(nParentWas);
				updateHeights(n);
				
				// Point n to the bottom node for consistency in LL/RR cases
				n = n.right;
			}
			
			// Tree is now in either left-left case or right-right case (with n being the bottom node)
			if (n.isLeftChild())
			{
				// Left-Left Case
				// Node node3 = n;
				Node node4 = n.parent;
				Node node5 = n.parent.parent;
				
				// Temporarily detach 5
				node4.parent = node5.parent;
				
				if (node5.isLeftChild())
					node5.parent.left = node4;
				else if (node5.isRightChild())
					node5.parent.right = node4;
				else
					root = node4;
				
				// Move c from 4 onto 5
				node5.left = node4.right;
				node4.right.parent = node5;
				
				// Reattach 5
				node4.right = node5;
				node5.parent = node4;
				
				// Update children count/heights
				if (node5.left.value == null)
					node5.numLeftDescendants = 0;
				else
					node5.numLeftDescendants = 1 + node5.left.numLeftDescendants + node5.left.numRightDescendants;
				
				node4.numRightDescendants = 1 + node5.numLeftDescendants + node5.numRightDescendants;
				
				updateHeights(node5);
				updateHeights(node4);
			}
			else
			{
				// Right-Right Case
				// Node node5 = n;
				Node node4 = n.parent;
				Node node3 = n.parent.parent;
				
				// Temporarily detach 3
				node4.parent = node3.parent;
				
				if (node3.isLeftChild())
					node3.parent.left = node4;
				else if (node3.isRightChild())
					node3.parent.right = node4;
				else
					root = node4;
				
				// Move 'b' from 4 to 3
				node3.right = node4.left;
				node4.left.parent = node3;
				
				// Reattach 3
				node4.left = node3;
				node3.parent = node4;
				
				// Update children count/heights
				if (node3.right.value == null)
					node3.numRightDescendants = 0;
				else
					node3.numRightDescendants = 1 + node3.right.numLeftDescendants + node3.right.numRightDescendants;
				
				node4.numLeftDescendants = 1 + node3.numLeftDescendants + node3.numRightDescendants;
				
				updateHeights(node3);
				updateHeights(node4);
				
			}
			
			// We made a change to the tree, so recursively check the root of this sub-tree for balance
			checkBalance(n.parent);
		}
		
	}
	
	/**
	 * Remove a node by the unique id that was assigned to it upon creation (this is different than the key used to add it originally).
	 * 
	 * @param idToRemove
	 *            unique id that was returned after adding it originally
	 * @return the generic <V> contents stored in the item
	 * @throws Warning
	 *             if the key doesn't exist
	 */
	public V removeById(int idToRemove) throws Warning
	{
		// If key doesn't exist, an exception will be thrown and propagate upwards from convertIdToKey
		K keyToRemove = convertIdToKey(idToRemove);
		Node current = root;
		
		// Loop until we find the given key
		while (keyToRemove.compareTo(current.key) != 0)
		{
			if (keyToRemove.compareTo(current.key) < 0)
			{
				current.numLeftDescendants--;
				current = current.left;
			}
			else
			{
				current.numRightDescendants--;
				current = current.right;
			}
		}
		
		deleteNode(current);
		updateHeights(current.parent);
		deletionRebalance(current.parent);
		return current.value;
	}
	
	/**
	 * Removes the element with the largest key (priority) from the tree and returns the value of the removed element
	 * 
	 * @return the generic <V> contents stored in the item
	 * @throws Warning
	 *             if the tree is empty
	 */
	public V removeMaximumKey() throws Warning
	{
		if (root.value == null)
			throw new Warning("The tree is empty and has no maximum key.");
		
		Node current = root;
		
		while (current.right.value != null)
		{
			current.numRightDescendants--;
			current = current.right;
		}
		
		deleteNode(current);
		updateHeights(current.parent);
		deletionRebalance(current.parent);
		return current.value;
	}
	
	/** Called after every deletion. Will check from node n and upwards to see if any rebalances need to be performed. */
	private void deletionRebalance(Node n)
	{
		if (n == null)
			return;
		
		// When deleting a node we may cause the tree to become unbalanced
		
		// We're going to go up the tree from the deleted node until we find a node violating the AVL property
		
		while (Math.abs(Math.max(0, n.left.height) - Math.max(0, n.right.height)) <= 1) // while this node is not breaking the AVL property
		{
			if (n.parent == null) // We examined up to the root and found no AVL violations, so return early
				return;
			n = n.parent;
		}
		
		// After we find that node, we travel 2 nodes downwards on its longest path, and call the AVL rebalance method on that bottom node
		if (n.left.height > n.right.height)
			n = n.left;
		else
			n = n.right;
		
		if (n.left.height > n.right.height)
			n = n.left;
		else
			n = n.right;
		
		// Call the AVL fix method, which will recursively call itself if needed
		checkBalance(n);
	}
	
	/**
	 * Returns the position in the queue of the element with the specified id.
	 * 
	 * @param id
	 *            unique id that was returned after adding it originally
	 * @return the position of the element
	 */
	public int queryPositionById(int id)
	{
		// To determine its position, we need to count up the total number of children to the right of the node (including above)
		K keyToFind = convertIdToKey(id);
		
		Node current = root;
		int position = 1;
		
		while (keyToFind.compareTo(current.key) != 0)
		{
			if (keyToFind.compareTo(current.key) < 0)
			{
				// We're going left in the tree, that means that this node and all of its right subchildren are greater than the node we're looking for
				position += current.numRightDescendants + 1;
				current = current.left;
			}
			else if (keyToFind.compareTo(current.key) > 0)
			{
				current = current.right;
			}
		}
		
		// As a last step, add this node's right children
		position += current.numRightDescendants;
		
		return position;
	}
	
	// ///////////
	// ///////////
	// ///////////
	// ///////////
	// /////////// TESTING METHODS BELOW (remove before submitting)
	// ///////////
	// ///////////
	// ///////////
	// ///////////
	
	public int getRootHeight()
	{
		return root.height;
	}
	
	// private void printNode(Node n)
	// {
	//
	// if (n == root)
	// System.out.println("key=[" + n.key + "] left.key=[" + ((n.left != null) ? n.left.key : "no left node") + "] right.key=["
	// + ((n.right != null) ? n.right.key : "no right node") + "] left.parent.key=[" + ((n.left != null) ? n.left.parent.key : "no left node")
	// + "] right.parent.key=[" + ((n.right != null) ? n.right.parent.key : "no right node") + "] root.key=[" + root.key + "]");
	// else
	// System.out.println("key=[" + n.key + "] left.key=[" + ((n.left != null) ? n.left.key : "no left node") + "] right.key=["
	// + ((n.right != null) ? n.right.key : "no right node") + "] left.parent.key=[" + ((n.left != null) ? n.left.parent.key : "no left node")
	// + "] right.parent.key=[" + ((n.right != null) ? n.right.parent.key : "no right node") + "] parent.left.key=[" + n.parent.left.key
	// + "] parent.right.key=[" + n.parent.right.key + "]");
	//
	// }
	
	public V getValueByID(int id) throws Warning
	{
		K key = idLookupList.get(id);
		Node current = root;
		
		while (current != null && current.value != null && key != current.key)
		{
			
			if (key.compareTo(current.key) < 0)
			{
				
				current = current.left;
			}
			else if (key.compareTo(current.key) > 0)
			{
				current = current.right;
			}
		}
		if (current == null)
		{
			throw new Warning();
		}
		return current.value;
	}
	
	public int getTreeSize()
	{
		if (root.value == null)
			return 0;
		else
			return 1 + root.numLeftDescendants + root.numRightDescendants;
	}
	
	public int getIdListSize()
	{
		return idLookupList.size();
	}
	
	public V getRootValue()
	{
		return root.value;
	}
	
	public void listNodes()
	{
		System.out.println("Tree with rootKey=[" + root.key + "] & leftChildren=[" + root.numLeftDescendants + "] & rightChildren=[" + root.numRightDescendants
				+ "] & height=[" + root.height + "]:");
		listNodesRecurs(root);
		System.out.println();
	}
	
	private void listNodesRecurs(Node n)
	{
		if (n.left.value != null)
			listNodesRecurs(n.left);
		
		// for (int i = 0; i < indent; i++)
		// System.out.print("     ");
		
		System.out.println("Node: thisKey=[" + n.key + "], leftKey=[" + n.left.key + "], rightKey=[" + n.right.key + "], parentKey=["
				+ ((n.parent != null) ? n.parent.key : "NULL") + "], numLeftChildren=[" + n.numLeftDescendants + "], numRightChildren=["
				+ n.numRightDescendants + "], height=[" + n.height + "]");
		
		if (n.right.value != null)
			listNodesRecurs(n.right);
		
	}
	
}
