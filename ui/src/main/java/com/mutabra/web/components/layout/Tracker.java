package com.mutabra.web.components.layout;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 */
public class Tracker {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String account;

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String domain;

    public String getAccount() {
        return account;
    }

    public String getDomain() {
        return domain;
    }
}
