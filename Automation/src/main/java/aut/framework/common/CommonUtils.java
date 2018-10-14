package aut.framework.common;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

// This Class Contains all the common Methods for UI and API Automation
public class CommonUtils {

	protected static WebDriver driver;

	public static void InitiateDriver() throws IOException {

		String browserType = ReadProperties.getPropertyValue("browser", "\\src\\main\\resources\\common\\Config.Properties");
		String geckopath = ReadProperties.getPropertyValue("GeckoDriverPath", "\\src\\main\\resources\\common\\Config.Properties");
		String chromepath = ReadProperties.getPropertyValue("ChromeDriverPath",
				"\\src\\main\\resources\\common\\Config.Properties");

		System.out.println("Browser Type is: " + browserType);
		System.out.println("Driver Path is: " + geckopath);
		System.out.println("Driver Path is: " + chromepath);

		if (browserType.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "./" + geckopath);
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
			driver = new FirefoxDriver();

		} else if (browserType.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./" + chromepath);
			driver = new ChromeDriver();

		} else {

			System.out.println("Invalid Browser Type");
			Assert.assertEquals("Invalid", "Inv");

		}

	}

	public static void LoadAndValidateURL(String urlTitle) throws IOException {

		String url = ReadProperties.getPropertyValue("URL", "\\src\\main\\resources\\common\\Config.Properties");

		System.out.println("URL is: " + url);

		driver.get(url);
		driver.manage().window().maximize();
//		String title = driver.getTitle();

//		Assert.assertEquals(title, urlTitle);

	}

	public static void CloseSession() {

		driver.quit();

	}

	public static void waitforElement(By Data) throws NumberFormatException, IOException {

		long defaultwait = Integer
				.parseInt(ReadProperties.getPropertyValue("defaultwait", "\\src\\main\\resources\\common\\Config.Properties"));

		System.out.println("Default wait: " + defaultwait);

		if (driver.findElements(Data).size() != 0) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		} else {

			driver.manage().timeouts().implicitlyWait(defaultwait, TimeUnit.SECONDS);
		}

	}

}
