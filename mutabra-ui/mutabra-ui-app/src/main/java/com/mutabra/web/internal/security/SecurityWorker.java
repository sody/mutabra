package com.mutabra.web.internal.security;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityWorker extends SecurityHelper implements ComponentClassTransformWorker2 {
	public void transform(final PlasticClass plasticClass,
						  final TransformationSupport support,
						  final MutableComponentModel model) {

		for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(RequiresAuthentication.class)) {
			method.addAdvice(new MethodAdvice() {
				public void advise(final MethodInvocation invocation) {
					checkAuthenticated();
					invocation.proceed();
				}
			});
		}

		for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(RequiresUser.class)) {
			method.addAdvice(new MethodAdvice() {
				public void advise(final MethodInvocation invocation) {
					checkUser();
					invocation.proceed();
				}
			});
		}

		for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(RequiresGuest.class)) {
			method.addAdvice(new MethodAdvice() {
				public void advise(final MethodInvocation invocation) {
					checkGuest();
					invocation.proceed();
				}
			});
		}

		for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(RequiresRoles.class)) {
			final RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
			method.addAdvice(new MethodAdvice() {
				public void advise(final MethodInvocation invocation) {
					checkRoles(requiresRoles.logical(), requiresRoles.value());
					invocation.proceed();
				}
			});
		}

		for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(RequiresPermissions.class)) {
			final RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
			method.addAdvice(new MethodAdvice() {
				public void advise(final MethodInvocation invocation) {
					checkPermissions(requiresPermissions.logical(), requiresPermissions.value());
					invocation.proceed();
				}
			});
		}
	}
}
