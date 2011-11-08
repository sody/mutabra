package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.PropertyMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TranslationMapper<E extends BaseEntity> extends BaseEntityMapper<E> {
	public final PropertyMapper<Long, E, String> type = property("type");
	public final PropertyMapper<Long, E, String> locale = property("locale");
	public final PropertyMapper<Long, E, String> code = property("code");
	public final PropertyMapper<Long, E, String> variant = property("variant");

	public TranslationMapper(final String path) {
		super(path);
	}
}
