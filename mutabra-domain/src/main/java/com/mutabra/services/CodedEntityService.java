package com.mutabra.services;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CodedEntityService<E extends CodedEntity> extends EntityService<E, String> {
}
