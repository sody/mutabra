package com.mutabra.web.services;

import org.apache.tapestry5.ValidationException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountManager {

	void signUp(String email) throws ValidationException;

	void restorePassword(String email) throws ValidationException;

	void changeEmail(String oldEmail, String newEmail);

	void confirmEmail(String email, String token) throws ValidationException;

	void changePassword(String email, String password);
}
