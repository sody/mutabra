package com.noname.services;

import com.noname.domain.CodedEntity;
import ga.domain.i18n.TranslatableEntityServiceImpl;
import ga.domain.i18n.TranslationService;
import ga.domain.repository.EntityRepository;

/**
 * @author Ivan Khalopik
 */
public class CodedEntityServiceImpl<E extends CodedEntity, F extends CodedEntityQuery<E, F>>
		extends TranslatableEntityServiceImpl<E, F>
		implements CodedEntityService<E> {

	public CodedEntityServiceImpl(final EntityRepository repository,
								  final TranslationService translationService,
								  final Class<E> entityClass,
								  final Class<F> queryClass) {
		super(repository, translationService, entityClass, queryClass);
	}
}
