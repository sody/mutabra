package com.mutabra.web.pages.game;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.player.Hero;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.common.RaceQuery;
import com.mutabra.services.player.HeroQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.services.AccountContext;
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
	private BaseEntityService<Hero, HeroQuery> heroService;

	@InjectService("raceService")
	private BaseEntityService<Race, RaceQuery> raceService;

	@Inject
	private AccountContext accountContext;
}
