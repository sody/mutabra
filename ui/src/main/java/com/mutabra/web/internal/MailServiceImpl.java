package com.mutabra.web.internal;

import com.mutabra.web.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private final Session session;

    public MailServiceImpl(final Session session) {
        this.session = session;

        // set debug property
        session.setDebug(logger.isDebugEnabled());
    }

    public void send(final String to, final String subject, final String body) {
        try {
            final Message message = new MimeMessage(session);
            // set from address retrieved from session properties
            message.setFrom();
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (Exception e) {
            final String error = String.format("Error occurs while sending email 'to:%s/%s'", to, subject);
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
    }
}
