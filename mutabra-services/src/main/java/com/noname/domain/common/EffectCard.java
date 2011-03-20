package com.noname.domain.common;

import javax.persistence.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@DiscriminatorValue(value = "1")
@Table(name = "EFFECT_CARD")
public class EffectCard extends Card {

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_EFFECT", nullable = false)
	private Effect effect = new Effect();

	public EffectCard() {
		super(CardType.EFFECT);
	}

	public Effect getEffect() {
		return effect;
	}
}
