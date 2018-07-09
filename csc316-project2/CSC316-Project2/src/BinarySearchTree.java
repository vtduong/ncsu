import java.util.TreeMap;

/**
 * A custom implementation of a normal binary search tree. The tree may become unbalanced, but it will always be properly ordered.
 * Supports generic key <K> (that must implement the Comparable interface) and generic value <V>.
 * This tree implementation differs from normal trees in that each new entry will be assigned a unique ID for external tracking.
 * No duplicate keys are allowed.
 * 
 * @author Veronica Alban <valban@ncsu.edu>
 * @author Van Duong <vtduong@ncsu.edu>
 * @author Bennett Lynch <bblynch2@ncsu.edu>
 * @author Chris Tran <hvtran@ncsu.edu>
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements PriorityQueueWithPositionQueries<K, V>
{
	/** The overall root of the tree. This starts off non-null itself but with a null key/value. */
	private Node root;
	
	/** Each new node is assigned a unique ID */
	private int lastIdAssigned;
	
	/** A separate list used solely to lookup items by their id (and find their corresponding priority) */
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
	 * Constructor to create a new, empty Binary Search Tree (no parameters allowed).
	 */
	BinarySearchTree()
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
	private K convertIdToKey(int id) throws Warning
	{
		// Determine what the associated key (e.g., priority) is with the given id
		K key = idLookupList.get(id);
		
		// If the id does not exist in the internal tracking list, throw an exception
		if (key == null)
			throw new Warning("given id does not exist");
		
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
	public int addByKey(K keyToAdd, V valueToAdd) throws Warning
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
				throw new Warning("cannot have duplicate keys");
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
		return lastIdAssigned;
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
		return current.value;
	}
	
	/**
	 * Returns the position in the queue of the element with the specified id.
	 * 
	 * @param id
	 *            unique id that was returned after adding it originally
	 * @return the position of the element
	 */
	public int queryPositionById(int id) throws Warning
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
				+ "]:");
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
				+ n.numRightDescendants + "]");
		
		if (n.right.value != null)
			listNodesRecurs(n.right);
		
	}
	
}
