package com.noname.services.security;

import com.noname.game.User;
import org.greatage.security.SecurityContextImpl;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GameSecurityContextImpl extends SecurityContextImpl<User> implements GameSecurityContext {
	private final Map<String, User> playingUsers = CollectionUtils.newConcurrentMap();

	@Override
	public List<User> getPlayingUsers() {
		return CollectionUtils.newList(playingUsers.values());
	}

	@Override
	public void initCurrentUser(final User user) {
		super.initCurrentUser(user);
		if (user.getHero() == null) {
			playingUsers.remove(user.getName());
		} else {
			playingUsers.put(user.getName(), user);
		}
	}

	@Override
	public void removeCurrentUser() {
		final User user = getCurrentUser();
		if (user != null) {
			playingUsers.remove(user.getName());
		}
		super.removeCurrentUser();
	}
}
