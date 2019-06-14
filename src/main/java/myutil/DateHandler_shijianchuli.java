package myutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 本类是时间处理的工具，分别使用下面的方法获取不同的时间格式
 * 
 * @author Administrator
 *
 */
public class DateHandler_shijianchuli {

	// 1.获取时间戳样式的
	public static String getTimeStamp() {
		return getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
	}

	// 2.获取当前年月日
	public static String getNowDay() {
		return getFormatDate(new Date(), "yyyy-MM-dd");
	}

	// 3_1.获取某一时间的增量时间（即某一时间点的前面多久或后面多久）
	public static String getForwardDay(String date, int increment) {// 参数increment指的是增量多少
		Calendar AddDay = Calendar.getInstance();
		AddDay.setTime(ParseDate(date, "yyyy-MM-dd"));
		AddDay.add(Calendar.DATE, increment);
		return getFormatDate(AddDay.getTime(), "yyyy-MM-dd");
	}

	// 3_2此方法可将字符串的时间类型转成日期类型
	private static Date ParseDate(String date, String reg) {// reg：时间格式字符串类型，即yyyy-MM-dd等
		SimpleDateFormat dateFormat = new SimpleDateFormat(reg);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	// 3_3此方法可将日期类型时间转成字符串类型
	private static String getFormatDate(Date date, String reg) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(reg);
		return dateFormat.format(date);
	}

	public static void main(String[] arg) {
		System.out.println(getTimeStamp());
		System.out.println(getNowDay());
		System.out.println(getForwardDay(getNowDay(), -3));// 写成3就是向后推3天，-3就是向前推3天
	}
}
