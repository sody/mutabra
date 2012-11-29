package com.mutabra.web.components.game;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.ClientElement;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SkipTurnDescription extends AbstractComponent implements ClientElement {

    public String getClientId() {
        return IdUtils.generateSkipDescriptionId();
    }
}
