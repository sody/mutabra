package com.mutabra.services;

import com.mutabra.annotations.Transactional;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TranslationService extends BaseEntityService<Translation> {

    List<Translation> getTranslations(final Translatable translatable, final Locale locale);

    @Transactional
    void saveTranslations(final Collection<Translation> translations);

    @Transactional
    void deleteTranslations(final Translatable translatable);

}
