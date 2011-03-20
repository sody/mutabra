package com.mutabra.web.pages.player.hero;

import com.mutabra.domain.player.Hero;
import com.mutabra.game.User;
import com.mutabra.services.player.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.player.PlayerHome;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Deny(AuthorityConstants.STATUS_PLAYING)
//@Allow(AuthorityConstants.STATUS_LOGGED)
public class HeroSelect extends AbstractPage {

	@Inject
	private HeroService heroService;

	@Inject
	private SecurityContext securityContext;

	@InjectPage
	private HeroCreate heroCreatePage;

	private Set<Hero> heroes;

	private Hero row;

	public Hero getRow() {
		return row;
	}

	public void setRow(final Hero row) {
		this.row = row;
	}

	public Set<Hero> getHeroes() {
		return heroes;
	}

	void setupRender() {
		heroes = getApplicationContext().getAccount().getHeroes();
	}

	Object onCreate() {
		return heroCreatePage;
	}

	Object onEnter(final long id) {
		final User user = getUserService().getCurrentUser();
		final Hero hero = heroService.get(id);
		if (user != null && hero != null) {
			final User newUser = new User(user.getAccount(), hero);
			getUserService().initCurrentUser(newUser);
			return PlayerHome.class;
		}
		return null;
	}

	Object onSettings(final long id) {
		return null; //todo: implement this
	}

	Object onDelete(final long id) {
		return null; //todo: implement this
	}
}
