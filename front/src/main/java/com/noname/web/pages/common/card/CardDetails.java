package com.noname.web.pages.common.card;

import com.noname.domain.common.Card;
import com.noname.domain.common.CardType;
import com.noname.services.common.CardService;
import com.noname.services.security.AuthorityConstants;
import com.noname.web.base.pages.CodedEntityDetailsPage;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.greatage.security.annotations.Authority;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_ADMIN)
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