/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Face;
import com.mutabra.services.CodedEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.FaceDialog;
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
@RequiresPermissions("face:view")
public class Faces extends AbstractPage {

    @InjectService("faceService")
    private CodedEntityService<Face> faceService;

    @InjectComponent
    private FaceDialog entityDialog;

    @Property
    private Face row;

    public GridDataSource getSource() {
        return new BaseEntityDataSource<Face>(faceService.query(), Face.class);
    }

    @OnEvent(value = "edit")
    Object editFace(final Face face) {
        return entityDialog.show(face);
    }

    @OnEvent(value = EventConstants.SUCCESS)
    @RequiresPermissions("face:edit")
    Object saveFace() {
        faceService.save(entityDialog.getValue());
        return this;
    }
}
