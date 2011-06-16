package com.mutabra.web.services.i18n;

import com.mutabra.domain.Translatable;
import com.mutabra.services.TranslationService;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.greatage.inject.cache.Cache;
import org.greatage.inject.cache.CacheSource;
import org.greatage.util.CompositeKey;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslatorImpl implements Translator {
	private final TranslationService translationService;
	private final ThreadLocale locale;
	//todo: add settings to cache
	private final Cache<CompositeKey, Map<String, String>> cache;

	public TranslatorImpl(final TranslationService translationService,
						  final CacheSource cacheSource,
						  final ThreadLocale locale) {
		this.translationService = translationService;
		this.locale = locale;

		cache = cacheSource.getCache(Translator.class);
	}

	public Map<String, String> translate(final Translatable translatable) {
		return translate(translatable, locale.getLocale());
	}

	public Map<String, String> translate(final Translatable translatable, final Locale locale) {
		final CompositeKey key = createKey(translatable, locale);
		if (cache.contains(key)) {
			return cache.get(key);
		}
		final Map<String, String> messages = translationService.getMessages(translatable, locale);
		cache.put(key, messages);
		return messages;
	}

	public void invalidateCache(final Translatable translatable) {
		cache.clear(); //todo: something else should be here
	}

	private CompositeKey createKey(final Translatable translatable, final Locale locale) {
		return new CompositeKey(translatable.getTranslationType(), translatable.getTranslationCode(), locale);
	}
}
