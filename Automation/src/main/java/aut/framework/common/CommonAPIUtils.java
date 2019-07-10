package aut.framework.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.github.dzieciou.testing.curl.Platform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIUtils {

	public static final String filepath = "/src/test/resources/test-data";

	static Date date = null;

	static long testCaseNo = 0;

	static SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

	public static Response runRequest(Object... params) {

//		System.setProperty("logback.configurationFile", "./" + "/src/main/resources/common/logback.xml");

		System.setProperty("log.name", "curl");

		Logger log = LoggerFactory.getLogger(CommonAPIUtils.class);

		Options options = Options.builder().targetPlatform(Platform.UNIX)
				.updateCurl(curl -> curl.removeHeader("Host").removeHeader("User-Agent").removeHeader("Connection")
						.setCompressed(false).setInsecure(false).setVerbose(false))
				.useLongForm().printMultiliner().build();
		RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig(options);

		testCaseNo = testCaseNo + 1;

		String RequestBody = null;

		String RequestType = params[0].toString();
		String RequestURL = params[1].toString();

		if (params[2] != null) {
			RequestBody = params[2].toString();
		}
		Object expRespCode = params[3];

		RestAssured.baseURI = RequestURL;
		RequestSpecification request = RestAssured.given().config(config).redirects().follow(false);
//										.log().headers();
//										.log().all(true);
//										.log().method()
//										.log().uri()
//										.log().headers()
//										.log().parameters();
		Response response = null;

		// Objects from 5th are considered headers or contentTypes for request

		int length = params.length;

		if (length >= 5) {

			for (int i = 4; i < length; i++) {
				String reqspec = (String) params[i];
				String[] parts = reqspec.split(":");
				String type = parts[0];
				String attributename = parts[1];
				String attributevalue = parts[2];

				if (type.equals("HEADER")) {

					request.given().headers(attributename, attributevalue);

				} else if ((type.equals("CONTENT"))) {

					request.given().headers(attributename, attributevalue);

				} else if ((type.equals("AUTH"))) {

					request.given().auth().preemptive().basic(attributename, attributevalue);

				} else if ((type.equals("URLENCODE"))) {

					boolean value;
					if (attributename == "true")
						value = true;
					else
						value = false;

					request.given().urlEncodingEnabled(value);

				}

			}

		}

		date = new Date();

		System.out.println("\n" + formatter.format(date) + "\n");

		System.out.println("-------------------------------------------------------------------");
		System.out.println(testCaseNo + ". " + RequestType + " " + RequestURL);
		System.out.println("================================================================");

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

		System.out.println("\nExpected Status Code : " + expRespCode + "\n");

		if (RequestBody != null) {

			String prettyRequestBody = toPrettyFormat(RequestBody);

			System.out.println("RequestBody: \n" + prettyRequestBody + "\n");

		} else {

			System.out.println("RequestBody: \n" + RequestBody + "\n");

		}

//		System.out.println("Expected Status Code : " + expRespCode + "\n");

		String responseBody = response.getBody().asString();
		int rstatuscode = response.getStatusCode();

		System.out.println("Actual Status Code : " + rstatuscode + "\n");

		if (responseBody != null) {

			String prettyResponseBody = toPrettyFormat(responseBody);

			System.out.println("ResponseBody: \n" + prettyResponseBody + "\n");

		} else {

			System.out.println("ResponseBody: \n" + responseBody + "\n");

		}

		return response;

	}

	public static boolean checkStatus(String requestType, String URL, int expStatusCode, int actStatusCode) {

		if (expStatusCode == actStatusCode) {

//			System.out.println(requestType + " " + URL + " ");
			System.out.println("INFO: Expected and Actual Status Code Matches\n");
			System.out.println("exit code: 0\n");
			System.out.println("-------------------------------------------------------------------");
			return true;

		} else {

//			System.out.println(requestType + " " + URL + " ");
			System.out.println("INFO: Expected and Actual Status Code Doesn't Match\n");
			System.out.println("exit code: 1\n");
			System.out.println("-------------------------------------------------------------------");
			return false;

		}

	}

	public static boolean checkValues(String requestType, String URL, String expattribute, String actualattribute) {

		testCaseNo = testCaseNo + 1;

		date = new Date();

		System.out.println(formatter.format(date) + "\n");

		if (expattribute.equals(actualattribute)) {

			System.out.println("-------------------------------------------------------------------");
			System.out.println(testCaseNo + ". " + requestType + " " + URL);
			System.out.println("================================================================");
			System.out.println("\nExpected Value : " + expattribute);
			System.out.println("Actual Value   : " + actualattribute + "\n");
			System.out.println("INFO: Expected and Actual Value Matches\n");
			System.out.println("exit code: 0\n");
			System.out.println("-------------------------------------------------------------------");
			return true;

		} else {

			System.out.println("-------------------------------------------------------------------");
			System.out.println(testCaseNo + ". " + requestType + " " + URL);
			System.out.println("================================================================");
			System.out.println("\nExpected Value : " + expattribute);
			System.out.println("Actual Value   : " + actualattribute + "\n");
			System.out.println("INFO: Expected and Actual Value Doesn't Match\n");
			System.out.println("exit code: 1\n");
			System.out.println("-------------------------------------------------------------------");
			return false;

		}

	}

	public static boolean checkValuesNotEqual(String requestType, String URL, String expattribute,
			String actualattribute) {

		testCaseNo = testCaseNo + 1;

		date = new Date();

		System.out.println(formatter.format(date) + "\n");

		if (expattribute.equals(actualattribute)) {

			System.out.println(testCaseNo + ". " + requestType + " " + URL + " ");
			System.out.println("================================================================");
			System.out.println("\nExpected Value : " + expattribute);
			System.out.println("Actual Value   : " + actualattribute + "\n");
			System.out.println("INFO: Expected and Actual Value Matches\n");
			System.out.println("exit code: 1\n");
			System.out.println("-------------------------------------------------------------------");
			return false;

		} else {

			System.out.println(testCaseNo + ". " + requestType + " " + URL + " ");
			System.out.println("================================================================");
			System.out.println("\nExpected Value : " + expattribute);
			System.out.println("Actual Value   : " + actualattribute + "\n");
			System.out.println("INFO: Expected and Actual Value Doesn't Match\n");
			System.out.println("exit code: 0\n");
			System.out.println("-------------------------------------------------------------------");
			return true;

		}

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

	public static String createRequestBody(Object... requestparams) {

		JSONObject requestBody = new JSONObject();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			String attribute = (String) requestparams[i];
			String[] parts = attribute.split("=");
			String attributename = parts[0];
			String attributevalue = parts[1];

			requestBody.put(attributename, attributevalue);

		}

		String reqBody = requestBody.toString();

		return reqBody;

	}

	public static String createArrayRequestBody(Object... requestparams) {

		JSONArray jsonArray = new JSONArray();

		JSONObject requestBody = new JSONObject();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			String attribute = (String) requestparams[i];
			String[] parts = attribute.split("=");
			String attributename = parts[0];
			String attributevalue = parts[1];

			requestBody.put(attributename, attributevalue);

		}

		jsonArray.put(requestBody);

		String jsonArrayString = jsonArray.toString();

		return jsonArrayString;
	}

	public static String createJsonObject(Object... requestparams) {

		JSONObject requestBody = new JSONObject();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			String attribute = (String) requestparams[i];
			String[] parts = attribute.split("=");
			String attributename = parts[0];
			String attributevalue = parts[1];

			if (attributename.contains("Child:")) {

				String[] cparts = attributename.split(":");
				String cattributename = cparts[1];

				JSONObject jsonObj = new JSONObject(attributevalue);

				requestBody.put(cattributename, jsonObj);

			}

			else {

				requestBody.put(attributename, attributevalue);
			}

		}

		String requestBodyString = requestBody.toString();

		return requestBodyString;

	}

	public static String createJsonArrayRequestBody(String... requestparams) {

		JSONArray jsonArray = new JSONArray();

		int length = requestparams.length;

		for (int i = 0; i < length; i++) {

			JSONObject jsonObj = new JSONObject(requestparams[i]);

			jsonArray.put(jsonObj);

		}

		String jsonArrayString = jsonArray.toString();

		return jsonArrayString;

	}

	public static String getFile(String filepath, String filename)
			throws FileNotFoundException, IOException, ParseException {

		File file = new File(System.getProperty("user.dir") + filepath + "/" + filename);

		JSONParser parser = new JSONParser();
		String jsonString = parser.parse(new FileReader(file)).toString();

		return jsonString;

	}

	public static String getValueFromJSON(String jsonString, String attribute) {

		String temp = JsonPath.read(jsonString, "$" + attribute);

		return temp;

	}

	public static String patchJson(String patchString, String SourceString) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchObj = mapper.readTree(patchString);
		JsonNode sourceObj = mapper.readTree(SourceString);

		JsonNode patchSource = JsonPatch.apply(patchObj, sourceObj);

		String sourceString = patchSource.toString();

		return sourceString;

	}

}
