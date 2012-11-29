package com.mutabra.domain.common;

import org.greatage.hibernate.type.OrderedEnum;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum EffectType implements OrderedEnum {
    UNKNOWN(0),

    /* attacks */
    MELEE_ATTACK(0),
    RANGED_ATTACK(0),
    MAGIC_ATTACK(0),

    /* abilities */
    SUMMON(1),
    HEAL(0),
    MOVE(0),

    /* enhancements */
    ATTACK_ENHANCEMENT(0),
    MELEE_ATTACK_ENHANCEMENT(0),
    RANGED_ATTACK_ENHANCEMENT(0),
    MAGIC_ATTACK_ENHANCEMENT(0),

    /* cancellations */
    ATTACK_CANCELLATION(0),
    MELEE_ATTACK_CANCELLATION(0),
    RANGED_ATTACK_CANCELLATION(0),
    MAGIC_ATTACK_CANCELLATION(0),

    ABILITY_CANCELLATION(0),
    SUMMON_CANCELLATION(0),
    HEAL_CANCELLATION(0),
    MOVE_CANCELLATION(0),

    /* absorptions */
    DAMAGE_ABSORPTION(0),
    MELEE_DAMAGE_ABSORPTION(0),
    RANGED_DAMAGE_ABSORPTION(0),
    MAGIC_DAMAGE_ABSORPTION(0),

    /* punishments */
    ATTACK_PUNISHMENT(0),
    MELEE_ATTACK_PUNISHMENT(0),
    RANGED_ATTACK_PUNISHMENT(0),
    MAGIC_ATTACK_PUNISHMENT(0),

    ABILITY_PUNISHMENT(0),
    SUMMON_PUNISHMENT(0),
    HEAL_PUNISHMENT(0),
    MOVE_PUNISHMENT(0),;

    private final int order;

    EffectType(final int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
