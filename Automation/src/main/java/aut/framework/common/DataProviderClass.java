package aut.framework.common;

import org.testng.annotations.DataProvider;


public class DataProviderClass extends aut.framework.common.DataProvider{

	
	@DataProvider(name = "GmailLoginPage")
	public static Object[][] GmailLoginPage() throws Exception {

		Object[][] data = testData("GmailLoginPage");
		return data;
	}
	
	@DataProvider(name = "APITest")
	public static Object[][] APITests() throws Exception {

		Object[][] data = testData("APITest");
		return data;
	}
	
	@DataProvider(name = "APIXMLTest")
	public static Object[][] APIXMLTest() throws Exception {

		Object[][] data = testDataXML("apiTest");
		return data;
	}

}
