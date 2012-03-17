package com.mutabra.web.components;

import com.mutabra.web.internal.Authorities;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.Authentication;
import org.greatage.security.SecurityContext;
import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Authority {
	private static final List<String> ANONYMOUS_AUTHORITIES = CollectionUtils.newList(Authorities.STATUS_ANONYMOUS);

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String value;

	@Inject
	private SecurityContext securityContext;

	@SetupRender
	boolean setupRender() {
		final List<String> authorities = getAuthorities();
		return !(value != null && !authorities.contains(value));
	}

	private List<String> getAuthorities() {
		final Authentication authentication = securityContext.getCurrentUser();
		return authentication != null ? authentication.getAuthorities() : ANONYMOUS_AUTHORITIES;
	}
}
