package aut.framework.common;

import org.testng.annotations.DataProvider;


public class DataProviderClass extends aut.framework.common.DataProvider {


  @DataProvider(name = "GmailLoginPage")
  public static Object[][] GmailLoginPage() throws Exception {

    Object[][] data = testData("GmailLoginPage");
    return data;
  }

  @DataProvider(name = "CreateUser")
  public static Object[][] CreateUser() throws Exception {

    Object[][] data = testData("CreateUser");
    return data;
  }

  @DataProvider(name = "SearchData")
  public static Object[][] SearchData() throws Exception {

    Object[][] data = testData("SearchData");
    return data;
  }

  @DataProvider(name = "APITest")
  public static Object[][] APITests() throws Exception {

    Object[][] data = testData("APITest");
    return data;
  }


  @DataProvider(name = "Analytics")
  public static Object[][] Analytics() throws Exception {

    Object[][] data = testData("Analytics");
    return data;
  }

  @DataProvider(name = "SocialWorkflowIndex")
  public static Object[][] SocialWorkflowIndex() throws Exception {

    Object[][] data = testData("SocialWorkflowIndex");
    return data;
  }

  @DataProvider(name = "SocialQueries")
  public static Object[][] SocialQueries() throws Exception {

    Object[][] data = testData("SocialQueries");
    return data;
  }

  @DataProvider(name = "APIXMLTest")
  public static Object[][] APIXMLTest() throws Exception {

    Object[][] data = testDataXML("apiTest");
    return data;
  }

  @DataProvider(name = "Queries")
  public static Object[][] Queries() throws Exception {

    Object[][] data = testData("Queries");
    return data;
  }


}
