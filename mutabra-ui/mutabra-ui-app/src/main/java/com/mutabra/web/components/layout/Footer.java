package com.mutabra.web.components.layout;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Footer {

	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String applicationVersion;

	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;
}
