package new1;

// Generated by Selenium IDE
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.net.URL;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class junitFunction {
  private static WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @BeforeClass
  public static void setUp() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bhash\\Downloads\\chromedriver_new1\\chromedriver.exe");  
      driver = new ChromeDriver();
      //Launch website
      driver.navigate().to("http://todomvc.com/examples/angularjs/#/");
	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
  }
  
  @AfterClass
  public static void tearDown() {
	    driver.close();
	  }
  
  // Method 1
  /* 1)Enter 3 inputs
   * 2)fetch all items from the to do list
   * 3)verify the added item is present in the To do list
   */
  

  public void itemToEnter(String name)
  {
	  driver.findElement(By.cssSelector(".new-todo")).sendKeys(name);
      driver.findElement(By.cssSelector(".new-todo")).sendKeys(Keys.ENTER);
  }
  
  public void verifyItem(String name)
  {
	  List<WebElement> listOfElements = driver.findElements(By.xpath("/html/body/ng-view/section/section/ul"));
      String[] temp = listOfElements.get(listOfElements.size()-1).getText().split("\n");
      System.out.println("Item in the list is :"+temp[temp.length-1]); //to get last element(As newly added element is the last element in the list)
      //Verify through Assert
      assertEquals(name, temp[temp.length-1]);
  }
  
  @Test
  public void test1AssertToDoList() {
	  
      // Adding to do List (Added 2 items in the list)
      String itemToAddInList="Drink Water Every Hour";
      String itemToAddInList1="Water";
      String itemToAddInList2="Cimate";
      driver.findElement(By.cssSelector(".new-todo")).click();
      itemToEnter(itemToAddInList);
      verifyItem(itemToAddInList);
      itemToEnter(itemToAddInList1);
      verifyItem(itemToAddInList1);
      itemToEnter(itemToAddInList2);
      verifyItem(itemToAddInList2);
     //verify the item added in the list
      
  }
  
  
  
  //Method 2
  /* 1)Get the name of the todo item to mark as complete
   * 2)Get the list of todo items from the to do list
   * 3)Tick the check box for marking complete 
   * 4)Once marked the todo item, the "crossover" of the text can be verified from the class "ng-scope complete"(This is the only CSS class added to make the text "CrossOver") 
   * 5)Before "marking as complete", store counter value and check with new counter value after "marking as complete"
   */
  @Test
  public void test2AssetCompleteMark() {
	  driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS) ;
	  
	  String itemToMarkComplete="Drink Water Every Hour"; //Item to Mark as Complete
	  
	  String getItemBeforeComplete=driver.findElement(By.xpath("/html/body/ng-view/section/footer/span")).getText();//get the counter value on the bottom-left before complete
	  String itemLeftNumberBeforeComplete = getItemBeforeComplete.split(" ")[0];
	  System.out.println("Counter Value Before complete ="+itemLeftNumberBeforeComplete);
	  
	  String getListOfItems=driver.findElement(By.xpath("/html/body/ng-view/section/section/ul")).getText();//get the list of Items
	  String[] listOfItems = getListOfItems.split("\n");
	  String classNameForCrossOver = "";
	  for(int i=0;i<listOfItems.length;i++) {
		  if(listOfItems[i].equals(itemToMarkComplete)) {
			  if(i == 0) {
				   driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li/div/input")).click(); //Marking the first item in the list
			  }else {
				  driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li["+(i+1)+"]/div/input")).click();  //Marking rest of the items in the list
			  }
			  //To check crossovering the text, I have got the class of the list. (In this class if it is "ng complete" then through CSS they are assinging the values). SO checking the class value
			  classNameForCrossOver = driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li["+(i+1)+"]")).getAttribute("class"); 
			  System.out.println(classNameForCrossOver);
		  }
	  }
	  assertEquals("ng-scope completed", classNameForCrossOver);
	  
	  String getItemAfterComplete=driver.findElement(By.xpath("/html/body/ng-view/section/footer/span")).getText();//get the counter value on the bottom-left after complete
	  String itemLeftNumberAfterComplete = getItemAfterComplete.split(" ")[0];
	  System.out.println("counter value after complete ="+itemLeftNumberAfterComplete);
	  assertEquals(Integer.parseInt(itemLeftNumberBeforeComplete)-1,Integer.parseInt(itemLeftNumberAfterComplete));
  }


  //Method 3
  /* 1)Get the list of items
   * 2)Get the item to delete
   * 3)Mouse hover the item to delete
   * 4)Delete by clicking "Cross" button
   * 5)Check whether the item is deleted from the list
   */
