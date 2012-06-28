package com.mutabra.domain;

import com.googlecode.objectify.Key;
import org.greatage.domain.internal.AbstractEntity;

import javax.persistence.Id;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityImpl extends AbstractEntity<Long> implements BaseEntity {

	@Id
	private Long id;

	public Long getId() {
		return id;
	}

	public <T> Key<T> getKey() {
		//noinspection unchecked
		return getParentKey() != null ? new Key(getParentKey(), getClass(), getId()) : new Key(getClass(), getId());
	}

	public Key<?> getParentKey() {
		return null;
	}

	@SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
	@Override
	public boolean equals(final Object obj) {
		final boolean result = super.equals(obj);
		if (!result) {
			return false;
		}

		final Key<?> parent = getParentKey();
		final Key<?> objParent = ((BaseEntityImpl) obj).getParentKey();
		return parent == null && objParent == null || parent != null && parent.equals(objParent);
	}
}
