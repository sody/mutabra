package com.mutabra.domain.game;

import com.mutabra.db.Tables;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.TranslationType;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum Role implements Translatable {
	ADMIN(Permission.values()),
	USER(Permission.PLAY);

	private final String code;
	private final Collection<Permission> permissions;

	Role(final Permission... permissions) {
		this.permissions = Arrays.asList(permissions);
		this.code = name().toLowerCase();
	}

	public Collection<Permission> getPermissions() {
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
