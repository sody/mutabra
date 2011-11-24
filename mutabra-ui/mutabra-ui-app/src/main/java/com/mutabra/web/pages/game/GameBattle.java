package com.mutabra.web.pages.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.Position;
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
	private static final Position[] DUEL_POSITIONS = {
			new Position(0, 0),
			new Position(0, 1),
			new Position(1, 0),
			new Position(1, 1),
			new Position(1, 2),
			new Position(2, 0),
			new Position(2, 1),
	};

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
	private Position position;

	private Map<Position, BattleMember> members;

	public Position[] getPositions() {
		return DUEL_POSITIONS;
	}

	public boolean isEmpty() {
		return !members.containsKey(position);
	}

	public boolean isHero() {
		return members.containsKey(position);
	}

	public boolean isSelected() {
		return position.equals(you.getPosition());
	}

	public Collection<BattleMember> getMembers() {
		return members.values();
	}

	public boolean isFriend() {
		return member.equals(you);
	}

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		final Hero hero = accountContext.getHero();
		battle = accountContext.getBattle();

		members = new HashMap<Position, BattleMember>();
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
