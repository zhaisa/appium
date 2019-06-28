package testcase;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.MobileSingle;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AndroidForRongBei extends BaseTest{
	private AndroidDriver<WebElement> driver;
	@BeforeClass
	public void setUp() throws Exception {
		// 启动appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "4d155fc5"); // 00617f9c0704
		capabilities.setCapability("automationName", "UiAutomator2");// 这是跑通的关键点
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "8.0.0");
		// 配置测试apk
		capabilities.setCapability("appPackage", "com.rongbei");//cn.cert.financial 上报app包//com.rongbei
		capabilities.setCapability("appActivity", ".ui.activity.StartUpAct");//上报app的activ .ui.WelcomeActivity//.ui.activity.StartUpAct
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);// 这也是

	}
	@AfterClass
	public void tearDown() throws Exception {

		driver.quit();
	}


	@Test
	public void loginRongBei() throws InterruptedException {
		String activity = driver.currentActivity();
		String pkg = driver.getCurrentPackage();
		Assert.assertEquals(activity, ".ui.activity.StartUpAct");
		Assert.assertEquals(pkg, "com.rongbei");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElementById("com.rongbei:id/butSelectCancel").click();
		Thread.sleep(2000);
		TouchAction ta = new TouchAction(driver);
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		// WebElement aa = driver.findElementById("cn.cert.financial:id/iv_guid");
		for (int i = 0; i < 3; i++) {
			ta.press(PointOption.point(x * 5 / 6, y / 2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
					.moveTo(PointOption.point(x * 1 / 6, y / 2)).release().perform();
		}


		driver.findElementById("com.rongbei:id/butopenNow").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/rl1").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/rl2").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/rl3").click();
		// driver.findElementById("com.rongbei:id/iv_close").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/iv_act").click();

		Thread.sleep(2000);
		String acname = driver.currentActivity();

		System.out.println(acname);
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			System.out.println(context);
		}
		driver.navigate().back();
		String acname2 = driver.currentActivity();

		System.out.println(acname2);
		Thread.sleep(1000);
		driver.context("NATIVE_APP");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//当实在定位不到元素的是，TouchAction 模拟点击
		TouchAction ta2=new TouchAction(driver);
		ta2.tap(PointOption.point(500,360)).release().perform();
//		driver.findElementById("com.rongbei:id/butHomeLogon").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/edtUserName").clear();
		driver.findElementById("com.rongbei:id/edtUserName").sendKeys("17409040317");
		driver.findElementById("com.rongbei:id/edtUserPwd").clear();
		driver.findElementById("com.rongbei:id/edtUserPwd").sendKeys("123456");
		driver.findElementById("com.rongbei:id/butLogon").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/butSelectCancel").click();
		Thread.sleep(2000);
		TouchAction ta3=new TouchAction(driver);
//		driver.findElementById("com.rongbei:id/radio_button1").click();
		ta3.tap(PointOption.point(350,1830)).release().perform();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(3000);
		TouchAction ta4=new TouchAction(driver);
		List<WebElement> list1=driver.findElementsById("com.rongbei:id/textItemPName");
		for(int i=0;i<list1.size();i++) {
			if(list1.get(i).getText().contains("测试app专用计划")) {
				Point position=list1.get(i).getLocation();
				System.out.println(position);
				ta4.tap(PointOption.point(position)).release().perform();
				break;
			}
		}
		
		
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/butDetails").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/editInvInput").clear();
		driver.findElementById("com.rongbei:id/editInvInput").sendKeys("100");
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/ckAssignAgreement").click();
		Thread.sleep(1000);
		driver.findElementById("com.rongbei:id/butInv").click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
}
