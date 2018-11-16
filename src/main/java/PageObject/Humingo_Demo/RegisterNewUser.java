package PageObject.Humingo_Demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import PageObject.Support.WebActions;

public class RegisterNewUser extends WebActions{
	
	@FindBy(how = How.LINK_TEXT, using = "Register")public static WebElement lnkRegister;
	@FindBy(id = "name")public static WebElement txtName;
	@FindBy(id = "email")public static WebElement txtEmail;
	@FindBy(id = "registerPass")public static WebElement txtPassword;
	@FindBy(xpath = "//*[@id=\"registerForm\"]/div[4]/label[2]/input")public static WebElement rdoFemale;
	@FindBy(xpath = "//*[@id=\"registerForm\"]/div[4]/label[1]/input")public static WebElement rdoMale;
	@FindBy(name = "location")public static WebElement city;
	@FindBy(id = "mobileNo")public static WebElement mobileNumber;
	@FindBy(xpath = "//*[@id=\"registerForm\"]/button")public static WebElement btnRegister;
	@FindBy(id = "navLogin")public static WebElement navigateLogin;
	@FindBy(id = "regError")public static WebElement registerError;
	@FindBy(xpath = "//*[@id='register-modal']//*[@class='fa fa-2x fa-times-circle']")public static WebElement closeRegisterPopUp;
	
	@FindBy(className = "dropdown-menu pull-right")public static WebElement userDropDownValues;
	@FindBy(xpath = "//*[@id=\"upperHeader\"]/div/div/div[2]/ul/li/ul/li[1]/a")public static WebElement lnkMyAccount;
	@FindBy(xpath = "//*[@id=\"upperHeader\"]/div/div/div[2]/ul/li/ul/li[2]/a")public static WebElement lnkLogout;
	
	/**
	 * This method is used to Create New User
	 * @return boolean true if is successfully executed, else false.
	 */
	public String createNewUser() {
		String returnValue = null;
		try {
			String gender = "Female";
			String location = "chennai";
			String mobNo = "9999999234";
			click(lnkRegister);
			type(txtName,new_username);
			type(txtEmail,new_email);
			type(txtPassword,newuser_password);
			if(gender.equalsIgnoreCase("Male")) {
				click(rdoMale);
			}else if(gender.equalsIgnoreCase("Female")) {
				click(rdoFemale);
			}
			type(city,location);
			type(mobileNumber,mobNo);
			click(btnRegister);
			if((registerError.getText().contains("User Already Exists"))){
				returnValue =  "User Already Exists";
			}else {
				returnValue = "Registered Successfully";
			}
			click(closeRegisterPopUp);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to Navigate To Login from create new popup
	 * @return boolean true if is successfully executed, else false.
	 */
	public boolean navigateToLogin() {
		boolean returnValue = false;
		try {
			click(navigateLogin);
			returnValue = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to Verify Login
	 * @return boolean true if is successfully executed, else false.
	 */
	public String verifyLogin() {
		String returnValue = null;
		try {
			String LoginName = humnigoUserName.getText();
			if(LoginName.equals(userName)) {
				returnValue = userName+"displayed on the top right corner";	
			}else {
				returnValue = userName+"is not displayed on the top right corner";	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to Verify User Drop Down
	 * @return boolean true if is successfully executed, else false.
	 */
	public String verifyUserDropDown() {
		String returnValue = null;
		try {
			click(humnigoUserName);
			if(lnkMyAccount.getText().equalsIgnoreCase("My Account")) {
				if(lnkLogout.getText().equalsIgnoreCase("Logout")) {
					returnValue = "My Account And Logout displayed in user Dropdown";
				}else {
					returnValue = "My Account and Logout not displayed in user dropdown";
				}
			}
			click(humnigoUserName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
}
