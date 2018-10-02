package set.fusion.api.test;

import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import set.fusion.common.CommonAPIUtils;
import set.fusion.common.DataProviderClass;

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
