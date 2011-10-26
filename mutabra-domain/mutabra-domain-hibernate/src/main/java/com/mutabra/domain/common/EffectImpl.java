package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = Tables.EFFECT)
public class EffectImpl extends CodedEntityImpl implements Effect {

	@Type(type = "org.greatage.hibernate.type.OrderedEnumUserType",
			parameters = {@Parameter(name = "enumClass", value = "com.mutabra.domain.common.TargetType")})
	@Column(name = "TARGET_TYPE", nullable = false)
	private TargetType targetType;

	@Column(name = "ATTACK", nullable = false)
	private int attack;

	@Column(name = "DEFENCE", nullable = false)
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
