package com.mutabra.web.pages.common.card;

import com.mutabra.domain.common.Card;
import com.mutabra.services.common.CardService;
import com.mutabra.web.base.pages.EntityListPage;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.ROLE_ADMIN)
public class CardList extends EntityListPage<Card> {

	@Inject
	private CardService cardService;

	@InjectPage
	private CardDetails cardDetails;

	@Override
	protected Object getDetails(String state, Long recordId) {
		cardDetails.setState(state);
		cardDetails.setRecordId(recordId);
		return cardDetails;
	}

	@Override
	protected CardService getEntityService() {
		return cardService;
	}
}