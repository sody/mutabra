package com.mutabra.web.components.battle;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SkipTurnDescription extends AbstractComponent implements ClientElement {
    public static final String ID = "d_skip_turn";

    public String getClientId() {
        return ID;
    }
}
