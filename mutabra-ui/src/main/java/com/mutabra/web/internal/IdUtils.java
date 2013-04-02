package com.mutabra.web.internal;

import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class IdUtils {

    public static String generateSkipId() {
        return "card_skip";
    }

    public static String generateId(final Card card) {
        return "card_" + card.getCode();
    }

    public static String generateId(final Ability ability) {
        return "ability_" + ability.getCode();
    }

    public static String generateSkipDescriptionId() {
        return "description_skip";
    }

    public static String generateDescriptionId(final Card card) {
        return "description_" + card.getCode();
    }

    public static String generateDescriptionId(final Ability ability) {
        return "description_" + ability.getCode();
    }
}
