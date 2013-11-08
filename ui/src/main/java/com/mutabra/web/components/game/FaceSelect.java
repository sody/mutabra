/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.game;

import com.mutabra.domain.common.Face;
import com.mutabra.services.CodedEntityService;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceSelect {

    @Property
    @Parameter
    private Face value;

    @InjectService("faceService")
    private CodedEntityService<Face> faceService;

    @Property
    private Face row;

    @Cached
    public List<Face> getSource() {
        return faceService.query().asList();
    }
}
