package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "SUMMON")
public class SummonImpl extends CodedEntityImpl implements Summon {

	@Column(name = "ATTACK", nullable = false)
	private int attack;

	@Column(name = "DEFENCE", nullable = false)
	private int defence;

	public SummonImpl() {
		super("SUMMON", TranslationType.STANDARD);
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
