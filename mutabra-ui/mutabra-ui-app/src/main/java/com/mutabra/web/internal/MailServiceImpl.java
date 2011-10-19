package com.mutabra.web.internal;

import com.mutabra.web.services.MailService;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MailServiceImpl implements MailService {
	private final Properties properties = new Properties();

	public void send(final String to, final String subject, final String body) {
		final Session session = Session.getDefaultInstance(properties, null);

		try {
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@mutabra.com", "Mutabra Admin"));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (Exception e) {
			//todo: log it
			e.printStackTrace();
		}
	}
}
