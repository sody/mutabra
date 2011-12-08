package com.mutabra.domain.battle;

import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE)
public class BattleImpl extends BaseEntityImpl implements Battle {
	private int round;
	private Date startedAt;
	private BattleState state;

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

	public List<BattleHero> getHeroes() {
		return Keys.getChildren(BattleHero.class, BattleHeroImpl.class, this);
	}

	public List<BattleEffect> getEffects() {
		return Keys.getChildren(BattleEffect.class, BattleEffectImpl.class, this);
	}
}
