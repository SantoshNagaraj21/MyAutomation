package set.fusion.common;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUITest extends CommonUtils {

	// This is the Base Test Class for the tests and test suites

	@BeforeClass
	public void InitialSetup() throws IOException {
		
		PropertyConfigurator.configure("./" + "\\src\\main\\resources\\common\\log4j.properties");
		String urlTitle = ReadProperties.getPropertyValue("URLTitle", "\\src\\main\\resources\\common\\Config.Properties");
		
		CommonUtils.InitiateDriver();
		CommonUtils.LoadAndValidateURL(urlTitle);
	}

	@AfterClass
	public void Closure() throws IOException {

		CommonUtils.CloseSession();

	}

}
