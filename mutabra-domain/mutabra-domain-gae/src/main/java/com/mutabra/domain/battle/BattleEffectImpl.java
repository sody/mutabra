package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectImpl;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.BATTLE_ACTION)
public class BattleEffectImpl extends BaseEntityImpl implements BattleEffect {

	@Parent
	private Key<BattleImpl> battle;

	@Unindexed
	private Key<EffectImpl> effect;

	@Unindexed
	private Key<BattleUnitImpl> caster;

	@Unindexed
	@Embedded
	private Position target;

	@Unindexed
	private int duration;

	public BattleEffectImpl() {
	}

	public BattleEffectImpl(final Battle battle, final Effect effect) {
		this.battle = Keys.getKey(battle);
		this.effect = Keys.getKey(effect);
	}

	public Battle getBattle() {
		return Keys.getInstance(battle);
	}

	public Effect getEffect() {
		return Keys.getInstance(effect);
	}

	public BattleUnit getCaster() {
		return Keys.getInstance(caster);
	}

	public void setCaster(final BattleUnit caster) {
		this.caster = Keys.getKey(caster);
	}

	public Position getTarget() {
		return target;
	}

	public void setTarget(final Position target) {
		this.target = target;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}

	@Override
	public Key<?> getParentKey() {
		return battle;
	}
}
