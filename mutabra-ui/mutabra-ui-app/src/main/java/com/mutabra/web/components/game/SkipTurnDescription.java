package com.mutabra.web.components.game;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SkipTurnDescription extends AbstractComponent implements ClientElement {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String id;

    public String getClientId() {
        return id;
    }
}
