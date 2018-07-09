import java.util.Iterator;
import java.util.TreeMap;

/**
 * This class is the blue print for a tree map that keeps track of words and their number occurrences.
 * The map tree is in alphabetical order.
 * 
 * @author Van Duong (vtduong)
 */
public class MapWords extends Heuristic
{
	/** a TreeMap **/
	TreeMap<String, WordWithCount> tree;
	
	/**
	 * constructs a MapWords with an input file and a default name
	 * 
	 * @param inputFileName
	 */
	public MapWords(String inputFileName)
	{
		this(inputFileName, "Map Words");
	}
	
	/**
	 * constructs a MapWords with an input file and a given name
	 * 
	 * @param inputFileName
	 * @param name
	 */
	public MapWords(String inputFileName, String name)
	{
		super(inputFileName, name);
	}
	
	/**
	 * initialize things before run
	 */
	@Override
	protected void preProcess()
	{
		tree = new TreeMap<String, WordWithCount>(new WordComparator());
	}
	
	/**
	 * look up for words and adds counts to word keys
	 */
	@Override
	protected void lookup(String word)
	{
		
		if (tree.firstEntry() == null)
		{
			tree.put(word, new WordWithCount(word));
		}
		else
		{
			WordWithCount lookUpResult = tree.get(word);
			if (lookUpResult != null)
			{
				lookUpResult.incrementCount();
				
			}
			else
			{
				tree.put(word, new WordWithCount(word));
			}
		}
	}
	
	/**
	 * returns the keys and associated values of entries in the map
	 */
	@Override
	protected Iterator<WordWithCount> result()
	{
		return tree.values().iterator();
	}
	
}
