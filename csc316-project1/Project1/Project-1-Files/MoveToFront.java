import java.util.Iterator;

/**
 * For each text word, move the corresponding word counter to
 * the front of the list unless it is a new word that was just added.
 * 
 * @author Hoang Tran (hvtran)
 */
public class MoveToFront extends Heuristic
{
	
	DList<WordWithCount> list;
	
	/**
	 * A constructor for the case that a name is not specified
	 * 
	 * @param inputFileName
	 *            the name of the input file
	 */
	public MoveToFront(String inputFileName)
	{
		this(inputFileName, "Move To Front");
	}
	
	/**
	 * A constructor for when a name is given.
	 * 
	 * @param inputFileName
	 *            the name of the input file.
	 * @param name
	 *            the name to look for after printing.
	 */
	public MoveToFront(String inputFileName, String name)
	{
		super(inputFileName, name);
	}
	
	/**
	 * Initializing data structures.
	 */
	@Override
	protected void preProcess()
	{
		list = new DList<WordWithCount>();
	}
	
	/**
	 * For each text word, move the corresponding word counter to
	 * the front of the list unless it is a new word that was just added.
	 */
	@Override
	protected void lookup(String word)
	{
		// If the list is empty, add the word.
		if (list.isEmpty())
		{
			list.addFirst(new DNode<WordWithCount>(new WordWithCount(word), null, null));
			return;
		}
		
		// Creating variables for determining the existence of
		// the word with counter in the list.
		DNode<WordWithCount> currentNode = list.getFirst();
		boolean containsWord = false;
		
		// Loop through the list to find if the word with count for
		// the incoming word exists.
		while (!containsWord && list.hasNext(currentNode))
		{
			if (compareWords(word, currentNode.getEntry().getWord()) == 0)
			{
				containsWord = true;
				currentNode.entry.incrementCount();
				break;
			}
			try
			{
				currentNode = list.getNext(currentNode);
			}
			catch (IllegalArgumentException e)
			{
				
			}
		}
		
		// If the list contains the word, move word to the front else,
		// add it the the back of the list.
		if (containsWord)
		{
			list.remove(currentNode);
			list.addFirst(currentNode);
		}
		else
		{
			list.addLast(new DNode<WordWithCount>(new WordWithCount(word), null, null));
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
