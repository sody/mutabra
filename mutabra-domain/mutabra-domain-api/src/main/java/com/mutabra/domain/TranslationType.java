package com.mutabra.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum TranslationType {
    NAME(Translations.NAME),
    DESCRIPTION(Translations.NAME, Translations.DESCRIPTION);

    private final List<String> variants;

    TranslationType(final String... variants) {
        this.variants = Arrays.asList(variants);
    }

    public List<String> getVariants() {
        return variants;
    }
}