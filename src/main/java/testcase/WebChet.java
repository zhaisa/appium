package testcase;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class WebChet {
	public static AndroidDriver driver;
	private Logger log = Logger.getLogger(this.getClass().getSuperclass());

	@Before
	public void setUp() throws Exception {
		// 启动appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "90dffcf2"); // 00617f9c0704
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "6.0.1");

		// 配置测试apk
		capabilities.setCapability("appPackage", "com.yiw.circledemo");
		capabilities.setCapability("appActivity", "com.yiw.circledemo.activity.MainActivity");// com.tencent.mm.ui.LauncherUI
																								// //com.tencent.mm.plugin.account.ui.LoginVoiceUI
		capabilities.setCapability("sessionOverride", true); // 每次启动时覆盖session，否则第二次后运行会报错不能新建session
		capabilities.setCapability("unicodeKeyboard", true); // 设置键盘
		capabilities.setCapability("resetKeyboard", true); // 设置默认键盘为appium的键盘 ,支持中文
		// capabilities.setCapability("appWaitActivity",
		// "com.yiw.circledemo.activity.MainActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// ChromeOptions options2 = new ChromeOptions();//这是使用微信作为浏览器必备的配置
		// options2.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
		// capabilities.setCapability(ChromeOptions.CAPABILITY, options2);
	}

	@Test
	public void login() throws InterruptedException {
		Thread.sleep(5000);
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		System.out.println(width);
		System.out.println(height);
		Dimension screen = driver.manage().window().getSize();
 //       driver.swipe(993, 1577, endx, endy, duration);

		
		List<MobileElement> list = driver.findElementsById("com.yiw.circledemo:id/snsBtn");
		// List<WebElement> list=we.findElements(By.id("com.yiw.circledemo:id/snsBtn"));
		List<WebElement> list1=new ArrayList();
		for(int i=1;i<list.size();i++) {
			System.out.println(list.get(i));
		}
		list.get(1).click();
		Thread.sleep(1000);
		driver.findElementByName("评论").click();
		Thread.sleep(1000);
		driver.findElementById("com.yiw.circledemo:id/commentBtn").click();
		driver.findElementById("com.yiw.circledemo:id/headIv").sendKeys("我数一二三，木头人");
		driver.findElementById("com.yiw.circledemo:id/sendIv").click();
		driver.findElementById("com.yiw.circledemo:id/bwn").click();
		Thread.sleep(10000);
		driver.quit();

	}

	@AfterClass
	public void tearDown() {
		try {
			this.driver.close();
			this.driver.quit();
		} catch (Exception e) {
			log.info("android driver 退出需切换会NATIVE_APP");
			AndroidDriver androidDriver = (AndroidDriver) driver;
			androidDriver.quit();
		}
	}
}
