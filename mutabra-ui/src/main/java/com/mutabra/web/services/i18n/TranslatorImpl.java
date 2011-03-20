package com.mutabra.web.services.i18n;

import com.mutabra.domain.Translatable;
import com.mutabra.services.TranslationService;
import org.greatage.ioc.cache.Cache;
import org.greatage.ioc.cache.CacheSource;
import org.greatage.util.CompositeKey;
import org.greatage.util.I18nUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslatorImpl implements Translator {
	private final TranslationService translationService;
	//todo: add settings to cache
	private final Cache<CompositeKey, Map<String, String>> cache;

	public TranslatorImpl(final TranslationService translationService, final CacheSource cacheSource) {
		this.translationService = translationService;
		cache = cacheSource.getCache(Translator.class);
	}

	public Map<String, String> translate(Translatable translatable) {
		return translate(translatable, I18nUtils.ROOT_LOCALE);
	}

	public Map<String, String> translate(Translatable translatable, Locale locale) {
		final Map<String, String> result = new HashMap<String, String>();
		for (Locale candidateLocale : I18nUtils.getCandidateLocales(locale)) {
			final Map<String, String> messages = getFromCache(translatable, candidateLocale);
			if (merge(translatable, result, messages)) {
				return result;
			}
		}
		return result;
	}

	public void updateCache(Translatable translatable, Locale locale, Map<String, String> messages) {
		final CompositeKey key = createKey(translatable, locale);
		cache.put(key, messages);
	}

	private boolean merge(Translatable translatable, Map<String, String> dst, Map<String, String> src) {
		boolean allFound = true;
		for (String variant : translatable.getTranslationVariants()) {
			if (!dst.containsKey(variant)) {
				if (src.containsKey(variant)) {
					dst.put(variant, src.get(variant));
				} else {
					allFound = false;
				}
			}
		}
		return allFound;
	}

	private Map<String, String> getFromCache(Translatable translatable, Locale locale) {
		final CompositeKey key = createKey(translatable, locale);
		if (!cache.contains(key)) {
			final Map<String, String> messages = translationService.getMessages(translatable, locale);
			cache.put(key, messages);
		}
		return cache.get(key);
	}

	private CompositeKey createKey(Translatable translatable, Locale locale) {
		return new CompositeKey(translatable.getTranslationType(), translatable.getTranslationCode(), locale);
	}
}
