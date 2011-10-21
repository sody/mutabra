package com.mutabra.web.services;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MailService {

	void send(String to, String topic, String body);

	void notifySignUp(String to, String token, String link);

	void notifyRestorePassword(String to, String token, String link);
}
