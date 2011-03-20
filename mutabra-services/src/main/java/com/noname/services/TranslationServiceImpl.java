package com.noname.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;

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
		final List<Translation> translations = getEntities(translatable, locale);
		final Map<String, String> result = new HashMap<String, String>();
		for (Translation translation : translations) {
			result.put(translation.getVariant(), translation.getValue());
		}
		return result;
	}

	public Map<String, Translation> getTranslations(final Translatable translatable, final Locale locale) {
		final List<Translation> translations = getEntities(translatable, locale);
		final Map<String, Translation> mapped = new HashMap<String, Translation>();
		for (Translation translation : translations) {
			mapped.put(translation.getVariant(), translation);
		}

		final Map<String, Translation> result = new HashMap<String, Translation>();
		for (String variant : translatable.getTranslationVariants()) {
			if (mapped.containsKey(variant)) {
				result.put(variant, mapped.get(variant));
			} else {
				final Translation translation = create();
				translation.setLocale(locale);
				translation.setVariant(variant);
				result.put(variant, translation);
			}
		}
		return result;
	}

	@Transactional
	public void saveTranslations(final Translatable translatable, final Map<String, Translation> translations) {
		for (Translation translation : translations.values()) {
			translation.setType(translatable.getTranslationType());
			translation.setCode(translatable.getTranslationCode());
			saveOrUpdate(translation);
		}
	}

	private List<Translation> getEntities(final Translatable translatable, final Locale locale) {
		if (translatable.getTranslationCode() == null) {
			return Collections.emptyList();
		}
		return createQuery()
				.setType(translatable.getTranslationType())
				.setLocale(locale)
				.addCode(translatable.getTranslationCode())
				.list();
	}
}
