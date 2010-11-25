package com.noname.web.services;

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.*;
import org.greatage.security.SecurityChecker;
import org.greatage.security.SecurityException;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthorityComponentAnnotationWorker implements ComponentClassTransformWorker {

	private final SecurityChecker securityChecker;

	public AuthorityComponentAnnotationWorker(final SecurityChecker securityChecker) {
		this.securityChecker = securityChecker;
	}

	public void transform(final ClassTransformation transformation, final MutableComponentModel model) {
		final Authority authority = transformation.getAnnotation(Authority.class);
		if (authority != null) {
			transformation.getOrCreateMethod(TransformConstants.SETUP_RENDER_SIGNATURE).addAdvice(new ComponentMethodAdvice() {
				@Override
				public void advise(final ComponentMethodInvocation invocation) {
					try {
						securityChecker.check(null, authority.value());
						invocation.proceed();
					} catch (SecurityException e) {
						invocation.overrideResult(Boolean.FALSE);
					}
				}
			});
		}
	}
}
