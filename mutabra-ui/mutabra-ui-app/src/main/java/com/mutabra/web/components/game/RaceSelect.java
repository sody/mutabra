package com.mutabra.web.components.game;

import com.mutabra.domain.common.Race;
import com.mutabra.services.CodedEntityService;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceSelect {

    @Property
    @Parameter
    private Race value;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String callback;

    @InjectService("raceService")
    private CodedEntityService<Race> raceService;

    @Property
    private Race row;

    @Cached
    public List<Race> getSource() {
        return raceService.query().list();
    }
}
