package com.mutabra.web.pages.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.BattlePlace;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.services.AccountContext;
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
public class GameBattle extends AbstractPage {
	@Inject
	private AccountContext accountContext;

	@Inject
	private BattleService battleService;

	@Property
	private Battle battle;

	@Property
	private BattleMember you;

	@Property
	private BattleMember opponent;

	@Property
	private BattleMember member;

	@Property
	private BattleCard card;

	@Property
	private BattlePlace place;

	@Property
	private List<BattlePlace> battleField;

	public boolean isFriend() {
		return member.equals(you);
	}

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		final Hero hero = accountContext.getHero();
		battle = accountContext.getBattle();
		if (battle == null) {
			return GameHome.class;
		}

		battleField = battleService.getBattleField(hero, battle);
		for (BattlePlace battlePlace : battleField) {
			if (battlePlace.hasHero()) {
				if (battlePlace.isEnemySide()) {
					opponent = battlePlace.getMember();
				} else {
					you = battlePlace.getMember();
				}
			}
		}
		return null;
	}
}
