package com.mutabra.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TranslationService extends BaseEntityService<Translation, TranslationQuery> {

	Map<String, Translation> getTranslations(final Translatable translatable, final Locale locale);

}
