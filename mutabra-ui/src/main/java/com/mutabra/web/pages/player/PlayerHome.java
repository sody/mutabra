package com.mutabra.web.pages.player;

import com.mutabra.domain.player.Hero;
import com.mutabra.game.BattleService;
import com.mutabra.game.User;
import com.mutabra.services.security.GameSecurityContext;
import com.mutabra.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.STATUS_PLAYING)
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
	}

	Object onCreateDuel(final String name) {
		final User user = securityContext.getUser(name);
		if (user != null && user.getHero() != null) {
			battleService.createDuel(user);
			return PlayerBattle.class;
		}
		return null;
	}

	Object onActivate() {
		if (battleService.getCurrentBattle() != null) {
			return PlayerBattle.class;
		}
		return null;
	}
}
