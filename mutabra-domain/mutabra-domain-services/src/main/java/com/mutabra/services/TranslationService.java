package com.mutabra.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import org.greatage.domain.annotations.Transactional;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TranslationService extends BaseEntityService<Translation, TranslationQuery> {

	Map<String, Translation> getTranslations(final Translatable translatable, final Locale locale);

	@Transactional
	void saveTranslations(final Collection<Translation> translations);

	@Transactional
	void deleteTranslations(final Translatable translatable);

}
