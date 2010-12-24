package com.noname.web.pages.player;

import com.noname.domain.player.Hero;
import com.noname.game.BattleService;
import com.noname.game.User;
import com.noname.services.security.AuthorityConstants;
import com.noname.services.security.GameSecurityContext;
import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Allow;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(AuthorityConstants.STATUS_PLAYING)
public class PlayerHome extends AbstractPage {

	@Inject
	private GameSecurityContext securityContext;

	@Inject
	private BattleService battleService;

	@Property
	private User row;

	private Hero hero;
	private List<User> users;

	public Hero getHero() {
		return hero;
	}

	public List<User> getUsers() {
		return users;
	}

	void setupRender() {
		hero = getApplicationContext().getHero();
		users = securityContext.getPlayingUsers();
		users.remove(securityContext.getCurrentUser());
	}

	Object onCreateDuel(final String name) {
		final User currentUser = securityContext.getCurrentUser();
		final User user = securityContext.getUser(name);
		if (user != null && user.getHero() != null) {
			battleService.createDuel(currentUser.getName(), user.getName());
			return PlayerBattle.class;
		}
		return null;
	}

	Object onActivate() {
		final User currentUser = securityContext.getCurrentUser();
		if (battleService.getPlayer(currentUser.getName()) != null) {
			return PlayerBattle.class;
		}
		return null;
	}
}
