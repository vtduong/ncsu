package edu.ncsu.csc.itrust.hw2p1.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class BugTestOneTwo extends iTrustSeleniumTest {

	protected WebDriver driver;
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testEditPersonnelSpecialtyDropDown() throws Exception {
		driver = login("9000000001", "pw");
		assertNotNull( driver );
		assertEquals( "iTrust - Admin Home", driver.getTitle() );
		driver.findElement( By.xpath("//*[@id='iTrustMenu']/div/div[2]/div[1]") ).click();
		driver.findElement( By.xpath("//*[@id='edit-menu']/ul/li[9]/a") ).click();
		assertEquals( "iTrust - Please Select a Personnel", driver.getTitle() );
		Thread.sleep( 1000 );
		driver.findElement( By.name("FIRST_NAME") ).sendKeys("Gandalf");
		driver.findElement( By.name("FIRST_NAME") ).sendKeys(Keys.RETURN);
		driver.findElement( By.id("9000000003") ).click();
		assertEquals( "iTrust - Edit Personnel", driver.getTitle() );
		WebElement element = driver.findElement( By.name("specialty") );
		assertEquals( "select", element.getTagName() );
	}
	
	public void testOfficeVisitSurveySubmitTwice() throws Exception {
		driver = login("1", "pw");
		assertNotNull( driver );
		assertEquals( "iTrust - Patient Home", driver.getTitle() );
		driver.findElement( By.linkText("Survey") ).click();
		Thread.sleep(1000);
		String surveyPageTitle = driver.getTitle();
		assertEquals( "iTrust - Patient Survey", surveyPageTitle );
		String surveyPageLink = driver.getCurrentUrl();
		driver.findElement( By.name("waitingMinutesString") ).sendKeys("5");
		driver.findElement( By.name("examMinutesString") ).sendKeys("5");
		driver.findElement( By.name("Satradios") ).click();
		driver.findElement( By.name("Treradios") ).click();
		driver.findElement( By.xpath("//*[@id='Content']/form/input[4]") ).click();
		assertTrue( surveyPageTitle != driver.getTitle() );
		driver.get( surveyPageLink );
		Thread.sleep(1000);
		WebElement element = driver.findElement( By.id("surveyRepeatErrorMessage") );
		assertTrue( element.getText().equalsIgnoreCase( "This survey has already been completed." ) );
	}
	
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
}
