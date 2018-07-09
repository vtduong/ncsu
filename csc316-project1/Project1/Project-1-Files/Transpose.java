//import java.util.ArrayList;
import java.util.Iterator;

/**
 * For each text word, swap its word counter with the one before it
 * unless it is already at the beginning of the list unless it's a
 * new word that was just added.
 * 
 * @author Veronica Alban (valban@ncsu.edu)
 */
public class Transpose extends Heuristic
{
	/**
	 * A list containing the words of the test that is going to be read
	 */
	DList<WordWithCount> list;
	
	/**
	 * This is the constructor for Transpose and it will only take one argument
	 * and the name will always be set to Transpose
	 * 
	 * @param inputFileName
	 *            The file that is going to be read
	 */
	
	public Transpose(String inputFileName)
	{
		this(inputFileName, "Transpose");
	}
	
	/**
	 * This constructor for Transpose will allow you to give a custom name to it
	 * 
	 * @param inputFileName
	 *            The file that is going to be read
	 * @param name
	 *            The name that you want this object to have
	 */
	
	public Transpose(String inputFileName, String name)
	{
		super(inputFileName, name);
	}
	
	/**
	 * This method will initiate the list for the class to use
	 */
	protected void preProcess()
	{
		list = new DList<WordWithCount>();
	}
	
	/**
	 * This method will take a word and check to see if it is in the list
	 * if the word cannot be found then a new node will be created with the given word
	 * and put at the back of the list
	 * if the word is found in the list and it is in the front of the list the counter will
	 * incremented and the that node will not be moved
	 * if the word is found anywhere else in the list then the count will be incremented AND
	 * it that node will swap places with the node in front of it
	 * 
	 * @param word
	 *            The word you are comparing the list to
	 */
	@Override
	protected void lookup(String word)
	{
		
		// if the list is empty then there is nothing to compare to so word is added to the front
		if (list.isEmpty())
		{
			list.addFirst(new DNode<WordWithCount>(new WordWithCount(word), null, null));
			return;
		}
		
		/** getting the first element of the list to begin iterating through */
		DNode<WordWithCount> iterator = list.getFirst();
		/** will store the node if it is found and if not it will stay null */
		DNode<WordWithCount> match = null;
		
		while (list.hasNext(iterator))
		{
			// make sure you are not comparing to trailer node
			if (iterator == list.trailer)
			{
				break;
			}
			
			// does comparison to see if the node exists in the list
			if (compareWords(word, iterator.getEntry().getWord()) == 0)
			{
				match = iterator;
				break;
			}
			
			iterator = list.getNext(iterator);
		}
		
		// if there are no entries that match the given value then it will be added to the end of list
		// if the found node happens to be the first node no swap is performed just increment
		// and anything else would be the node is an element of the rest of the list which means swap must be done
		if (match == null)
		{
			list.addLast(new DNode<WordWithCount>(new WordWithCount(word), null, null));
			return;
		}
		else if (compareWords(iterator.getEntry().getWord(), list.getFirst().getEntry().getWord()) == 0)
		{
			iterator.getEntry().incrementCount();
		}
		else
		{
			iterator.getEntry().incrementCount();
			DNode<WordWithCount> before = list.getPrev(iterator);
			DNode<WordWithCount> temp = iterator;
			list.remove(iterator);
			list.addBefore(before, temp);
		}
		
	}
	
	/**
	 * Returns an iterator of WordWithCount objects that will be used to
	 * move through the list
	 * 
	 * @return ElementIterator an iterator for the list
	 */
	@Override
	protected Iterator<WordWithCount> result()
	{
		return new ElementIterator<WordWithCount>(list);
	}
	
}
