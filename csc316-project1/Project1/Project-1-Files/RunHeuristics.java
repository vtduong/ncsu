//import java.util.*;

//import java.io.*;

/**
 * Framework for running a variety of heuristics/algorithms that count the number of occurrences of each word in a file.
 *
 * @author Matt Stallmann 2010/08/16, based on earlier programs by Suzanne Balik and Dennis Bahler.
 */

public class RunHeuristics
{
	/**
	 * Number of lines to print for each output (instead of full result)
	 */
	public static final int LINES_TO_PRINT = 10;
	
	/**
	 * Number of times to execute each test
	 */
	private static final int NUM_TESTS_PER_HEUR = 100;
	
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: java RunHeuristics input_file");
			return;
		}
		
		String inputFileName = args[0];
		
		// The baseline heuristic has to be run first - any heuristic that is run first will take a lot more time than the ones that are run thereafter.
		// My guess is that the first heuristic has the effect of loading the input file into a buffer.
		Heuristic baseline = new Baseline(inputFileName);
		baseline.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", baseline.getName());
		baseline.printResult(System.out, LINES_TO_PRINT);
		baseline.printWordCount(System.out);
		System.out.println();
		
		Heuristic naive = new Naive(inputFileName);
		naive.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", naive.getName());
		naive.printResult(System.out, LINES_TO_PRINT);
		naive.printWordCount(System.out);
		System.out.println();
		
		// Comment out the rest of these and uncomment as they are implemented.
		// The last two are not in the list because their outputs are the same as for BinarySearch.
		
		Heuristic mtf = new MoveToFront(inputFileName);
		mtf.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", mtf.getName());
		mtf.printResult(System.out, LINES_TO_PRINT);
		mtf.printWordCount(System.out);
		System.out.println();
		
		Heuristic xp = new Transpose(inputFileName);
		xp.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", xp.getName());
		xp.printResult(System.out, LINES_TO_PRINT);
		xp.printWordCount(System.out);
		System.out.println();
		
		Heuristic ac = new AccessCount(inputFileName);
		ac.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", ac.getName());
		ac.printResult(System.out, LINES_TO_PRINT);
		ac.printWordCount(System.out);
		System.out.println();
		
		Heuristic bs = new BinarySearch(inputFileName);
		bs.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", bs.getName());
		bs.printResult(System.out, LINES_TO_PRINT);
		bs.printWordCount(System.out);
		System.out.println();
		
		Heuristic se = new SortAtEnd(inputFileName);
		se.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", se.getName());
		se.printResult(System.out, LINES_TO_PRINT);
		se.printWordCount(System.out);
		System.out.println();
		
		Heuristic mw = new MapWords(inputFileName);
		mw.run(NUM_TESTS_PER_HEUR);
		System.out.printf("%s:\n", mw.getName());
		mw.printResult(System.out, LINES_TO_PRINT);
		mw.printWordCount(System.out);
		System.out.println();
		
		Heuristic.printHeader();
		
		baseline.printStatistics();
		naive.printStatistics();
		mtf.printStatistics();
		xp.printStatistics();
		ac.printStatistics();
		bs.printStatistics();
		se.printStatistics();
		mw.printStatistics();
		
		// int wordCount = 0;
		// Iterator<WordWithCount> iter = naive.result();
		// while (iter.hasNext())
		// {
		// iter.next();
		// wordCount++;
		// }
		// System.out.println("\nUnique number of words: " + wordCount);
	}
}

// [Last modified: 2010 09 18 at 01:37:45 GMT]
