package base;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import io.appium.java_client.android.AndroidDriver;
import myutil.Screenshot;

/**
 * 自己写的断言，让它遇到失败时（即实际结果与预期结果不符时）就将错误捕获而不让它上抛给JVM，这样测试就不会停了，
 * 而是继续执行下面的数据
 * 
 * @author Administrator
 *
 */
public class AssertPoint extends Assertion {
	private int flag = 0;// 定义一个失败的计数器，如果flag>0就证明测试运行结果是失败的
	private String caseName = "";// 定义一个用例名称的变量
	public static String screenName = "";// 这个是失败截图生成后的截图名称，往Extent报告页面中嵌的时候会用，方法是在ExecutionListener类中调用
	private AndroidDriver<WebElement> driver = null;
	
	public AssertPoint(String caseName, AndroidDriver<WebElement> driver) {
		this.caseName = caseName;
		this.driver = driver;
	}
	
	// 10.这是一个最终的断言，放在脚本末尾，用以判断所有数据执行完时整个测试结果是否通过
	public void result(String msg) {
		Assert.assertEquals(flag, 0);// 如果flag>0就表示测试过程中至少出现了1个错误，此行代码就会执行退出，就不会执行下面那行的打印了
		MobileSuite.resultLog.add(caseName + "：" + msg);
		Reporter.log(caseName + "：" + msg);// 这个会显示在报告里：用例名+信息
		System.out.println(caseName + "：" + msg);
	}
	
	// 11.初始化flag
	public void initFlag() {
		flag = 0;
	}

	// 1.断言失败时的操作
	@Override
	public void onAssertFailure(IAssert assertCommand) {
		System.out.println("[Log]：断言失败：实际结果是" + assertCommand.getActual() + "，预期结果是" + assertCommand.getExpected());
		MobileSuite.resultLog.add(caseName + "：" + assertCommand.getMessage());// 将caseName和断言信息放进一个list中，方便后面的报告和邮件等使用
		Reporter.log(caseName + "：" + assertCommand.getMessage());// 将caseName和断言信息放进testng.Reporter.log中
		screenName = Screenshot.takeScreenshot(driver);
		Log.error(caseName + "：" + assertCommand.getMessage());// 这个是写到log4j文件中的日志信息
		System.out.println(caseName + "：" + assertCommand.getMessage());// 这是打印到控制台的
		flag = flag + 1;// flag是用来统计失败用例数的，失败一个就加1，最后就知道失败了多少个
	}

	// 2.断言成功时的操作
	@Override
	public void onAssertSuccess(IAssert assertCommand) {
		initFlag();
		System.out.println("[Log]：断言成功：实际结果是" + assertCommand.getActual() + "，预期结果是" + assertCommand.getExpected());
	}

	// 1.判断两个字符串是否包含
	public void contains(String actual, String expected, String msg) {
		if(!actual.contains(expected)) {// 如果实际结果不包含预期结果，
			equals(true, false, msg);// 就调用下面的方法2，然后走上面的onAssertFailure流程
		}
		System.out.println("实际结果与预期结果一致！");
	}
	// 判断两个字符串是否一样
	public void equals(String actual, String expected, String msg) {
		if(!actual.equals(expected)) {
			equals(true, false, msg);
		}
		System.out.println("实际结果与预期结果一致！");
	}
	
	// 2.判断两个布尔型一致
	public void equals(boolean actual, boolean expected, String msg) {
		try {
			assertEquals(actual, expected, msg);
		} catch (Error e) {// 这儿改成了捕获Error，遇到失败或错误时，用例会继续往下执行，而不是抛出异常后停止运行
		}
	}

	// 3.判断两个整形一致
	public void equals(int actual, int expected) {
		try {
			assertEquals(actual, expected);
		} catch (Error e) {
		}
	}

	// 4.判断两个字符串一致
	public void equals(String actual, String expected) {
		try {
			assertEquals(actual, expected);
		} catch (Error e) {
		}
	}
	
	// 5.两个入参，一个list，一个字符串。循环遍历进行比较
	public void equals(List<String> actuals, String expected) {
		for (String actual : actuals) {
			this.equals(actual, expected);// 调用的是上面那个方法2
		}
	}
	
	// 8.三个入参，一个list，一个字符串，一个msg。循环遍历进行比较（一致是成功的）
	public void equals(List<String> actuals, String expected, String msg) {
		if(actuals.size() != 0) {
			for (String actual : actuals) {
				try {
					assertEquals(actual, expected, msg);// 一致就走上面的onAssertSuccess流程，不一致就走onAssertFailure流程
				} catch (Error e) {
				}
			}
		} else {
			System.out.println("检查点函数：实际结果集合为空!");
		}
	}
	
	// 6.判断非空
	public void equalsNotNull(Object actual, String msg) {
		try {
			assertNotNull(actual, msg);
		} catch (Error e) {
		}
	}
	
	// 7.判断两个字符串不一致，不一致才是正确的
	public void notEquals(String actual, String expected, String msg) {
		try {
			assertNotEquals(actual, expected, msg);
		} catch (Error e) {
		}
	}
	
	// 9.三个入参，一个list，一个字符串，一个msg。循环遍历进行比较（不一致才是正确的）
	public void notEquals(List<String> actuals, String expected, String msg) {
		if(actuals.size() != 0) {
			for (String actual : actuals) {
				try {
					assertNotEquals(actual, expected, msg);
				} catch (Error e) {
				}
			}
		} else {
			System.out.println("检查点函数：实际结果集合为空!");
		}
	}
	
	// 12.手动强制给置一个失败
	public void isFailed(String msg) {
		assertEquals(true, false, msg);
		MobileSuite.resultLog.add(caseName + "：" + msg);
		Reporter.log(caseName + "：" + msg);// 这个会显示在报告里：用例名+信息
	}
}
