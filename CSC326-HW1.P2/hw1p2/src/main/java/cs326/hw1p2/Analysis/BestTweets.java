package cs326.hw1p2.Analysis;

import java.util.List;

import twitter4j.Status;

public class BestTweets 
{
	public Status mostRetweeted(List<Status> list)
	{
		int max = 0;
		Status maxStatus = list.get(0);
		for( Status s : list )
		{
			if( max < s.getRetweetCount() )
			{
				max = s.getRetweetCount();
				maxStatus = s;
			}
		}
		return maxStatus;
	}
}
