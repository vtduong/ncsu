package edu.ncsu.csc.itrust.hw2p1.selenium;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class Bug3AndBugHuntCase45 extends iTrustSeleniumTest {

	WebDriver driver;
	WebElement element;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp(); 
		gen.clearAllTables();
		gen.standardData();
		gen.patient1();
		gen.apptRequestConflicts();
	}
	
	/*
	 * This test is written for bug 3. Input(s) are 0s so a warning should be thrown.
	 * The test passes if it finds the warning in the page and the page remains unchanged
	 */
	@Test
	public void testNoZeroAllow() throws Exception{
		driver = login("1", "pw"); //log in as patient1
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//click Survey link
		driver.findElement(By.linkText("Survey")).click();
		for (String window : driver.getWindowHandles()){
			driver.switchTo().window(window);
		}
		assertEquals("iTrust - Patient Survey", driver.getTitle());
		//fill in waiting minutes with 0
		element = driver.findElement(By.name("waitingMinutesString"));
		element.sendKeys("0");
		//fill in exam minutes with 0
		element = driver.findElement(By.name("examMinutesString"));
		element.sendKeys("0");
		//select very satisfied
		element = driver.findElement(By.name("Satradios"));
		assertEquals("satRadio5", element.getAttribute("value"));
		element.click();
		assertTrue(element.isSelected());
		//select very satisfied
		WebElement e1;
		e1 = driver.findElement(By.name("Treradios"));
		assertEquals("treRadio5", e1.getAttribute("value"));
		//e1 = driver.findElement(By.xpath("//*[@id='Content']/form/table[3]/tbody/tr[2]/td/input"));
		e1.click();
		e1.isSelected();
		//submit survey
		element = driver.findElement(By.xpath("//*[@id='Content']/form/input[4]"));
		assertEquals("Submit Survey", element.getAttribute("value"));
		element.click();
	
		WebElement e2 = driver.findElement(By.xpath("//*[contains(text(),'Waiting Room Minutes must be 1-999')]"));
		assertNotNull(e2);
		assertEquals("iTrust - Patient Survey", driver.getTitle());
	}
	
	/**
	 * This test inserts legal input values thus the survey is submitted successfully
	 * @throws Exception
	 */
	@Test
	public void testLegalInputs() throws Exception{
		driver = login("1", "pw"); //log in as patient1
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//click Survey link
		driver.findElement(By.linkText("Survey")).click();
		for (String window : driver.getWindowHandles()){
			driver.switchTo().window(window);
		}
		assertEquals("iTrust - Patient Survey", driver.getTitle());
		//fill in waiting minutes with 1
		element = driver.findElement(By.name("waitingMinutesString"));
		element.sendKeys("1");
		//fill in exam minutes with 1
		element = driver.findElement(By.name("examMinutesString"));
		element.sendKeys("1");
		//select very satisfied
		element = driver.findElement(By.name("Satradios"));
		assertEquals("satRadio5", element.getAttribute("value"));
		element.click();
		assertTrue(element.isSelected());
		//select very satisfied
		WebElement e1;
		e1 = driver.findElement(By.name("Treradios"));
		assertEquals("treRadio5", e1.getAttribute("value"));
		//e1 = driver.findElement(By.xpath("//*[@id='Content']/form/table[3]/tbody/tr[2]/td/input"));
		e1.click();
		e1.isSelected();
		//submit survey
		element = driver.findElement(By.xpath("//*[@id='Content']/form/input[4]"));
		assertEquals("Submit Survey", element.getAttribute("value"));
		element.click();
	
		WebElement e2 = driver.findElement(By.xpath("//*[@id='iTrustContent']/div[contains(text(),'Survey Successfully Submitted')]"));
		assertNotNull(e2);
	}
	
	/**
	 * 
	 * This test is written for bug 3. Input values are negative numbers so a warning should be thrown.
	 * The test passes if it finds the warning in the page and the page remains unchanged
	 *
	 * @throws Exception
	 */
	@Test
	public void testNegativeInputs() throws Exception{
		driver = login("1", "pw"); //log in as patient1
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//click Survey link
		driver.findElement(By.linkText("Survey")).click();
		for (String window : driver.getWindowHandles()){
			driver.switchTo().window(window);
		}
		assertEquals("iTrust - Patient Survey", driver.getTitle());
		//fill in waiting minutes with -1
		element = driver.findElement(By.name("waitingMinutesString"));
		element.sendKeys("-1");
		//fill in exam minutes with -1
		element = driver.findElement(By.name("examMinutesString"));
		element.sendKeys("-1");
		//select very satisfied
		element = driver.findElement(By.name("Satradios"));
		assertEquals("satRadio5", element.getAttribute("value"));
		element.click();
		assertTrue(element.isSelected());
		//select very satisfied
		WebElement e1;
		e1 = driver.findElement(By.name("Treradios"));
		assertEquals("treRadio5", e1.getAttribute("value"));
		//e1 = driver.findElement(By.xpath("//*[@id='Content']/form/table[3]/tbody/tr[2]/td/input"));
		e1.click();
		e1.isSelected();
		//submit survey
		element = driver.findElement(By.xpath("//*[@id='Content']/form/input[4]"));
		assertEquals("Submit Survey", element.getAttribute("value"));
		element.click();
	
		WebElement e2 = driver.findElement(By.xpath("//*[contains(text(),'Waiting Room Minutes must be 1-999')]"));
		assertNotNull(e2);
	}
	
	/**
	 * This test is written for case #45 in Bug Hunt issues. The select option should not be an option for user to select.
	 * It should be disabled from listed options so that no OutOfBoundException is thrown when a user clicks "Check"
	 * The test passes if "select" is not listed as an option in the drop-down menu.
	 * @throws Exception
	 */
	@Test
	public void testOutofBoundExceptionWithSelectAsDefault() throws Exception{
		driver = login("9000000001", "pw");//login as admin Shape
		element = driver.findElement(By.xpath("//div[@id = 'iTrustContent']/div/h2[text() = 'Welcome Shape Shifter!']"));
		assertEquals("Welcome Shape Shifter!", element.getText());
		
		//select Edit Diagnoses URLs option
		element = driver.findElement(By.xpath("//ul[@class = 'nav nav-sidebar']/li/a[text() = 'Edit Diagnoses URLs']"));
		assertEquals("Edit Diagnoses URLs", element.getText());
		element.click();
		
		//see if select is as default
		element = driver.findElement(By.name("diagnoses"));
		WebElement selectOption = element.findElement(By.xpath("./option[text() = 'Select:']"));
		assertEquals("Select:", selectOption.getText());
		assertFalse(selectOption.isEnabled());
		
//		//click Check
//		element = driver.findElement(By.xpath("//*[@id='action']"));
//		element.click();
//		//check if IndexOutofBoundException is NOT thrown
//		element = driver.findElement(By.xpath("//*[@id='iTrustContent']/div[1]/span"));
//		assertNotEquals("ArrayIndexOutOfBoundsException: 19", element.getText());
	}
	
	@Override
	protected void tearDown(){
		driver.close();
	}
}


