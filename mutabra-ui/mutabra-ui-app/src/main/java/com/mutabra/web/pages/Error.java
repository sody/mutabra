package com.mutabra.web.pages;

import com.mutabra.web.base.pages.AbstractPage;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Error extends AbstractPage {

	@Property
	@Inject
	private Request request;

	@Property
	@Inject
	@Symbol(SymbolConstants.PRODUCTION_MODE)
	private boolean productionMode;
}
