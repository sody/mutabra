package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable
public class CardImpl extends CodedEntityImpl implements Card {

	@Persistent
	private CardType type;

	@Persistent
	private Level level;

	@Persistent
	private Effect effect;

	@Persistent
	private Summon summon;

	public CardImpl() {
		this(CardType.UNKNOWN);
	}

	protected CardImpl(final CardType type) {
		super("CARD", TranslationType.STANDARD);
		this.type = type;
	}

	public CardType getType() {
		return type;
	}

	public void setType(final CardType type) {
		this.type = type;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(final Effect effect) {
		this.effect = effect;
	}

	public Summon getSummon() {
		return summon;
	}

	public void setSummon(final Summon summon) {
		this.summon = summon;
	}
}
