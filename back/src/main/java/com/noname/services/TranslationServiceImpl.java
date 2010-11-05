package com.noname.services;

import com.noname.domain.Translation;
import ga.domain.repository.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationServiceImpl extends ga.domain.i18n.TranslationServiceImpl {

	public TranslationServiceImpl(final EntityRepository repository) {
		super(repository);
	}

	@Override
	public Translation create() {
		return new Translation();
	}
}
