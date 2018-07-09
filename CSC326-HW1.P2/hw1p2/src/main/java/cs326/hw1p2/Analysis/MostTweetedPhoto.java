package cs326.hw1p2.Analysis;

import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Status;

public class MostTweetedPhoto {
	//contains tweet count (both original and retweet) of the ost tweeted photo
	//this variable is used for testing only
	private int mostCount = 0;	
	public String mostTweetedPhoto( List<Status> list){
		List<Photo> photoList = new ArrayList<Photo>();
		
		//search for unique photos in all tweets, assuming a unique photo has its unique URL
		for (Status s : list){
			for (MediaEntity m : s.getMediaEntities()){
				if (m.getType().equals("photo") && !photoList.contains(m.getDisplayURL())){
					//add a unique photo to photoList
					photoList.add(new Photo(m.getDisplayURL()));
				}
			}
		}
		
		if (photoList.size() == 0){
			return null;
		}
		//get tweet count and retweet count of each unique photo
		for (Photo p : photoList){
			for (Status s : list){
				for (MediaEntity m : s.getMediaEntities()){
					if (m.getDisplayURL().equals(p.getURL())){
						if (!s.isRetweet()){// if this tweet is original, add 1 and its retweet count
							p.addCount(1 + s.getRetweetCount());
						} else{ //if it's a retweet, add its retweet count
							p.addCount(s.getRetweetCount());
						}
					}
				}	
			} 
		}
		//get the most tweeted photo
		int max = 0;
		String url = null;
		for (Photo p : photoList){
			if (max < p.getCount()){
				max = p.getCount();
				url = p.getURL();
			}
		}
		mostCount = max;
		return url;
	}
	private class Photo{
		private String url;
		private int count;
		
		public Photo(String url){
			this.url = url;
			this.count = 0;
		}
		
		public void addCount(int count){
			this.count+=count;
		}
		
		public int getCount(){
			return count;
		}
		
		public String getURL(){
			return url;
		}
	}
	
	//This is for testing only
	public int getMostCount() {
		return mostCount;
	}
}
