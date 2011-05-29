package com.mutabra.services;

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
	private final String email;

	public MailServiceImpl(final Session session, final String email) {
		this.session = session;
		this.email = email;
	}

	public void send(final String to, final String subject, final String message) {
		try {
			final Transport transport = session.getTransport("smtp");
			transport.connect();
			final Message msg = new MimeMessage(session);
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setFrom(new InternetAddress(email));
			msg.setSentDate(new Date());
			msg.setSubject(subject);
			msg.setText(message);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();  //todo: change with exception processing
		}
		//todo: change this
	}
}
