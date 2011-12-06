package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_ACTION)
public class BattleActionImpl extends BaseEntityImpl implements BattleAction {

	@Parent
	private Key<BattleImpl> battle;

	@Indexed
	private int round;

	@Unindexed
	private Key<BattleCardImpl> card;

	@Unindexed
	@Embedded
	private Position target = new Position();

	public BattleActionImpl() {
	}

	public BattleActionImpl(final Battle battle) {
		this.battle = Keys.getKey(battle);
		this.round = battle.getRound();
	}

	public Battle getBattle() {
		return Keys.getInstance(battle);
	}

	public int getRound() {
		return round;
	}

	public BattleCard getCard() {
		return Keys.getInstance(card);
	}

	public void setCard(final BattleCard card) {
		this.card = Keys.getKey(card);
	}

	public BattleAbility getAbility() {
		return null;
	}

	public void setAbility(final BattleAbility ability) {
	}

	public Position getTarget() {
		return target;
	}

	public void setTarget(final Position target) {
		this.target = target;
	}

	@Override
	public Key<?> getParentKey() {
		return battle;
	}
}
