package AppiumSelenium.com.AppiumSelenium;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * 
 *
 */
public class AppForYjzx extends BaseTest {
	private AndroidDriver<WebElement> driver;

	@BeforeClass
	public void setUp() throws Exception {
		// 启动appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "4d155fc5"); // 00617f9c0704
		capabilities.setCapability("automationName", "UiAutomator2");//这是跑通的关键点
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0.0");
		// 配置测试apk
		capabilities.setCapability("appPackage", "cn.cert.financial");
		capabilities.setCapability("appActivity", ".ui.WelcomeActivity");
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);//这也是

	}

	@Test
	public void loginWithMicroBlog() throws InterruptedException {
		String activity = driver.currentActivity();
		String pkg = driver.getCurrentPackage();
		Assert.assertEquals(activity, ".ui.WelcomeActivity");
		Assert.assertEquals(pkg, "cn.cert.financial");
		Thread.sleep(2000);
		// 最新版本滑动屏幕
		TouchAction ta = new TouchAction(driver);
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
//		WebElement aa = driver.findElementById("cn.cert.financial:id/iv_guid");
		ta.press(PointOption.point(x*5/6, y/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
				.moveTo(PointOption.point(x*1/6,y/2)).release().perform();
		Thread.sleep(2000);
		driver.findElementById("cn.cert.financial:id/tv_enter").click();
		Thread.sleep(2000);
		driver.findElementById("cn.cert.financial:id/tv_agreee").click();
		Thread.sleep(1000);
		driver.findElementById("cn.cert.financial:id/iv_cancel").click();
		Thread.sleep(1000);
//		driver.findElementByLinkText("我的").click();
		driver.findElementById("cn.cert.financial:id/tv_tab7").click();
		Thread.sleep(1000);
	//	driver.findElementByLinkText("登录/注册").click();
		driver.findElementById("cn.cert.financial:id/phone_tv").click();		
		Thread.sleep(1000);
		driver.findElementById("cn.cert.financial:id/et_phone").clear();
		driver.findElementById("cn.cert.financial:id/et_phone").sendKeys("15321612750");
		driver.findElementById("cn.cert.financial:id/et_psw").clear();
		driver.findElementById("cn.cert.financial:id/et_psw").sendKeys("qweasd123");  
		driver.findElementById("cn.cert.financial:id/tv_login").click();
		Thread.sleep(2000);
		//这是个bug，可能会登录失败，然后从新登录一遍
		driver.findElementById("cn.cert.financial:id/phone_tv").click();		
		Thread.sleep(1000);
		driver.findElementById("cn.cert.financial:id/et_phone").clear();;
		driver.findElementById("cn.cert.financial:id/et_phone").sendKeys("15321612750");
		driver.findElementById("cn.cert.financial:id/et_psw").clear();
		driver.findElementById("cn.cert.financial:id/et_psw").sendKeys("qweasd123");  
		driver.findElementById("cn.cert.financial:id/tv_login").click();
		Thread.sleep(2000);
		driver.findElementById("cn.cert.financial:id/tv_tab1").click();
		
		Thread.sleep(1000);
		TouchAction ta1 = new TouchAction(driver);
		ta1.tap(PointOption.point(900,760)).release().perform();

		Thread.sleep(2000);
		driver.findElementById("cn.cert.financial:id/tv_checkXQ").click();

		//".ui.main.MainActivity
		Thread.sleep(1000);
		driver.findElementById("cn.cert.financial:id/iv_back").click();
		// driver.navigate().back();
		Thread.sleep(1000);
		for (int i = 0; i < 1000; i++) {
			driver.findElementById("cn.cert.financial:id/tv_checkXQ").click();
			Thread.sleep(1500);
			driver.findElementById("cn.cert.financial:id/iv_back").click();
			Thread.sleep(1500);
		}
	}

	@AfterClass
	public void tearDown() throws Exception {

	driver.quit();
	}
}
