package com.mutabra.web.internal;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.ComponentEvent;
import org.apache.tapestry5.services.ComponentEventHandler;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.greatage.security.AccessDeniedException;
import org.greatage.security.Authentication;
import org.greatage.security.AuthorityConstants;
import org.greatage.security.SecurityContext;
import org.greatage.security.annotations.Allow;
import org.greatage.security.annotations.Deny;
import org.greatage.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityAnnotationWorker implements ComponentClassTransformWorker2 {
	private static final List<String> ANONYMOUS_AUTHORITIES = CollectionUtils.newList(AuthorityConstants.STATUS_ANONYMOUS);

	private final SecurityContext securityContext;

	public SecurityAnnotationWorker(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public void transform(final PlasticClass plasticClass, final TransformationSupport support, final MutableComponentModel model) {
		final Allow allow = plasticClass.getAnnotation(Allow.class);
		final Deny deny = plasticClass.getAnnotation(Deny.class);
		if (model.isPage() && (allow != null || deny != null)) {
			support.addEventHandler(EventConstants.ACTIVATE, 0, "Page Security", new ComponentEventHandler() {
				public void handleEvent(final Component instance, final ComponentEvent event) {
					final Authentication authentication = securityContext.getCurrentUser();
					final List<String> authorities = authentication != null ? authentication.getAuthorities() : ANONYMOUS_AUTHORITIES;

					if (allow != null) {
						if (!authorities.containsAll(Arrays.asList(allow.value()))) {
							throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
						}
					}
					if (deny != null) {
						for (String authority : deny.value()) {
							if (authorities.contains(authority)) {
								throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
							}
						}
					}
				}
			});
		}
	}
}
