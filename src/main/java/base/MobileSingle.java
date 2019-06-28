package base;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

/**
 * 本类是单个用例调试运行的模板，case继承于本类后，就可以跑单个用例
 * 
 * @author Administrator
 *
 */
public class MobileSingle extends TestBase {

	protected AndroidDriver<WebElement> driver;
	protected static AppiumDriverLocalService service;
	protected AssertPoint ap;

	@BeforeSuite
	public void globalSetup() throws IOException {
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		ap = new AssertPoint(this.getClass().getSimpleName(), driver);
	}

	@AfterSuite
	public void globalTearDown() {
		service.stop();
	}

	public URL getServiceUrl() {
		return service.getUrl();
	}
	
	
}
