package cs326.hw1p2;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import twitter4j.MediaEntity;
import twitter4j.Status;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import cs326.hw1p2.Analysis.BestTweets;
import cs326.hw1p2.Analysis.MostCommonWord;
import cs326.hw1p2.Analysis.MostReTweetedPhoto;
import cs326.hw1p2.Analysis.MostTweetedPhoto;
import cs326.hw1p2.Analysis.WorstTweets;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void BestTweets()
    {
    	BestTweets best = new BestTweets();

    	List<Status> statuses = new ArrayList<Status>();
    	Status testStatus;
    	
    	testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(900000L);
    	when(testStatus.getRetweetCount()).thenReturn(33);
    	statuses.add(testStatus);
    	
    	testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(100000L);
    	when(testStatus.getRetweetCount()).thenReturn(3);
    	statuses.add(testStatus);
    	
    	Status bestTweet = best.mostRetweeted(statuses);
    	assertEquals(bestTweet.getId(), 900000L );
       	assertEquals(bestTweet.getRetweetCount(), 33 );
    }
    
    @Test
    public void WorstTweets()
    {
    	WorstTweets worst = new WorstTweets();

    	List<Status> statuses = new ArrayList<Status>();
    	Status testStatus;
    	
    	testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(900000L);
    	when(testStatus.getRetweetCount()).thenReturn(33);
    	statuses.add(testStatus);
    	
    	testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(100000L);
    	when(testStatus.getRetweetCount()).thenReturn(3);
    	statuses.add(testStatus);
    	
    	Status worstTweet = worst.leastRetweeted(statuses);
    	assertEquals(worstTweet.getId(), 100000L );
       	assertEquals(worstTweet.getRetweetCount(), 3 );
    }
    
    @Test
    public void MostRetweetedPhoto(){
    	MostReTweetedPhoto most = new MostReTweetedPhoto();
    	BestTweets best = new BestTweets();
    	MediaEntity testEntity1 = Mockito.mock(MediaEntity.class);
    	MediaEntity testEntity2 = Mockito.mock(MediaEntity.class);
    	MediaEntity [] array1 = {testEntity1};
    	MediaEntity [] array2 = {testEntity2};

    	List<Status> statuses = new ArrayList<Status>();
    	Status testStatus;
    	
    	Status testStatus1 = Mockito.mock(Status.class);
    	when(testStatus1.getId()).thenReturn(100000L);
    	when(testStatus1.getRetweetCount()).thenReturn(33);
    	when(testStatus1.getMediaEntities()).thenReturn(array1);
    	when(testEntity1.getType()).thenReturn("photo");
    	when(testStatus1.getMediaEntities()[0].getDisplayURL()).thenReturn("apple");
    	statuses.add(testStatus1);
    	
    	Status testStatus2 = Mockito.mock(Status.class);
    	when(testStatus2.getId()).thenReturn(900000L);
    	when(testStatus2.getRetweetCount()).thenReturn(3);
    	when(testStatus2.getMediaEntities()).thenReturn(array2);
    	when(testEntity2.getType()).thenReturn("photo");
    	when(testStatus2.getMediaEntities()[0].getDisplayURL()).thenReturn("grape");
    	statuses.add(testStatus2);
    	
    	String mostTweetURL = most.mostRetweetedPhoto(statuses);
    	assertEquals("apple", mostTweetURL);
    }
    
    @Test
    public void testMostTweetedPhoto(){
    	MostTweetedPhoto most = new MostTweetedPhoto();

    	MediaEntity testEntity = Mockito.mock(MediaEntity.class);
    	MediaEntity testEntity1 = Mockito.mock(MediaEntity.class);
    	MediaEntity testEntity2 = Mockito.mock(MediaEntity.class);
    	MediaEntity testEntity3 = Mockito.mock(MediaEntity.class);
    	MediaEntity testEntity4 = Mockito.mock(MediaEntity.class);


    	MediaEntity [] array = {testEntity};
    	MediaEntity [] array1 = {testEntity1};
    	MediaEntity[] array2 = {testEntity2};
    	MediaEntity[] array3 = {testEntity3};
    	MediaEntity[] array4 = {testEntity4};


    	List<Status> statuses = new ArrayList<Status>();
    	
    	//original tweet photo1
    	Status testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(100000L);
    	when(testStatus.isRetweet()).thenReturn(false);
    	when(testStatus.getRetweetCount()).thenReturn(3);
    	when(testStatus.getMediaEntities()).thenReturn(array);
    	when(testEntity.getType()).thenReturn("photo");
    	when(testStatus.getMediaEntities()[0].getDisplayURL()).thenReturn("apple");
    	statuses.add(testStatus);
    	
    	//a retweet of photo1
    	Status testStatus1 = Mockito.mock(Status.class);
    	when(testStatus1.getId()).thenReturn(900000L);
    	when(testStatus1.getRetweetCount()).thenReturn(3);
    	when(testStatus1.isRetweet()).thenReturn(true);
    	when(testStatus1.getRetweetedStatus()).thenReturn(testStatus);
    	when(testStatus1.getMediaEntities()).thenReturn(array1);
    	when(testEntity1.getType()).thenReturn("photo");
    	when(testStatus1.getMediaEntities()[0].getDisplayURL()).thenReturn("apple");
    	statuses.add(testStatus1);
    	
    	//an original tweet photo2
    	Status testStatus2 = Mockito.mock(Status.class);
    	when(testStatus2.getId()).thenReturn(800000L);
    	when(testStatus2.isRetweet()).thenReturn(false);
    	when(testStatus2.getRetweetCount()).thenReturn(3);
		when(testStatus2.getMediaEntities()).thenReturn(array2);
		when(testEntity2.getType()).thenReturn("photo");
    	when(testStatus2.getMediaEntities()[0].getDisplayURL()).thenReturn("durian");
    	statuses.add(testStatus2);
    	
    	//a retweet of photo2
    	Status testStatus3 = Mockito.mock(Status.class);
    	when(testStatus3.getId()).thenReturn(700000L);
    	when(testStatus3.getRetweetCount()).thenReturn(3);
    	when(testStatus3.isRetweet()).thenReturn(true);
    	when(testStatus3.getRetweetedStatus()).thenReturn(testStatus2);
		when(testStatus3.getMediaEntities()).thenReturn(array3);
		when(testEntity3.getType()).thenReturn("photo");
    	when(testStatus3.getMediaEntities()[0].getDisplayURL()).thenReturn("durian");
    	statuses.add(testStatus3);
    	
    	//a retweet of photo2
    	Status testStatus4 = Mockito.mock(Status.class);
    	when(testStatus4.getId()).thenReturn(800000L);
    	when(testStatus4.getRetweetCount()).thenReturn(3);
    	when(testStatus4.isRetweet()).thenReturn(true);
    	when(testStatus4.getRetweetedStatus()).thenReturn(testStatus2);
		when(testStatus4.getMediaEntities()).thenReturn(array4);
		when(testEntity4.getType()).thenReturn("photo");
    	when(testStatus4.getMediaEntities()[0].getDisplayURL()).thenReturn("durian");
    	statuses.add(testStatus4);
    	
    	String url = most.mostTweetedPhoto(statuses);
    	assertEquals(10, most.getMostCount());
    	assertEquals("durian", url );
    }
    
    @Test
    public void testMostCommonWord(){
    	MostCommonWord most = new MostCommonWord();

    	List<Status> statuses = new ArrayList<Status>();
    	Status testStatus;
    	
    	testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(900000L);
    	when(testStatus.getText()).thenReturn("apple apple grape");
    	statuses.add(testStatus);
    	
    	testStatus = Mockito.mock(Status.class);
    	when(testStatus.getId()).thenReturn(100000L);
    	when(testStatus.getText()).thenReturn("APPLE Apple grape");
    	statuses.add(testStatus);
    	
    	int count = most.mostCommonWord(statuses);
    	assertEquals(4, count);
    }
}
