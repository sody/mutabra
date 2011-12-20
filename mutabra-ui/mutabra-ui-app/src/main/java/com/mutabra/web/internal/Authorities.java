package com.mutabra.web.internal;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Permission;
import com.mutabra.domain.game.Role;
import org.greatage.security.User;
import org.greatage.util.StringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class Authorities implements org.greatage.security.AuthorityConstants {
	private static final String PERMISSION_PREFIX = "PERMISSION_";
	private static final String ROLE_PREFIX = "ROLE_";
	private static final String TWITTER_PREFIX = "twitter:";
	private static final String ANONYMOUS_USER = "anonymous";

	public static final String PAGE_AUTHORITY_META = "page-authority";

	public static final String ROLE_ADMIN = ROLE_PREFIX + Role.ADMIN.name().toUpperCase();
	public static final String ROLE_USER = ROLE_PREFIX + "USER";

	public static User createUser(final Account account) {
		return new User(account(account), authorities(account));
	}

	public static boolean isTwitterUser(final String name) {
		return name.startsWith(TWITTER_PREFIX);
	}

	public static String getTwitterUser(final String name) {
		return name.substring(TWITTER_PREFIX.length());
	}

	public static String generateSecret() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

	public static String permission(final Permission permission) {
		return PERMISSION_PREFIX + permission.name().toUpperCase();
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

	private static List<String> authorities(final Account account) {
		final List<String> authorities = new ArrayList<String>();
		authorities.add(STATUS_AUTHENTICATED);
		authorities.add(ROLE_PREFIX + account.getRole().name().toUpperCase());
		for (Permission permission : account.getRole().getPermissions()) {
			authorities.add(permission(permission));
		}
		return authorities;
	}
}
