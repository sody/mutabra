/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.components.layout;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(stack = "mutabra",
		stylesheet = {
				"context:css/flag.css",
				"context:css/main-theme.css"
		})
@SupportsInformalParameters
public class DefaultLayout extends EmptyLayout {

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String[] buttons;

	@Property
	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Inject
	private AccountContext accountContext;

	public Account getAccount() {
		return accountContext.getAccount();
	}

	public Hero getHero() {
		return accountContext.getHero();
	}
}
