package myutil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 本类是Excel文件解析的工具类，属于高级版
 * 
 * @author Administrator
 *
 */
public class ExcelParserPlus {
	private String filePath;
	private HSSFWorkbook book;

	public ExcelParserPlus(String filePath) {
		this.filePath = filePath;
		this.load();
	}

	// 本方法是加载excel文件的，即创建对Excel工作簿文件的引用
	private void load() {
		File file = new File(filePath);// 现在file是文件
		if (file.exists()) {
			try {
				FileInputStream xls = new FileInputStream(file);// 如果file文件存在，就创建xls流对象
				this.book = new HSSFWorkbook(xls);// 到此，就创建了对Excel工作簿文件的引用
			} catch (Exception e) {
				System.out.println("Excel[" + filePath + "]异常");
			}
		} else {
			System.out.println("Excel[" + filePath + "]不存在！");
		}
	}

	// 本方法定义了几种单元格数据类型，被下面的方法所调用
	private String getValue(HSSFCell cell) {
		Object value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case NUMERIC:// 数值类型
				value = (int) cell.getNumericCellValue();
				// value=cell.getDateCellValue();这应该是获取日期的
				break;
			case STRING:// 字符串类型
				value = cell.getStringCellValue();
				// value=cell.toString();
				break;
			case BOOLEAN:// 布尔类型
				value = cell.getBooleanCellValue();
				break;
			case FORMULA:// 表达式类型（即公式）
				value = cell.getCellFormula();
				break;
			case BLANK:// 空（不知道何时算空，空就不要取值了吧）
				break;
			case ERROR:// 异常类型（不知道何时算异常）
				// value=cell.getErrorCellValue();//异常也可以不要取值
				break;
			default:
				break;
			}
		}
		return value.toString();
	}

	// 本方法是得到单元格的值，入参是sheet页名、行数、列数
	public String getCellValue(String sheetName, int x, int y) {
		String cellValue = "";
		HSSFSheet hssfSheet = book.getSheet(sheetName);// 创建对工作表的引用
		if (hssfSheet == null) {
			System.out.println("Sheet[" + sheetName + "]不存在");
			return null;
		}
		int maxRow = hssfSheet.getLastRowNum();// maxRow这个是指去除了首行标题之外的数据总行数（是从2开始的）
		if (x <= maxRow + 1) {// 而x是excel表中的从1开始的行数
			HSSFRow hssfRow = hssfSheet.getRow(x - 1);// 这个hssfRow行数是从0开始的数
			int maxCol = hssfRow.getLastCellNum();// 最大列数maxCol是从1开始的
			if (y < maxCol + 1) {// y是excel表中的从1开始的列数
				HSSFCell cell = hssfRow.getCell(y - 1);
				if (cell != null) {
					cellValue = getValue(cell);
				}
			} else {
				System.out.println("Sheet[" + sheetName + "]的第" + maxRow + "行只有" + maxCol + "列，参数超过最大列数限制！");
				return null;
			}
		} else {
			System.out.println("Sheet[" + sheetName + "]只有" + maxRow + "行，参数超过最大行数限制！");
			return null;
		}
		return cellValue;
	}

	// 本方法是获取sheet页内所有的数据，入参是sheet页名
	public List<Map<String, String>> getData(String sheetName) {
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		HSSFSheet hssfSheet = book.getSheet(sheetName);// 创建对工作表的引用
		if (hssfSheet == null) {
			System.out.println("Sheet[" + sheetName + "]不存在");
			return null;
		}
		int lastRowNum = hssfSheet.getLastRowNum();//
		// System.out.println(lastRowNum);
		for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {// rowNum行号是excel中的第二行开始
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);// 创建行对象
			// System.out.println("++++"+hssfRow);
			if (hssfRow != null) {
				Map<String, String> userMap = new LinkedHashMap<String, String>();
				for (int col = hssfRow.getFirstCellNum(); col < hssfRow.getLastCellNum(); col++) {
					HSSFCell keyCell = hssfSheet.getRow(0).getCell(col);// 例子中，keyCell是第一行的标题的单元格的值
					HSSFCell valueCell = hssfRow.getCell(col);// 而valueCell是下面数据行的数据值
					boolean hasKey = keyCell != null && getValue(keyCell) != "";
					boolean hasValue = valueCell != null && getValue(valueCell) != "";
					if (hasKey && hasValue) {
						userMap.put(getValue(keyCell), getValue(valueCell));// 所以keyCell和valueCell，就是username=shan2016等
						// System.out.println(getValue(keyCell));
						// System.out.println(getValue(valueCell));
					} // 上面代码是在把每一行的数据（username=shan2016,pwd=shan1111等）塞进map
				}
				if (userMap.keySet().size() == hssfRow.getLastCellNum()) {// 当一行数据列都塞进map后，
					userList.add(userMap);// 再把那一行数据的map塞进list中
				}
			} else {
				continue;// 如果excel表中有空行的情况，本行代码就可以跳过当次循环，即跳过这个空行，继续遍历下一行数据
			}
		}
		return userList;
	}
}
