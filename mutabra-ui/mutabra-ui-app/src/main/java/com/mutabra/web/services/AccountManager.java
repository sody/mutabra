package com.mutabra.web.services;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountManager {

	void signUp(String email);

	void restorePassword(String email);
}
