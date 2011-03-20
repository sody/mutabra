package com.noname.services.security;

import com.noname.game.User;
import org.greatage.security.SecurityContext;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface GameSecurityContext extends SecurityContext<User> {

	List<User> getPlayingUsers();

}
