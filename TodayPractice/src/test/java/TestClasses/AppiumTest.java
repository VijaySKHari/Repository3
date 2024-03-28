package TestClasses;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class AppiumTest {

	public static void main(String[] args) throws MalformedURLException {

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "android");
		cap.setCapability("deviceName", "redmi");
		cap.setCapability("browserName", "chrome");

		AndroidDriver driver = new AndroidDriver(new URL("http://localhost:4723"), cap);

	}

}
