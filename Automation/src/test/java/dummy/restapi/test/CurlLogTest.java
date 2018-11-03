package dummy.restapi.test;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;

import aut.framework.common.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CurlLogTest extends BaseAPITest {
	
	static Logger logger;

	@Test
	public static void CurlLogCheck() {
		
		logger = Logger.getLogger("CurlLogCheck");

		// Creating First Employee Record
		logger.info("INFO: Creating Employee from input JSON File\n");
		
		Options options = Options.builder().useLongForm().build();
		RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig(options);
		
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/employee/70";

		RequestSpecification request = RestAssured.given().config(config);
		
//		System.out.println("Requye : " + request.redirects().follow(false).get());
		
		Response resp = request.redirects().follow(false).get();
		
		System.out.println("Request Body " + resp.getBody().asString());
		

		
	    
	    
	    

	}

}
