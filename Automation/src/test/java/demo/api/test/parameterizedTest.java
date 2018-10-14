package demo.api.test;

import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import aut.framework.common.BaseAPITest;

public class parameterizedTest extends BaseAPITest {

	@Parameters({ "baseurl", "extendedUrl" })
	@Test
	public void ApiPostRequest(String url, String exturl) {

		Logger logger = Logger.getLogger("APITest");

		logger.info("---------------------------------------------------");
		logger.info("Running test for Scenario apiposttest");
		logger.info("---------------------------------------------------");

		System.out.println("Parameters from testNG Xml" + url + exturl);

	}

}
