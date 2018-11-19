package PageObject.Support;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

public class WebActions {

	// creating object for Extent Report
	ExtentReport extentReportObject = new ExtentReport();
	// Create Object For DatabaseActions
		

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
	// Config Properties
	public static String browser = PropertyReader.readProperty("browser");
	public static WebDriver driver = null;
	public String previousUserName = null;
	public static String file = null;

	// Environment
	public String url = PropertyReader.readProperty("url");

	// Login Credentials
	public static String userName = PropertyReader.readProperty("username");
	public static String login_Email = PropertyReader.readProperty("login_Email");
	public static String login_Password = PropertyReader.readProperty("login_password");

	// Register New User Credentials
	public String new_username = PropertyReader.readProperty("new_username");
	public String newuser_password = PropertyReader.readProperty("newuser_password");
	public String new_email = PropertyReader.readProperty("new_email");
	
	//Jira Switch
	public final String JIRA_SWITCH; //= PropertyReader.readProperty("jira_switch");
	public String JIRA_ENGINE_URL = PropertyReader.readProperty("jira_engine_url");
	public String JIRA_USERNAME = PropertyReader.readProperty("jira_username");
	public String JIRA_PASSWORD = PropertyReader.readProperty("_password");
	public String JIRA_PROJECTID = PropertyReader.readProperty("jira_projectID");
		

	// After method
	public static ExtentTest test;
	public String message = "";
	public int stepNum = 1;
	public String testCaseName;
	public String testRailId;

	public int test_runId;
	public String testCaseID;
	public String summary;

	public String Case_id;
	public String testCaseStatus;
	
	public static DOMConfigurator domconfig;

	public WebActions() {
	domconfig = new DOMConfigurator();
	DOMConfigurator.configure("log4j.xml");
	JIRA_SWITCH = PropertyReader.readProperty("jira_switch");
	}
	
	
	@FindBy(id = "username")
	public static WebElement txUserName;

	@FindBy(id = "login-pass")
	public static WebElement txtPassword;
	@FindBy(id = "loginButton")
	public static WebElement btnLogin;
	@FindBy(linkText = "Login")
	public static WebElement lnkLogin;
	
	@FindBy(id = "shortlistBadge")
	public static WebElement lnkMyShortlist;
	@FindBy(xpath = "//*[contains(@class,'delete-shortlist')]")
	public static WebElement icnDelete;
	
	@FindBy(xpath = "//*[contains(@class,'dropdown-menu pull-right')]/following::*[contains(@href,'/logout')]")
	public static WebElement lnkLogout;
	@FindBy(xpath = "//div[@class='col-md-6 col-sm-6']//a[@class='dropdown-toggle']")
	public static WebElement humnigoUserName;

	@BeforeSuite
	public void beforeSuite() throws IOException {
		extentReportObject.publishReports();

	}

	public String incrementSteps() {
		return "Step No: " + stepNum++;
	}

	@AfterSuite
	public void afterSuite() {
		if (driver != null) {
			// driver.quit();
		}
		ExtentReport.extent.flush();
	}

