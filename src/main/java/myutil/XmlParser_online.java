package myutil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 本类是，解析xml文件的工具类，包含方法实现的功能如下：
 * 
 * @author Administrator
 *
 */
public class XmlParser_online {
	private String xmlPath;
	private Document d;

	public XmlParser_online(String xmlPath) {
		this.xmlPath = xmlPath;
		load();// 这种写法的好处：只要调了这个构造器（传进来xml文件路径），即new这个类对象时就加载解析了文件
	}

	private void load() {// 加载解析xml文件的方法
		File xmlFile = new File(xmlPath);
		if (xmlFile.exists()) {
			SAXReader sr = new SAXReader();
			try {
				d = sr.read(xmlFile);
			} catch (DocumentException e) {
				e.printStackTrace();
				System.out.println("xml文件解析异常！");
			}
		} else {
			System.out.println("待解析的xml文件不存在！");
		}
	}

	// 1.获取单个节点元素
	public Element getSingleElement(String pathStr) {
		return (Element) d.selectSingleNode(pathStr);
	}

	// 2.获取单个节点元素的text
	public String getSingleElementText(String pathStr) {
		Element e = getSingleElement(pathStr);
		if (e != null) {
			return e.getTextTrim();
		}
		return null;
	}

	// 3.获取多个节点元素
	public List<Element> getElements(String pathStr) {
		return d.selectNodes(pathStr);
	}

	// 4.获取多个节点元素的text列表
	public List<String> getElementsText(String pathStr) {
		List<String> textList = new ArrayList<String>();
		for (Element e : getElements(pathStr)) {
			textList.add(e.getTextTrim());
		}
		return textList;
	}

	// 5.传入一个XPath表达式，返回这个XPath对应元素下的所有一级子节点的name和text
	public Map<String, String> getChildrenInfo(String elementXPath) {
		List<Element> elements = getSingleElement(elementXPath).elements();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Element e : elements) {
			map.put(e.getName(), e.getTextTrim());
		}
		return map;
	}

	// 6.传入一个元素对象，返回这个元素下的所有一级子节点的name和text
	public Map<String, String> getChildrenInfoByElement(Element e) {
		List<Element> elements = e.elements();
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Element e1 : elements) {
			map.put(e1.getName(), e1.getTextTrim());
		}
		return map;
	}

	// 7.判断指定路径的元素是否存在（或可理解为是否找到指定元素）
	public boolean isExist(String elementPath) {
		boolean flag = false;
		Element e = this.getSingleElement(elementPath);
		if (e != null) {
			flag = true;
		}
		return flag;
	}
}
