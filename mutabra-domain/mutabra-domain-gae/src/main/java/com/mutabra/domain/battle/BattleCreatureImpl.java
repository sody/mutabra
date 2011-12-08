package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_SUMMON)
public class BattleCreatureImpl extends BattleUnitImpl implements BattleCreature {

	@Parent
	private Key<BattleHeroImpl> owner;

	@Unindexed
	private Key<CardImpl> card;

	public BattleCreatureImpl() {
	}

	public BattleCreatureImpl(final BattleHero owner, final Card card) {
		this.owner = Keys.getKey(owner);
		this.card = Keys.getKey(card);
	}

	public BattleHero getOwner() {
		return Keys.getInstance(owner);
	}

	public Card getCard() {
		return Keys.getInstance(card);
	}

	@Override
	public Key<?> getParentKey() {
		return owner;
	}
}
