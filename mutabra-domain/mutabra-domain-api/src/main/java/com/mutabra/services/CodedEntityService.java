package com.mutabra.services;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Deprecated
public interface CodedEntityService<E extends CodedEntity> extends BaseEntityService<E> {

    E get(String code);

    E create(String code);
}
