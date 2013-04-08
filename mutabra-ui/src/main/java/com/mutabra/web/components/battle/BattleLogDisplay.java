package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleLogDisplay extends AbstractComponent implements ClientElement {

    @Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Parameter(required = true, allowNull = false)
    private List<BattleLogEntry> log;

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleHero hero;

    @Parameter
    private Integer size;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    @Property
    private BattleLogEntry logEntry;

    private String assignedClientId;

    @Environmental
    private ClientBehaviorSupport clientBehaviorSupport;

    @Inject
    private JavaScriptSupport jsSupport;

    public String getClientId() {
        return assignedClientId;
    }

    public String getContainerClass() {
        return size == null ? "log" : "log log-brief";
    }

    public List<BattleLogEntry> getLog() {
        final List<BattleLogEntry> chunk = size != null ?
                new ArrayList<BattleLogEntry>(size) :
                new ArrayList<BattleLogEntry>();

        final ListIterator<BattleLogEntry> iterator = log.listIterator(log.size());
        while (iterator.hasPrevious() && (size == null || chunk.size() < size)) {
            chunk.add(iterator.previous());
        }
        return chunk;
    }

    @SetupRender
    void setupId() {
        assignedClientId = jsSupport.allocateClientId(clientId);
    }

    @AfterRender
    void linkZone() {
        if (zone != null) {
            final Link link = getResources().createEventLink(EventConstants.ACTION);
            clientBehaviorSupport.linkZone(getClientId(), zone, link);
        }
    }
}
