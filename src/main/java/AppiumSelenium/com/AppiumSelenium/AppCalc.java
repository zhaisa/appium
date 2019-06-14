package AppiumSelenium.com.AppiumSelenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class AppCalc {
	public static AndroidDriver driver;
	@Before
	public void setUp() throws Exception {
		// 启动appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "90dffcf2"); // 00617f9c0704
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "6.0.1");

		// 配置测试apk
		capabilities.setCapability("appPackage", "com.android.calculator2");
		capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");// com.tencent.mm.ui.LauncherUI
																								// //com.tencent.mm.plugin.account.ui.LoginVoiceUI
		capabilities.setCapability("sessionOverride", true); // 每次启动时覆盖session，否则第二次后运行会报错不能新建session
		capabilities.setCapability("unicodeKeyboard", true); // 设置键盘
		capabilities.setCapability("resetKeyboard", true); // 设置默认键盘为appium的键盘 ,支持中文
//		capabilities.setCapability("appWaitActivity", "com.yiw.circledemo.activity.MainActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// ChromeOptions options2 = new ChromeOptions();//这是使用微信作为浏览器必备的配置
		// options2.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
		// capabilities.setCapability(ChromeOptions.CAPABILITY, options2);
	}

	@Test
	public void testCalc() throws InterruptedException {
		
		Thread.sleep(5000);
		driver.findElementById("com.android.calculator2:id/digit1").click();
		driver.findElementById("com.android.calculator2:id/sign").click();
		driver.findElementById("com.android.calculator2:id/digit8").click();
		driver.findElementById("com.android.calculator2:id/equal").click();
		Thread.sleep(3000);
		driver.quit();
	}
	
	
	
	 @AfterClass  
	    public static void tearDown() throws Exception {  
	        driver.quit();  
	    }  
	
}