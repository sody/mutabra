package com.noname.services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MailServiceImpl implements MailService {
	private final Session session;

	public MailServiceImpl(Session session) {
		this.session = session;
	}

	@Override
	public void send(final String to, final String subject, final String message) {
		try {
			final Transport transport = session.getTransport("smtp");
			transport.connect();
			final Message msg = new MimeMessage(session);
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSentDate(new Date());
			msg.setSubject(subject);
			msg.setText(message);
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();  //todo: change with exception processing
		}
		//todo: change this
	}
}
