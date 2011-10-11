package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Tables;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.RACE)
public class RaceImpl extends CodedEntityImpl implements Race {

	public RaceImpl() {
		super("RACE", TranslationType.STANDARD);
	}
}
