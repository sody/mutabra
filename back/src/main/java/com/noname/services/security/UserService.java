package com.noname.services.security;

import com.noname.game.User;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface UserService {

	List<User> getLoggedUsers();

	List<User> getPlayingUsers();

	User getUser(String id);

	void updateUser(User user);

	void removeUser(final User user);

}
