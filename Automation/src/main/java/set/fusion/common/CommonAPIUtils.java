package set.fusion.common;

import java.util.LinkedHashMap;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIUtils {

	public static Response PostRequest(LinkedHashMap<String, String> inputMap) {

		RestAssured.baseURI = "http://restapi.demoqa.com/customer";

		RequestSpecification request = RestAssured.given();

		// Get the data from excel

		String FirstName = inputMap.get("FirstName");
		String LastName = inputMap.get("LastName");
		String UserName = inputMap.get("UserName");
		String Password = inputMap.get("Password");
		String Email = inputMap.get("Email");

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

		return response;

	}

}
