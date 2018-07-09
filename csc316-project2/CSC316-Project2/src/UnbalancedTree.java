import java.util.Arrays;
import java.util.Map;
//import java.util.ArrayList;
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
public class UnbalancedTree<K extends Comparable<K>, V>
{
	/** The overall root of the tree */
	private Node root;
	
	/** Each new node is assigned a unique ID */
	private int lastIdAssigned;
	
	/** A separate list used solely to lookup items by their id (and find their corresponding priority) */
	TreeMap<Integer, K> idLookupList;
	
	/**
	 * Constructor
	 */
	UnbalancedTree()
	{
		lastIdAssigned = 0;
		idLookupList = new TreeMap<Integer, K>();
		
		// Start the list with an empty root
		root = new Node(null);
	}
	
	/**
	 * Add a new item to the list, with priority (not id) as the key.
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws Warning
	 */
	public int add(K key, V value) throws Warning
	{
		// Make sure the element to be added actually has a value (and does not interfere with our dummy node usage)
		if (key == null || value == null)
			throw new IllegalArgumentException();
		
		// Set the first node to examine as the root
		Node current = root;
		
		// Keep going left/right until we find a dummy node to replace
		while (current.value != null)
		{
			if (key.compareTo(current.key) < 0)
			{
				current.numLeftDescendants++;
				current = current.left;
			}
			else if (key.compareTo(current.key) > 0)
			{
				current.numRightDescendants++;
				current = current.right;
			}
			else
			{
				// The key already exists (and our implementation does not allow duplicates)
				// We need to undo the descendant increase we just performed, then throw an exception
				while (current.parent != null)
				{
					current = current.parent;
					if (key.compareTo(current.key) < 0)
						current.numLeftDescendants--;
					else if (key.compareTo(current.key) > 0)
						current.numRightDescendants--;
				}
				throw new IllegalArgumentException("key already exists");
			}
		}
		
		// Current is now pointing to a dummy leaf
		// Update current's contents, and create two new dummy leaves to point to
		current.key = key;
		current.value = value;
		current.left = new Node(current);
		current.right = new Node(current);
		
		// Create a new unique ID to use
		lastIdAssigned++;
		
		// Internally track the new node by its newly assigned ID
		idLookupList.put(lastIdAssigned, key);
		
		// Tell the node its ID
		current.id = lastIdAssigned;
		
		// Return the ID (for printing/handling by client class)
		return lastIdAssigned;
	}
	
	/**
	 * Remove an entry by the unique id that was assigned to it
	 * 
	 * @param id
	 */
	public V removeByID(int id) throws Warning
	{
		// Determine what the associated priority is with the given id
		K key = idLookupList.get(id);
		
		// If the id does not exist in the internal tracking list, error
		if (key == null)
			throw new IllegalArgumentException("given id does not exist");
		Node removed;
		// Remove the node by its key (i.e. priority), and store a reference to it
		try
		{
			removed = removeByKey(key);
		}
		catch (IllegalStateException e)
		{
			throw new Warning();
		}
		
		// No exceptions thrown in removeByKey call, so we can now safely remove the id from the lookup list
		idLookupList.remove(removed.id);
		
		// Return the value of the removed node to the client for further processing
		return removed.value;
	}
	
