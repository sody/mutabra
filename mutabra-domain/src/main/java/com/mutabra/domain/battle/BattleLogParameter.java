package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Effect;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleLogParameter implements Translatable {
    private BattleSide side;
    private BattlePosition position;

    private ObjectId heroId;
    private Long cardId;
    private Long creatureId;
    private Long abilityId;

    private String code;
    private String value;

    public BattleLogParameter() {
    }

    public BattleLogParameter(final BattleSide side, final BattlePosition position) {
        this.side = side;
        this.position = position;
        //TODO: calculate value?
    }

    public BattleLogParameter(final BattleUnit battleUnit) {
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

    public BattleLogParameter(final BattleSpell battleSpell) {
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

    public BattleLogParameter(final BattleEffect battleEffect) {
        code = battleEffect.getCode() + ".name";
    }

    public BattleLogParameter(final Object value) {
        this.value = String.valueOf(value);
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
        if (creatureId != null) {
            return BattleCreature.BASENAME;
        } else if (cardId != null) {
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
