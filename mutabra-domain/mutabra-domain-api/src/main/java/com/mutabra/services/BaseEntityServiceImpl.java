package com.mutabra.services;

import com.mutabra.annotations.Transactional;
import com.mutabra.domain.BaseEntity;
import org.greatage.domain.Repository;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityServiceImpl<E extends BaseEntity>
		implements BaseEntityService<E> {

	private final Repository repository;
	private final Class<E> entityClass;
	private final String entityName;

	public BaseEntityServiceImpl(final Repository repository,
								 final Class<E> entityClass) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.entityName = entityClass.getSimpleName();
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public String getEntityName() {
		return entityName;
	}

	public E create() {
		return repository.create(getEntityClass());
	}

	@Transactional
	public void saveOrUpdate(final E entity) {
		if (entity.isNew()) {
			save(entity);
		} else {
			update(entity);
		}
	}

	@Transactional
	public void save(final E entity) {
		repository.save(entity);
	}

	@Transactional
	public void update(final E entity) {
		repository.update(entity);
	}

	@Transactional
	public void delete(final E entity) {
		repository.delete(entity);
	}

	public E get(final Long pk) {
		return repository.get(getEntityClass(), pk);
	}

	public Repository.Query<Long, E> query() {
		return repository.query(entityClass);
	}

	protected Repository repository() {
		return repository;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("name", entityName);
		return builder.toString();
	}
}
