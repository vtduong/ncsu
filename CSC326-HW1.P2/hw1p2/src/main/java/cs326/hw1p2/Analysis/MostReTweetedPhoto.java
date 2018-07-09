package cs326.hw1p2.Analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Status;

public class MostReTweetedPhoto {

	public String mostRetweetedPhoto(List<Status> list) {
		//a list that contains tweets with photo(s)
		List<Status> photoTweets = new ArrayList<Status>();
		int max = 0;
		String photoURL = null;

		if (list == null || list.size() == 0){
			throw new IllegalArgumentException("Empty or null list!");
		} else{
			for (int i = 0; i < list.size(); i++){
				if (list.get(i).getMediaEntities().length != 0){ //check if this status contains media sources
					for (MediaEntity m : list.get(i).getMediaEntities()){
						if (m.getType().equals("photo")){ //check type of media sources
							photoTweets.add(list.get(i));
						}
					}
				}
			}
		}
		if (photoTweets.size() != 0){
			for (Status s : photoTweets){
				if (max < s.getRetweetCount()){
					max = s.getRetweetCount();
					photoURL = s.getMediaEntities()[0].getDisplayURL();
				}
			}
		}
		return photoURL;
	}
	
}
