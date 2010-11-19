package com.noname.domain.common;

import javax.persistence.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@DiscriminatorValue(value = "2")
@Table(name = "SUMMON_CARD")
public class SummonCard extends Card {

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_SUMMON", nullable = false)
	private Summon summon = new Summon();

	public SummonCard() {
		super(CardType.SUMMON);
	}

	public Summon getSummon() {
		return summon;
	}

}
