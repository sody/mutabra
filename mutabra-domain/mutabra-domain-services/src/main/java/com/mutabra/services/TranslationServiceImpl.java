package com.mutabra.services;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.mutabra.services.Mappers.translation$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationServiceImpl extends BaseEntityServiceImpl<Translation> implements TranslationService {

	public TranslationServiceImpl(final EntityRepository repository) {
		super(repository, Translation.class);
	}

	public List<Translation> getTranslations(final Translatable translatable, final Locale locale) {
		return translatable.getTranslationCode() == null ?
				Collections.<Translation>emptyList() :
				query(
						translation$.type.eq(translatable.getTranslationType()),
						translation$.locale.eq(locale.toString()),
						translation$.code.eq(translatable.getTranslationCode())
				).list();
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
			final List<Translation> translations = query(
					translation$.type.eq(translatable.getTranslationType()),
					translation$.code.eq(translatable.getTranslationCode())
			).list();

			for (Translation translation : translations) {
				delete(translation);
			}
		}
	}
}
