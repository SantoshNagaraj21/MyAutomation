package socialsearch;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import aut.framework.common.BaseAPITest;
import aut.framework.common.CommonAPIUtils;
import aut.framework.common.DataProviderClass;

public class SocialSearchTest extends BaseAPITest {

	@Test(dataProvider = "SearchData", dataProviderClass = DataProviderClass.class)
	public void ApiPostRequest(LinkedHashMap<String, String> dataMap) {

		Logger logger = Logger.getLogger("SearchData");

		logger.info("---------------------------------------------------");
		logger.info("Running test for Scenario APITest Through Excel DataProvider");
		logger.info("---------------------------------------------------");

		// Get the data from excel

		String FirstName = dataMap.get("FirstName");
		String LastName = dataMap.get("LastName");
		String UserName = dataMap.get("UserName");
		String Password = dataMap.get("Password");
		String Email = dataMap.get("Email");

		System.out.println("Input Values " + FirstName + LastName + UserName + Password + Email);

		String child1 = CommonAPIUtils.createRequestBody("name=" + FirstName, "salary=" + LastName, "age=" + UserName);

		String child2 = CommonAPIUtils.createRequestBody("name=" + FirstName, "salary=" + LastName, "age=" + UserName);

		String nestedChild = CommonAPIUtils.createJsonObject("name=Santoshtest", "Child:child1=" + child1, "salary=123",
				"age=23");

		String request = CommonAPIUtils.createJsonObject("name=Santoshtest", "Child:nestedChild=" + nestedChild,
				"salary=123", "Child:Ch1=" + child1, "age=23", "Child:Ch2=" + child2);

		String jsonobjArrays = CommonAPIUtils.createJsonArrayRequestBody(request);

		System.out.println("JSON Obj Array " + jsonobjArrays);

	}

}
