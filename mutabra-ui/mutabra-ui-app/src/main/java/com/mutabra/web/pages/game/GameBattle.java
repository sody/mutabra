package com.mutabra.web.pages.game;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Card;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Allow;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_USER)
public class GameBattle extends AbstractPage {

	@Inject
	private AccountContext accountContext;

	@Inject
	private BattleService battleService;

	@Property
	private Battle battle;

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		battle = accountContext.getBattle();
		if (battle == null) {
			return GameHome.class;
		}
		return null;
	}

	@OnEvent("registerActions")
	Object registerActions(final BattleHero hero,
						   final Card card,
						   final @RequestParameter(value = "x") int x,
						   final @RequestParameter(value = "y") int y) {
		battleService.registerAction(battle, hero, card, new Position(x, y));
		return null;
	}
}
