package com.mutabra.domain.battle;

import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE)
public class BattleImpl extends BaseEntityImpl implements Battle {
	private int round;
	private Date startedAt;
	private BattleState state;

	public Set<BattleMember> getMembers() {
		return Keys.getChildren(BattleMember.class, BattleMemberImpl.class, this);
	}

	public Set<BattleAction> getActions() {
		return Keys.getChildren(BattleAction.class, BattleActionImpl.class, this, "round =", round);
	}

	public BattleState getState() {
		return state;
	}

	public void setState(final BattleState state) {
		this.state = state;
	}

	public int getRound() {
		return round;
	}

	public void setRound(final int round) {
		this.round = round;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(final Date startedAt) {
		this.startedAt = startedAt;
	}
}
