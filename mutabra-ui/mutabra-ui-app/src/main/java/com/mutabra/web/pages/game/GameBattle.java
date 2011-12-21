package com.mutabra.web.pages.game;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.game.Permission;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authority;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(Permission.PLAY)
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

	@OnEvent("registerHeroAction")
	Object registerHeroAction(final BattleHero hero,
							  final Card card,
							  final @RequestParameter(value = "x") int x,
							  final @RequestParameter(value = "y") int y) {
		if (!hero.isExhausted()) {
			battleService.registerAction(battle, hero, card, new Position(x, y));
		}
		return null;
	}

	@OnEvent("registerCreatureAction")
	Object registerCreatureAction(final BattleCreature creature,
							  final Ability ability,
							  final @RequestParameter(value = "x") int x,
							  final @RequestParameter(value = "y") int y) {
		if (!creature.isExhausted()) {
			battleService.registerAction(battle, creature, ability, new Position(x, y));
		}
		return null;
	}
}
