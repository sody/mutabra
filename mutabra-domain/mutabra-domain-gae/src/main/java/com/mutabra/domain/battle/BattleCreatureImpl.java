package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectImpl;

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
	private Key<EffectImpl> effect;

	public BattleCreatureImpl() {
	}

	public BattleCreatureImpl(final BattleHero owner, final Effect effect) {
		this.owner = Keys.getKey(owner);
		this.effect = Keys.getKey(effect);
	}

	public BattleHero getOwner() {
		return Keys.getInstance(owner);
	}

	public Effect getEffect() {
		return Keys.getInstance(effect);
	}

	@Override
	public Key<?> getParentKey() {
		return owner;
	}
}
