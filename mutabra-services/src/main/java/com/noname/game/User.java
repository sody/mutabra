/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.game;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.Role;
import com.noname.services.security.AuthorityConstants;
import org.greatage.security.PasswordAuthentication;
import org.greatage.util.CollectionUtils;

import java.util.List;

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
		super(account.getEmail(), account.getPassword(), createAuthorities(account));
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

	private static List<String> createAuthorities(final Account account) {
		final List<String> authorities = CollectionUtils.newList();
		authorities.add(account.getEmail());
		for (Role role : account.getRoles()) {
			authorities.add(role.getAuthority());
			for (Permission permission : role.getPermissions()) {
				authorities.add(permission.getAuthority());
			}
		}
		return authorities;
	}
}
