package com.mutabra.web.pages;

import com.mutabra.security.TwitterService;
import com.mutabra.web.base.pages.AbstractPage;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.PasswordAuthenticationToken;
import org.greatage.security.SecurityContext;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Security extends AbstractPage {

	@Property
	private String email;

	@Property
	private String password;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private TwitterService twitterService;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		securityContext.signIn(new PasswordAuthenticationToken(email, password));
		return Index.class;
	}

	Object onTwitterConnect(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "denied", allowBlank = true) String denied) {

		final String info = new DescriptionBuilder("TWITTER TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.append("denied", denied)
				.toString();
		System.out.println(info);
		return null;
	}

	Object onFacebookConnect(
			@RequestParameter(value = "code", allowBlank = true) final String code,
			@RequestParameter(value = "error", allowBlank = true) final String error,
			@RequestParameter(value = "error_reason", allowBlank = true) final String errorReason,
			@RequestParameter(value = "error_description", allowBlank = true) final String errorDescription) {

		final String info = new DescriptionBuilder("FACEBOOK TOKEN")
				.append("code", code)
				.append("error", error)
				.append("error_reason", errorReason)
				.append("error_description", errorDescription)
				.toString();
		System.out.println(info);
		return null;
	}

	Object onGoogleConnect(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier) {

		final String info = new DescriptionBuilder("GOOGLE TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.toString();
		System.out.println(info);
		return null;
	}

	Object onVKontakteConnect(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "denied", allowBlank = true) String denied) {

		final String info = new DescriptionBuilder("VKONTAKTE TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.append("denied", denied)
				.toString();
		System.out.println(info);
		return null;
	}
}
