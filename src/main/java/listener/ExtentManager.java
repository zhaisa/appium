package listener;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * 本类是使用第三方工具包生成测试报告的类
 * 
 * @author Administrator
 *
 */
public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String filePath) {
		if (extent == null) {
			// 下面是filePath和true两个参数的解释：
			// 1.这个filePath（生成报告的路径），配在了TestBase类里面，它里面实例化了本类并调用getReporter方法，生成报告
			// 2.replaceExisting Setting to overwrite (TRUE) the existing file
			// or append (FALSE) to it
			// （替换现有设置以覆盖（true）现有文件或向其追加（false））
			extent = new ExtentReports(filePath, true);// true是覆盖，false是追加
			extent.addSystemInfo("Environment", "测试节点");
		}
		return extent;
	}
}
