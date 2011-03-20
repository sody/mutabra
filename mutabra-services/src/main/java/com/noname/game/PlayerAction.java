package com.noname.game;

import com.noname.domain.player.HeroCard;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PlayerAction {
	private HeroCard card;
	private String location;

	public PlayerAction(final HeroCard card, final String location) {
		this.card = card;
		this.location = location;
	}

	public HeroCard getCard() {
		return card;
	}

	public String getLocation() {
		return location;
	}

	public void execute() {

	}
}
