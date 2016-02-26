package com.bgj.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bgj.autojobs.NotificationMail;

public class MailUtil {
	private static String host = "smtp.163.com";
	private static String username = "lqj_liu@163.com";
	private static String password = "GGaaooyyoouu456";

	private static String mail_head_name = "KLineService Daily Report";

	private static String mail_head_value = "KLineService Daily Report";

	private static String mail_from = "lqj_liu@163.com";

	private static String personalName = "KLineServiceNotification(NotReply)";


	/**
	 * �˶δ�������������ͨ�����ʼ�
	 */
	public static void send(NotificationMail notification) throws Exception {
		try {
			Properties props = new Properties();
			Authenticator auth = new Authenticator(){
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			}; // �����ʼ��������û���֤
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			// ����session,���ʼ�����������ͨѶ��
			MimeMessage message = new MimeMessage(session);
			// message.setContent("foobar, "application/x-foobar"); // �����ʼ���ʽ
			message.setSubject(notification.getSubject()); // �����ʼ�����
			message.setText(notification.getBody()); // �����ʼ�����
			message.setHeader(mail_head_name, mail_head_value); // �����ʼ�����

			message.setSentDate(new Date()); // �����ʼ���������
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address); // �����ʼ������ߵĵ�ַ

			message.addRecipient(Message.RecipientType.TO, new InternetAddress("lqj.liu@qq.com"));

			Transport.send(message); // �����ʼ�
			System.out.println("send success!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}


	public static void main(String[] args) {
		try {
			NotificationMail notification = new NotificationMail();
			notification.setSuccessful(true);
			MailUtil.send(notification);
			
			NotificationMail notificationFailed = new NotificationMail();
			notificationFailed.setSuccessful(false);
			notificationFailed.setFailedCause(new NullPointerException());
			MailUtil.send(notificationFailed);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}