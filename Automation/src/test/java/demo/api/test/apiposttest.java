package demo.api.test;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import aut.framework.common.BaseAPITest;
import aut.framework.common.DataProviderClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class apiposttest extends BaseAPITest {
	
	@Test(dataProvider="APITest",dataProviderClass=DataProviderClass.class)
	public void ApiPostRequest(LinkedHashMap<String, String> dataMap) {
		
		Logger logger = Logger.getLogger("APITest");
		
		logger.info("---------------------------------------------------");
		logger.info("Running test for Scenario apiposttest");
		logger.info("---------------------------------------------------");

		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		RequestSpecification request = RestAssured.given();
		
		// Get the data from excel
		
		String FirstName=dataMap.get("FirstName");
		String LastName=dataMap.get("LastName");
		String UserName=dataMap.get("UserName");
		String Password=dataMap.get("Password");
		String Email=dataMap.get("Email");
		
		System.out.println("Input Values " + FirstName + LastName + UserName + Password + Email);
				
//		JSONObject requestParams = new JSONObject();

//		requestParams.put("FirstName", FirstName); // Cast
//		requestParams.put("LastName", LastName);
//
//		requestParams.put("UserName", UserName);	
//		requestParams.put("Password", Password);
//		requestParams.put("Email", Email);
//
//		request.header("Content-Type", "application/json");
//		request.body(requestParams.toJSONString());
	
		Response response = request.get("/employees");		

		String responseBody = response.getBody().asString();

		System.out.println("Response Body of the POST request is: " + responseBody);

		int rstatuscode = response.getStatusCode();

		Assert.assertEquals(rstatuscode, 200);

//		String successCode = response.jsonPath().get("SuccessCode");
//		Assert.assertEquals(successCode, "OPERATION_SUCCESS");

	}

}
