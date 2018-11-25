package aut.google.testscenarios.login;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import aut.framework.common.BaseUITest;
import aut.framework.common.DataProviderClass;
import aut.google.objrepo.common.GmailLoginPage;

public class GmailLogin extends BaseUITest {

	@Test(dataProvider = "GmailLoginPage", dataProviderClass = DataProviderClass.class)
	public static void LoginTest(LinkedHashMap<String, String> dataMap) throws Exception {
		
		Logger logger = Logger.getLogger("GmailLoginTest");
		
		String runvalue = dataMap.get("Run");
		String username = dataMap.get("UserName");
		String password = dataMap.get("Password");
		String url = dataMap.get("URL");

		System.out.println("Username and Password " + username + " " + password);

		logger.info("Logging into GMAIL");
		GmailLoginPage.GmailLogin(username, password, url);
		logger.info("Logging out of GMAIL");
		GmailLoginPage.GmailLogout();
		

	}

}
