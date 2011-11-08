package com.mutabra.web.pages.game;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.player.Hero;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.pages.game.hero.CreateHero;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.annotations.Allow;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_USER)
public class GameHome extends AbstractPage {

	@InjectService("heroService")
	private BaseEntityService<Hero> heroService;

	@InjectService("raceService")
	private BaseEntityService<Race> raceService;

	@Inject
	private AccountContext accountContext;

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		if (accountContext.getHero() == null) {
			//todo: add more complex rules
			return CreateHero.class;
		}
		return null;
	}
}
