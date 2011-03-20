package com.mutabra.web.components.common;

import com.mutabra.domain.common.Effect;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EffectEditor {

	@Parameter
	private Effect effect;

	@Parameter
	private String state;

	public Effect getEffect() {
		return effect;
	}

	public String getState() {
		return state;
	}

}
