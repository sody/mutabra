package com.mutabra.web.pages.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_USER)
public class GameBattle extends AbstractPage {
	private static final int[] DUEL_POSITIONS = new int[]{0, 1, 100, 101, 102, 200, 201};

	@Inject
	private AccountContext accountContext;

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
	private int position;

	private Map<Integer, BattleMember> members;

	public int[] getPositions() {
		return DUEL_POSITIONS;
	}

	public int getX() {
		return position / 100;
	}

	public int getY() {
		return position % 100;
	}

	public boolean isEmpty() {
		return !members.containsKey(position);
	}

	public boolean isSelected() {
		return position == you.getPosition();
	}

	public Collection<BattleMember> getMembers() {
		return members.values();
	}

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		final Hero hero = accountContext.getHero();
		battle = accountContext.getBattle();

		members = new HashMap<Integer, BattleMember>();
		for (BattleMember member : battle.getMembers()) {
			members.put(member.getPosition(), member);
			if (member.getHero().equals(hero)) {
				you = member;
			} else {
				opponent = member;
			}
		}
		return null;
	}
}
