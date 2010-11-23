package com.noname.web.pages.player.hero;

import com.noname.domain.player.Hero;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.services.AuthorityConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.greatage.security.annotations.Authority;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_USER)
public class HeroSelect extends AbstractPage {

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
		heroes = getCurrentAccount().getHeroes();
	}

	Object onCreate() {
		return heroCreatePage;
	}

	Object onEnter(final long id) {
		return null; //todo: implement this
	}

	Object onSettings(final long id) {
		return null; //todo: implement this
	}

	Object onDelete(final long id) {
		return null; //todo: implement this
	}
}
