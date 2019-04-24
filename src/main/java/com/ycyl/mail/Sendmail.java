package com.ycyl.mail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.handlers.multipart_mixed;

/**
 * 邮件发送工具类 mail.jar activation.jar
 * 
 * @author ycyl
 *
 */
public class Sendmail {

	public static final String MAIL_HOST = "smtp.163.com"; // 主机
	public static final String MAIL_PORT = "25";// 端口号

	public static void main(String[] args) throws Exception {
		sendSimpleMail();
	}

	/**
	 * 发送简单邮件
	 * 
	 * @throws Exception
	 */
	public static void sendSimpleMail() throws Exception {

		Properties properties = new Properties();
		properties.setProperty("mail.host", "smtp.163.com");
		properties.setProperty("mail.smtp.port", "25");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");

		// 使用JavaMail发送邮件的5个流程
		// 1.创建session
		Session session = Session.getInstance(properties, new Authenticator() {
			// 设置认证账户信息
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("scnucxy@163.com", "chen6282194738");
			}
		});
		// 开启session的debug状态，获取程序发送email的状态
		session.setDebug(true);
		// 2.通过session获取transport对象
		// 4.创建邮件
//		Message message = createSimpleMail(session);
//		Message message = createMailAndImage(session);
		Message message = createSimpleMail(session);
		// 5.发送邮件
		Transport.send(message);
	}

	/**
	 * 创建简单的邮件
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createSimpleMail(Session session) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指定发件人
		message.setFrom(new InternetAddress("scnucxy@163.com"));
		// 指定收件人
		InternetAddress[] s = new InternetAddress[10];
//		message.setRecipients(MimeMessage.RecipientType.TO, new InternetAddress[] {
//				new InternetAddress("scnucxy@qq.com"), new InternetAddress("cxycxy20080808@sina.com") });
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("18482195695@139.com"));
		// 设置标题
		message.setSubject("测试");
		// 设置内容
		message.setContent("javaMail测试", "text/html;charset=utf-8");
		return message;
	}

	public static MimeMessage createMailAndImage(Session session) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 发件人
		message.setFrom(new InternetAddress("scnucxy@163.com"));
		// 收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("scnucxy@qq.com"));
		// 主题
		message.setSubject("带图片的邮件");
		// 准备content
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent("这是一封到图片的邮件<img src='cid:ss.png'>", "text/html;charset=utf-8");
		// 准备图片数据
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("src/main/java/122131389484760.png"));
		image.setDataHandler(dh);
		image.setContentID("ss.png");

		// 描述数据关系
		MimeMultipart mimeMultipart = new MimeMultipart();
		mimeMultipart.addBodyPart(bodyPart);
		mimeMultipart.addBodyPart(image);
		mimeMultipart.setSubType("related");

		message.setContent(mimeMultipart);

//	    message.saveChanges();
//	     //将创建的Email写入到E盘存储
//	    message.writeTo(new FileOutputStream("D:\\attachMail.eml"));

		return message;
	}

	public static MimeMessage createAttachMail(Session session) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// 发件人
		message.setFrom(new InternetAddress("scnucxy@163.com"));
		// 收件人
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("scnucxy@qq.com"));
		// 设置主题
		message.setSubject("带附件的邮件");

		// 创建邮件正文
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent("带附件的邮件", "text/html;charset=utf-8");

		// 创建邮件附件
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dataHandler = new DataHandler(new FileDataSource("src/main/java/122131389484760.png"));
		attach.setDataHandler(dataHandler);
		attach.setFileName(dataHandler.getName());

		// 创建容器数据关系
		MimeMultipart mimeMultipart = new MimeMultipart();
		mimeMultipart.addBodyPart(bodyPart);
		mimeMultipart.addBodyPart(attach);
		mimeMultipart.setSubType("mixed");

		message.setContent(mimeMultipart);
		return message;
	}

	public static MimeMessage createAttachAndImageMail(Session session) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// 发件人
		message.setFrom(new InternetAddress("scnucxy@163.com"));
		// 收件人
		// 多收件人
		InternetAddress[] s = new InternetAddress[10];
		message.setRecipients(MimeMessage.RecipientType.TO, new InternetAddress[] {
				new InternetAddress("scnucxy@qq.com"), new InternetAddress("sccxyc@sina.com") });

		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("cxycxy20080808@sina.com"));
		// 设置主题
		message.setSubject("带附件的邮件");

		// 创建邮件正文
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent("带附件的邮件<img src='cid:ss.png'>", "text/html;charset=utf-8");

		// 创建邮件附件
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dataHandler = new DataHandler(new FileDataSource("src/main/java/122131389484760.png"));
		attach.setDataHandler(dataHandler);
		attach.setFileName(dataHandler.getName());

		MimeBodyPart attach2 = new MimeBodyPart();
		DataHandler dataHandler2 = new DataHandler(new FileDataSource("src/main/java/122128504487771.zip"));
		attach2.setDataHandler(dataHandler2);
		attach2.setFileName(MimeUtility.encodeText(dataHandler2.getName()));

		// 创建邮件图片
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("src/main/java/122131389484760.png"));
		image.setDataHandler(dh);
		image.setContentID("ss.png");

		// 正文和图片
		MimeMultipart mp1 = new MimeMultipart();
		mp1.addBodyPart(bodyPart);
		mp1.addBodyPart(image);
		mp1.setSubType("related");

		// 正文和附件
		MimeMultipart mimeMultipart = new MimeMultipart();
		mimeMultipart.addBodyPart(attach2);
		mimeMultipart.addBodyPart(attach);

		MimeBodyPart content = new MimeBodyPart();
		content.setContent(mp1);

		mimeMultipart.addBodyPart(content);
		mimeMultipart.setSubType("mixed");

		message.setContent(mimeMultipart);
		return message;
	}

}
