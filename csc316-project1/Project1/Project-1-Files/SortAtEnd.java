import java.util.*;

/**
 * For each text word, store a new word counter at the end of the ArrayList (or array) with a count of 1. Do not look for entries having the same word.
 * After all input words have been read, sort the array and scan it for duplicates.
 * Entries with the same word will be adjacent and can be combined into a single word counter with the appropriate count.
 * 
 * @author Bennett Lynch (bblynch2)
 */
public class SortAtEnd extends Heuristic
{
	private ArrayList<WordWithCount> list;
	
	/**
	 * Constructor that does NOT specify the name that will be used when printing statistics.
	 * 
	 * @param fileName
	 *            path and name of the input file
	 */
	public SortAtEnd(String fileName)
	{
		this(fileName, "Sort At End");
	}
	
	/**
	 * Constructor that DOES specify the name that will be used when printing statistics.
	 *
	 * @param fileName
	 *            path and name of the input file
	 * @param name
	 *            name that will be used when printing statistics
	 */
	public SortAtEnd(String fileName, String name)
	{
		super(fileName, name);
	}
	
	/**
	 * Any actions the heuristic takes to initialize data structures.
	 */
	@Override
	protected void preProcess()
	{
		list = new ArrayList<WordWithCount>();
	}
	
	/**
	 * For each text word, store a new word counter at the end of the ArrayList (or array) with a count of 1. Do not look for entries having the same word.
	 */
	@Override
	protected void lookup(String searchTerm)
	{
		list.add(new WordWithCount(searchTerm));
	}
	
	/**
	 * Any actions that need to take place before that heuristic is able to produce the correct result.
	 * -
	 * After all input words have been read, sort the array and scan it for duplicates.
	 * Entries with the same word will be adjacent and can be combined into a single word counter with the appropriate count.
	 */
	@Override
	protected void postProcess()
	{
		// Sort the list
//		list.sort(new WordWithCountComparator());
		Collections.sort(list, new WordWithCountComparator());
		
		WordWithCount leftWWC = null;
		WordWithCount rightWWC = null;
		
		// Remove duplicates
		for (int i = 0; i < list.size() - 1; i++) // we only check up to the 2nd to last element, since last ele can't have remaining duplicates
		{
			// Only update leftWord if it's null (we didn't find a match before)
			// This will reduce the number of get() calls for the same word
			if (leftWWC == null)
				leftWWC = list.get(i);
			// Always update right word
			rightWWC = list.get(i + 1);
			
			if (compareWords(leftWWC.getWord(), rightWWC.getWord()) == 0)
			{
				// The left and right words are the same
				list.remove(i + 1);
				list.get(i).incrementCount();
				i--; // decrement i so that we can examine the same word again (for more duplicates)
				// Keep leftWWC pointing to the same element
			}
			else
			{
				leftWWC = null;
			}
		}
	}
	
	/**
	 * Returns an iterator for objects of class WordWithCount
	 * 
	 * @return an iterator for objects of class WordWithCount
	 */
	@Override
	protected Iterator<WordWithCount> result()
	{
		return list.iterator();
	}
	
}
