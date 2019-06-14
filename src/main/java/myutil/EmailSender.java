package myutil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import base.Log;

/**
 * 本类是封装的发送邮件的工具
 * 
 * @author Administrator
 *
 */
public class EmailSender {

	// 定义系统常量
	public static String SMTP = "smtp";// 邮箱服务器类型
	public static String SUBJECT = "subject";// 邮件主题
	public static String FROM = "from";// 发件人邮箱地址
	public static String SENDER_NAME = "senderName";// 发件人名字
	public static String SET_TO = "setTo";// 收件人邮箱地址
	public static String COPY_TO = "copyTo";// 抄送人邮箱地址
	public static String USER_NAME = "userName";// 发件人邮箱用户名
	public static String PASS_WORD = "password";// 发件人邮箱密码
	public static String FILE_PATH = "filePath";// 需要添加的附件文件路径
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	private String subject = null; // 邮件主题
	private String filePath = null; // 附件的路径

	private String smtp = null; // 发件人SMTP服务器地址
	private String userName = null; // 发件人邮箱用户名
	private String password = null; // 发件人邮箱密码
	private String from = null; // 发件人邮箱地址
	private String senderName = null; // 发件人显示名称
	private String setTo = null; // 收件人邮箱地址，多个地址时以英文逗号分隔
	private String copyTo = null; // 抄送人邮箱地址，多个地址时以英文逗号分隔

	/*
	 * 初始化发送邮件的基本配置
	 */
	public boolean InitMail() {
		Map<String, String> map = getMailMap();

		// 检查是否缺少发送邮件的必要参数
		// for (String name : map.keySet()) {
		// String value = map.get(name);
		// if (value == null || value.equals("")) {
		// System.out.println("缺少发送邮件的必要参数：" + name);
		// return false;
		// }
		// }
		String[] argNames = { SMTP, FROM, SET_TO, USER_NAME, PASS_WORD };// 这个和上面那个是一个意思，只不过这个指定了必要条件是哪些
		for (String name : argNames) {
			String value = map.get(name);
			if (value == null || value.equals("")) {
				System.out.println("缺少发送邮件的必要参数：" + name);
				return false;
			}
		}

		// 通过反射初始化参数列表
		for (String name : map.keySet()) {
			String value = map.get(name);
			if (value != null && !value.equals("")) {
				try {
					Field field = this.getClass().getDeclaredField(name);
					field.set(this, value);
				} catch (NoSuchFieldException e) {
					System.out.println("EmailSender不存在如下参数名：" + name);
				} catch (SecurityException e) {
					System.out.println("初始化EmailSender参数异常!");
				} catch (IllegalArgumentException e) {
					System.out.println("EmailSender以下参数，参数值类型不正确：" + name);
				} catch (IllegalAccessException e) {
					System.out.println("初始化EmailSender参数异常!");
				}
			}
		}

		smtp = map.get(SMTP);
		userName = map.get(USER_NAME);
		password = map.get(PASS_WORD);
		from = map.get(FROM);
		setTo = map.get(SET_TO);
		subject = map.get(SUBJECT);
		senderName = map.get(SENDER_NAME);
		return true;
	}

	/**
	 * 得到发送邮件的基本配置Map
	 */
	public Map<String, String> getMailMap() {
		XmlParser_online xmljx = new XmlParser_online("./config/Config.xml");
		Map<String, String> mailParameters = xmljx.getChildrenInfo("/config/mail");
		return mailParameters;
	}

	/**
	 * 设置邮件发送服务器
	 * 
	 * @param hostName
	 */
	protected void setSmtpHost() {
		Log.info("设置系统属性：mail.smtp.host = " + smtp);
		if (props == null)
			props = System.getProperties();// 获得系统属性对象
		props.setProperty("mail.host", smtp);// 设置SMTP主机
		props.setProperty("mail.transport.protocol", "smtp");// 邮件协议
	}

	/**
	 * 创建MIME邮件对象
	 * 
	 * @return
	 */
	protected boolean createMimeMessage() {
		try {
			Log.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props);// 获得邮件会话对象
		} catch (Exception e) {
			System.out.println("获取邮件会话对象时发生错误！");
			return false;
		}

