package com.mutabra.web.components.battle;

import com.mutabra.services.battle.BattleField;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleLogDialog extends AbstractComponent implements ClientElement {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL, name = "id")
    private String clientId;

    @Property
    private BattleField battleField;

    @Inject
    private Block modalBlock;

    public String getClientId() {
        return clientId;
    }

    public String getModalId() {
        return clientId + "_modal";
    }

    public String getTitle() {
        return message("title");
    }

    public Object show(final BattleField value) {
        battleField = value;
        return modalBlock;
    }
}
