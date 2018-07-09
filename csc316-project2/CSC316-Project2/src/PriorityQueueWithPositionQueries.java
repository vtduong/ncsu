/**
 * An interface that defines the methods all custom priority queues must implement.
 * 
 * @author Veronica Alban <valban@ncsu.edu>
 * @author Van Duong <vtduong@ncsu.edu>
 * @author Bennett Lynch <bblynch2@ncsu.edu>
 * @author Chris Tran <hvtran@ncsu.edu>
 */
public interface PriorityQueueWithPositionQueries<K extends Comparable<K>, V>
{
	
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
	public int addByKey(K keyToAdd, V valueToAdd) throws Warning;
	
	/**
	 * Remove a node by the unique id that was assigned to it upon creation (this is different than the key used to add it originally).
	 * 
	 * @param idToRemove
	 *            unique id that was returned after adding it originally
	 * @return the generic <V> contents stored in the item
	 * @throws Warning
	 *             if the key doesn't exist
	 */
	public V removeById(int idToRemove) throws Warning;
	
	/**
	 * Removes the element with the largest key (priority) from the tree and returns the value of the removed element
	 * 
	 * @return the generic <V> contents stored in the item
	 * @throws Warning
	 *             if the tree is empty
	 */
	public V removeMaximumKey() throws Warning;
	
	/**
	 * Returns the position in the queue of the element with the specified id.
	 * 
	 * @param id
	 *            unique id that was returned after adding it originally
	 * @return the position of the element
	 */
	public int queryPositionById(int id) throws Warning;
}
