package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.db.Tables;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.EFFECT)
public class EffectImpl extends CodedEntityImpl implements Effect {

	@Persistent
	private TargetType targetType;

	@Persistent
	private int attack;

	@Persistent
	private int defence;

	public EffectImpl() {
		super(Tables.EFFECT, TranslationType.STANDARD);
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(final TargetType targetType) {
		this.targetType = targetType;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(final int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(final int defence) {
		this.defence = defence;
	}
}
