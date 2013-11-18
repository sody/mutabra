/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.admin;

import com.mutabra.domain.common.Level;
import com.mutabra.web.base.components.EntityDialog;
import org.apache.tapestry5.annotations.OnEvent;

import static org.apache.tapestry5.EventConstants.PREPARE_FOR_SUBMIT;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelDialog extends EntityDialog<Level> {

    @OnEvent(PREPARE_FOR_SUBMIT)
    void prepare(final Level entity) {
        init(entity);
    }
}
