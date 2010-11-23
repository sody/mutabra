package com.noname.web.pages.player.hero;

import com.noname.domain.player.Hero;
import com.noname.services.player.HeroService;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.pages.player.PlayerHome;
import com.noname.web.services.AuthorityConstants;
import com.noname.web.services.security.GameUser;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;
import org.greatage.security.annotations.Authority;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_USER)
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
		final GameUser user = (GameUser) getSecurityContext().getAuthentication();
		final Hero hero = heroService.get(id);
		if (user != null && hero != null) {
			final GameUser newUser = new GameUser(user.getAccount(), hero);
			newUser.getAuthorities().add(AuthorityConstants.ROLE_PLAYER);
			getSecurityContext().setAuthentication(newUser);
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
