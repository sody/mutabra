package com.mutabra.web.components.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractConditional;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PermissionSecurity extends AbstractConditional {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String value;

    @Override
    protected boolean test() {
        return getSubject() != null && getSubject().isPermitted(value);
    }

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
