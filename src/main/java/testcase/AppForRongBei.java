package testcase;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.MobileSingle;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppForRongBei extends MobileSingle{
@Test
public void loginRongBei() throws InterruptedException  {
	String activity = driver.currentActivity();
	String pkg = driver.getCurrentPackage();
	Assert.assertEquals(activity, ".ui.activity.StartUpAct");
	Assert.assertEquals(pkg, "com.rongbei");
	Thread.sleep(2000);
	driver.findElementById("com.rongbei:id/butSelectCancel").click();
	TouchAction ta = new TouchAction(driver);
	int x = driver.manage().window().getSize().width;
	int y = driver.manage().window().getSize().height;
//	WebElement aa = driver.findElementById("cn.cert.financial:id/iv_guid");
	for(int i=0;i<3;i++) {
	ta.press(PointOption.point(x*5/6, y/2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
			.moveTo(PointOption.point(x*1/6,y/2)).release().perform();
}
	
	driver.findElementById("com.rongbei:id/butopenNow").click();
	driver.findElementById("com.rongbei:id/rl1").click();
	driver.findElementById("com.rongbei:id/rl2").click();
	driver.findElementById("com.rongbei:id/rl3").click();
//	driver.findElementById("com.rongbei:id/iv_close").click();
	Thread.sleep(1000);
	TouchAction ta1 = new TouchAction(driver);
	ta1.tap(PointOption.point(950,350)).release().perform();
	Thread.sleep(1000);
	
}
}
