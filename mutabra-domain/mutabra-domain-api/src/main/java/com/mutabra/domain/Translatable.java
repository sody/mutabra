package com.mutabra.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Translatable extends Serializable {

    String getTranslationType();

    String getTranslationCode();

    Collection<String> getTranslationVariants();
}