package com.mutabra.web.internal;

import com.mutabra.web.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

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
            logger.debug("Sending mail 'to:{}/{}':\n{}", new Object[]{to, subject, body});
        } catch (Exception e) {
            final String error = String.format("Error occurs while sending email 'to:%s/%s'", to, subject);
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
    }
}
