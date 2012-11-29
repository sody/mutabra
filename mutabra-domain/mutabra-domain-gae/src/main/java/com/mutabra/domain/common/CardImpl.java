package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.CARD)
public class CardImpl extends CastableImpl implements Card {

    public CardImpl() {
        this(null);
    }

    public CardImpl(final String code) {
        super(Tables.CARD, code, TranslationType.DESCRIPTION);
    }

    public List<Ability> getAbilities() {
        return Keys.getChildren(Ability.class, AbilityImpl.class, this);
    }
}
