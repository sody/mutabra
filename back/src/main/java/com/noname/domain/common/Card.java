package com.noname.domain.common;

import com.noname.domain.CodedEntity;
import com.noname.domain.TranslationType;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author Ivan Khalopik
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorFormula(value = "CARD_TYPE")
@DiscriminatorValue(value = "0")
@Table(name = "CARD")
public class Card extends CodedEntity {

	@Type(type = "ga.hibernate.OrderedEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.noname.domain.common.CardType")})
	@Column(name = "CARD_TYPE", nullable = false)
	private CardType type;

	@ManyToOne
	@JoinColumn(name = "ID_LEVEL", nullable = false)
	private Level level;

	public Card() {
		this(CardType.UNKNOWN);
	}

	protected Card(final CardType type) {
		super("card", TranslationType.STANDARD);
		this.type = type;
	}

	public CardType getType() {
		return type;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}
}
