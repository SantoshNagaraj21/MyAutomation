package aut.irctc.objrepo.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

	private static WebDriver driver;

	@FindBy(how = How.CSS, using = "#userId")
	public static WebElement UserName;

	@FindBy(how = How.CSS, using = "#pwd")
	public static WebElement Password;

	@FindBy(how = How.CSS, using = "#loginText")
	public static WebElement loginbtn;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'SIGN IN')]")
	public static WebElement signinbtn;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'TRAINS')]")
	public static WebElement trains;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Book Ticket')]")
	public static WebElement bookTicket;

	@FindBy(how = How.XPATH, using = "//li[@class='menu-list header-icon-menu']//span[@class='list_text'][contains(text(),'PNR Enquiry')]")
	public static WebElement pnrEnquiry;

	public static void loginIrctc(String sUserName, String sPassword, String indicator) throws InterruptedException {

		WebElement test;

		Actions builder = new Actions(driver);
		Action mouseOverHome = builder.moveToElement(trains).build();

		mouseOverHome.perform();

		if (indicator.equalsIgnoreCase("trains")) {

			Actions mouseOverTickets = builder.moveToElement(trains).click();

			mouseOverTickets.perform();

		} else if (indicator.equalsIgnoreCase("pnrEnquiry")) {

			Actions mouseOverTickets = builder.moveToElement(pnrEnquiry).click();

			mouseOverTickets.perform();

		}

		Thread.sleep(3000);
		loginbtn.click();
		Thread.sleep(3000);
		UserName.sendKeys(sUserName);
		signinbtn.click();

	}

}
