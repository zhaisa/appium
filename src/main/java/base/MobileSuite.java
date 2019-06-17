package base;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

/**
 * 本类是case的父类，实现定制测试执行流程的功能
 * 本类是批量正式测试的模板，指定了Test开始之前、Class开始之前和Test结束之后的一些操作
 * @author Administrator
 *
 */
public class MobileSuite extends TestBase {
	private static AppiumDriverLocalService service;
	private AndroidDriver<WebElement> driver;
	public static List<String> resultLog = new ArrayList<String>();// 各case中调用此方法，会将测试结果信息存入这个list中，方便测试报告、测试邮件等使用
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
