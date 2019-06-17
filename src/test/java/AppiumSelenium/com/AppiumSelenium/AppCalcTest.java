package AppiumSelenium.com.AppiumSelenium;

import testcase.AppCalc;

public class AppCalcTest {

public static void main(String[] args) throws Exception {
	AppCalc ac=new AppCalc();
	ac.setUp();
	ac.testCalc();
	ac.tearDown();
}
}
