package base;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;

import listener.ExtentManager;
import myutil.DateHandler_shijianchuli;
import myutil.XmlParser_online;

/**
 * 本类是用例运行模板的基类，实现了数据驱动工厂等
 * 指定了测试套件开始之前、结束之后的一些操作，还有得到报告、得到合并数据等方法
 * 
 * @author Administrator
 *
 */
public class TestBase {
	// 1.xml数据驱动
	private String filePath = "test-data/" + this.getClass().getSimpleName() + ".xml";// 放测试数据的xml文件（用的反射，是直接或间接继承了TestBase的用例的Name）
	private XmlParser_online xp;
	private Map<String, String> commonMap;// 建一个读common节点的Map
	private static Map<String, String> global;// 建一个读global的节点的Map（因为global只有一个，可供全局使用，所以用static）
	private static String reportPath = "report/" + DateHandler_shijianchuli.getTimeStamp() + ".html";// 为测试报告指定路径
	private static ExtentReports extentReports;// 创建ExtentReports对象
	
	// 2.Excel数据驱动
//	private static String filePathExcel = "E:\\test.xls";// Excel文件
//	private static ExcelParserPlus epp;// Excel解析类
//	private static Map<String,String> globalExcel;// Excel文件中的global数据（sheet页）

	// 静态代码块（会在类加载之前优先被执行，且只执行一次）
//	static {
//		XmlParser_online g_xp = new XmlParser_online("test-data/Global.xml");
//		global = g_xp.getChildrenInfoByElement(g_xp.getSingleElement("/*"));// 1.读到global的map（xml文件的）
//		
//		epp = new ExcelParserPlus(filePathExcel);
//		globalExcel = epp.getData("Global").get(0);// 1.读到global的map（excel文件的）
//	}
	
	/**
	 * 一、首先在testng @beforesuite中初始化一些ExtentReports，其中配置了报告的生成路径，以及其他一些信息，这里为了在监听中调用extent，在这里写了一个get方法
	 * 测试套件开始之前，创建extentReports对象，
	 * 调用ExtentManager类的getReporter方法并把“报告生成的路径”作为参数传进去
	 */
	@BeforeSuite
	public void beforeSuite() {
		extentReports = ExtentManager.getReporter(reportPath);
	}
	
	// 二、测试套件结束之后，关闭报告
	@AfterSuite
	public void afterSuite() {
		extentReports.close();
	}
	
	// 三、得到报告
	public static ExtentReports getExtent() {
		return extentReports;
	}
	
	// 四、得到报告路径
	public static String getReportPath() {
		return reportPath;
	}

	// 五、xml数据驱动，本方法是将Global.xml和Case5.xml（这是举例）中的数据合并，得到一个大的整的测试数据好直接让测试用例使用
	@DataProvider
	public Object[][] getData(Method method) {
		this.initXml();// 初始化，把那个xml数据文件先解析出来
		this.getCommonData();// 得到Common节点的数据
		File file = new File(filePath);
		List<Element> elements = xp.getElements("data/" + method.getName());// 3.读那个数据文件的普通节点
		if (file.exists() && elements.size() > 0) {
			Object[][] object = new Object[elements.size()][];
			for (int i = 0; i < elements.size(); i++) {// 循环一次就将那3个节点的数据合并一次
				// 普通节点的数据和common节点的数据先合并一下
				Map<String, String> mergeCommon = this.getMergeData(xp.getChildrenInfoByElement(elements.get(i)),
						commonMap);
				// 再将mergeCommon和global节点的map再合并一次
				Map<String, String> mergeGlobal = this.getMergeData(mergeCommon, global);
				Object[] temp = new Object[] { mergeGlobal };
				object[i] = temp;// 将3个节点数据合成的最终数据赋给二维数组的第二维
			}
			return object;
		} else {
			Object[][] object = new Object[1][1];
			object[0][0] = global;
			return object;
		}
	}
	// 本方法就是实现两个节点数据合并的功能，返回合并后的map
	private Map<String, String> getMergeData(Map<String, String> map1, Map<String, String> map2) {
		Iterator<String> it = map2.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();// 这个key是map2的
			String value = map2.get(key);// 这个value也是map2的
			if (!map1.containsKey(key)) {
				map1.put(key, value);
			}
		}
		return map1;
	}
	private void getCommonData() {// 得到Common节点的数据
		if (commonMap == null) {
			Element e = xp.getSingleElement("/*/common");
			commonMap = xp.getChildrenInfoByElement(e);// 2.读到common的map
		}
	}
	private void initXml() {// 初始化，把那个数据文件先解析出来
		if (xp == null) {
			xp = new XmlParser_online(filePath);
		}
	}
	
//	// 六、excel数据驱动
//	@DataProvider
//	public Object[][] getDataExcel(Method method) {
//		this.initExcel();// 初始化Excel数据文件
//		File file = new File(filePathExcel);
//		List<Map<String,String>> dataMaps = epp.getData(method.getName());
//		if (file.exists() && dataMaps.size() > 0) {
//			Object[][] object = new Object[dataMaps.size()][];
//			for (int i = 0; i < dataMaps.size(); i++) {// 这个dataMaps就是ExcelParserPlus类中解析出来的那个list数据列表
//				// 这里所调用的getMergeData方法和上面所调的是同一个，只是后面那个参数不一样而已（这个globalExcel指的是excel文件中sheet页的数据，而上面那个则是xml文件的节点的数据）
//				Map<String,String> mergeGlobal = getMergeData(dataMaps.get(i), globalExcel);
//				Object[] temp = new Object[]{mergeGlobal};
//				object[i] = temp;
//			}
//			return object;
//		} else {
//			Object[][] object = new Object[1][1];
//			object[0][0] = globalExcel;
//			return object;
//		}
//	}
//	private void initExcel() {
//		if(epp == null){
//			epp = new ExcelParserPlus(filePathExcel);
//		}
//	}
}
