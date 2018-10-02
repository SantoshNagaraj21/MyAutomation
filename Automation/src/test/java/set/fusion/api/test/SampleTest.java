package set.fusion.api.test;

import java.io.IOException;

import org.testng.annotations.Test;

import set.fusion.common.ReadProperties;

public class SampleTest {
  @Test
  public void f() throws IOException {
	  
		String filepath = ReadProperties.getPropertyValue("TestDataPath", "\\src\\main\\resources\\common\\Config.Properties");
		
		System.out.println("File Path is : " + filepath);
		
  }
}
