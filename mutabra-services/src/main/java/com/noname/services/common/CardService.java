package com.noname.services.common;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CardService extends CodedEntityService<Card> {

	Card create(CardType cardType);

}