	/**
	 * A private function to remove a node from the tree by its key.
	 * This is private since we only want clients to be able to call removals by their given unique IDs.
	 * 
	 * @param key
	 * @return
	 */
	private Node removeByKey(K key) throws IllegalStateException
	{
		// Find the node with the given key (i.e. priority) in our tree
		Node current = root;
		while (current.key != key && current.value != null)
		{
			if (key.compareTo(current.key) < 0)
			{
				current.numLeftDescendants--;
				current = current.left;
			}
			else if (key.compareTo(current.key) > 0)
			{
				current.numRightDescendants--;
				current = current.right;
			}
		}
		
		if (current.value == null)
		// The value didn't exist in the tree (even though the id was in the lookup list)
		// This should never happen, but is added as an extra precaution and for debugging
		{
			// Undo all of the descendant increments we just did
			while (current.parent != null)
			{
				current = current.parent;
				if (key.compareTo(current.key) < 0)
					current.numLeftDescendants++;
				else if (key.compareTo(current.key) > 0)
					current.numRightDescendants++;
			}
			// Then throw an exception
			throw new IllegalStateException("unable to find key in tree");
		}
		
		// Now we want to remove the current node from the tree, while updating references of its neighboring nodes
		/*
		 * BST deletion Rules:
		 * Deletion with 0 immediate children: just remove node
		 * Deletion with 1 immediate child: just move the node into original spot
		 * Deletion with 2 immediate children: Find either left-most node in right subtree or right-most node in left subtree
		 */
		
		// Get a count of how many immediate children this node has (only nodes DIRECTLY below it)
		int numImmediateChildren = 0;
		numImmediateChildren += (current.left.value != null) ? 1 : 0;
		numImmediateChildren += (current.right.value != null) ? 1 : 0;
		
		switch (numImmediateChildren)
		{
		case 0:
			// No immediate children, so just remove the current node
			
			// If the node we are trying to remove is the root and the rest of the tree is empty, set it to null
			if (current == root)
			{
				root = new Node(null);
			}
			
			// Update current's parent to point to one of current's dummy nodes, effectively removing current
			else if (key.compareTo(current.parent.key) < 0)
			{
				// The node we're removing was the left child of its parent
				
				// Tell the parent to point to the left dummy leaf
				current.parent.left = current.left;
				// Tell the left dummy leaf to point back up to the parent
				current.left.parent = current.parent;
			}
			else if (key.compareTo(current.parent.key) > 0)
			{
				// The node we're removing was the right child of its parent
				
				// Tell the parent to point to the right dummy leaf
				current.parent.right = current.right;
				// Tell the left dummy leaf to point back up to the parent
				current.right.parent = current.parent;
			}
			return current;
			
		case 1:
			// Only one immediate child, so just move that child into original's old position
			
			// If the node we are trying to remove is the root, set the root to the new node
			if (current == root)
			{
				if (current.left.value != null)
				{
					// The root only had a left child
					
					root = current.left;
					root.parent = null;
				}
				else
				{
					// The root only had a right child
					
					root = current.right;
					root.parent = null;
				}
				return current;
			}
			
			// Not the root...
			if (key.compareTo(current.parent.key) < 0)
			{
				// The node we're removing was the left child of its parent
				
				if (current.left.value != null)
				{
					// The node-to-remove only had a left child
					
					current.parent.left = current.left;
					current.left.parent = current.parent;
				}
				else
				{
					// The node-to-remove only had a right child
					
					current.parent.left = current.right;
					current.right.parent = current.parent;
				}
				
			}
			else if (key.compareTo(current.parent.key) > 0)
			{
				// The node we're removing was the right child of its parent
				
				if (current.left.value != null)
				{
					// The node-to-remove only had a left child
					
					current.parent.right = current.left;
					current.left.parent = current.parent;
				}
				else
				{
					// The node-to-remove only had a right child
					
					current.parent.right = current.right;
					current.right.parent = current.parent;
				}
			}
			return current;
			
		case 2:
			// Two immediate children, so find either (left-most node in right subtree) OR (right-most node in left subtree)
			// We don't have to keep this tree balanced, but let's choose the heavier side to make it at least somewhat balanced (whenever trivially possible)
			
			Node nodeToMove;
			int hop = 0;
			
			// Find the right-most node in left subtree
			current.numLeftDescendants--;
			nodeToMove = current.left;
			hop++;
			
			while (nodeToMove.right.value != null)
			{
				hop++;
				nodeToMove.numRightDescendants--;
				nodeToMove = nodeToMove.right;
			}
			
			if (hop == 1)
			{
				nodeToMove.parent.left = nodeToMove.left;
			}
			else
			{
				nodeToMove.parent.right = nodeToMove.left;
			}
			
			// Since we found the right-most node, we KNOW that right of this is dummy
			// Left may or may not have a value, so shift into old nodeToMove position to be safe
			// nodeToMove.parent.right = nodeToMove.left;
			if (nodeToMove.left.value != null)
				nodeToMove.left.parent = nodeToMove.parent;
			
			// nodeToMove has now been (temporarily) removed from the tree
			// Now we need to replace current with nodeToMove
			
			// Set the node-to-move's descendants to match the current node's descendants
			nodeToMove.numLeftDescendants = current.numLeftDescendants;
			nodeToMove.numRightDescendants = current.numRightDescendants;
			
			// Set the children of nodeToMove to match the children of current, but make sure we don't point to itself
			if (nodeToMove != current.left) // make sure we don't point the node towards itself
				nodeToMove.left = current.left;
			if (nodeToMove != current.right) // make sure we don't point the node towards itself
				nodeToMove.right = current.right;
			
			// Set nodeToMove parent to match current parent
			nodeToMove.parent = current.parent;
			
			// Now to completely remove current from the tree, update current's parent to point to nodeToMove
			if (current == root)
			{
				root = nodeToMove;
			}
			else if (key.compareTo(current.parent.key) < 0)
			{
				// The node we're removing was the left child of its parent
				current.parent.left = nodeToMove;
			}
			else if (key.compareTo(current.key) > 0)
			{
				// The node we're removing was the right child of its parent
				current.parent.right = nodeToMove;
			}
			return current;
			
		default:
			throw new IllegalStateException("children of node to remove was not 0, 1, or 2"); // this should never happen, added for extra precaution
		}
	}
	
