package dummy.restapi.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CurlTest {


//	private final static Logger logger = LoggerFactory.getLogger(CurlTest.class);

	@Test
	public static void sampleTest() {
		
		System.setProperty("logback.configurationFile", "./" + "\\src\\main\\resources\\common\\logback.xml");
		
		final Logger log = LoggerFactory.getLogger(CurlTest.class);

		System.setProperty("log.name", "curl");

		log.info("------ Starting Ant------");

		Options options = Options.builder()
				.updateCurl(curl -> curl.removeHeader("Host").removeHeader("User-Agent").removeHeader("Connection").setCompressed(false).setInsecure(false).setVerbose(false))
				.useShortForm().build();
		RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig(options);

//		RestAssured.given().config(config).redirects().follow(false).when().get("http://google.com").then().statusCode(301);

//		RestAssured.given().config(config).redirects().follow(false).when().get("http://google.com").then().statusCode(301);

		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/employee/70";

		RequestSpecification request = RestAssured.given().config(config).redirects().follow(false);

		Response response = request.get();
		
		System.out.println("Response Body: " + response.getBody().asString());
	}

}
