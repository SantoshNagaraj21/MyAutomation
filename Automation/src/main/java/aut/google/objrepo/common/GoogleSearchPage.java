package aut.google.objrepo.common;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import aut.framework.common.BaseUITest;
import aut.framework.common.CommonUtils;

public class GoogleSearchPage extends BaseUITest {

	static By googletextbox = By.id("lst-ib");
	static By googlesrchbutton = By.xpath("//input[@value='Google Search']");

	public static WebElement gettextbox() {

		WebElement gettextbox = driver.findElement(googletextbox);
		return gettextbox;
	}

	public static WebElement getsearchbutton() {

		WebElement getsearchbutton = driver.findElement(googlesrchbutton);
		return getsearchbutton;
	}

	public static void SearchGoogleText() throws NumberFormatException, IOException {

		gettextbox().click();
		gettextbox().clear();
		gettextbox().sendKeys("Selenium");
		waitforElement(googlesrchbutton);
		getsearchbutton().click();

	}

}
