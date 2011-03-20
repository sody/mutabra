/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.game;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import com.noname.services.security.AuthorityConstants;
import org.greatage.security.PasswordAuthentication;
import org.greatage.util.CollectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class User extends PasswordAuthentication {
	private final Account account;
	private final Hero hero;

	public User(final String name) {
		super(name, null, CollectionUtils.newList(AuthorityConstants.STATUS_ANONYMOUS));
		account = null;
		hero = null;
	}

	public User(final Account account) {
		this(account, null);
	}

	public User(final Account account, final Hero hero) throws IllegalArgumentException {
		super(account.getEmail(), account.getPassword(), account.createAuthorities());
		this.account = account;
		this.hero = hero;
		getAuthorities().add(AuthorityConstants.STATUS_LOGGED);
		if (hero != null) {
			getAuthorities().add(AuthorityConstants.STATUS_PLAYING);
		}
	}

	public Account getAccount() {
		return account;
	}

	public Hero getHero() {
		return hero;
	}
}
