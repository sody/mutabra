package com.mutabra.db;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.CodedEntity;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.Role;
import org.greatage.db.ChangeSet;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RepositoryData implements ChangeSet {

	private final EntityRepository repository;

	public RepositoryData(final EntityRepository repository) {
		this.repository = repository;
	}

	public void execute() {
		role(1l, "admin");
		role(2l, "user");
	}

	protected void role(final long pk, final String code) {
		codedEntity(Role.class, pk, code);
	}

	protected void permission(final long pk, final String code) {
		codedEntity(Permission.class, pk, code);
	}

	protected <E extends CodedEntity> void codedEntity(final Class<E> entityClass, final long pk, final String code) {
		final E entity = getCodedEntity(entityClass, pk, code);
		save(entity);
	}

	protected <E extends CodedEntity> E getCodedEntity(final Class<E> entityClass, final long pk, final String code) {
		final E entity = getEntity(entityClass, pk);
		entity.setCode(code);
		return entity;
	}

	protected <E extends BaseEntity> E getEntity(final Class<E> entityClass, final long pk) {
		final E entity = repository.get(entityClass, pk);
		return entity != null ? entity : repository.create(entityClass);
	}

	protected <E extends BaseEntity> void save(final E entity) {
		repository.saveOrUpdate(entity);
	}
}
