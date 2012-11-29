package com.mutabra.domain.common;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.ABILITY)
public class AbilityImpl extends CastableImpl implements Ability {

    @Parent
    private Key<CardImpl> card;

    public AbilityImpl() {
        this(null, null);
    }

    public AbilityImpl(final Card card, final String code) {
        super(Tables.ABILITY, code, TranslationType.DESCRIPTION);
        this.card = Keys.getKey(card);
    }

    public Card getCard() {
        return Keys.getInstance(card);
    }

    @Override
    public Key<?> getParentKey() {
        return card;
    }
}
