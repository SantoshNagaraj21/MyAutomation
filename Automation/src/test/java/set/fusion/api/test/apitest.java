package set.fusion.api.test;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import set.fusion.common.BaseAPITest;
import set.fusion.common.DataProviderClass;

public class apitest extends BaseAPITest {

	@Test(dataProvider="APIXMLTest",dataProviderClass=DataProviderClass.class)
	public void APiTest(LinkedHashMap<String, String> dataMap) throws IOException, ParserConfigurationException, SAXException {

		Logger logger = Logger.getLogger("APITest");

		logger.info("---------------------------------------------------");
		logger.info("Running test for Scenario api for XML");
		logger.info("---------------------------------------------------");

		String FirstName=dataMap.get("firstname");
		String ID=dataMap.get("id");
		String lastname=dataMap.get("lastname");
		String nickname=dataMap.get("nickname");
		String marks=dataMap.get("marks");
		
		System.out.println("Input Values " + FirstName + ID + marks +  nickname +   lastname);

	}
}
