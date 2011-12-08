package com.mutabra.domain.security;

import com.mutabra.db.Tables;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.TranslationType;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum Role implements Translatable {
	ADMIN,
	USER;

	private final String code;

	Role() {
		this.code = name().toLowerCase();
	}

	public String getTranslationType() {
		return Tables.ROLE;
	}

	public String getTranslationCode() {
		return code;
	}

	public Collection<String> getTranslationVariants() {
		return TranslationType.DESCRIPTION.getVariants();
	}
}
