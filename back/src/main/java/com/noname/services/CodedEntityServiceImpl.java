package com.noname.services;

import com.noname.domain.CodedEntity;
import com.noname.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class CodedEntityServiceImpl<E extends CodedEntity, Q extends CodedEntityQuery<E, Q>>
		extends BaseEntityServiceImpl<E, Q>
		implements CodedEntityService<E> {
	private final TranslationService translationService;

	public CodedEntityServiceImpl(EntityRepository repository, TranslationService translationService, Class<E> entityClass, Class<Q> queryClass) {
		super(repository, entityClass, queryClass);
		this.translationService = translationService;
	}

	@Override
	public E create() {
		final E entity = super.create();
		if (entity != null) {
			final Map<String, Translation> translations = translationService.getTranslations(entity, Locale.ROOT);
			entity.setTranslations(translations);
		}
		return entity;
	}

	@Override
	public E get(Long pk) {
		return get(pk, null);
	}

	public E get(Long pk, Locale locale) {
		final E entity = super.get(pk);
		if (entity != null) {
			final Map<String, Translation> translations = translationService.getTranslations(entity, locale);
			entity.setTranslations(translations);
		}
		return entity;
	}

	@Override
	@Transactional
	public void save(E entity) {
		super.save(entity);
		translationService.saveTranslations(entity, entity.getTranslations());
	}

	@Override
	@Transactional
	public void update(E entity) {
		super.update(entity);
		translationService.saveTranslations(entity, entity.getTranslations());
	}

	@Override
	@Transactional
	public void saveOrUpdate(E entity) {
		super.saveOrUpdate(entity);
		translationService.saveTranslations(entity, entity.getTranslations());
	}

}
