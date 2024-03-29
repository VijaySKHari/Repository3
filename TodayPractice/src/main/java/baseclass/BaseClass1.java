package baseclass;

import java.io.File;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass1 {
	public WebDriver driver;

	@BeforeMethod
	public void initialiseDriver() {
		driver = new EdgeDriver();

		driver.get("https://www.google.co.in/");
		driver.findElement(By.xpath("//textarea[@id='APjFqb']")).sendKeys("rahulshettyacademy client", Keys.ENTER);
		driver.findElement(By.xpath("//a[@jsname='UWckNb'][1]//h3[text()=\"Let's Shop\"]")).click();
	}

	@AfterMethod
	public void afterMethod() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("@AfterMetod:::::");
		driver.close();
	}

	public static Object[][] readExcel(String excelPath) {

		FileInputStream fis = null;
		Workbook wb = null;
		try {
			fis = new FileInputStream(excelPath);

			if (fis != null) {
				System.out.println("FileInputStream is executed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			wb = WorkbookFactory.create(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// getLastCellNum() = count starts from one
		// getLastRowNum() = count starts from zero = physicalnumberofrows-1
		// getPhysicalNumberOfCells() == count starts from 1

		Sheet sheet = wb.getSheetAt(0);

		int noOfRows = sheet.getPhysicalNumberOfRows();
		int noOfColumnns = sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println("physical columns " + noOfColumnns);
		System.out.println("physical rows " + noOfRows);

		Object[][] a = new Object[noOfRows - 1][noOfColumnns];
		DataFormatter formatter = new DataFormatter();
		for (int rowIndex = 1; rowIndex < noOfRows; rowIndex++) {
			for (int columnIndex = 0; columnIndex < noOfColumnns; columnIndex++) {

				a[rowIndex - 1][columnIndex] = formatter.formatCellValue(sheet.getRow((rowIndex)).getCell(columnIndex));

			}
		}
		return a;

	}

	public String screenShootTake(String testMethodName, WebDriver driver) {

		TakesScreenshot screenShotDriver = (TakesScreenshot) driver;
		File src = screenShotDriver.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		Date date = new Date();
		String formatedDate = sdf.format(date);
		String destinationPath = System.getProperty("user.dir")+"//screenshotsfolder/"
				+ testMethodName + formatedDate + ".png";
		File dest = new File(destinationPath);
		try {
			FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("i am taking screen shot");
		return destinationPath;

	}

}
