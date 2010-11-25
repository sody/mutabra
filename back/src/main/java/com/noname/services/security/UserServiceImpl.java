package com.noname.services.security;

import com.noname.game.User;
import org.greatage.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserServiceImpl implements UserService {
	private final Map<String, User> loggedUsers = new HashMap<String, User>();
	private final Map<String, User> playingUsers = new HashMap<String, User>();

	@Override
	public List<User> getLoggedUsers() {
		return CollectionUtils.newList(loggedUsers.values());
	}

	@Override
	public List<User> getPlayingUsers() {
		return CollectionUtils.newList(playingUsers.values());
	}

	@Override
	public User getUser(final String id) {
		return loggedUsers.get(id);
	}

	@Override
	public void updateUser(final User user) {
		final String id = user.getName();
		if (user.getHero() == null) {
			loggedUsers.put(id, user);
			playingUsers.remove(id);
		} else {
			loggedUsers.put(id, user);
			playingUsers.put(id, user);
		}
	}

	public void removeUser(final User user) {
		loggedUsers.remove(user.getName());
		playingUsers.remove(user.getName());
	}
}
