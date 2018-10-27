package aut.framework.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIUtils {

	public static SoftAssert softAssertion = new SoftAssert();

	public static final String filepath = "\\src\\test\\resources\\test-data";

	public static Response runRequest(Object... params) {

		String RequestBody = null;

		String RequestType = params[0].toString();
		String RequestURL = params[1].toString();

		if (params[2] != null) {
			RequestBody = params[2].toString();
		}
		Object expRespCode = params[3];

		RestAssured.baseURI = RequestURL;
		RequestSpecification request = RestAssured.given();
		Response response = null;

		// Objects from 5th are considered headers for request

		int length = params.length;

		if (length >= 5) {

			for (int i = 4; i < length; i++) {
				String header = (String) params[i];
				String[] parts = header.split(":");
				String headername = parts[0];
				String headervalue = parts[1];
				request.given().headers(headername, headervalue);

			}

		}

		System.out.println(RequestType + " " + RequestURL);

		System.out.println("Expected Status Code : " + expRespCode);

		if (RequestBody != null) {

			String prettyRequestBody = toPrettyFormat(RequestBody);

			System.out.println("RequestBody: " + prettyRequestBody);

		} else {

			System.out.println("RequestBody: " + RequestBody);

		}

		if (RequestType == "GET") {

			response = request.get();

		} else if (RequestType == "POST") {

			request.body(RequestBody);
			request.given().headers("Content-Type", "application/json");
			response = request.post();

		} else if (RequestType == "PUT") {

			request.body(RequestBody);
			request.given().headers("Content-Type", "application/json");
			response = request.put();

		} else if (RequestType == "PATCH") {

			request.body(RequestBody);
			request.given().headers("Content-Type", "application/json");
			response = request.patch();
		}

		else {

			System.out.println("Invalid Request Type");
			Assert.assertEquals(RequestType, "Inv");

		}

		String responseBody = response.getBody().asString();
		int rstatuscode = response.getStatusCode();

		System.out.println("Actual Status Code : " + rstatuscode);

		if (responseBody != null) {

			String prettyResponseBody = toPrettyFormat(responseBody);

			System.out.println("ResponseBody: " + prettyResponseBody);

		} else {

			System.out.println("ResponseBody: " + responseBody);

		}

		return response;

	}

	public static String createRequestBody(Object... requestparams) {

		JSONObject requestBody = new JSONObject();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			String attribute = (String) requestparams[i];
			String[] parts = attribute.split(":");
			String attributename = parts[0];
			String attributevalue = parts[1];

			requestBody.put(attributename, attributevalue);

		}

		String reqBody = requestBody.toString();

		return reqBody;

	}

	public static String toPrettyFormat(String jsonString) {

		Object obj = new JSONTokener(jsonString).nextValue();
		String prettyJson = null;

		if (obj instanceof JSONObject) {

			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(jsonString).getAsJsonObject();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			prettyJson = gson.toJson(json);
			return prettyJson;

		} else if (obj instanceof JSONArray) {

			JsonParser parser = new JsonParser();
			JsonArray json = parser.parse(jsonString).getAsJsonArray();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			prettyJson = gson.toJson(json);

			return prettyJson;

		} else {

			return jsonString;

		}

	}

	public static JSONArray createArrayRequestBody(Object... requestparams) {

		JSONArray jsonArray = new JSONArray();

		JSONObject requestBody = new JSONObject();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			String attribute = (String) requestparams[i];
			String[] parts = attribute.split(":");
			String attributename = parts[0];
			String attributevalue = parts[1];

			requestBody.put(attributename, attributevalue);

		}

		jsonArray.put(requestBody);

		return jsonArray;

	}

	public static String getValueFromJSON(String jsonString, String attribute) {

		String attributeValue = null;

		Object obj = new JSONTokener(jsonString).nextValue();

		if (obj instanceof JSONObject) {

			JSONObject object = new JSONObject(jsonString);

			System.out.println("JSON Object" + object);

			attributeValue = object.getString(attribute);

			return attributeValue;

		}

		return attributeValue;

	}

	public static String getFile(String filepath, String filename)
			throws FileNotFoundException, IOException, ParseException {

		File file = new File("./" + filepath + "\\" + filename);

		JSONParser parser = new JSONParser();
		String jsonString = parser.parse(new FileReader(file)).toString();

		return jsonString;

	}

	public static JSONArray createJsonArrayRequestBody(JSONObject... requestparams) {

		JSONArray jsonArray = new JSONArray();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			jsonArray.put(requestparams[i]);

		}

		return jsonArray;

	}

	public static JSONObject createJsonObject(Object... requestparams) {

		JSONObject requestBody = new JSONObject();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			String attribute = (String) requestparams[i];
			String[] parts = attribute.split(":");
			String attributename = parts[0];
			String attributevalue = parts[1];

			requestBody.put(attributename, attributevalue);

		}

		return requestBody;

	}

	public static boolean checkStatus(String requestType, String URL, int expStatusCode, int actStatusCode) {

		if (expStatusCode == actStatusCode) {

//			System.out.println(requestType + " " + URL + " ");
			System.out.println("Expected and Actual Status Code Matches");
			System.out.println("exit code: 0");
			return true;

		} else {

//			System.out.println(requestType + " " + URL + " ");
			System.out.println("Expected and Actual Status Code Doesn't Match");
			System.out.println("exit code: 1");
			return false;

		}

	}
	
	public static boolean checkValues(String requestType, String URL, String expattribute, String actualattribute) {

		if (expattribute.equals(actualattribute)) {

			System.out.println(requestType + " " + URL + " ");
			System.out.println("Expected Value : " + expattribute );
			System.out.println("Actual Value   : " + actualattribute);
			System.out.println("Expected and Actual Value Matches");
			System.out.println("exit code: 0");
			return true;

		} else {

			System.out.println(requestType + " " + URL + " ");
			System.out.println("Expected Value : " + expattribute );
			System.out.println("Actual Value   : " + actualattribute);
			System.out.println("Expected and Actual Value Doesn't Match");
			System.out.println("exit code: 1");
			
			return false;

		}

	}
	
	public static boolean checkValuesNotEqual(String requestType, String URL, String expattribute, String actualattribute) {

		if (expattribute.equals(actualattribute)) {

			System.out.println(requestType + " " + URL + " ");
			System.out.println("Expected Value : " + expattribute );
			System.out.println("Actual Value   : " + actualattribute);
			System.out.println("Expected and Actual Value Matches");
			System.out.println("exit code: 1");
			return false;

		} else {

			System.out.println(requestType + " " + URL + " ");
			System.out.println("Expected Value : " + expattribute );
			System.out.println("Actual Value   : " + actualattribute);
			System.out.println("Expected and Actual Value Doesn't Match");
			System.out.println("exit code: 0");
			
			return true;

		}

	}

}
