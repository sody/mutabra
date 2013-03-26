package com.mutabra.web.internal;

import com.mutabra.domain.battle.*;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleEncoderFactory implements ValueEncoderFactory {
    private static final char SEPARATOR = '-';

    private final AccountContext accountContext;
    private final TypeCoercer typeCoercer;

    private final Map<Class, ValueEncoder> valueEncoders = new HashMap<Class, ValueEncoder>();

    public BattleEncoderFactory(final AccountContext accountContext, final TypeCoercer typeCoercer) {
        this.accountContext = accountContext;
        this.typeCoercer = typeCoercer;

        valueEncoders.put(BattleHero.class, new ValueEncoder<BattleHero>() {
            public String toClient(final BattleHero value) {
                return encodeHero(value);
            }

            public BattleHero toValue(final String clientValue) {
                return decodeHero(clientValue);
            }
        });
        valueEncoders.put(BattleCard.class, new ValueEncoder<BattleCard>() {
            public String toClient(final BattleCard value) {
                return encodeCard(value);
            }

            public BattleCard toValue(final String clientValue) {
                return decodeCard(clientValue);
            }
        });
        valueEncoders.put(BattleCreature.class, new ValueEncoder<BattleCreature>() {
            public String toClient(final BattleCreature value) {
                return encodeCreature(value);
            }

            public BattleCreature toValue(final String clientValue) {
                return decodeCreature(clientValue);
            }
        });
        valueEncoders.put(BattleAbility.class, new ValueEncoder<BattleAbility>() {
            public String toClient(final BattleAbility value) {
                return encodeAbility(value);
            }

            public BattleAbility toValue(final String clientValue) {
                return decodeAbility(clientValue);
            }
        });
    }

    public ValueEncoder create(final Class type) {
        return valueEncoders.get(type);
    }

    private String encodeHero(final BattleHero battleHero) {
        return typeCoercer.coerce(battleHero.getId(), String.class);
    }

    private BattleHero decodeHero(final String clientValue) {
        final Battle battle = accountContext.getBattle();
        if (battle == null) {
            throw new NotFoundException("Battle is not found.");
        }

        final ObjectId heroId;
        try {
            heroId = typeCoercer.coerce(clientValue, ObjectId.class);
        } catch (RuntimeException e) {
            throw new NotFoundException("Couldn't parse hero primary key", e);
        }
        if (heroId != null) {
            for (BattleHero battleHero : battle.getHeroes()) {
                if (battleHero.getId().equals(heroId)) {
                    return battleHero;
                }
            }
        }

        throw new NotFoundException("BattleHero is not found.");
    }

    private String encodeCard(final BattleCard battleCard) {
        return encodeHero(battleCard.getHero()) + SEPARATOR + typeCoercer.coerce(battleCard.getId(), String.class);
    }

    private BattleCard decodeCard(final String clientValue) {
        if (clientValue == null) {
            throw new NotFoundException("Empty value.");
        }
        final int index = clientValue.lastIndexOf(SEPARATOR);
        final String heroClientValue = index > 0 ? clientValue.substring(0, index) : null;
        final String cardClientValue = index > 0 ? clientValue.substring(index + 1) : null;
        final BattleHero battleHero = decodeHero(heroClientValue);

        final Long cardId;
        try {
            cardId = typeCoercer.coerce(cardClientValue, Long.class);
        } catch (RuntimeException e) {
            throw new NotFoundException("Couldn't parse card primary key", e);
        }
        if (cardId != null) {
            for (BattleCard battleCard : battleHero.getCards()) {
                if (battleCard.getId().equals(cardId)) {
                    return battleCard;
                }
            }
        }

        throw new NotFoundException("BattleCard is not found.");
    }

    private String encodeCreature(final BattleCreature battleCreature) {
        return encodeHero(battleCreature.getHero()) + SEPARATOR + typeCoercer.coerce(battleCreature.getId(), String.class);
    }

    private BattleCreature decodeCreature(final String clientValue) {
        if (clientValue == null) {
            throw new NotFoundException("Empty value.");
        }
        final int index = clientValue.lastIndexOf(SEPARATOR);
        final String heroClientValue = index > 0 ? clientValue.substring(0, index) : null;
        final String creatureClientValue = index > 0 ? clientValue.substring(index + 1) : null;
        final BattleHero battleHero = decodeHero(heroClientValue);

        final Long creatureId;
        try {
            creatureId = typeCoercer.coerce(creatureClientValue, Long.class);
        } catch (RuntimeException e) {
            throw new NotFoundException("Couldn't parse creature primary key", e);
        }
        if (creatureId != null) {
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                if (battleCreature.getId().equals(creatureId)) {
                    return battleCreature;
                }
            }
        }

        throw new NotFoundException("BattleCreature is not found.");
    }

    private String encodeAbility(final BattleAbility battleAbility) {
        return encodeCreature(battleAbility.getCreature()) + SEPARATOR + typeCoercer.coerce(battleAbility.getId(), String.class);
    }

    private BattleAbility decodeAbility(final String clientValue) {
        if (clientValue == null) {
            throw new NotFoundException("Empty value.");
        }
        final int index = clientValue.lastIndexOf(SEPARATOR);
        final String creatureClientValue = index > 0 ? clientValue.substring(0, index) : null;
        final String abilityClientValue = index > 0 ? clientValue.substring(index + 1) : null;
        final BattleCreature battleCreature = decodeCreature(creatureClientValue);

        final Long abilityId;
        try {
            abilityId = typeCoercer.coerce(abilityClientValue, Long.class);
        } catch (RuntimeException e) {
            throw new NotFoundException("Couldn't parse ability primary key", e);
        }
        if (abilityId != null) {
            for (BattleAbility battleAbility : battleCreature.getAbilities()) {
                if (battleAbility.getId().equals(abilityId)) {
                    return battleAbility;
                }
            }
        }

        throw new NotFoundException("BattleAbility is not found.");
    }
}
