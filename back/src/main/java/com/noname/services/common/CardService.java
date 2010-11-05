package com.noname.services.common;

import com.noname.domain.common.Card;
import com.noname.domain.common.CardType;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 */
public interface CardService extends CodedEntityService<Card> {

	Card create(CardType cardType);

}
