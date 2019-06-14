package AppiumSelenium.com.AppiumSelenium;
/*
 * 
 * 直接使用chrome定位即可
 * 
 * 
 * 
 */
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class AppH5 {
	public static AndroidDriver driver;
	 @Before  
	    public void setUp() throws Exception {  
	        //启动appium  
	        DesiredCapabilities capabilities = new DesiredCapabilities();  
	        capabilities.setCapability("deviceName","90dffcf2");  //00617f9c0704
	        capabilities.setCapability("automationName","Appium");  
	        capabilities.setCapability("platformName","Android");  
	        capabilities.setCapability("platformVersion","6.0.1");  
	          
	       //配置测试apk  
	        capabilities.setCapability("appPackage", "com.android.chrome");  
	        capabilities.setCapability("appActivity", "org.chromium.chrome.browser.ChromeTabbedActivity");  
	        capabilities.setCapability("browserName", "Chrome");
	        capabilities.setCapability("sessionOverride", true);    //每次启动时覆盖session，否则第二次后运行会报错不能新建session  
	        capabilities.setCapability("unicodeKeyboard", true);    //设置键盘  
	        capabilities.setCapability("resetKeyboard", true);     //设置默认键盘为appium的键盘  ,支持中文
	        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);  
	        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);  
//	  
}
	
	 
	 @Test 
	 public void testH5() {
		 driver.get("http://testhf.irongbei.com");
		 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElementByXPath("//*[@id=\"header\"]/div[2]/div[1]/div[1]/div[2]/a[1]/img").click();;
		 driver.navigate().to("http://testhf.irongbei.com/MUserLogin/index");
		 driver.findElementByName("name").clear();
		 driver.findElementByName("name").sendKeys("jxttm100");
		 driver.findElementByName("passwd").clear();
		 driver.findElementByName("passwd").sendKeys("123456");
		 driver.findElementById("loginBtn").click();
		 try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
	 
	    @AfterClass  
	    public static void tearDown() throws Exception {  
	        driver.quit();  
	    }  
}