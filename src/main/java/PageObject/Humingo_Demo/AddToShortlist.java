package PageObject.Humingo_Demo;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import PageObject.Support.HumingoLib;
import PageObject.Support.WebActions;

public class AddToShortlist extends WebActions {

	// @FindBy(name = "firstname") public WebElement firstName;

	@FindBy(id = "shortlistBadge")
	public static WebElement lnkMyShortlist;
	@FindBy(xpath = "//*[contains(@class,'delete-shortlist')]")
	public static WebElement icnDelete;
	@FindBy(xpath = "//*[@placeholder='Start discovering your product..']")
	public static WebElement txtSearchBox;
	@FindBy(xpath = "//*[@class='input-group']//button[@type='submit']")
	public static WebElement icnSearch;
	@FindBy(xpath = "//i[contains(@class,'fa fa-heart fa-2x')]")
	public static WebElement icnHeart;
	@FindBy(xpath = "//*[@id=\"searchResults\"]/ul/li[1]/div/div[2]/div[1]/a/img")
	public static WebElement lnkProducThumbnail;
	@FindBy(xpath = "//a[@id='shortlistBadge']//span[@class='badge-count']")
	public static WebElement shortlistCount;
	@FindBy(xpath = "//*[contains(text(),' Discover by categories')]")
	public static WebElement txtDiscoverByCategories;
	@FindBy(xpath = "//*[contains(text(),' Discover by categories')]/following::*[contains(@href,'section=clothing')]")
	public static WebElement lnkClothing;
	@FindBy(xpath = "//*[contains(text(),' Discover by stores')]")
	public static WebElement txtDiscoverByStores;
	@FindBy(xpath = "//*[contains(text(),' Discover by stores')]/following::*[contains(@href,'f=channel=jabong.com')]")
	public static WebElement lnkJobongCom;
	
	public String countBeforeShortlist =null;
	public String countAfterShortlist =null; 
	
	/**
	 * This method is used to search product in search box
	 * @para humingoObj
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean filterProductBySearchTextBox(HumingoLib humingoObj) {
		boolean returnValue = false;
		try {
			countBeforeShortlist = shortlistCount.getText();
			type(txtSearchBox,humingoObj.searchProduct);
			click(icnSearch);
			returnValue = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to Add Product To Shortlist
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean addProductToShortlist() {
		boolean returnValue = false;
		try {
			mouseHover(lnkProducThumbnail);
			click(icnHeart);
			returnValue = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to Switch Into Specific Product Page
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean switchIntoProductPage() {
		boolean returnValue = false;
		try {
			click(lnkProducThumbnail);
			click(icnHeart);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to Verify Product Added In My Shortlist Or Not
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean verifyProductAddedInMyShortlist() {
		boolean returnValue = false;
		try {
			countAfterShortlist = shortlistCount.getText();
			if(Integer.parseInt(countAfterShortlist)>Integer.parseInt(countBeforeShortlist)) {
				returnValue = true;
			}else {
				returnValue = false;
			}
			
		} catch (Exception e) {
			Assert.fail("Expected result verification failed.");
			e.printStackTrace();
		}
		return returnValue;
	}
}
