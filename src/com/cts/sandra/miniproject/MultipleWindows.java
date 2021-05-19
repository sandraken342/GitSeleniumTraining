package com.cts.sandra.miniproject;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class MultipleWindows {
	static WebDriver driver=null;
	public static void main(String[] args) throws InterruptedException {
		Scanner sc=new Scanner(System.in);	
		
		//Multiple browser option
		System.out.println("Select Browser\n1.Chrome\n2.Firefox");
		String b =sc.next();
		if(b.equals("Chrome"))
		{
			//Launch Chrome browser
			System.setProperty("webdriver.chrome.driver","C:\\Users\\Prabu\\eclipse-workspace\\MiniProject\\driver\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.get("https://www.rediff.com/");
		}
		else
		{
			//Launch FireFox browser
			System.setProperty("webdriver.gecko.driver","C:\\Users\\Prabu\\eclipse-workspace\\MiniProject\\driver\\geckodriver.exe");
			driver=new FirefoxDriver();
			driver.get("https://www.rediff.com/");
		}
		
		//Click on "Create Account" link
		driver.findElement(By.xpath("//a[contains(text(),'Create Account')]")).click();

		//Validate “Create Rediffmail account” webpage is opened.
		try{
			Assert.assertEquals("Rediffmail Free Unlimited Storage",driver.getTitle());
			System.out.println("Navigated to correct webpage: Create Rediffmail account.");
			}
			catch(Throwable pageNavigationError){
			  System.out.println("Didn't navigate to correct webpage: Create Rediffmail account.");
			}
		
		

		//Find the total number of links in “Create Rediffmail account” webpage and print the links.
		List<WebElement>links=driver.findElements(By.tagName("a"));
		System.out.println("Total number of links are : "+links.size());
		  for(WebElement link : links )
           {
			  System.out.println(link.getText()+ " - " +link.getAttribute("href"));
           }
		
		//Click on "terms and conditions" link
		driver.findElement(By.xpath("//a[contains(text(),'terms and conditions')]")).click();
		
		Set<String> windowIds=driver.getWindowHandles();
		java.util.Iterator<String> itr=windowIds.iterator();
		String parentPageId=itr.next();
		String childPageId=itr.next();
		driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);

		System.out.println(driver.getCurrentUrl());
		
		//Switch to the child window
		driver.switchTo().window(childPageId);
		driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);
		
		//Validate child window “Terms and Conditions” is opened.
		try{
			System.out.println(driver.getCurrentUrl());
			  Assert.assertEquals("http://register.rediff.com/help/terms.htm", driver.getCurrentUrl());
			  System.out.println("Terms and Conditions child window is opened.");
			}
			catch(Throwable pageNavigationError){
			  System.out.println("Terms and Conditions child window is not opened.");
			}
	

		//Get the title of the child window
		System.out.println("Title of the Child Window: "+driver.getTitle());
		
		//Validate the title of the child window with the expected title.
		try{
			  Assert.assertEquals("Rediffmail: Terms and Conditions",driver.getTitle());
			  System.out.println("The title of the child window is same as the expected title.");
			}
			catch(Throwable pageNavigationError){
			  System.out.println("The title of the child window is not same as the expected title.");
			}
		
		
		//close the child window
		driver.close();
		
		//Switch to the parent window(“Create Rediffmail account” webpage)
		driver.switchTo().window(parentPageId);
		driver.manage().timeouts().implicitlyWait(50000,TimeUnit.SECONDS);
		
		//Quit from the browser
		driver.quit();
		sc.close();

	 
	}

}
