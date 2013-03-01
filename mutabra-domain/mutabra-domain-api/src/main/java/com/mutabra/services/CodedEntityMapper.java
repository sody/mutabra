package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.PropertyMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Deprecated
public class CodedEntityMapper<E extends BaseEntity> extends BaseEntityMapper<E> {
    public final PropertyMapper<Long, E, String> code$ = property("code");

    public CodedEntityMapper(final String path) {
        super(path);
    }
}
