package com.mutabra.web.pages.game;

import com.mutabra.domain.player.Hero;
import com.mutabra.services.player.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.pages.game.hero.CreateHero;
import com.mutabra.web.services.AccountContext;
import com.mutabra.web.services.PlayerService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Allow;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_USER)
public class GameHome extends AbstractPage {

	@Inject
	private HeroService heroService;

	@Inject
	private AccountContext accountContext;

	@Inject
	private PlayerService playerService;

	@Property
	private Hero row;

	public List<Hero> getPlayers() {
		return playerService.getPlayers();
	}

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		if (accountContext.getHero() == null) {
			//todo: add more complex rules
			return CreateHero.class;
		}
		return null;
	}
}