	/**
	 * Remove the item with the maximum key (i.e., priority).
	 */
	public V removeMax() throws Warning
	{
		
		// The maximum node will, by definition, have only dummy leaves as its children
		Node current = root;
		
		if (root.value == null)
		{
			throw new Warning("Removal Atempted when queue is empty");
		}
		while (current.right.value != null)
		{
			current.numRightDescendants--;
			current = current.right;
		}
		
		// Current is now pointing to the maximum node, which has 2 dummy leaves as its children
		
		// Remove the id from the idlist
		idLookupList.remove(current.id);
		
		if (current == root)
		{
			root = new Node(null);
		}
		else
		{
			// Tell the parent to point to the left dummy leaf
			current.parent.right = current.right;
			// Tell the left dummy leaf to point back up to the parent
			current.right.parent = current.parent;
		}
		// Return the old max
		return current.value;
		
	}
	
	/** An inner class used to represent a single node. */
	private class Node
	{
		public K key;
		public V value;
		
		/** The unique id associated with those node. */
		public int id;
		
		/** The number of children this node has. */
		public int numLeftDescendants, numRightDescendants;
		
		/** Pointers to the left/right children (may be null). */
		public Node parent, left, right;
		
		/** Default constructor creates a dummy node. */
		Node(Node parent)
		{
			this.parent = parent;
		}
		
	}
	
	public int getPositionByID(int id) throws Warning
	{
		// Determine what the associated priority is with the given id
		K key = idLookupList.get(id);
		
		Node current = root;
		
		if (current.value == null || current.key == null)
			throw new Warning("null root or key in getPositionByID");
		
		// As long as the current node isn't what we're looking for...
		while (key.compareTo(current.key) != 0)
		{
			// If what we're looking for is greater, go right
			if (key.compareTo(current.key) > 0)
				current = current.right;
			// If what we're looking for is less, go left
			else
				current = current.left;
		}
		
		if (current.value == null)
			throw new IllegalStateException("unable to find key in tree");
		// The value didn't exist in the tree (even though the id was in the lookup list)
		// This should never happen, but is added as an extra precaution and for debugging
		
		// Current is now pointing to the node we were looking for
		// To find it's position, we want to find the number of nodes that are to the RIGHT of it (whether they're children or not)
		int count = 1 + current.numRightDescendants;
		
		// Go up to examine the parent, whether it's greater or lesser
		while (current.parent != null)
		{
			current = current.parent;
			// If the parent was greater, add 1 to the count and all of the parent's right children
			if (current.key.compareTo(key) > 0)
				count += (1 + current.numRightDescendants);
		}
		
		return count;
	}
	
	// This method is for testing purpose
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
	
	// Some misc. public methods below, mainly used for testing
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
	
	@Override
	public String toString()
	{
		int[] array = new int[idLookupList.size()];
		int i = 0;
		for (Map.Entry<Integer, K> entry : idLookupList.entrySet())
		{
			array[i++] = entry.getKey();
		}
		return Arrays.toString(array);
	}
	
	public void listNodes()
	{
		listNodesRecurs(root);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void listNodesRecurs(Node n)
	{
		if (n.left.value != null)
			listNodesRecurs(n.left);
		
		// for (int i = 0; i < indent; i++)
		// System.out.print("     ");
		
		System.out.println("Node: thisKey=[" + n.key + "], leftKey=[" + n.left.key + "], rightKey=[" + n.right.key + "], parentKey=["
				+ ((n.parent != null) ? n.parent.key : "NULL") + "]");
		
		if (n.right.value != null)
			listNodesRecurs(n.right);
		
	}
	
}
