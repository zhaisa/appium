package testcase;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class RongBeiLogin extends BaseTest {
@Test
public void Login(AndroidDriver<WebElement> driver,String username) throws InterruptedException  {
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
//	driver.findElementById("com.rongbei:id/butHomeLogon").click();
	Thread.sleep(1000);
	driver.findElementById("com.rongbei:id/edtUserName").clear();
	driver.findElementById("com.rongbei:id/edtUserName").sendKeys(username);
	driver.findElementById("com.rongbei:id/edtUserPwd").clear();
	driver.findElementById("com.rongbei:id/edtUserPwd").sendKeys("123456");
	driver.findElementById("com.rongbei:id/butLogon").click();
	Thread.sleep(1000);
	driver.findElementById("com.rongbei:id/butSelectCancel").click();
	Thread.sleep(2000);
}
}
