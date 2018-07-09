//import java.awt.Toolkit;
import java.util.Iterator;

/**
 * For each text word, move its word counter toward the front of the list until it encounters one that has a count that is the same or greater;
 * in this heuristic the list will always be sorted by decreasing count.
 * 
 * @author Bennett Lynch (bblynch2)
 */
public class AccessCount extends Heuristic
{
	DList<WordWithCount> list;
	
	/**
	 * Constructor that does NOT specify the name that will be used when printing statistics.
	 * 
	 * @param fileName
	 *            path and name of the input file
	 */
	public AccessCount(String fileName)
	{
		this(fileName, "Access Count");
	}
	
	/**
	 * Constructor that DOES specify the name that will be used when printing statistics.
	 *
	 * @param fileName
	 *            path and name of the input file
	 * @param name
	 *            name that will be used when printing statistics
	 */
	public AccessCount(String fileName, String name)
	{
		super(fileName, name);
	}
	
	/**
	 * Any actions the heuristic takes to initialize data structures.
	 */
	@Override
	protected void preProcess()
	{
		list = new DList<WordWithCount>();
	}
	
	/**
	 * For each text word, move its word counter toward the front of the list until it encounters one that has a count that is the same or greater;
	 * in this heuristic the list will always be sorted by decreasing count.
	 */
	@Override
	protected void lookup(String searchTerm)
	{
		// See if the list is empty
		if (list.isEmpty())
		{
			list.addFirst(new DNode<WordWithCount>(new WordWithCount(searchTerm), null, null));
			return;
		}
		
		// See if the word already exists
		DNode<WordWithCount> node = list.getFirst();
		DNode<WordWithCount> foundNode = null;
		
		while (foundNode == null)
		{
			// First check to see if hasNext, because we don't want to call getEntry on the trailer node
			if (compareWords(searchTerm, node.getEntry().getWord()) == 0)
			{
				foundNode = node;
			}
			// Look 2 nodes in advance, to make sure we don't store the dummy node
			else if (list.hasNext(node))
			{
				node = list.getNext(node);
				
				// If this is the trailer node that we just accessed, then break now before trying to read the entry
				if (!list.hasNext(node))
					break;
			}
			else
			{
				break;
			}
		}
		
		// If the word didn't exist, add it to the rear and return
		if (foundNode == null)
		{
			list.addLast(new DNode<WordWithCount>(new WordWithCount(searchTerm), null, null));
			return;
		}
		
		// We found a matching node!
		// Increment this word count by 1 and bump it forward in the list if needed
		foundNode.getEntry().incrementCount();
		int count = foundNode.getEntry().getCount(); // store the count as a var to reduce 'get' calls
		
		boolean madeReplacement = true;
		while (madeReplacement)
		{
			if (list.hasPrev(foundNode))
			{
				DNode<WordWithCount> leftNode = list.getPrev(foundNode);
				
				// If this is the trailer node that we just accessed, then break now before trying to read the entry
				if (!list.hasPrev(leftNode))
					break;
				
				if (leftNode.getEntry().getCount() < count)
				{
					// Swap their places
					list.remove(foundNode);
					list.addBefore(leftNode, foundNode); // may throw exception?
				}
				else
				{
					madeReplacement = false;
				}
			}
		}
		
	}
	
	/**
	 * Any actions that need to take place before that heuristic is able to produce the correct result.
	 */
	@Override
	protected void postProcess()
	{
		
	}
	
	/**
	 * Returns an iterator for objects of class WordWithCount
	 * 
	 * @return an iterator for objects of class WordWithCount
	 */
	@Override
	protected Iterator<WordWithCount> result()
	{
		return new ElementIterator<WordWithCount>(list);
	}
	
}
