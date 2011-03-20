package com.noname.services;

import com.noname.domain.Translatable;
import com.noname.domain.Translation;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface TranslationService extends BaseEntityService<Translation> {

	Map<String, String> getMessages(Translatable translatable, Locale locale);

	Map<String, Translation> getTranslations(Translatable translatable, Locale locale);

	void saveTranslations(Translatable translatable, Map<String, Translation> translations);


}
