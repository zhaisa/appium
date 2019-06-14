package AppiumSelenium.com.AppiumSelenium;

public class AppCalcTest {

public static void main(String[] args) throws Exception {
	AppCalc ac=new AppCalc();
	ac.setUp();
	ac.testCalc();
	ac.tearDown();
}
}
