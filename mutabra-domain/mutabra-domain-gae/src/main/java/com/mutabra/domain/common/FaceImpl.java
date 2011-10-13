package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.db.Tables;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.FACE)
public class FaceImpl extends CodedEntityImpl implements Face {

	public FaceImpl() {
		super("FACE", TranslationType.STANDARD);
	}
}
