package demo.api.test;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import aut.framework.common.BaseAPITest;
import aut.framework.common.DataProviderClass;

public class dataProvDemo extends BaseAPITest {

  @Test(dataProvider = "APITest", dataProviderClass = DataProviderClass.class)
  public void ApiPostRequest(LinkedHashMap<String, String> dataMap) {

    Logger logger = Logger.getLogger("APITest");

    logger.info("---------------------------------------------------");
    logger.info("Running test for Scenario APITest Through Excel DataProvider");
    logger.info("---------------------------------------------------");

    // Get the data from excel̥

    String FirstName = dataMap.get("FirstName");
    String LastName = dataMap.get("LastName");
    String UserName = dataMap.get("UserName");
    String Password = dataMap.get("Password");
    String Email = dataMap.get("Email");

    System.out.println("Input Values " + FirstName + LastName + UserName + Password + Email);

  }

  @Test(enabled = true, dataProvider = "APIXMLTest", dataProviderClass = DataProviderClass.class)
  public void APiTest(LinkedHashMap<String, String> dataMap) {

    Logger logger = Logger.getLogger("APITest");

    logger.info("---------------------------------------------------");
    logger.info("Running test for Scenario api for XML");
    logger.info("---------------------------------------------------");

    String FirstName = dataMap.get("firstname");
    String ID = dataMap.get("id");
    String lastname = dataMap.get("lastname");
    String nickname = dataMap.get("nickname");
    String marks = dataMap.get("marks");

    System.out.println("Input Values " + FirstName + ID + marks + nickname + lastname);

  }

  @Parameters({"baseurl", "extendedUrl"})
  @Test(enabled = true)
  public void ApiPostRequest(String url, String exturl) {

    Logger logger = Logger.getLogger("APITest");

    logger.info("---------------------------------------------------");
    logger.info("Running test for Scenario Data Provider through T̥estNg Parameters");
    logger.info("---------------------------------------------------");

    System.out.println("Parameters from testNG Xml" + url + exturl);

  }


}
