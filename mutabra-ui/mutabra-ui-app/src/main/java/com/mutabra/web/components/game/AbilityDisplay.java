package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.TargetType;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbilityDisplay extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private BattleCreature creature;

	@Property
	@Parameter
	private Ability value;

	private String clientId;

	public String getClientId() {
		return IdUtils.generateId(value);
	}

	public String getContainerClass() {
		return creature.isExhausted() ?
				"card disabled" :
				"card";
	}

	public String getDescriptionSelector() {
		return "#" + IdUtils.generateDescriptionId(value);
	}

	public String getFieldSelector() {
		final TargetType targetType = value.getTargetType();

		final StringBuilder sideSelector = new StringBuilder("path");
		if (targetType.supportsEnemy() && !targetType.supportsFriend()) {
			sideSelector.append(".enemy");
		} else if (targetType.supportsFriend() && !targetType.supportsEnemy()) {
			sideSelector.append(".friend");
		}

		final StringBuilder selector = new StringBuilder();
		if (targetType.supportsEmpty()) {
			selector.append(sideSelector).append(".empty");
		}
		if (targetType.supportsHero()) {
			if (selector.length() > 0) {
				selector.append(",");
			}
			selector.append(sideSelector).append(".hero");
		}
		if (targetType.supportsCreature()) {
			if (selector.length() > 0) {
				selector.append(",");
			}
			selector.append(sideSelector).append(".creature");
		}
		if (selector.length() == 0) {
			selector.append(sideSelector);
		}

		return selector.toString();
	}

	public String getActionLink() {
		return getResources().createEventLink("registerCreatureAction", creature, value).toAbsoluteURI();
	}
}
