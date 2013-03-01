package com.mutabra.services.common;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.BaseEntityMapper;
import org.greatage.domain.PropertyMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardMapper<E extends BaseEntity> extends BaseEntityMapper<E> {
    public final PropertyMapper<Long, E, String> code$ = property("code");

    public CardMapper(final String path) {
        super(path);
    }
}
