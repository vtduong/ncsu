package processor_ReadAndWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Reader extends ProcessorCommon {
	
	private String specialChar = "&#039;";
	private String emDash = "\\u2014";
	/**
	 * constant String
	 */
	private String content = "content=\"";
	/**
	 * length of constant String content
	 */
	private final int contentLength = content.length();
	/**
	 * image info
	 */
	private String imgInfo;
	/**
	 * author info
	 */
	private String authorInfo;
	/**
	 * url info
	 */
	private String urlInfo;
	/**
	 * title info
	 */
	private String titleInfo;

	/**
	 * input scanner
	 */
	private Scanner input;
	/**
	 * input file
	 */
	
	/**
	 * description info
	 */
	private String descriptionInfo;
	private String line = "";
	private String authorIndicator = "author text-italic";
	/**
	 *constructs a reader 
	 * @param file input file
	 * @throws FileNotFoundException 
	 */
//	public Reader(File file) throws FileNotFoundException {
//		input = new Scanner(file);
//	}

	public Reader(InputStream input) {
		this.input = new Scanner(input);
	}

	/**
	 * processes reading file
	 * @throws FileNotFoundException 
	 */
	@Override
	public void process()  {
		while(input.hasNextLine() && 
				super.getNumProcess() < 4){
			line = input.nextLine();
//			if(line.contains("og:image")) {
//				imgInfo = readImage();
//			} 

			if(line.contains("og:url")) {
				urlInfo = readUrl();

			} else if(line.contains("og:title")) {
				titleInfo = readTitle();

			} else if(line.contains("og:description")) {

				descriptionInfo = readDescription();

			} else if(line.contains(authorIndicator)){

				authorInfo = readAuthor();

			}
		}

	}
	
	/**
	 * read author name from file
	 * @return author name
	 */
	private String readAuthor() {
//		int startingIdx = line.indexOf(authorIndicator) + authorIndicator.length();
//		int startOfAuthorIdx = line.indexOf(">", startingIdx) + 1;
//		int endOfAuthorIdx = line.lastIndexOf("</a>");
//		String info = line.substring(startOfAuthorIdx, endOfAuthorIdx);
		Document doc = Jsoup.parse(line);
		Element link = doc.select("a").first();
		
		String info = link.text(); // "should be author name"
		incrementNumProcess();
		
		return info;
	}

	/**
	 * read description, readDescription, readURL, and readImage have content in common
	 * @return description
	 */
	private String readDescription() {
		return readContent(line);
	}

	/**
	 * read title
	 * @return title
	 */
	private String readTitle() {
		int startingIdx = line.indexOf(content) + contentLength;
		int endingIdx = line.lastIndexOf("|");
		String info = line.substring(startingIdx, endingIdx-1);
		if(info.contains(specialChar)) 
			info = info.replace(specialChar, "'");
//		if(info.contains(emDash))
//			info = info.replaceAll(emDash, handleSpecialCase(emDash));
		incrementNumProcess();
		return info;
	}


	private String readUrl() {
		return readContent(line);
	}

	private String readImage() {
		return readContent(line);
	}

	/**
	 * this method is used for reading description, image and url, 
	 * @param string is the line that has info about image, description or title
	 * @return
	 */
	private String readContent(String string) {
		int startingIdx = string.indexOf(content) + contentLength;
		int endingIdx = string.lastIndexOf("\"");
		String info = string.substring(startingIdx, endingIdx);
		if(info.contains(specialChar))
			info = info.replace(specialChar, "'");
		incrementNumProcess();
		return info;
	}

	/**
	 * @return the imgInfo
	 */
	public String getImgInfo() {
		return imgInfo;
	}

	/**
	 * @return the authorInfo
	 */
	public String getAuthorInfo() {
		return authorInfo;
	}

	/**
	 * @return the urlInfo
	 */
	public String getUrlInfo() {
		return urlInfo;
	}

	/**
	 * @return the titleInfo
	 */
	public String getTitleInfo() {
		return titleInfo;
	}

	/**
	 * @return the descriptionInfo
	 */
	public String getDescriptionInfo() {
		return descriptionInfo;
	}

	public void setAuthorInfo(String str) {
		authorInfo = str;
	}

	public void setImgInfo(String str) {
		imgInfo = str;
	}

	public void setUrlInfo(String str) {
		urlInfo = str;
	}

	public void setDescriptionInfo(String str) {
		descriptionInfo = str;
	}

	public void setTitleInfo(String str) {
		titleInfo = str;
	}

	public void reset() {
		input.close();
	}
}
