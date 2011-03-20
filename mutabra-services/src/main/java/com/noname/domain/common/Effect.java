package com.noname.domain.common;

import com.noname.domain.CodedEntity;
import com.noname.domain.TranslationType;
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
@Table(name = "EFFECT")
public class Effect extends CodedEntity {

	@Type(type = "org.greatage.hibernate.type.OrderedEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.noname.domain.common.TargetType")})
	@Column(name = "TARGET_TYPE", nullable = false)
	private TargetType targetType;

	@Column(name = "ATTACK", nullable = false)
	private int attack;

	@Column(name = "DEFENCE", nullable = false)
	private int defence;

	public Effect() {
		super("EFFECT", TranslationType.STANDARD);
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
