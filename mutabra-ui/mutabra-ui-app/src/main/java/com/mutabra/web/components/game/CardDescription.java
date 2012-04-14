package com.mutabra.web.components.game;

import com.mutabra.domain.common.Card;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private Card value;

	public String getClientId() {
		return IdUtils.generateDescriptionId(value);
	}
}
