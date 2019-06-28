package testcase;

import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class RongBeiTiXian {
	private AndroidDriver<WebElement> driver;

	@BeforeClass
	public void setUp() throws Exception {
		// 启动appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "4d155fc5"); // 00617f9c0704
		capabilities.setCapability("automationName", "UiAutomator2");// 这是跑通的关键点
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0.0");
		capabilities.setCapability("chromedriverExecutable", "C:\\Users\\Administrator\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
		// 配置测试apk
		capabilities.setCapability("appPackage", "com.rongbei");// cn.cert.financial 上报app包//com.rongbei
		capabilities.setCapability("appActivity", ".ui.activity.StartUpAct");// 上报app的activ
																				// .ui.WelcomeActivity//.ui.activity.StartUpAct
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);// 这也是

	}

	@AfterClass
	public void tearDown() throws Exception {

		driver.quit();
	}

	@Test
	public void Recharge() throws InterruptedException {
		RongBeiLogin rb = new RongBeiLogin();
		rb.Login(driver, "17409040319");
		TouchAction ta = new TouchAction(driver);
		ta.tap(PointOption.point(900, 1860)).release().perform();
		Thread.sleep(2000);
		TouchAction ta2 = new TouchAction(driver);
		ta2.tap(PointOption.point(300,700)).release().perform();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driver.findElementById("com.rongbei:id/view05but01").click();

		driver.findElementById("com.rongbei:id/quick_recharge_btn").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/editRecInput").clear();
		driver.findElementById("com.rongbei:id/editRecInput").sendKeys("1");
		driver.findElementById("com.rongbei:id/butChargeNow").click();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(2000);
		String acname = driver.currentActivity();

		System.out.println(acname);
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			System.out.println(context);
		}
//		driver.context((String) contexts.toArray()[2]);
		driver.context("WEBVIEW_com.rongbei");
		
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(3000);
		// driver.findElement(By.id("inputArea"));
		driver.findElementByClassName("msgFont").click();
	//	driver.findElementById("SMS_CODE").clear();

		Scanner sc = new Scanner(System.in);
		System.out.println("请输入验证码：");
		while (sc.hasNext()) {
			
			String aaa = sc.next();
			System.out.println(aaa);
			
			driver.findElementById("SMS_CODE").sendKeys(aaa);
			break;
		}
		driver.findElementById("pass").clear();
		driver.findElementById("pass").sendKeys("121121");
		Thread.sleep(200);
		driver.findElementById("sub").click();
		Thread.sleep(5000);
	}
}
