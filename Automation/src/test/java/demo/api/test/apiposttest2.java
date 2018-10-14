package demo.api.test;

import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import aut.framework.common.CommonAPIUtils;
import aut.framework.common.DataProviderClass;
import io.restassured.response.Response;

public class apiposttest2 {

	@Test(dataProvider = "APITest", dataProviderClass = DataProviderClass.class)
	public void ApiPostRequest(LinkedHashMap<String, String> dataMap) {

		Response response = CommonAPIUtils.PostRequest(dataMap);

		String responseBody = response.getBody().asString();

		System.out.println("Response Body of the POST request is: " + responseBody);

		int rstatuscode = response.getStatusCode();

		Assert.assertEquals(rstatuscode, 201);

		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals(successCode, "OPERATION_SUCCESS");

	}

}
