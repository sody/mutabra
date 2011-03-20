package com.mutabra.web.pages.common.card;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import com.mutabra.services.common.CardService;
import com.mutabra.web.base.pages.CodedEntityDetailsPage;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.ROLE_ADMIN)
public class CardDetails extends CodedEntityDetailsPage<Card> {

	@Inject
	private CardService cardService;

	@InjectPage
	private CardList cardList;

	private SelectModel levelModel;

	@Property
	private CardType type;

	@Override
	protected Object getListPage() {
		return cardList;
	}

	@Override
	protected CardService getEntityService() {
		return cardService;
	}

	public boolean isTypeSelected() {
		return getRecord() != null;
	}

	@Override
	protected Card create() {
		return type == null ? null : cardService.create(type);
	}

	protected void onActivate(final String state, final Long recordId, final CardType type) {
		this.type = type;
		onActivate(state, recordId);
	}

	@Override
	protected List<?> onPassivate() {
		return type != null ? CollectionFactory.newList(getState(), getRecordId(), type) : super.onPassivate();
	}
}