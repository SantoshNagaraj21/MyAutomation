package dummy.restapi.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.diff.JsonDiff;
import com.jayway.jsonpath.JsonPath;

import aut.framework.common.BaseAPITest;
import aut.framework.common.CommonAPIUtils;
import io.restassured.response.Response;

@Listeners(aut.framework.common.TestNGListeners.class)

public class EmployeeApiTests extends BaseAPITest {

	static Logger logger;

	public static final String filepath = "\\src\\test\\resources\\test-data";

	public static final String baseUrl = "http://dummy.restapiexample.com/api/v1";

	public static SoftAssert softAssertion = new SoftAssert();

	@Test(enabled = true)
	public static void createEmployees() throws IOException, ParseException {

		logger = Logger.getLogger("createEmployees");

		String createUrl = "/create";
		String getUrl = "/employee";

		// Creating First Employee Record
		logger.info("INFO: Creating Employee from input JSON File\n");

		String RequestBody = CommonAPIUtils.getFile(filepath, "createEmployee.json");

		String expempname = CommonAPIUtils.getValueFromJSON(RequestBody, ".name");

		Response ResponseBody = CommonAPIUtils.runRequest("POST", baseUrl + createUrl, RequestBody, 200,
				"HEADER:X-Tenant-ID:dhin", "HEADER:Content-Type:application/json", "AUTH:Santosh:Test",
				"URLENCODE:Enabled:false");

		int returncode = ResponseBody.getStatusCode();
		String respBody = ResponseBody.getBody().asString();

		String rempID = JsonPath.read(respBody, "$.id");
		String rempname = JsonPath.read(respBody, "$.name");

		softAssertion.assertTrue(CommonAPIUtils.checkStatus("POST", baseUrl + createUrl, returncode, 200));
		softAssertion.assertTrue(CommonAPIUtils.checkValues("POST", baseUrl + createUrl, expempname, rempname));

		// Creating Second Employee Record
		logger.info("INFO: Creating Employee from Request Body function\n");

		String RequestBody1 = CommonAPIUtils.createRequestBody("name:Santoshtest1", "salary:123", "age:23");

		String expempname1 = JsonPath.read(RequestBody1, "$.name");

		Response ResponseBody1 = CommonAPIUtils.runRequest("POST", baseUrl + createUrl, RequestBody1, 200);

		int returncode1 = ResponseBody1.getStatusCode();
		String respBody1 = ResponseBody1.getBody().asString();

		String rempID1 = JsonPath.read(respBody1, "$.id");
		String rempname1 = JsonPath.read(respBody1, "$.name");

		softAssertion.assertTrue(CommonAPIUtils.checkStatus("POST", baseUrl + createUrl, returncode1, 200));
		softAssertion.assertTrue(CommonAPIUtils.checkValues("POST", baseUrl + createUrl, rempname1, expempname1));
		softAssertion.assertTrue(CommonAPIUtils.checkValuesNotEqual("POST", baseUrl + createUrl, rempID, rempID1));

		// Get the First Created Employee Record
		logger.info("INFO: Get the first Created Employee Record\n");

		Response ResponseBody2 = CommonAPIUtils.runRequest("GET", baseUrl + getUrl + "/" + rempID, null, 200);

		int returncode2 = ResponseBody2.getStatusCode();
		String respBody2 = ResponseBody2.getBody().asString();

		String rempID2 = JsonPath.read(respBody2, "$.id");
		String rempname2 = JsonPath.read(respBody2, "$.employee_name");

		softAssertion.assertTrue(CommonAPIUtils.checkStatus("POST", baseUrl + getUrl + "/" + rempID, returncode2, 200));
		softAssertion
				.assertTrue(CommonAPIUtils.checkValues("POST", baseUrl + getUrl + "/" + rempID, rempname2, expempname));
		softAssertion.assertTrue(CommonAPIUtils.checkValues("POST", baseUrl + getUrl + "/" + rempID, rempID, rempID2));

		softAssertion.assertAll();

	}

	@Test(enabled = true)
	public static void createArrayRequestBody() {

		JSONArray jsonArrays = CommonAPIUtils.createArrayRequestBody("name:Santoshtest", "salary:123", "age:23");

		System.out.println("JSON Array " + jsonArrays);

	}

	@Test(enabled = true)
	public static void createJsonArrayRequestBody() {

		JSONObject request = CommonAPIUtils.createJsonObject("name:Santoshtest", "salary:123", "age:23");
		JSONObject request1 = CommonAPIUtils.createJsonObject("name:Santoshtest", "salary:123", "age:23");

		JSONArray jsonobjArrays = CommonAPIUtils.createJsonArrayRequestBody(request, request1);

		System.out.println("JSON Obj Array " + jsonobjArrays);

	}

	@Test(enabled = false)
	public static void patchJsonTest() throws FileNotFoundException, IOException, ParseException {

		// Creating First Employee Record
//		logger.info("INFO: Patching a JSON File\n");

		String RequestBody = CommonAPIUtils.getFile(filepath, "createEmployee.json");

		JSONObject request = CommonAPIUtils.createJsonObject("name:Santoshtest", "salary:123", "age:23");

		JSONArray jsonArrays = CommonAPIUtils
				.createArrayRequestBody("op: replace", "path: name", "value: patchTest");

		System.out.println("Patch JSON " + jsonArrays);
		System.out.println("Source JSON  " + request + "\n " +  RequestBody);

//		final JsonPatch patch = JsonDiff.asJson(jsonArrays, request);

	}

}
