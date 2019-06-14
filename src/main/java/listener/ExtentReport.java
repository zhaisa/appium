package listener;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 本类是，演示，在ExtentReport报告页面中添加图片，的demo
 * 但此类中的方法可被别的类调用，用作把图片添加到实际项目的报告中
 * 
 * @author Administrator
 *
 */
public class ExtentReport {
	static ExtentReports extent;
	static String reportLocation = "report/ExtentReport.html";// 这是生成报告的输出路径

	public static void main(String[] args) {
		extent = new ExtentReports(reportLocation, true);// true是覆盖，false是追加
		extent.addSystemInfo("Host Name", "tiantianliuzehohoho");// 这个是在页面上加了一个“HostName”信息，“tiantianliuzehohoho”是值

		ExtentTest test1 = extent.startTest("Case01", "Sample description");// 用例名
		String image1 = test1.addScreenCapture("../test-output/snapshot/20180227181246995.jpg");// 定义图片的路径（指运行用例过程中根据需要自动保存的截图，一般用于用例失败时的截图），可能图片需要写成变量（后期根据实际项目要作改进）
		test1.log(LogStatus.INFO, "Running the Case01", image1);// 在页面中添加用例运行信息及图片
		extent.endTest(test1);

		ExtentTest test2 = extent.startTest("Case02", "Sample description");
		String image2 = test2.addScreenCapture("../test-output/snapshot/20180130015621085.jpg");
		test2.log(LogStatus.INFO, "Running the Case02", image2);
		extent.endTest(test2);

		extent.flush();
		extent.close();
	}

}