		try {
			Log.info("准备创建MIME邮件对象！");
			mimeMsg = new MimeMessage(session);// 创建MIME邮件对象
			mp = new MimeMultipart("related");
			return true;
		} catch (Exception e) {
			System.out.println("创建MIME邮件对象失败！");
			return false;
		}
	}

	/**
	 * 设置SMTP是否需要验证
	 * 
	 * @param need
	 */
	protected void setNeedAuth(boolean need) {
		Log.info("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.setProperty("mail.smtp.auth", "true");
		} else {
			props.setProperty("mail.smtp.auth", "false");
		}
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailSubject
	 * @return
	 */
	protected boolean setSubject() {
		Log.info("设置邮件主题！");
		try {
			if (subject != null || !subject.equals("")) {
				mimeMsg.setSubject(subject);
			} else
				mimeMsg.setSubject(" ");
			return true;
		} catch (Exception e) {
			System.out.println("设置邮件主题发生错误！");
			return false;
		}
	}

	/**
	 * 设置邮件正文
	 * 
	 * @param mailBody
	 */
	protected boolean setBody(String text, String encoding) {
		try {
			MimeBodyPart textBody = new MimeBodyPart();
			textBody.setContent(text, encoding);

			mp.addBodyPart(textBody);
			return true;
		} catch (Exception e) {
			System.out.println("设置邮件正文时发生错误！");
			return false;
		}
	}

	/**
	 * 设置收信人
	 * 
	 * @param to
	 */
	protected boolean setTo() {
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(setTo));
			return true;
		} catch (Exception e) {
			System.out.println("设置收信人异常！");
			return false;
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @throws MessagingException
	 */
	protected boolean sendOut(boolean needCopyto) {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			Log.info("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport();
			transport.connect(smtp, userName, password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			if (needCopyto) {
				transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
			}
			Log.info("发送邮件成功！");
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("邮件发送失败！");
			return false;
		}
	}

	/**
	 * 调用sendOut方法完成邮件发送
	 * 
	 * @throws MessagingException
	 */
	public boolean send(String text, String encoding) {
		if (!InitMail()) {
			return false;
		}
		boolean needCopyto = true;
		setSmtpHost(); // 设置smtp服务器地址
		createMimeMessage();// 创建会话
		setNeedAuth(true); // 设置是否身份验证

		if (!setSubject())
			return false; // 设置主题
		if (!setBody(text, encoding))
			return false; // 设置正文

		 if (!(filePath == null || filePath.equals(""))) {// 设置附件
			 if (!addFileAffix()) return false;
		 }
		
		if (!setTo())
			return false; // 设置收件人

		if (copyTo == null || copyTo.equals("")) {// 设置抄送人
			needCopyto = false;
		} else {
			if (!setCopyTo())
				return false;
		}
		if (!setFrom())
			return false; // 设置发件人
		if (!sendOut(needCopyto))
			return false;// 调用发送函数

		return true;
	}

	/**
	 * 设置发信人
	 * 
	 * @param from
	 */
	protected boolean setFrom() {
		Log.info("设置发信人！");
		try {
			if (senderName == null || senderName.equals("")) {
				mimeMsg.setFrom(new InternetAddress(from));
			} else
				mimeMsg.setFrom(new InternetAddress(from, senderName));
			return true;
		} catch (Exception e) {
			System.out.println("设置发信人异常！");
			return false;
		}
	}

	/**
	 * 设置抄送人
	 * 
	 * @param copyto
	 */
	protected boolean setCopyTo() {
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyTo));
			return true;
		} catch (Exception e) {
			System.out.println("设置抄送人异常！");
			return false;
		}
	}

	/**
	 * 添加附件
	 * 
	 * @param filename
	 */
	protected boolean addFileAffix() {
		Log.info("增加邮件附件：" + filePath);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filePath);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			System.out.println("增加邮件附件：" + filePath + "发生错误！ ");
			return false;
		}
	}
}
