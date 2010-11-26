package com.noname.services.security;

import com.noname.game.User;
import org.greatage.security.AnonymousAuthenticationProvider;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnonymousProvider extends AnonymousAuthenticationProvider<User> {
	private final AtomicInteger counter = new AtomicInteger(0);

	@Override
	protected User getAntonymousAuthentication() {
		return new User("anonymous" + counter.getAndIncrement());
	}
}
