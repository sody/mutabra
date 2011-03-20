package com.noname.services.common;

import com.noname.domain.common.Card;
import com.noname.domain.common.CardType;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CardService extends CodedEntityService<Card> {

	Card create(CardType cardType);

}
