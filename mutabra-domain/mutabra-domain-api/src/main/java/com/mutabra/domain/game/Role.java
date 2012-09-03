package com.mutabra.domain.game;

import com.mutabra.db.Tables;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.TranslationType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum Role implements Translatable {
	ADMIN("*"),
	USER("game:play");

	private final String code;
	private final Set<String> permissions;

	Role(final String... permissions) {
		this.permissions = new HashSet<String>(Arrays.asList(permissions));
		this.code = name().toLowerCase();
	}

	public Set<String> getPermissions() {
		return permissions;
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
