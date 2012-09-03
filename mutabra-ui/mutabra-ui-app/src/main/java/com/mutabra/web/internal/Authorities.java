package com.mutabra.web.internal;

import com.mutabra.domain.game.Account;
import org.greatage.util.StringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class Authorities {
	private static final String TWITTER_PREFIX = "twitter:";
	private static final String ANONYMOUS_USER = "anonymous";

	public static final String SHIRO_REQUIRES_AUTHENTICATION_META = "shiro.requires-authentication";
	public static final String SHIRO_REQUIRES_USER_META = "shiro.requires-user";
	public static final String SHIRO_REQUIRES_GUEST_META = "shiro.requires-guest";
	public static final String SHIRO_REQUIRES_ROLES_META = "shiro.requires-roles";
	public static final String SHIRO_REQUIRES_PERMISSIONS_META = "shiro.requires-permissions";

	public static boolean isTwitterUser(final String name) {
		return name.startsWith(TWITTER_PREFIX);
	}

	public static String getTwitterUser(final String name) {
		return name.substring(TWITTER_PREFIX.length());
	}

	public static String generateSecret() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

	private static String account(final Account account) {
		if (!StringUtils.isEmpty(account.getEmail())) {
			return account.getEmail();
		}
		if (!StringUtils.isEmpty(account.getTwitterUser())) {
			return TWITTER_PREFIX + account.getTwitterUser();
		}
		return ANONYMOUS_USER;
	}
}
