package com.mutabra.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TranslationService extends BaseEntityService<Translation> {

	Map<String, String> getMessages(Translatable translatable, Locale locale);

	Map<String, Translation> getTranslations(Translatable translatable, Locale locale);

	void saveTranslations(Translatable translatable, Map<String, Translation> translations);
}
