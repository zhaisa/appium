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
	@BeforeClass
	public void setUp() throws Exception {
		// 启动appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "4d155fc5"); // 00617f9c0704
		capabilities.setCapability("automationName", "UiAutomator2");// 这是跑通的关键点
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0.0");
		// 配置测试apk
		capabilities.setCapability("appPackage", "com.rongbei");//cn.cert.financial 上报app包
		capabilities.setCapability("appActivity", ".ui.activity.StartUpAct");//上报app的activ .ui.WelcomeActivity
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);// 这也是

	}

	@AfterClass
	public void tearDown() throws Exception {

		driver.quit();
	}

	
	
}
