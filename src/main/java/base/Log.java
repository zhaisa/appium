package base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 本类是封装的日志类，根据Debug、Info、Warn、Error等级别打印相应的内容
 * @author Administrator
 *
 */
public class Log {
	static final Logger logger = Logger.getLogger(Log.class.getName());
	// static final Logger logger = LogManager.getLogger(Log.class.getName());// 老师的代码中用的是LogManager，他说一样
	
	static {
		DOMConfigurator.configure("log4j.xml");
		// PropertyConfigurator.configure("Log4j.properties");
	}

	/**
	 * Debug级别LOG
	 * 
	 * @param msg 用户赋值，期望打印的内容
	 */
	public synchronized static void debug(String msg) {
		logger.log(Log.class.getName(), Level.DEBUG, msg, null);
	}

	/**
	 * Info级别LOG
	 * 
	 * @param msg 用户赋值，期望打印的内容
	 */
	public synchronized static void info(String msg) {
		logger.log(Log.class.getName(), Level.INFO, msg, null);
	}

	/**
	 * Warn级别的LOG
	 * 
	 * @param msg 用户赋值，期望打印的内容
	 */
	public synchronized static void warn(String msg) {
		logger.log(Log.class.getName(), Level.WARN, msg, null);
	}

	/**
	 * Error级别的LOG
	 * 
	 * @param msg 用户赋值，期望打印的内容
	 */
	public synchronized static void error(String msg) {
		logger.log(Log.class.getName(), Level.ERROR, msg, null);
	}

	// 和上一个方法一样，只是多加了个参数cause（原始异常）
	public synchronized static void error(String msg, Throwable cause) {
		logger.log(Log.class.getName(), Level.ERROR, msg, cause);
	}
}
