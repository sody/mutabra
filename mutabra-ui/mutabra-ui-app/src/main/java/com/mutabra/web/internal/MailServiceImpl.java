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
	private final String adminAddress;

	public MailServiceImpl(final String adminAddress) {
		this.adminAddress = adminAddress;
	}

	public void send(final String to, final String subject, final String body) {
		final Session session = Session.getDefaultInstance(properties, null);

		try {
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(adminAddress));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			System.out.println("MESSAGE: " + body);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Error occurs while sending email 'to:%s/%s'", to, subject), e);
		}
	}

	public void notifySignUp(final String to, final String token, final String link) {
		final String message = String.format(REGISTRATION_NOTIFICATION, to, token, link, DEFAULT_SIGNATURE);
		send(to, "Mutabra Account", message);
	}

	public void notifyRestorePassword(final String to, final String token, final String link) {
		final String message = String.format(RESTORE_PASSWORD_NOTIFICATION, to, token, link, DEFAULT_SIGNATURE);
		send(to, "Mutabra Account", message);
	}


	private static final String REGISTRATION_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("New account was created for you.\n")
			.append("\tlogin: %s\n\tactivation token: %s\n\n")
			.append("To activate your account please follow the link:\n\n\t%s\n\n")
			.append("---\n%s").toString();

	private static final String RESTORE_PASSWORD_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("New access token was created for you.\n")
			.append("\tlogin: %s\n\tactivation token: %s\n\n")
			.append("To enter your account please follow the link:\n\n\t%s\n\n")
			.append("---\n%s").toString();

	private static final String DEFAULT_SIGNATURE = "BR, Mutabra team";
}
