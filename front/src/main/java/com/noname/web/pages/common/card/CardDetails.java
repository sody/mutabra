package com.noname.web.pages.common.card;

import com.noname.domain.common.*;
import com.noname.services.common.CardService;
import com.noname.services.common.LevelService;
import ga.tapestry.commonlib.base.pages.TranslatableEntityDetailsPage;
import ga.tapestry.internal.SelectModelBuilder;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class CardDetails extends TranslatableEntityDetailsPage<Card> {

	@Inject
	private LevelService levelService;

	@Inject
	private CardService cardService;

	@Inject
	private SelectModelBuilder selectModelBuilder;

	@InjectPage
	private CardList cardList;

	private SelectModel levelModel;

	@Property
	private CardType type;

	@Override
	protected Object getList() {
		return cardList;
	}

	@Override
	protected CardService getEntityService() {
		return cardService;
	}

	public SelectModel getLevelModel() {
		if (levelModel == null) {
			final List<Level> levels = levelService.getEntities();
			levelModel = selectModelBuilder.buildFormatted(Level.class, levels, "%s", "this:description");
		}
		return levelModel;
	}

	public boolean isTypeSelected() {
		return getRecord() != null;
	}

	public boolean isSummonCard() {
		return getRecord() instanceof SummonCard;
	}

	public boolean isEffectCard() {
		return getRecord() instanceof EffectCard;
	}

	public SummonCard getSummonCardRecord() {
		if (isSummonCard()) {
			return (SummonCard) getRecord();
		}
		return null;
	}

	public EffectCard getEffectCardRecord() {
		if (isEffectCard()) {
			return (EffectCard) getRecord();
		}
		return null;
	}

	@Override
	protected Card create() {
		return type == null ? null : cardService.create(type);
	}

	protected void onActivate(String state, Long recordId, CardType type) {
		this.type = type;
		onActivate(state, recordId);
	}

	@Override
	protected List<?> onPassivate() {
		return type != null ? CollectionFactory.newList(getState(), getRecordId(), type) : super.onPassivate();
	}
}