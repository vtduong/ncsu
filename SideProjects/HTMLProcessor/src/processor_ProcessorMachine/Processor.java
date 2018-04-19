package processor_ProcessorMachine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JTextField;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import processor_Exception.FileIsEmptyException;
import processor_ReadAndWrite.Reader;
import processor_ReadAndWrite.Writer;

import io.github.bonigarcia.wdm.ChromeDriverManager;


public class Processor {
	
	private static Processor instance;
	/**
	 * reader reads the html source file
	 */
	private Reader reader;
	/**
	 * writer writes desired template to a buffer
	 */
	private Writer writer;

	/**
	 * constructs a processor 
	 * @param inputFile input file from the user
	 * @throws FileIsEmptyException 
	 */
	public static Processor getInstance(){
		if (instance == null)
			instance = new Processor();
		return instance;
	}
	//private constructor to prevent other classes from creating another instance of processor
	private Processor() {
	}
	
	/**
	 * reads the file
	 * @throws FileNotFoundException 
	 */
	public void readFile(InputStream in) {
		reader = new Reader(in);
		reader.process();
	}
	
	/**
	 * writes the necessary paragraph to a file
	 */
	public void writeParagraph() {
		writer = new Writer(reader.getImgInfo(), reader.getUrlInfo(), reader.getTitleInfo(), 
				reader.getDescriptionInfo(), reader.getAuthorInfo());
		writer.process();
	}
	public String getOutput(){
		return writer.getOutput();
	}
	
	/**
	 * determine if the process is completed
	 * @return true if the process is completed
	 */
	public boolean isDone() {
		return (writer.isDone());
	}
	public int getNumProcess(){
		return reader.getNumProcess();
	}

	public void resetNumProcess() {
		reader.resetNumProcess();
	}
	
	public void resetProgram() {
		reader.resetNumProcess();
		reader.setAuthorInfo(null);
		reader.setImgInfo(null);
		reader.setUrlInfo(null);
		reader.setDescriptionInfo(null);
		reader.setTitleInfo(null);
		reader.reset();
	}
	
	public String getTitleInfo() {
		return reader.getTitleInfo();
	}
	
	public ArrayList<String> submit(String name, String subject, List<JTextField> textFieldList) throws Exception{
		ArrayList<String> errorList = new ArrayList<String>();
		for(int i =1; i <=6; i++){
			try{
				Jsoup.connect(textFieldList.get(i-1).getText()).get();
			}catch(IOException a){
				errorList.add("There's a problem with link "+ i);
				continue;
			}
		}
		if(!errorList.isEmpty()){
			return errorList;
		}
        ChromeDriverManager.getInstance().setup();
	        WebDriver driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        

		//go to mailchimp.com
        driver.get("http://mailchimp.com/");
		//if not logged in, log in (check for MailChimp Dashboard | editor@thefix.com)
		String title = driver.getTitle();
		if(!title.equalsIgnoreCase("MailChimp Dashboard | editor@thefix.com")){
			driver.findElement(By.cssSelector("a[href*='https://login.mailchimp.com/']")).click();
			if(driver.findElement(By.id("username")).getAttribute("value").isEmpty()){
				driver.findElement(By.id("username")).sendKeys("editor@thefix.com");
				driver.findElement(By.id("password")).sendKeys("aZk4*cc9");
			}
			driver.findElement(By.xpath("//*[contains(text(), 'Log in')]")).click();
		}
		
		//click <a href="/campaigns/create">Create Campaign</a>
		driver.findElement(By.xpath("//*[@id='dijit_form_ComboButton_0_label']/a")).click();
		//click <a href="/campaigns/create?type=regular" class="step button mar-r0" onclick="ga('send', 'event', 'Created a Campaign', 'Regular Campaign', location.pathname);" role="button">Select</a>
		driver.findElement(By.xpath("//*[@id='predelivery-checklist']/li[1]/div/div[2]/a")).click();
		//select drop down list
		driver.findElement(By.xpath("//*[contains(text(), 'Choose a list')]")).click();
		//select newsletter
		driver.findElement(By.cssSelector("#dijit_MenuItem_4_text")).click();
		//click next
		//*[@id="goToNextStepContainer"]/a[2]
		driver.findElement(By.xpath("//*[@id='goToNextStepContainer']/a[2]")).click();
		//set "Name your campaign with name
		driver.findElement(By.id("title")).sendKeys(name);;
		//set Email subject with subject
		driver.findElement(By.xpath("//*[@id='EmojiPicker_0']/div[1]")).clear();
		driver.findElement(By.xpath("//*[@id='EmojiPicker_0']/div[1]")).sendKeys(subject);
		//click Next
		driver.findElement(By.id("goToNextStepContainer")).click();
		//select Saved Template
		driver.findElement(By.id("template-container_tablist_templates")).click();
		//select The Fix Newsletter
		driver.findElement(By.xpath("//*[@id='template-240489']/div[2]/div[2]/a")).click();
		//select BEST OF THE QUICK FIX
		//first, switch to iframe
		driver.switchTo().frame(driver.findElement(By.id("preview-template")));
		WebElement quickFix = driver.findElement(By.xpath("//*[contains(text(), 'BEST OF THE QUICK FIX')]"));
		quickFix.click();
		//switch out of the iframe
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver, 4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("cke_editor1"))));
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Rich Text Editor, editor1']")));
		for(int i = 1; i<=6; i++){
			WebElement e = driver.findElement(By.xpath("/html/body/ul/li["+i+"]"));
			e.clear();
			try{	
				Document doc = Jsoup.connect(textFieldList.get(i-1).getText()).get();
				String pageTitle = doc.getElementsByClass("rr-page-header").first().text().trim();
				Actions action = new Actions(driver);
				action.sendKeys(e, pageTitle).perform();
				new WebDriverWait(driver, 4000).until(ExpectedConditions.textToBePresentInElement(e, pageTitle));
				
			}catch(IOException a){
				//all IO error should be handled by now.
			}catch(Exception b){
				throw new Exception("A problem has occured. Please contact the developer for support. "+ b.getMessage());
			}
		}
		return errorList;
	}
}
