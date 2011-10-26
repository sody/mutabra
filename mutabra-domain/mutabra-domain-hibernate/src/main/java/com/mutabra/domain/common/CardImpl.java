package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = Tables.CARD)
public class CardImpl extends CodedEntityImpl implements Card {

	@Type(type = "org.greatage.hibernate.type.OrderedEnumUserType",
			parameters = {@Parameter(name = "enumClass", value = "com.mutabra.domain.common.CardType")})
	@Column(name = "CARD_TYPE", nullable = false)
	private CardType type;

	@ManyToOne(targetEntity = LevelImpl.class)
	@JoinColumn(name = "ID_LEVEL", nullable = false)
	private Level level;

	@ManyToOne(targetEntity = EffectImpl.class)
	@JoinColumn(name = "ID_EFFECT", nullable = true)
	private Effect effect;

	@ManyToOne(targetEntity = SummonImpl.class)
	@JoinColumn(name = "ID_SUMMON", nullable = true)
	private Summon summon;

	public CardImpl() {
		this(CardType.UNKNOWN);
	}

	protected CardImpl(final CardType type) {
		super(Tables.CARD, TranslationType.STANDARD);
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
