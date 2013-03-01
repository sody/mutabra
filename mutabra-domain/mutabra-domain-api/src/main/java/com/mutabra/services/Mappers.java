package com.mutabra.services;

import com.mutabra.domain.Translation;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.common.CardMapper;
import com.mutabra.services.game.AccountMapper;
import com.mutabra.services.game.HeroMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Mappers {
    TranslationMapper<Translation> translation$ = new TranslationMapper<Translation>(null);
    AccountMapper<Account> account$ = new AccountMapper<Account>(null);
    HeroMapper<Hero> hero$ = new HeroMapper<Hero>(null);
    CardMapper<Card> card$ = new CardMapper<Card>(null);
}
