package com.mutabra.web.components.game;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardDisplay {

	@Property
	@Parameter
	private Card value;

	public boolean isEffectCard() {
		return value.getType() == CardType.EFFECT;
	}

	public boolean isSummonCard() {
		return value.getType() == CardType.SUMMON;
	}
}
