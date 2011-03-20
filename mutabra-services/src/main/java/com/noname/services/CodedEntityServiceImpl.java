package com.noname.services;

import com.noname.domain.CodedEntity;
import com.noname.domain.Translation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.I18nUtils;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityServiceImpl<E extends CodedEntity, Q extends CodedEntityQuery<E, Q>>
		extends BaseEntityServiceImpl<E, Q>
		implements CodedEntityService<E> {
	private final TranslationService translationService;

	public CodedEntityServiceImpl(final EntityRepository repository,
								  final TranslationService translationService,
								  final Class<E> entityClass,
								  final Class<Q> queryClass) {
		super(repository, entityClass, queryClass);
		this.translationService = translationService;
	}

	@Override
	public E create() {
		final E entity = super.create();
		if (entity != null) {
			initializeTranslations(entity, I18nUtils.ROOT_LOCALE);
		}
		return entity;
	}

	public E get(final Long pk, final Locale locale) {
		final E entity = get(pk);
		if (entity != null) {
			initializeTranslations(entity, locale);
		}
		return entity;
	}

	public E get(final String code) {
		if (code == null) {
			return null;
		}
		return createQuery().withCode(code).unique();
	}

	public E get(final String code, final Locale locale) {
		final E entity = get(code);
		if (entity != null) {
			initializeTranslations(entity, locale);
		}
		return entity;
	}

	@Override
	@Transactional
	public void save(final E entity) {
		super.save(entity);
		saveTranslations(entity);
	}

	@Override
	@Transactional
	public void update(final E entity) {
		super.update(entity);
		saveTranslations(entity);
	}

	@Override
	@Transactional
	public void saveOrUpdate(final E entity) {
		super.saveOrUpdate(entity);
		saveTranslations(entity);
	}

	@Override
	@Transactional
	public void delete(final E entity) {
		super.delete(entity);
		deleteTranslations(entity);
	}

	protected void deleteTranslations(final E entity) {
		for (Translation translation : entity.getTranslations().values()) {
			translationService.delete(translation);
		}
	}


	protected void saveTranslations(final E entity) {
		translationService.saveTranslations(entity, entity.getTranslations());
	}

	protected void initializeTranslations(final E entity, final Locale locale) {
		final Map<String, Translation> translations = translationService.getTranslations(entity, locale);
		entity.setTranslations(translations);
	}

	protected TranslationService getTranslationService() {
		return translationService;
	}
}
