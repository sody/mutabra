package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.FACE)
public class FaceImpl extends CodedEntityImpl implements Face {

	public FaceImpl() {
		this(null);
	}

	public FaceImpl(final String code) {
		super(Tables.FACE, code, TranslationType.STANDARD);
	}
}
