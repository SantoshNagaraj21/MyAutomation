package aut.google.objrepo.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import aut.framework.common.BaseUITest;
import aut.framework.common.CommonUtils;

public class GmailLoginPage extends BaseUITest {

	static By username = By.xpath("//input[@id='identifierId']");
	static By password = By.xpath("//input[@name='password']");
	static By next = By.xpath("//span[contains(text(),'Next')]");
	static By profileicon = By.xpath("//a[@class='gb_b gb_eb gb_R']");
	static By logout = By.id("gb_71");
	static By profile = By.xpath("//div[@id='profileIdentifier']");
	static By changeaccount = By.xpath("//p[contains(text(),'Use another account')]");

	public static WebElement getusername() {

		WebElement getusername = driver.findElement(username);
		return getusername;
	}

	public static WebElement getpassword() {

		WebElement getpassword = driver.findElement(password);
		return getpassword;
	}

	public static WebElement getnext() {

		WebElement getnext = driver.findElement(next);
		return getnext;
	}

	public static WebElement getprofileicon() {

		WebElement getprofileicon = driver.findElement(profileicon);
		return getprofileicon;
	}

	public static WebElement getlogout() {

		WebElement getlogout = driver.findElement(logout);
		return getlogout;
	}

	public static WebElement getprofile() {

		WebElement getprofile = driver.findElement(profile);
		return getprofile;
	}

	public static WebElement getchangeaccount() {

		WebElement getchangeaccount = driver.findElement(changeaccount);
		return getchangeaccount;
	}

	public static void GmailLogin(String sUserName, String sPassword, String URL) throws Exception {

		getusername().click();
		waitforElement(username);
		getusername().sendKeys(sUserName);
		getnext().click();
		Thread.sleep(3000);
		waitforElement(password);
		getpassword().click();
		getpassword().sendKeys(sPassword);
		getnext().click();

	}

	public static void GmailLogout() throws Exception {

		waitforElement(profileicon);
		getprofileicon().click();
		waitforElement(logout);
		getlogout().click();
		waitforElement(changeaccount);
		getchangeaccount().click();
		Thread.sleep(3000);

	}

}
