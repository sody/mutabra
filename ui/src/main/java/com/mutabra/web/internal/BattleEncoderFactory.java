/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

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
        final ObjectId heroId = decodeId(ObjectId.class, clientValue);
        if (heroId != null) {
            final Battle battle = battle();
            for (BattleHero battleHero : battle.getHeroes()) {
                if (battleHero.getId().equals(heroId)) {
                    return battleHero;
                }
            }
        }

        throw new NotFoundException("BattleHero is not found.");
    }

    private String encodeCard(final BattleCard battleCard) {
        return typeCoercer.coerce(battleCard.getId(), String.class);
    }

    private BattleCard decodeCard(final String clientValue) {
        final Long cardId = decodeId(Long.class, clientValue);
        if (cardId != null) {
            final Battle battle = battle();
            for (BattleHero battleHero : battle.getHeroes()) {
                for (BattleCard battleCard : battleHero.getCards()) {
                    if (battleCard.getId().equals(cardId)) {
                        return battleCard;
                    }
                }
            }
        }

        throw new NotFoundException("BattleCard is not found.");
    }

    private String encodeCreature(final BattleCreature battleCreature) {
        return typeCoercer.coerce(battleCreature.getId(), String.class);
    }

    private BattleCreature decodeCreature(final String clientValue) {
        final Long creatureId = decodeId(Long.class, clientValue);
        if (creatureId != null) {
            final Battle battle = battle();
            for (BattleHero battleHero : battle.getHeroes()) {
                for (BattleCreature battleCreature : battleHero.getCreatures()) {
                    if (battleCreature.getId().equals(creatureId)) {
                        return battleCreature;
                    }
                }
            }
        }

        throw new NotFoundException("BattleCreature is not found.");
    }

    private String encodeAbility(final BattleAbility battleAbility) {
        return typeCoercer.coerce(battleAbility.getId(), String.class);
    }

    private BattleAbility decodeAbility(final String clientValue) {
        final Long abilityId = decodeId(Long.class, clientValue);
        if (abilityId != null) {
            final Battle battle = battle();
            for (BattleHero battleHero : battle.getHeroes()) {
                for (BattleCreature battleCreature : battleHero.getCreatures()) {
                    for (BattleAbility battleAbility : battleCreature.getAbilities()) {
                        if (battleAbility.getId().equals(abilityId)) {
                            return battleAbility;
                        }
                    }
                }
            }
        }

        throw new NotFoundException("BattleAbility is not found.");
    }

    private <T> T decodeId(final Class<T> idClass, final String clientValue) {
        try {
            return typeCoercer.coerce(clientValue, idClass);
        } catch (RuntimeException e) {
            throw new NotFoundException("Couldn't parse card primary key", e);
        }
    }

    private Battle battle() {
        final Battle battle = accountContext.getBattle();
        if (battle == null) {
            throw new NotFoundException("Battle is not found.");
        }
        return battle;
    }
}
