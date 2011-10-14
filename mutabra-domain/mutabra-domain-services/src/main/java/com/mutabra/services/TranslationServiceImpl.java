package com.mutabra.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationServiceImpl extends BaseEntityServiceImpl<Translation, TranslationQuery> implements TranslationService {

	public TranslationServiceImpl(final EntityRepository repository) {
		super(repository, Translation.class, TranslationQuery.class);
	}

	public List<Translation> getTranslations(final Translatable translatable, final Locale locale) {
		return translatable.getTranslationCode() == null ?
				Collections.<Translation>emptyList() :
				query()
						.setType(translatable.getTranslationType())
						.addLocale(locale)
						.addCode(translatable.getTranslationCode())
						.list();
	}

	@Transactional
	public void saveTranslations(final Collection<Translation> translations) {
		for (Translation translation : translations) {
			saveOrUpdate(translation);
		}
	}

	@Transactional
	public void deleteTranslations(final Translatable translatable) {
		if (translatable.getTranslationCode() != null) {
			final List<Translation> translations = query()
					.setType(translatable.getTranslationType())
					.addCode(translatable.getTranslationCode())
					.list();
			for (Translation translation : translations) {
				delete(translation);
			}
		}
	}
}
