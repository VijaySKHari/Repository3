package listenersImplementation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsClass {

	public static ExtentReports createExtentReports() {

		ExtentSparkReporter reporter = new ExtentSparkReporter(
				System.getProperty("user.dir")+"//ExtentReportsFoldersss/indexes.html");

		reporter.config().setDocumentTitle("WebAutomation__Testing");
		reporter.config().setReportName("VijayHariSKReports");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("TESTER", "AMMA_APPA_VIJAY");

		return extentReports;

	}
}
