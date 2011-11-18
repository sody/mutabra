package com.mutabra.domain.game;

import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Entity;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE)
public class BattleImpl extends BaseEntityImpl implements Battle {
	private int round;

	public Set<BattleMember> getMembers() {
		return Keys.getChildren(BattleMember.class, BattleMemberImpl.class, this);
	}

	public int getRound() {
		return round;
	}

	public void setRound(final int round) {
		this.round = round;
	}
}
