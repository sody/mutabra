package com.mutabra.web.components.layout;

import com.mutabra.domain.game.Account;
import com.mutabra.security.FacebookToken;
import com.mutabra.security.OAuth;
import com.mutabra.security.TwitterToken;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecurityContext;

import static com.mutabra.services.Mappers.account$;

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
	private BaseEntityService<Account> accountService;

	@Inject
	private AccountManager accountManager;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		securityContext.signIn(new Credentials(email, password));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signUp")
	Object signUp() throws ValidationException {
		if (accountService.query(account$.email.eq(email)).unique() != null) {
			throw new ValidationException("Account already exists");
		}

		accountManager.signUp(email);

		return getResources().getPageName();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	Object facebookConnected(final OAuth.Session session) {
		securityContext.signIn(new FacebookToken(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	Object twitterConnected(final OAuth.Session session) {
		securityContext.signIn(new TwitterToken(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "google")
	Object googleConnected(final OAuth.Session session) {
		System.out.println();
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "vkontakte")
	Object vkontakteConnected(final OAuth.Session session) {
		System.out.println();
		return GameHome.class;
	}
}
