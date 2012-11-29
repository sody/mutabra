package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Race;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.TranslationService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.RaceDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import com.mutabra.web.services.Translator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresAuthentication
@RequiresPermissions("race:view")
public class Races extends AbstractPage {

    @InjectService("raceService")
    private CodedEntityService<Race> raceService;

    @Inject
    private TranslationService translationService;

    @Inject
    private Translator translator;

    @InjectComponent
    private RaceDialog entityDialog;

    @Property
    private Race row;

    public GridDataSource getSource() {
        return new BaseEntityDataSource<Race>(raceService.query(), Race.class);
    }

    @OnEvent(value = "edit")
    Object editRace(final Race race) {
        return entityDialog.show(race);
    }

    @OnEvent(value = EventConstants.SUCCESS)
    @RequiresPermissions("race:edit")
    Object saveRace() {
        raceService.saveOrUpdate(entityDialog.getValue());
        translationService.saveTranslations(entityDialog.getTranslations());
        //todo: should be automatic
        translator.invalidateCache(entityDialog.getValue());
        return this;
    }
}
