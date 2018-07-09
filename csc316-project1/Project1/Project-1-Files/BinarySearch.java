import java.util.ArrayList;
import java.util.Iterator;

/**
 * Store word counters in an ArrayList. For each text word, do a binary search for it.
 * If its word counter is not in the list, insert it into the appropriate position;
 * the words in the list will be in increasing lexicographic order.
 * 
 * @author Bennett Lynch (bblynch2)
 */
public class BinarySearch extends Heuristic
{
	private ArrayList<WordWithCount> list;
	
	/**
	 * Constructor that does NOT specify the name that will be used when printing statistics.
	 * 
	 * @param fileName
	 *            path and name of the input file
	 */
	public BinarySearch(String fileName)
	{
		this(fileName, "Binary Search");
	}
	
	/**
	 * Constructor that DOES specify the name that will be used when printing statistics.
	 *
	 * @param fileName
	 *            path and name of the input file
	 * @param name
	 *            name that will be used when printing statistics
	 */
	public BinarySearch(String fileName, String name)
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
	 * Primary method to implement the binary search pattern.
	 * Binary search works by comparing the search term to the median of a given list.
	 * Based on whether the search term is lower or greater than the median, we can then eliminate half of the list since it is already sorted.
	 * -
	 * Each text word is first searched for in the data structure.
	 * If it is found the counter in the corresponding word counter is incremented; if not a new word counter is added
	 */
	@Override
	protected void lookup(String searchTerm)
	{
		// First check for empty list
		if (list.isEmpty())
		{
			list.add(new WordWithCount(searchTerm));
			return;
		}
		
		int low = 0;
		int high = list.size() - 1;
		int median;
		
		while (true)
		{
			median = (high - low) / 2 + low; // allow integer division to truncate result (round down)
			
			WordWithCount leftWWC = list.get(low);
			WordWithCount middleWWC = list.get(median);
			WordWithCount rightWWC = list.get(high);
			
			// Do all the comparisons and store them as an int to reduce the comparison count
			int leftCompare = compareWords(searchTerm, leftWWC.getWord());
			
			if (leftCompare == 0)
			{
				leftWWC.incrementCount();
				return;
			}
			
			int midCompare = compareWords(searchTerm, middleWWC.getWord());
			
			if (midCompare == 0)
			{
				middleWWC.incrementCount();
				return;
			}
			
			int rightCompare = compareWords(searchTerm, rightWWC.getWord());
			
			if (rightCompare == 0)
			{
				rightWWC.incrementCount();
				return;
			}
			
			// We examined all possible words in the list and didn't find the search term...
			if (high - low <= 2)
			{
				if (leftCompare < 0)
				{
					list.add(low, new WordWithCount(searchTerm));
				}
				else if (midCompare < 0)
				{
					list.add(median, new WordWithCount(searchTerm));
				}
				else if (rightCompare < 0)
				{
					list.add(high, new WordWithCount(searchTerm));
				}
				else
				{
					list.add(high + 1, new WordWithCount(searchTerm));
				}
				return;
			}
			
			// The word wasn't at one of the ends or the middle, so now we must cut the list in half
			if (midCompare < 0)
			{
				// The search term is greater than the median
				high = median;
			}
			else
			// if (midCompare > 0)
			{
				// The search term is less than the median
				low = median;
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
		return list.iterator();
	}
	
}
