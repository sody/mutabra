package com.mutabra.domain.security;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.PERMISSION)
public class PermissionImpl extends CodedEntityImpl implements Permission {

	public PermissionImpl() {
		this(null);
	}

	public PermissionImpl(final String code) {
		super(Tables.PERMISSION, code, TranslationType.STANDARD);
	}
}