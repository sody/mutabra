package com.noname.web.components.common;

import com.noname.domain.common.*;
import com.noname.services.common.LevelService;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.tapestry.internal.SelectModelBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardEditor {

	@Parameter
	private Card card;

	@Parameter
	private String state;

	@Inject
	private LevelService levelService;

	@Inject
	private SelectModelBuilder selectModelBuilder;

	private SelectModel levelModel;

	public SelectModel getLevelModel() {
		if (levelModel == null) {
			final List<Level> levels = levelService.getEntities();
			levelModel = selectModelBuilder.buildFormatted(Level.class, levels, "%s", "this:description");
		}
		return levelModel;
	}

	public String getState() {
		return state;
	}

	public Card getCard() {
		return card;
	}

	public boolean isEffectCard() {
		return card instanceof EffectCard;
	}

	public boolean isSummonCard() {
		return card instanceof SummonCard;
	}

	public Effect getEffect() {
		return ((EffectCard) card).getEffect();
	}

	public Summon getSummon() {
		return ((SummonCard) card).getSummon();
	}

}
