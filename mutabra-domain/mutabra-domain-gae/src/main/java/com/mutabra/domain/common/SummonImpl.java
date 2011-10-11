package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Tables;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.SUMMON)
public class SummonImpl extends CodedEntityImpl implements Summon {

	@Persistent
	private int attack;

	@Persistent
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
