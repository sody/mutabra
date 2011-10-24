package com.mutabra.web.components.layout;

import com.mutabra.domain.security.Account;
import com.mutabra.security.FacebookToken;
import com.mutabra.security.OAuth;
import com.mutabra.security.TwitterToken;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.services.AccountManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnonymousHeader extends AbstractComponent {

	@Property
	private String email;

	@Property
	private String password;

	@Inject
	private SecurityContext securityContext;

	@InjectService("accountService")
	private BaseEntityService<Account, AccountQuery> accountService;

	@Inject
	private AccountManager accountManager;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		securityContext.signIn(new Credentials(email, password));
		return getResources().getPageName();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signUp")
	Object signUp() throws ValidationException {
		if (accountService.query().withEmail(email).unique() != null) {
			throw new ValidationException("Account already exists");
		}

		accountManager.signUp(email);

		return getResources().getPageName();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	void facebookConnected(final OAuth.Session session) {
		securityContext.signIn(new FacebookToken(session));
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	void twitterConnected(final OAuth.Session session) {
		securityContext.signIn(new TwitterToken(session));
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "google")
	void googleConnected(final OAuth.Session session) {
		System.out.println();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "vkontakte")
	void vkontakteConnected(final OAuth.Session session) {
		System.out.println();
	}
}
