package aut.framework.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseAPITest extends CommonUtils {

	static PrintStream out = null;
	static PrintStream stdout = System.out;

	// This is the Base Test Class for the tests and test suites

	@BeforeClass
	public void InitialSetup() throws IOException {
		
		String ClassName = this.getClass().getSimpleName();
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Running Tests for: " + ClassName);
		System.out.println("---------------------------------------------------------");

		PropertyConfigurator.configure("./" + "\\src\\main\\resources\\common\\log4j.properties");
	}

	@AfterClass
	public void Closure() throws IOException {
		
		String ClassName = this.getClass().getSimpleName();
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Finished running Tests for: " + ClassName);
		System.out.println("---------------------------------------------------------");


	}

	@BeforeSuite
	public void StartSuite(ITestContext ctx) throws IOException {

		String suiteName = ctx.getCurrentXmlTest().getSuite().getName();

//		String ClassName = this.getClass().getSimpleName();

		try {
			out = new PrintStream(new FileOutputStream("./" + "\\test-results\\" + suiteName + ".xml", false), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(out);

	}

	@AfterSuite
	public void EndSuite(ITestContext ctx) throws IOException {

		System.setOut(stdout);

		String suiteName = ctx.getCurrentXmlTest().getSuite().getName();

//		String ClassName = this.getClass().getSimpleName();

//		System.out.println("Suite Name: " + suiteName);

		System.out.println(new String(Files.readAllBytes(Paths.get("./" + "\\test-results\\" + suiteName + ".xml"))));

	}

}
