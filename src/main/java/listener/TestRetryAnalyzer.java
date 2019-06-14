package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 本类是自定义的失败重跑机制
 * 
 * @author Administrator
 *
 */
public class TestRetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 1;// 设一个重跑统计次数为1
	private int maxRetryTimes = 3;// 设一个最大重试次数为3

	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryTimes) {
			result.setAttribute("RETRY", retryCount);// 这行是为了把失败重跑次数写到一个全局的属性，方便别地儿调用（一般没什么用）
			System.out.println("用例：" + result.getName() + "正在进行第" + retryCount + "次失败重跑！");
			retryCount++;
			return true;// 返回true的话就进行重跑
		}
		return false;// 返回false就不再重跑了
	}

}
