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
@Table(name = "RACE")
public class RaceImpl extends CodedEntityImpl implements Race {

	public RaceImpl() {
		super("RACE", TranslationType.STANDARD);
	}
}
