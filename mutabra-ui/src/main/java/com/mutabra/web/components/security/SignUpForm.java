package com.mutabra.web.components.security;

import com.mutabra.services.MailService;
import com.mutabra.services.security.AccountService;
import com.mutabra.web.pages.security.Activate;
import com.mutabra.web.pages.security.SignUp;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.greatage.security.PasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignUpForm {

	@Inject
	private AccountService accountService;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private MailService mailService;

	@Inject
	private PageRenderLinkSource linkSource;

	@Property
	private String email;

	@Component
	private Form signUpForm;

	Object onSignUp() {
		if (signUpForm.isValid()) {
			if (accountService.getAccount(email) != null) {
				signUpForm.recordError("Account already exists");
			} else {
				final String password = generateRandomString();
				final String token = generateRandomString();

				final Link link = linkSource.createPageRenderLinkWithContext(Activate.class, email, token);
				final String message = String.format("Hello Mr., New account was created for you, (login: %s, password: %s, activation token: %s). To activate your account please follow the link: %s", email, password, token, link.toAbsoluteURI());

				mailService.send(email, "NoName account confirmation", message);
				accountService.createAccount(email, passwordEncoder.encode(password), token);
				return null;
			}
		}
		return SignUp.class;
	}

	private String generateRandomString() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
