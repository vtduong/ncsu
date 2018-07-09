package cs326.hw1p2.Analysis;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import twitter4j.Status;

public class MostCommonWord {

	public int mostCommonWord(List<Status> statuses) {
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		String text = "";
		//concatenate all statuses text to a single string
		for (Status s: statuses){
			text = text + " " + s.getText();
		}
		text = text.toLowerCase();
		
		String [] tokens = text.split(" ");
		//count frequency of occurrence of each word
		for (String s : tokens){
			if (table.containsKey(s)){
				table.put(s, table.get(s) + 1);
			} else{
				table.put(s,1);
			}
		}
		
		//get the most frequent word
		Set<String> keySet = table.keySet();
		int max = 0;
		for (String s : keySet){
			if (max < table.get(s)){
				max = table.get(s);
			}
		}
		
		return max;
	}

}
