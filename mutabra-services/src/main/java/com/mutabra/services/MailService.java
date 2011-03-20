package com.mutabra.services;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MailService {

	void send(String to, String subject, String message);

}