@Test
public void test3VerifyDeleteToDo() {
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
	  String itemToDelete="Drink Water Every Hour"; //Item to Delete
	  String deleteStatus="true";
	//Mouseover to delete an element from to do list
	  	String getListOfItems=driver.findElement(By.xpath("/html/body/ng-view/section/section/ul")).getText();//get the list of Items
		  String[] listOfItems = getListOfItems.split("\n");
		  for(int i=0;i<listOfItems.length;i++) {
			  if(listOfItems[i].equals(itemToDelete)) {
				  Actions act = new Actions(driver);
				  act.moveToElement(driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li["+(i+1)+"]"))).build().perform();			  
				  driver.findElement(By.xpath("//li["+i+1+"]/div/button")).click();
			  }
		  }
		  getListOfItems=driver.findElement(By.xpath("/html/body/ng-view/section/section/ul")).getText();//get the list of Items
		  listOfItems = getListOfItems.split("\n");
		  //Checking whether the item is deleted from the list
		  for(int i=0;i<listOfItems.length;i++) {
			  if(listOfItems[i].equals(itemToDelete)) {
				  System.out.println(listOfItems[i]);
				  deleteStatus="false";
			  } 
		  }
		  assertEquals(deleteStatus,"true");
}

//Method 4
/*	1) Click on "active" and "completed" button for displaying the corresponding active and completed state todo items
 *	2) This can be verified with the CSS class("ng-scope" - for active and "ng-scope completed" - for completed) which differentiates it.
 *  3)Print the results	            
 */
@Test
public void test4VerifyActiveComplete() {

	String classNameForActive="";
	String classNameForComplete="";
	String isActive="true";
	String isComplete="true";
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
	  //Click on Active button
	driver.findElement(By.linkText("Active")).click();
	//Getting the list of values
	String getListOfItems=driver.findElement(By.xpath("/html/body/ng-view/section/section/ul")).getText();//get the list of Items
	String[] listOfItems = getListOfItems.split("\n");
	//For each lst of values checking whether the class "ng scope complete" is not present. 
	if(listOfItems.length > 1) {
		for(int i=0;i<listOfItems.length;i++) {
			classNameForActive = driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li["+(i+1)+"]")).getAttribute("class"); 
			  if(classNameForActive.equals("ng-scope completed")) {
				  isActive="false";
			  }
	}
	}else if(listOfItems.length == 1){
		classNameForActive = driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li")).getAttribute("class");
		if(classNameForActive.equals("ng-scope completed")) {
			  isActive="false";
		  }
	}
	assertEquals("true",isActive);
	
	getListOfItems = "";
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
	 //Click on Completed button
		driver.findElement(By.linkText("Completed")).click();
		getListOfItems=driver.findElement(By.xpath("/html/body/ng-view/section/section/ul")).getText();//get the list of Items
		listOfItems = getListOfItems.trim().split("\n");
		//For each lst of values checking whether the class "ng scope" is not present. 
		if(listOfItems.length > 1) {
			for(int i=0;i<listOfItems.length;i++) {
				classNameForComplete = driver.findElement(By.xpath("/html/body/ng-view/section/section/ul/li["+(i+1)+"]")).getAttribute("class"); 
				  if(classNameForComplete.equals("ng-scope")) {				  
					  isComplete="false";
				  }
			}
			
		}
		else if(listOfItems.length == 1 && !listOfItems[0].equals("")){
			System.out.println("else if");
			classNameForComplete = driver.findElement(By.xpath("//html/body/ng-view/section/section/ul/li")).getAttribute("class"); 
			  if(classNameForComplete.equals("ng-scope")) {
				  isComplete="false";
			  }
		}
		assertEquals("true",isComplete);
}
}

