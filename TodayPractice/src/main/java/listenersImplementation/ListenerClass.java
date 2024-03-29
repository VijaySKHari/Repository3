package listenersImplementation;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseclass.BaseClass1;

public class ListenerClass extends BaseClass1 implements ITestListener {
	ExtentReports extentReports = ExtentReportsClass.createExtentReports();
	ExtentTest test;
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {

		System.out.println("OnTestStart() method");
		test = extentReports.createTest(result.getMethod().getMethodName());
		threadLocal.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("OnTestSuccess() method");
		threadLocal.get().log(Status.PASS, result.getMethod().getMethodName() + " passed successfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		System.out.println("OnTestFailure()");
		threadLocal.get().log(Status.FAIL, result.getMethod().getMethodName() + "Failed");
		threadLocal.get().log(Status.FAIL, result.getThrowable());

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String srcForExtentRportScreenShot = screenShootTake(result.getMethod().getMethodName(), driver);
		threadLocal.get().addScreenCaptureFromPath(srcForExtentRportScreenShot, result.getMethod().getMethodName());
		System.out.println("addscreencaptured executed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("onTestFinish::");
		extentReports.flush();
	}
}
