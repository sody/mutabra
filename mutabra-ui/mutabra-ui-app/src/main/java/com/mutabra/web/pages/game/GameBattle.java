package com.mutabra.web.pages.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.Hero;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
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

	@Property
	private Battle battle;

	@Property
	private BattleMember you;

	@Property
	private BattleMember opponent;

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		battle = accountContext.getBattle();
		final Hero hero = accountContext.getHero();
		for (BattleMember member : battle.getMembers()) {
			if (member.getHero().equals(hero)) {
				you = member;
			} else {
				opponent = member;
			}
		}
		return null;
	}
}
