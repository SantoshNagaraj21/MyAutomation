package aut.irctc.objrepo.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class IrctcPageInitialization {

	public static void initializeAllPages(WebDriver driver) {

		LoginPage LoginPage = PageFactory.initElements(driver, LoginPage.class);

	}

}
