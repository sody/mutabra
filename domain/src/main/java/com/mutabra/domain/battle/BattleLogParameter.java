/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Effect;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleLogParameter implements Translatable {
    private String parameter;

    private BattleSide side;
    private BattlePosition position;

    private ObjectId heroId;
    private Long cardId;
    private Long creatureId;
    private Long abilityId;

    private String code;
    private String value;

    protected BattleLogParameter() {
    }

    public BattleLogParameter(final String parameter, final BattleSide side, final BattlePosition position) {
        this.parameter = parameter;
        this.side = side;
        this.position = position;
        //TODO: calculate value?
    }

    public BattleLogParameter(final String parameter, final BattleUnit battleUnit) {
        this.parameter = parameter;

        if (battleUnit.isHero()) {
            final BattleHero battleHero = (BattleHero) battleUnit;
            heroId = battleHero.getId();
            value = battleHero.getAppearance().getName();
        } else {
            final BattleCreature battleCreature = (BattleCreature) battleUnit;
            heroId = battleCreature.getHero().getId();
            creatureId = battleCreature.getId();
            code = battleCreature.getCode();
        }
    }

    public BattleLogParameter(final String parameter, final BattleSpell battleSpell) {
        this.parameter = parameter;

        if (battleSpell.isCard()) {
            final BattleCard battleCard = (BattleCard) battleSpell;
            cardId = battleCard.getId();
            code = battleCard.getCode();
        } else {
            final BattleAbility battleAbility = (BattleAbility) battleSpell;
            abilityId = battleAbility.getId();
            code = battleAbility.getCode();
        }
    }

    public BattleLogParameter(final String parameter, final BattleEffect battleEffect) {
        this.parameter = parameter;

        code = battleEffect.getCode() + "." + Translatable.NAME;
    }

    public BattleLogParameter(final String parameter, final Object value) {
        this.parameter = parameter;
        this.value = String.valueOf(value);
    }

    public String getParameter() {
        return parameter;
    }

    public BattlePosition getPosition() {
        return position;
    }

    public BattleSide getSide() {
        return side;
    }

    public ObjectId getHeroId() {
        return heroId;
    }

    public Long getCardId() {
        return cardId;
    }

    public Long getCreatureId() {
        return creatureId;
    }

    public Long getAbilityId() {
        return abilityId;
    }

    public String getValue() {
        return value;
    }

    public String getBasename() {
        if (cardId != null) {
            return Card.BASENAME;
        } else if (abilityId != null) {
            return Ability.BASENAME;
        }
        return Effect.BASENAME;
    }

    public String getCode() {
        return code;
    }
}
