package listenersImplementation;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
		TakesScreenshot driver1 = (TakesScreenshot) driver;
		File file = driver1.getScreenshotAs(OutputType.FILE);

		String screenShotFolder = "./ScreenShotFolder/";
		String screenShotFile = screenShotFolder + "screenshots.docx";

		try {

			Path Files_Folder = Path.of(screenShotFolder);
			if (!Files.exists(Files_Folder)) {
				new File(screenShotFolder).mkdirs();
			}
			XWPFDocument document;
			
			Path  screenShotPath = Path.of(screenShotFile);
			if (!Files.exists(screenShotPath)) {
				document = new XWPFDocument();
			} else {
				document = new XWPFDocument(new FileInputStream(screenShotFile));
			}

			XWPFParagraph paragraph = document.createParagraph();

			XWPFRun run = paragraph.createRun();
			run.addCarriageReturn();
			run.addCarriageReturn();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			Date date = new Date();
			String formattedDate=formatter.format(date);
			run.setText("test method is :::" + result.getMethod().getMethodName()+" "+formattedDate);
			run.addCarriageReturn();
			FileInputStream fis = new FileInputStream(file);
			int picturetype = XWPFDocument.PICTURE_TYPE_JPEG;

			String filename = file.getName();
			int width = 500;
			int height = 250;
			run.addPicture(fis, picturetype, filename, Units.toEMU(width), Units.toEMU(height));
			FileOutputStream fos = new FileOutputStream(screenShotFile);
			document.write(fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

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
