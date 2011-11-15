package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.RACE)
public class RaceImpl extends CodedEntityImpl implements Race {

	public RaceImpl() {
		this(null);
	}

	public RaceImpl(final String code) {
		super(Tables.RACE, code, TranslationType.STANDARD);
	}
}
