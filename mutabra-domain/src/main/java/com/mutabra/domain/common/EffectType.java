package com.mutabra.domain.common;

import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum EffectType implements Translatable {
    UNKNOWN,

    /* abilities */
    SUMMON,
    HEAL,
    MOVE,

    /* attacks */
    MELEE_ATTACK,
    RANGED_ATTACK,
    MAGIC_ATTACK,

    /* enhancements */
    ATTACK_ENHANCEMENT,
    MELEE_ATTACK_ENHANCEMENT,
    RANGED_ATTACK_ENHANCEMENT,
    MAGIC_ATTACK_ENHANCEMENT,

    /* absorptions */
    DAMAGE_ABSORPTION,
    MELEE_DAMAGE_ABSORPTION,
    RANGED_DAMAGE_ABSORPTION,
    MAGIC_DAMAGE_ABSORPTION,

    /* cancellations */
    ATTACK_CANCELLATION,
    MELEE_ATTACK_CANCELLATION,
    RANGED_ATTACK_CANCELLATION,
    MAGIC_ATTACK_CANCELLATION,

    ABILITY_CANCELLATION,
    SUMMON_CANCELLATION,
    HEAL_CANCELLATION,
    MOVE_CANCELLATION,

    /* punishments */
    ATTACK_PUNISHMENT,
    MELEE_ATTACK_PUNISHMENT,
    RANGED_ATTACK_PUNISHMENT,
    MAGIC_ATTACK_PUNISHMENT,

    ABILITY_PUNISHMENT,
    SUMMON_PUNISHMENT,
    HEAL_PUNISHMENT,
    MOVE_PUNISHMENT;

    private static final String BASENAME = "effect-type";
    private final String code;

    private EffectType() {
        code = name().replaceAll("([A-Z])", "-$1").toLowerCase();
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
