package com.mutabra.web.components.game;

import com.mutabra.domain.common.Ability;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbilityDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private Ability value;

	public String getClientId() {
		return IdUtils.generateDescriptionId(value);
	}
}
