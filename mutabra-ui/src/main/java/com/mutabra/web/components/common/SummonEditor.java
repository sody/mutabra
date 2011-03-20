package com.mutabra.web.components.common;

import com.mutabra.domain.common.Summon;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SummonEditor {

	@Parameter
	private String state;

	@Parameter
	private Summon summon;

	public Summon getSummon() {
		return summon;
	}

	public String getState() {
		return state;
	}
}
