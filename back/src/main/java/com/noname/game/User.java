/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.game;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import org.greatage.security.DefaultAuthentication;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class User extends DefaultAuthentication {
	private final Account account;
	private final Hero hero;

	private UserStatus status;

	public User(final Account account) {
		this(account, null);
	}

	public User(final Account account, final Hero hero) throws IllegalArgumentException {
		super(account.getEmail(), account.getPassword(), account.createAuthorities());
		this.account = account;
		this.hero = hero;
		this.status = hero != null ? UserStatus.STATUS_PLAYING : UserStatus.STATUS_LOGGED;
		getAuthorities().add(status.name());
	}

	public UserStatus getStatus() {
		return status;
	}

	public Account getAccount() {
		return account;
	}

	public Hero getHero() {
		return hero;
	}
}
