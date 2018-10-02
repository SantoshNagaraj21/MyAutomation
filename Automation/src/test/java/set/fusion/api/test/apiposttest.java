package set.fusion.api.test;

import java.util.LinkedHashMap;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import set.fusion.common.DataProviderClass;

public class apiposttest {
	
	@Test(dataProvider="APITest",dataProviderClass=DataProviderClass.class)
	public void ApiPostRequest(LinkedHashMap<String, String> dataMap) {

		RestAssured.baseURI = "http://restapi.demoqa.com/customer";

		RequestSpecification request = RestAssured.given();
		
		// Get the data from excel
		
		String FirstName=dataMap.get("FirstName");
		String LastName=dataMap.get("LastName");
		String UserName=dataMap.get("UserName");
		String Password=dataMap.get("Password");
		String Email=dataMap.get("Email");
		
		System.out.println("Input Values " + FirstName + LastName + UserName + Password + Email);
				
		JSONObject requestParams = new JSONObject();

		requestParams.put("FirstName", FirstName); // Cast
		requestParams.put("LastName", LastName);

		requestParams.put("UserName", UserName);	
		requestParams.put("Password", Password);
		requestParams.put("Email", Email);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
	
		Response response = request.post("/register");		

		String responseBody = response.getBody().asString();

		System.out.println("Response Body of the POST request is: " + responseBody);

		int rstatuscode = response.getStatusCode();

		Assert.assertEquals(rstatuscode, 201);

		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals(successCode, "OPERATION_SUCCESS");

	}

}
