package TestClasses;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import baseclass.BaseClass1;

public class TestClass1 extends BaseClass1 {

	@Test(dataProvider = "provideData")
	public void checkTest(String email, String password) throws Exception {

		driver.findElement(By.xpath("//input[@id='userEmail']")).clear();
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='userPassword']")).clear();
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys(password);
		Assert.fail();
		driver.findElement(By.xpath("//input[@id='login']")).click();
		// Assert.fail();
		// Thread.sleep(10000);
		// driver.close();

	}

	@DataProvider
	public Object[][] provideData() {
		Object[][] data = readExcel(".//src//test//resources//mavericexcel.xlsx");
		return data;
	}

}
