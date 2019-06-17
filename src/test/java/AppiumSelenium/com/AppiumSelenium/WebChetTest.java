package AppiumSelenium.com.AppiumSelenium;

import testcase.WebChet;

public class WebChetTest {
public static void main(String[] args) throws Exception {
	WebChet wc=new WebChet();
	wc.setUp();
	wc.login();
	wc.tearDown();
}
}