	 @AfterMethod
	public void getResult(ITestResult result) {
		try {
			System.out.println(System.getenv("BUILD_NUMBER"));
			String screenShotPath = null;
			// creating object for ScreenShotCapture
			ScreenShotCapture screenshotObject = new ScreenShotCapture();
			if (result.getStatus() == ITestResult.FAILURE) {
				Log.error(message);
				test.log(LogStatus.FAIL, message + "- FAIL");

				// Logging into JIRA for Failure

				test.log(LogStatus.FAIL, result.getThrowable());
				if (driver != null) {
					screenShotPath = screenshotObject.capture(driver, generateFileName(result));
					test.log(LogStatus.FAIL,
							"Snapshot below: " + result.getMethod() + test.addScreenCapture(screenShotPath + ".png"));
				}

				testCaseStatus = "FAIL";
			} else {
				testCaseStatus = "PASS";
				test.log(LogStatus.PASS, "Test case Executed Successfully - PASS");
			}
		
			
			// Ending Extent Test
			ExtentReport.extent.endTest(test);
			// Logout post pass or fail
			if (driver != null) {
				driver.quit();
			}
            driver = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	 public void jiraStatusUpdate() {
		 
	 }
	 

	/**
	 * This method is used to launch Webdriver.
	 */
	public void launchDriver() {
		File dir1 = new File(".");
		try {
			if(driver == null) {
				 if(browser.trim().toLowerCase().equals("chrome")) {
						System.setProperty("webdriver.chrome.driver", "C:\\WebdriverPath\\drivers\\chromedriver.exe");
						driver = new ChromeDriver();
					}
			}
		} catch (Exception e) {
			Assert.fail("Could not launch Webdriver");
		}
	}

	
	public void loginToAnApplication() {

		try {
			driver.get("http://www.google.com");
			WebElement element = driver.findElement(By.name("q"));

			 element.sendKeys("BrowserStack");
			 element.submit();

			System.out.println(driver.getTitle());

		} catch (AssertionError e) {
			e.printStackTrace();
			Assert.fail("Could not login to the application");
		}
	}
	/**
	 * This method is used to get url from config properties.
	 */
	public void getURL() {
		try {

			driver.get(url);

		} catch (Exception e) {
			Assert.fail("Could not launch URL");
		}
	}

	public boolean click(WebElement element) {
		boolean returnValue = false;
		try {
			waitForElementToVisible(element);
			isElementClickable(element);
			Thread.sleep(3000);
			element.click();
			returnValue = true;
		} catch (Exception e) {
			return returnValue;
		}
		return returnValue;
	}
	
		
	public WebElement findElementByText(String elementText) {
		WebElement returnValue = null;
		try {
			driver.findElement(By.linkText(elementText));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
		
	}

	/**
	 * This method is used to check element is clickable or not.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean isElementClickable(WebElement element) {
		boolean isElementClickable = false;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			isElementClickable = true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not clickable - " + element);
		}
		return isElementClickable;
	}

	/**
	 * This method is used to scroll down the element.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean scroll(WebElement element) {
		boolean Scroll = false;
		try {
		waitForElementToVisible(element);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		Scroll = true;
		} catch (Exception e) {
		e.printStackTrace();
		Assert.fail("Could not Scroll - " +element);
		}
		return Scroll;
		}

	/**
	 * This method is used to type text in the textbox.
	 * 
	 * @param by
	 * @param textToType
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean type(WebElement element, String textToType) {
		boolean returnValue = false;
		try {
			element.clear();
			Thread.sleep(3000);
			element.sendKeys(textToType);
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not Type - " + textToType);
		}
		return returnValue;
	}
	
	
	/**
	 * This method is used to get the text from specify path.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public String getText(By by) {
		String returnValue = "";
		try {
			returnValue = driver.findElement(by).getText();
		} catch (Exception e) {
			returnValue = "";
		}
		return returnValue;
	}

	/**
	 * This method is used to get textbox values.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public String getAttribute(By by) {
		String returnValue = "";
		try {
			returnValue = driver.findElement(by).getAttribute("value");
		} catch (Exception e) {
			returnValue = "";
		}
		return returnValue;
	}

	/**
	 * This method is used to close the alert box.
	 * 
	 * @return boolean true if is successfully executed, else false.
	 */
	public Boolean handleAlert() {
		// String message = null;
		boolean foundAlert = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			message = alert.getText();
			alert.accept();
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}

	/**
	 * This method is used to switching between multiple windows
	 * 
	 * @param windowName
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean switchToNewWindow(String windowName) {
		boolean returnValue = false;
		try {
			Set<String> s = driver.getWindowHandles();
			Iterator<String> ite = s.iterator();
			int window = 1;
			while (ite.hasNext() && window < 10) {
				String popUpHandle = ite.next().toString();
				driver.switchTo().window(popUpHandle);
				if (driver.getTitle().equalsIgnoreCase(windowName)) {
					returnValue = true;
					break;
				}
				window++;
			}
		} catch (Exception e) {
			Assert.fail("Could Not Able To Switch to New Window");
		}
		return returnValue;
	}

	/**
	 * This method is used to switching between multiple windows and also verify
	 * elements on the new window.
	 * 
	 * @param windowName
	 * @param id
	 * @param text
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean switchToNewWindow(String windowName, By id, String text) {
		boolean returnValue = false;
		try {
			Set<String> s = driver.getWindowHandles();
			Iterator<String> ite = s.iterator();
			int window = 1;
			while (ite.hasNext() && window < 10) {
				String popUpHandle = ite.next().toString();
				driver.switchTo().window(popUpHandle);
				if (driver.getTitle().equalsIgnoreCase(windowName) && getText(id).equalsIgnoreCase(text)) {
					returnValue = true;
					break;
				}
				window++;
			}
		} catch (Exception e) {
			Assert.fail("Could Not Able To Switch to New Window");
		}
		return returnValue;
	}

	/**
	 * This method is used to select values from dropdown based on iteration.
	 * 
	 * @param textBox
	 * @param Value
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean selectFromDropDown(By textBox, String Value) {
		boolean DropDown = false;
		try {
			WebElement pro = driver.findElement(textBox);
			List<WebElement> options = pro.findElements(By.tagName("option"));
			for (int i = 0; i < options.size(); i++) {
				String val = options.get(i).getText().trim();
				if (val.equalsIgnoreCase(Value)) {
					options.get(i).click();
				}
			}

			DropDown = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DropDown;
	}

	/**
	 * This method is used to select dropdown based on visible text.
	 * 
	 * @param by
	 * @param textToSelect
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean selectByVisibleText(By by, String textToSelect) {
		boolean returnValue = false;
		try {
			Select select = new Select(driver.findElement(by));
			select.selectByVisibleText(textToSelect);
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("Could not select - " + textToSelect);
		}
		return returnValue;
	}

	/**
	 * This method is used to select dropdown values based on index.
	 * 
	 * @param by
	 * @param indexToSelect
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean selectByIndex(By by, int indexToSelect) {
		boolean returnValue = false;
		try {
			Select select = new Select(driver.findElement(by));
			select.selectByIndex(indexToSelect);
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("Could not select - " + indexToSelect);
		}
		return returnValue;
	}

	/**
	 * This method is used to check the element is present or not.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean isElementPresent(By by) {

		boolean returnValue = false;
		try {
			driver.findElement(by);
			returnValue = true;
		} catch (Exception e) {
		}
		return returnValue;
	}

	/**
	 * This method is used to check the element is displayed or not.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean isElementDisplayed(By by) {

		boolean returnValue = false;
		try {
			isElementPresent(by);
			returnValue = driver.findElement(by).isDisplayed();
		} catch (Exception e) {
		}
		return returnValue;
	}

	/**
	 * This method is used to clear the existing values in textbox.
	 * 
	 * @param by
	 */
	public void clear(By by) {
		try {
			driver.findElement(by).clear();
		} catch (Exception e) {
			Assert.fail("Could not clear the text box");
		}
	}

	public static void main(String args[]) {

		// System.out.println(readProperty("testrail_engine_url"));

	}
	
	public boolean initializeElement() {
		boolean returnValue = false;
		try {

			PageFactory.initElements(driver, this);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * This method is used to wait for the element to visible.
	 * 
	 * @param by
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean waitForElementToVisible(WebElement element) {
		boolean returnValue = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 250);
			wait.until(ExpectedConditions.visibilityOf(element));
			// wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * To verify text messages is equals or not.
	 * 
	 * @param elementPath
	 * @param textValue
	 * @return
	 */
	public boolean verifyTextMessages(By elementPath, String textValue) {
		boolean returnValue = false;
		try {
			Assert.assertEquals(getText(elementPath), textValue);
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * To verify text box messages is equals or not.
	 * 
	 * @param elementPath
	 * @param textValue
	 * @return
	 */
	public boolean verifyTextBoxMessages(By elementPath, String textValue) {
		boolean returnValue = false;
		try {
			Assert.assertEquals(getAttribute(elementPath), textValue);
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * create generic file name.
	 * 
	 * @param result
	 * @return
	 */
	public static String generateFileName(ITestResult result) {

		Date date = new Date();
		String fileName = result.getName() + "_" + sdf.format(date);
		return fileName;

	}

	/**
	 * This method is used to Browse the file for upload
	 * 
	 * @param elementName
	 * @param fileName
	 * @return boolean true if successfully browsed the file
	 *
	 */

	public boolean uploadFile(By elementName, String fileName) {
		boolean returnValue = false;
		try {
			File dirl = new File(".");
			String uploadFile = dirl.getCanonicalPath() + File.separator + "src" + File.separator + "data"
					+ File.separator + "UploadFile" + File.separator;
			WebElement uploadElement = driver.findElement(elementName);
			// enter the file path onto the file-selection input field
			uploadElement.sendKeys(uploadFile + fileName);
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("Could not Browse " + fileName);
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * This method is used to login to Humingo.
	 * 
	 * @param username
	 * @param password
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean loginToHumingo(String username, String password) {
		boolean loginToHumingo = false;
		try {
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
			click(lnkLogin);
			type(txUserName, username);
			type(txtPassword, password);
			if (click(btnLogin)) {

			}
			// Assert.assertEquals("Sign Out", getText(lnkSignout));
			loginToHumingo = true;
		} catch (AssertionError e) {
			e.printStackTrace();
			Assert.fail("Could not login to the application");
		}
		return loginToHumingo;
	}
	
	/**
	 * This method is used for Mouse Hover on specific element
	 * 
	 * @param By
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean mouseHover(WebElement element) {
		boolean returnValue = false;
		try {
			Actions action = new Actions(driver);
			//WebElement ele = driver.findElement(by);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * This method is used to get Content from Browser
	 * 
	 * @return content
	 */
	public String getContentUnderBodyTag() {
		String content = null;
		try {
			content = driver.findElement(By.xpath("/html/body")).getText();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * This method is used to verify the element is visible,selected, present,open
	 * or not. Eg. 'Search' button is visible.
	 * 
	 * @param elementDetail
	 *            -- Value of the expected result Eg.Search , template preview, etc.
	 * @return boolean true if verified successfully
	 */
	public Boolean verifyExpectedResult(String elementDetail) {
		Boolean returnValue = false;

		try {
			boolean isTheTextPresent = driver.getPageSource().contains(elementDetail);
			assertTrue(isTheTextPresent);
			return true;
		} catch (Exception e) {
			Assert.fail("Expected result verification failed.");
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	/**
	 * This method used to make the my shortlist as empty.
	 * 
	 * @return boolean true if successfully empty the My Shortlist, else false.
	 */
	public boolean deleteMyShortList() {

		
		boolean returnValue = false;
		try {
			String text = lnkMyShortlist.getText();
			String value = text.replace("My Shortlist", "");
			value = value.replace("\n", "");
			int x = Integer.parseInt(value);
			if (x != 0) {
				click(lnkMyShortlist);
				for (int i = 0; i < x; i++) {
					click(icnDelete);
					Thread.sleep(2000);
					Actions act = new Actions(driver);
					act.sendKeys(Keys.ENTER).build().perform();
					Thread.sleep(2000);
				}
			}
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("could not delete my shortlist items");
		}
		return returnValue;
	}
	
	public boolean logout() {
		boolean returnValue = false;
		try {
			click(humnigoUserName);
			click(lnkLogout);
			returnValue = true;
		}catch(Exception e) {
	
		}
		return returnValue;
	}
	
	/**
	 * It is used to get todays date
	 * 
	 * @return
	 */
	public String getTodayDate() {
		String getDate = "";
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			getDate = dtf.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDate;
	}

	
	/**
	 * It is used to verify data from database
	 * 
	 * @param humingoObj
	 * @param arrayList
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean verifyDBStatus(HumingoLib humingoObject,String arrayList) {
		
		boolean returnValue = false;
		try {
			DatabaseActions databaseactionsObject = new DatabaseActions();
			String query = humingoObject.selectQuery;
			String[] data = databaseactionsObject.fetchQuery(query, arrayList);
			String actualValue = humingoObject.expectedResult;
			for (int val = 0; val < data.length; val++) {
				if (data[val] != null) {
					System.out.println("Actual: "+actualValue);
					System.out.println("Expected: "+data[val]);
					Assert.assertEquals(actualValue, data[val]);
				} else {
					break;
				}
			}
			returnValue = true;
		} catch (Exception e) {
			Assert.fail("Failed in Verify DB Status");
		}
		return returnValue;
	}
	
	/**
	 * It is used to get Time Stamp
	 * 
	 * @param humingoObj
	 * @param arrayList
	 * @return String execution date with time
	 */
	public String getTimestamp() {
		String returnValue = null;
		try {
			Date date= new Date();
			long time = date. getTime();
			//System. out. println("Time in Milliseconds: " + time);
			Timestamp ts = new Timestamp(time);
			String ts1 = ts.toString();
			System.out.println(ts1);
			//System. out. println("Current Time Stamp: " + ts);
			
			String time1 =ts1.substring(0, 19);
			System.out.println(time1);
			returnValue = time1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}


}
