	package cs326.hw1p2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebTest
{
	private static 	WebDriver driver;
	
	@Before
	public void setUp() throws Exception 
	{
		//added 'true' to enable JavaScript
		//driver = new HtmlUnitDriver(true);
		//System.setProperty("webdriver.chrome.driver", "/Users/gameweld/classes/326/HW1.P2/hw1p2/chromedriver");
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
	}
	
	@Ignore	
	public void googleExists() throws Exception
	{
		this.driver.get("http://www.google.com");
        assertEquals("Google", this.driver.getTitle());		
	}
	
	@Test
	public void googleiTrustNumberOne() throws Exception
	{
		this.driver.get("http://www.google.com");
		//this.driver.
		WebElement search = this.driver.findElement(By.name("q"));
		search.sendKeys("ncsu iTrust");
		search.sendKeys(Keys.RETURN);
		//this.driver.findElement(By.name("btnK")).click();
		
		WebDriverWait wait = new WebDriverWait(this.driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultStats")));

		List<WebElement> links = this.driver.findElements(By.xpath("//a[@data-href]"));
		int rank = 0;
		for( WebElement link : links )
		{
			if( link.getAttribute("data-href").equals("http://agile.csc.ncsu.edu/iTrust/wiki/"))
			{
				break;
			}
			rank++;
		}
		
		assertEquals(0, rank);
	}
	
	@Test
	public void checkboxioFrustratationEquals55() throws Exception
	{
	    driver.get("http://www.checkbox.io/studies.html");
	    // http://stackoverflow.com/questions/14570857/retrieve-an-xpath-text-contains-using-text
	    WebElement title = driver.findElement(By.xpath("//h3/span[.='Frustration of Software Developers']"));
	    WebElement participants = driver.findElement(By.xpath("//span[@class='backers' and text()='55']"));

	    assertNotNull(title);
	    assertNotNull(participants);

	    // http://stackoverflow.com/questions/8577636/select-parent-element-of-known-element-in-selenium
	    WebElement parent = title.findElement(By.xpath(".."));
	    assertNotNull(parent);
	    // http://stackoverflow.com/questions/23021830/xpath-to-select-following-sibling
	    WebElement next = parent.findElement(By.xpath("//following-sibling::div/p/span[@class='backers' and text()='55']"));
	    assertNotNull(next);
	    assertEquals(next.getText(), "55");
	    }
	
	@Test
	public void checkNumberOfStudiesClosed() throws Exception
	{
	    driver.get("http://www.checkbox.io/studies.html");
	 
	    List<WebElement> closed = driver.findElements(By.xpath("//a[@class='status' and span[text()='CLOSED']]"));
	    //could also use: xpath("span[@"data-bind = 'status: text']
	    assertNotNull(closed);
	    //assert number of closed topics is 5
		assertEquals(5, closed.size());
		
	}
	@Test
	public void checkButtonClickOpenStudies() throws Exception
    {
        driver.get("http://www.checkbox.io/studies.html");
    
        List<WebElement> open = driver.findElements(By.xpath("//a[@class='status' and span[text()='OPEN']]"));
        assertNotNull(open);
        int num = open.size();
        int actualNum = 0;
        String url = null;
        ArrayList<String> buttonURL = new ArrayList<String>();
      
        for (WebElement w : open){
            WebElement parent = w.findElement(By.xpath("../.."));
            WebElement next = parent.findElement(By.xpath(".//button"));
            url = next.getAttribute("data-href");
            System.out.println("buttonURL before add: "+ url);
            buttonURL.add(url);
            next.click();
       }
        int i = buttonURL.size()-1;
        for (String winHandle : driver.getWindowHandles()) {
        	//the driver switches to the most recently opened window, thus the order of opening new is reversse of the driver's window
        	driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        	String currentURL = driver.getCurrentUrl();
        	if (currentURL.equals(buttonURL.get(i))){
        		i--;
        		actualNum++;
        	}
        }
       assertEquals(num, actualNum);
    }
	
	@Test
	public void testCheckEnterText(){
		driver.get("http://checkbox.io/studies/?id=569e667f12101f8a12000001");
		WebDriverWait wait = new WebDriverWait(this.driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'HappyFace Frustration Survey')]")));
		WebElement question = driver.findElement(By.xpath("//div[@data-kind = 'singlechoice' and @data-question = '1']"));
		WebElement radioButton = question.findElement(By.xpath("//input[@type='radio' and @value='0']"));
		assertNotNull(radioButton);
		radioButton.click();
		radioButton.sendKeys(Keys.RETURN);
		assertTrue(radioButton.isSelected());
		WebElement question2 = driver.findElement(By.xpath("//div[@data-kind = 'textarea' and @data-question = '2']"));
		assertNotNull(question2);
		WebElement textArea1 = question2.findElement(By.xpath(".//textarea"));
		assertNotNull(textArea1);
		textArea1.sendKeys("I don't know");
		assertEquals("I don't know", textArea1.getAttribute("value"));
		
		WebElement question3 = driver.findElement(By.xpath("//div[@data-kind = 'textarea' and @data-question = '3']"));
		assertNotNull(question3);
		WebElement textArea2 = question3.findElement(By.xpath(".//textarea"));
		assertNotNull(textArea2);
		textArea2.sendKeys("I love you");
		assertEquals("I love you", textArea2.getAttribute("value"));	
	}
	
	
	@After
	public void  tearDown() throws Exception
	{
	    driver.quit();
	}
}

