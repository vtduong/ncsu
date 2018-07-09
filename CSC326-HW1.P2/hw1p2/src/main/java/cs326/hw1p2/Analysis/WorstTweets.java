package cs326.hw1p2.Analysis;

import java.util.List;

import twitter4j.Status;

public class WorstTweets {

	public Status leastRetweeted(List<Status> list) {
		int min = list.get(0).getRetweetCount();
		Status minStatus = list.get(0);
		for( Status s : list )
		{
			if( min > s.getRetweetCount() )
			{
				min = s.getRetweetCount();
				minStatus = s;
			}
		}
		return minStatus;
	}

}
