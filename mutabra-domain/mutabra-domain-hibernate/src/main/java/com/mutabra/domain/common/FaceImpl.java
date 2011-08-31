package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "FACE")
public class FaceImpl extends CodedEntityImpl implements Face {

	public FaceImpl() {
		super("FACE", TranslationType.STANDARD);
	}
}
