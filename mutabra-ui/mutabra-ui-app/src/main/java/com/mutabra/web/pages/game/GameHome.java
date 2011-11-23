package com.mutabra.web.pages.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.Mappers;
import com.mutabra.services.game.BattleService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.pages.game.hero.CreateHero;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.domain.PaginationBuilder;
import org.greatage.security.annotations.Allow;

import java.util.Date;
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
	private BattleService battleService;

	@Inject
	private AccountContext accountContext;

	@Property
	private Hero row;

	private Hero hero;

	public List<Hero> getPlayers() {
		final Date timeout = new Date(System.currentTimeMillis() - 50000);
		final PaginationBuilder pagination = PaginationBuilder.create().count(20);
		return heroService.query(Mappers.hero$.lastActive.gt(timeout)).list(pagination);
	}

	public boolean isCanCreateBattle() {
		return row.equals(hero);
	}

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		if (accountContext.getHero() == null) {
			//todo: add more complex rules
			return CreateHero.class;
		}
		if (accountContext.getBattle() != null) {
			return GameBattle.class;
		}
		hero = accountContext.getHero();
		return null;
	}

	@OnEvent("createBattle")
	Object createBattle(final Hero target) {
		if (target.getBattle() == null && hero.getBattle() == null && !target.equals(hero)) {
			final Battle battle = battleService.createBattle(hero, target);
			battleService.startRound(battle);
			return GameBattle.class;
		}
		return null;
	}
}
