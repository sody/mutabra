package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.LevelDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresAuthentication
@RequiresPermissions("level:view")
public class Levels extends AbstractPage {

    @InjectService("levelService")
    private CodedEntityService<Level> levelService;

    @InjectComponent
    private LevelDialog entityDialog;

    @Property
    private Level row;

    public GridDataSource getSource() {
        return new BaseEntityDataSource<Level>(levelService.query(), Level.class);
    }

    @OnEvent(value = "edit")
    Object editLevel(final Level level) {
        return entityDialog.show(level);
    }

    @OnEvent(value = EventConstants.SUCCESS)
    @RequiresPermissions("level:edit")
    Object saveLevel() {
        levelService.save(entityDialog.getValue());
        return this;
    }
}
