package com.mutabra.services.security;

import com.mutabra.game.User;
import org.greatage.security.SecurityContext;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface GameSecurityContext extends SecurityContext<User> {

	List<User> getPlayingUsers();

}
