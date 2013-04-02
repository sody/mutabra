package com.mutabra.web.components.admin;

import com.mutabra.domain.common.Card;
import com.mutabra.web.base.components.EntityDialog;
import org.apache.tapestry5.annotations.OnEvent;

import static org.apache.tapestry5.EventConstants.PREPARE_FOR_SUBMIT;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardDialog extends EntityDialog<Card> {

    @OnEvent(PREPARE_FOR_SUBMIT)
    void prepare(final Card entity) {
        init(entity);
    }
}
