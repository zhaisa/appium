package listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import base.MobileSuite;
import myutil.EmailSender;

/**
 * 自定义的报告：
 * 本类是封装的生成测试报告的工具（这个是自己写的拼的html页面形式的那种简单的报告）
 * 
 * @author Administrator
 *
 */
public class Reporter implements IReporter {

	//重写的生成报告的方法，得到了项目名、结束时间、用例通过数、失败数、跳过数、总数、通过率等
	public void generateReport(List<XmlSuite> xmlsuites, List<ISuite> suites, String arg2) {
		// 1.一开始就把我需要的数据初始化一下
		String PROJECTNAME = "";// 项目名
		String ENDTIMESTRING = "";// 结束时间
		int PASSED_NUMBER = 0;// 用例通过数
		int FAILED_NUMBER = 0;// 失败数
		int SKIPPED_NUMBER = 0;// 跳过数
		int TOTAL_NUMBER = 0;// 总数
		float PASSRATE = 0;// 成功率

		// 2.项目名
		PROJECTNAME = xmlsuites.get(0).getName();// 因为我的xml文件中就只有一个suite节点，所以写get(0)即可
		// 3.结束时间（为当前时间）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ENDTIMESTRING = "测试结束时间：" + sdf.format(new Date());

		// 4.拿各个用例数
		for (String key : suites.get(0).getResults().keySet()) {// 1.遍历第一个suite的Results（是个Map）的key值
			ITestContext testResultContext = suites.get(0).getResults().get(key).getTestContext();// 2.用上面得到的key值获取value，然后用一个ITestContext类型的变量接一下，
			PASSED_NUMBER = testResultContext.getPassedTests().size();// ITestContext类型的变量就有了getPassedTests().size()等这些方法。就搞定了各用例的数量
			FAILED_NUMBER = testResultContext.getFailedTests().size();
			SKIPPED_NUMBER = testResultContext.getSkippedTests().size();
		}
		TOTAL_NUMBER = PASSED_NUMBER + FAILED_NUMBER + SKIPPED_NUMBER;
		PASSRATE = 100 * ((float) PASSED_NUMBER / TOTAL_NUMBER);
		if (Double.isNaN(PASSRATE)) {// 如果成功率是一个空值的话，
			PASSRATE = 0;// 就给它个0，避免在测试报告中出现【成功率=null】这样的情况
		}
		System.out.println("通过用例数：" + PASSED_NUMBER);
		System.out.println("失败用例数：" + FAILED_NUMBER);
		System.out.println("跳过用例数：" + SKIPPED_NUMBER);
		System.out.println("总用例数：" + TOTAL_NUMBER);
		System.out.println("成功率：" + PASSRATE);

		// 5.用上面的测试数据拼一个html的报告出来
		StringBuffer sb = new StringBuffer();// 三者在执行速度方面的比较：StringBuilder > StringBuffer > String
		sb.append("<html lang=\"en\">");
		sb.append("<h1>" + PROJECTNAME + "</h1>");
		sb.append("<p>" + ENDTIMESTRING + "</p>");
		sb.append("<p>通过用例数：" + PASSED_NUMBER + "</p>");
		sb.append("<p>失败用例数：" + FAILED_NUMBER + "</p>");
		sb.append("<p>跳过用例数：" + SKIPPED_NUMBER + "</p>");
		sb.append("<p>总用例数：" + TOTAL_NUMBER + "</p>");
		sb.append("<p>成功率：" + PASSRATE + "%</p>");
		sb.append("<p>测试日志：</p>");
		for (String text : MobileSuite.resultLog) {
			sb.append("<p>" + text + "</p>");
		}
		sb.append("</html>");
		
		// 将上面拼出的报告写到文件中
		write("test-output/HtmlReport/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".html",
				sb.toString());
		
		// 将上面拼出的报告弄成邮件发出去
		EmailSender esend = new EmailSender();
		String content = sb.toString();
		esend.send(content, "text/html;charset=UTF-8");
	}

	private void write(String filePath, String data) {
		File file = new File(filePath);// 1.建立联系
		if (!file.exists()) {
			try {
				file.createNewFile();// 如果没有此文件就新建一个
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));// 2.选择流
			bw.write(data);// 3.操作
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.flush();// 4.刷流、关闭流
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
