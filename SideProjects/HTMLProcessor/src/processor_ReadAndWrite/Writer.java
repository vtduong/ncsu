package processor_ReadAndWrite;

/**
 * This class uses a default template, adds necessary info to the template and stores it 
 * in an output buffer
 * @author Van Duong
 *
 */
public class Writer extends ProcessorCommon {
	/**
	 *  is the necessary paragraph
	 */
	private String output;
	private String imgInfo;
	private String urlInfo;
	private String titleInfo;
	private String authorInfo;
	private String descriptionInfo;
	private boolean isDone;
	//this is old template
	private String Oldtemplate = "<tr><td align=\"left\" valign=\"top\" bgcolor=\"#FFFFFF\" width=\"178\"><img src=\"" + 
							"\" border=\"0\" width=\"196\" height=\"123\" style=\"padding:8px 13px 0 0;\"></td><td align=\"left\" valign=\"top\" bgcolor=\"#FFFFFF\"><table border=\"0\" cellpadding=\"0\" width=\"381\"><tr><td align=\"left\" style=\"font-family:arial, helvetica, sans-serif;font-size:18px;line-height:19px;\"><span style=\"font-size:14px;text-transform:uppercase;line-height:19px;color:#d31920;font-weight:bold;\">treatment advice//</span> <a href=\"" + 
							"\" style=\"color:#000000;font-weight:bold;text-decoration:none;\">" + "\n" + "\n"+ 
							"</a></td></tr><tr><td align=\"left\" style=\"font-family:arial, helvetica, sans-serif;font-size:15px;color:#000000;font-weight:normal;line-height:21px;\">" + "\n" +
							"\n" + "<br><em>" + "\n" + "By " + "\n" + "</em></td></tr></table></td></tr>";
	private String newTemplate = "<a href=\"URL\"" +
			"target=\"_blank\"><span style=\"font-size:18px\"><strong><span style=\"color:rgb(211, 25, 32)\">PROBLEM SOLUTION"+
			"//</span>&nbsp;</strong></span></a><a href=\"URL\"><span style=\"color:#696969\"><span style=\"font-size:18px\"><strong>TITLE</strong></span></span></a><br />"+ "\n" +
			"DESCRIPTION<br style=\"line-height: 20.8px;\" />"+ "\n" +
			"<em style=\"line-height:20.8px\">By AUTHOR</em>";
	private int offset = 0;
 
	public Writer(String imgInfo, String urlInfo, String titleInfo,
			String descriptionInfo, String authorInfo) {
		this.imgInfo = imgInfo;
		this.urlInfo = urlInfo;
		this.titleInfo = titleInfo;
		this.descriptionInfo = descriptionInfo;
		this.authorInfo = authorInfo;
		output = new String(newTemplate);
		isDone = false;
		
	}
	/**
	 * processes writing process
	 */
	@Override
	public void process() {
		//addImgInfo();
		addUrlInfo();
		addTitleInfo();
		addDescriptionInfo();
		addAuthorInfo();
	}
	private void addAuthorInfo() { 
		
//		int i = offset + descriptionInfo.length();
//		char c = output.charAt(i);
//		while(c != '\n'){
//			c= output.charAt(i++);
//		}
//		offset = i;
//		int startingIdx = output.indexOf("By", offset);
//		output.insert(startingIdx + 3, authorInfo);
		output = output.replace("AUTHOR", authorInfo);
		isDone = true;
		incrementNumProcess();
		
	}
	private void addDescriptionInfo() {
//		int startingIdx = output.indexOf("br") - 2;
//		output.insert(startingIdx, descriptionInfo);
		output = output.replace("DESCRIPTION", descriptionInfo);
		incrementNumProcess();
		
	}
	private void addTitleInfo() {
//		int i = offset + urlInfo.length();
//		char c = output.charAt(i);
//		while(c != '\n'){
//			c= output.charAt(i++);
//		}
//		offset = i;
//		output.insert(offset, titleInfo);
		output = output.replace("TITLE", titleInfo);
		incrementNumProcess();
		
	}
	private void addUrlInfo() {
//		String urlTag = "<a href=\"";
//		offset = output.indexOf(urlTag) + urlTag.length();
//		output.insert(offset, urlInfo);
		output = output.replace("URL", urlInfo);
		incrementNumProcess();
	}
	private void addImgInfo() {
//		String imgTag = "img src=\"";
//		offset = output.indexOf(imgTag) + imgTag.length();
//		output.insert(offset, imgInfo);
		
		incrementNumProcess();
	}
	public String getOutput(){
		return output.toString();
	}
	public void setOutput(String str) {
		output = str;
	}
	
	public boolean isDone(){
		return isDone;
	}
}

