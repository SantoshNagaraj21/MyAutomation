package set.fusion.ui.google.test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import set.fusion.common.BaseUITest;
import set.fusion.ui.google.objrepo.GoogleSearchPage;

public class GoogleTextSearch extends BaseUITest {

	@Test
	public static void UITest() throws IOException, InterruptedException {

		GoogleSearchPage.SearchGoogleText();
		driver.findElement(By.xpath("//a[contains(text(),'Selenium - Web Browser Automation')]")).click();
		String navTitle = driver.getTitle();
		Assert.assertEquals(navTitle, "Selenium - Web Browser Automation");

	}

}
