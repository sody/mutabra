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
public class GoogleRealm extends OAuthRealm<GoogleRealm.Token> {
	public GoogleRealm(final @InjectService("accountService") BaseEntityService<Account> accountService,
					   final HashService hashService) {
		super(accountService, hashService, Token.class);
	}

	@Override
	protected Account getAccountByProfileId(final String profileId) {
		return findAccount(account$.googleUser.eq(profileId));
	}

	@Override
	protected SimpleAccount attachAccount(final Account account, final String profileId) {
		account.setGoogleUser(profileId);
		return super.attachAccount(account, profileId);
	}

	public static class Token extends OAuthToken {
		public Token(final OAuth.Session session) {
			super(session);
		}
	}
}
