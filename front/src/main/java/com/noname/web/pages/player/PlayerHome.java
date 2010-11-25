package com.noname.web.pages.player;

import com.noname.domain.player.Hero;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.services.AuthorityConstants;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.STATUS_PLAYER)
public class PlayerHome extends AbstractPage {

	private Hero hero;

	public Hero getHero() {
		return hero;
	}

	void setupRender() {
		hero = getApplicationContext().getHero();
	}

}
