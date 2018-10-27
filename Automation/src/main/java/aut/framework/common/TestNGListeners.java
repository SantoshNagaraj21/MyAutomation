package aut.framework.common;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListeners implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

		System.out.println("All Test PASSED for: " + result.getName());
//		System.out.println("exit code: 0");

	}

	@Override
	public void onTestFailure(ITestResult result) {

//		Throwable thrown = result.getThrowable();
//
//		StackTraceElement[] outTrace = new StackTraceElement[0];
//
//		thrown.setStackTrace(outTrace);

		System.out.println("Tests Failed for: " + result.getName());
//		System.out.println("exit code: 1");

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
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
