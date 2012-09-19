package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.security.OAuth;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.tapestry5.ioc.annotations.InjectService;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterRealm extends OAuthRealm<TwitterRealm.Token> {
	public TwitterRealm(final @InjectService("accountService") BaseEntityService<Account> accountService,
						final HashService hashService) {
		super(accountService, hashService, Token.class);
	}

	@Override
	protected Account getAccountByProfileId(final String profileId) {
		return findAccount(account$.twitterUser.eq(profileId));
	}

	@Override
	protected SimpleAccount attachAccount(final Account account, final String profileId) {
		account.setTwitterUser(profileId);
		return super.attachAccount(account, profileId);
	}

	public static class Token extends OAuthToken {
		public Token(final OAuth.Session session) {
			super(session);
		}
	}
}
