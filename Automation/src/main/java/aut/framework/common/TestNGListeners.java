package aut.framework.common;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListeners implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

		System.out.println("Tests started for : " + result.getName());
		System.out.println("---------------------------------------------------------");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

		System.out.println("All Test PASSED for: " + result.getName());
		System.out.println("---------------------------------------------------------");

	}

	@Override
	public void onTestFailure(ITestResult result) {

//		Throwable thrown = result.getThrowable();
//
//		StackTraceElement[] outTrace = new StackTraceElement[0];
//
//		thrown.setStackTrace(outTrace);

		System.out.println("Tests Failed for: " + result.getName());
		System.out.println("---------------------------------------------------------");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {

		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		String suiteName = context.getCurrentXmlTest().getSuite().getName();

		System.out.println("Test Suite Started for " + suiteName);
		System.out.println("---------------------------------------------------------");

	}

	@Override
	public void onFinish(ITestContext context) {
		
		String suiteName = context.getCurrentXmlTest().getSuite().getName();

		System.out.println("Test Suite Ended for " + suiteName);
		System.out.println("---------------------------------------------------------");

	}

}
