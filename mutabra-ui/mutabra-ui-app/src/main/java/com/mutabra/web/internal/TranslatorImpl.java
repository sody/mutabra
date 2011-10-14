package com.mutabra.web.internal;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import com.mutabra.services.TranslationService;
import com.mutabra.web.services.Translator;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.greatage.util.CompositeKey;
import org.greatage.util.LocaleUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslatorImpl implements Translator {
	private final ThreadLocale locale;
	private final TranslationService translationService;
	//todo: add settings to cache
	private final Map<CompositeKey, Map<String, String>> cache = new HashMap<CompositeKey, Map<String, String>>();

	public TranslatorImpl(final TranslationService translationService, final ThreadLocale locale) {
		this.translationService = translationService;
		this.locale = locale;
	}

	public Map<String, String> translate(final Translatable translatable) {
		return translate(translatable, locale.getLocale());
	}

	public Map<String, String> translate(final Translatable translatable, final Locale locale) {
		final Map<String, String> result = new HashMap<String, String>();
		for (Locale candidateLocale : LocaleUtils.getCandidateLocales(locale)) {
			final Map<String, String> messages = getFromCache(translatable, candidateLocale);
			if (merge(translatable, result, messages)) {
				return result;
			}
		}
		return result;
	}

	public void invalidateCache(final Translatable translatable) {
		//todo: work with this
		cache.clear();
	}

	public void updateCache(final Translatable translatable, final Locale locale, final Map<String, String> messages) {
		final CompositeKey key = createKey(translatable, locale);
		cache.put(key, messages);
	}

	private boolean merge(final Translatable translatable, final Map<String, String> dst, final Map<String, String> src) {
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

	private Map<String, String> getFromCache(final Translatable translatable, final Locale locale) {
		final CompositeKey key = createKey(translatable, locale);
		if (!cache.containsKey(key)) {
			final Map<String, String> messages = new HashMap<String, String>();
			final Map<String, Translation> translations = translationService.getTranslations(translatable, locale);
			for (Map.Entry<String, Translation> entry : translations.entrySet()) {
				messages.put(entry.getKey(), entry.getValue().getValue());
			}
			cache.put(key, messages);
		}
		return cache.get(key);
	}

	private CompositeKey createKey(final Translatable translatable, final Locale locale) {
		return new CompositeKey(translatable.getTranslationType(), translatable.getTranslationCode(), locale);
	}
}
