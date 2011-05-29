package com.mutabra.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.CollectionUtils;
import org.greatage.util.LocaleUtils;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationServiceImpl
		extends BaseEntityServiceImpl<Translation, TranslationQuery>
		implements TranslationService {

	public TranslationServiceImpl(final EntityRepository repository) {
		super(repository, Translation.class, TranslationQuery.class);
	}

	public Map<String, String> getMessages(final Translatable translatable, final Locale locale) {
		final Map<String, String> messages = CollectionUtils.newMap();
		for (Locale candidateLocale : LocaleUtils.getCandidateLocales(locale)) {
			final Map<String, Translation> translations = getTranslations(translatable, candidateLocale);

			boolean allFound = true;
			for (String variant : translatable.getTranslationVariants()) {
				if (!messages.containsKey(variant)) {
					if (translations.containsKey(variant)) {
						messages.put(variant, translations.get(variant).getValue());
					} else {
						allFound = false;
					}
				}
			}
			if (allFound) {
				break;
			}
		}
		return messages;
	}

	public Map<String, Translation> getTranslations(final Translatable translatable, final Locale locale) {
		final List<Translation> translations = createQuery()
				.setType(translatable.getTranslationType())
				.addLocale(locale)
				.addCode(translatable.getTranslationCode())
				.list();

		final Map<String, Translation> mapped = CollectionUtils.newMap();
		for (Translation translation : translations) {
			mapped.put(translation.getVariant(), translation);
		}
		return mapped;
	}

	@Transactional
	public void saveTranslations(final Translatable translatable, final Map<String, Translation> translations) {
		for (Translation translation : translations.values()) {
			translation.setType(translatable.getTranslationType());
			translation.setCode(translatable.getTranslationCode());
			saveOrUpdate(translation);
		}
	}
}
