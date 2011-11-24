package com.mutabra.services.game;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroCard;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroServiceImpl extends BaseEntityServiceImpl<Hero> implements HeroService {
	private final Class<? extends Hero> realEntityClass;
	private final Class<? extends HeroCard> realHeroCardClass;

	private final CodedEntityService<Level> levelService;
	private final CodedEntityService<Card> cardService;

	public HeroServiceImpl(final EntityRepository repository,
						   final CodedEntityService<Level> levelService,
						   final CodedEntityService<Card> cardService) {
		super(repository, Hero.class);
		this.levelService = levelService;
		this.cardService = cardService;

		realEntityClass = repository.create(Hero.class).getClass();
		realHeroCardClass = repository.create(HeroCard.class).getClass();
	}

	public Hero create(final Account account) {
		final Hero hero = ReflectionUtils.newInstance(realEntityClass, account);
		hero.setName(account.getName());
		hero.setLevel(levelService.get("newbie"));
		hero.setHealth(15);
		return hero;
	}

	@Transactional
	@Override
	public void save(final Hero entity) {
		super.save(entity);

		if ("plunger".equals(entity.getRace().getCode())) {
			addCard(entity, "electric-ray");
			addCard(entity, "seahorse");
			addCard(entity, "mermaid");
			addCard(entity, "calm");
			addCard(entity, "wave");
			addCard(entity, "whirlpool");
			addCard(entity, "trident-blow");
			addCard(entity, "swim-away");
			addCard(entity, "storm");
			addCard(entity, "drop-of-the-ocean");
		}

		if ("flyer".equals(entity.getRace().getCode())) {
			addCard(entity, "chamois");
			addCard(entity, "carrion-vulture");
			addCard(entity, "chives");
			addCard(entity, "scramble");
			addCard(entity, "scratch");
			addCard(entity, "snowball");
			addCard(entity, "throw");
			addCard(entity, "falling-boulder");
			addCard(entity, "echo-mountain");
			addCard(entity, "decompression");
		}
	}

	private void addCard(final Hero entity, final String cardCode) {
		final HeroCard card = ReflectionUtils.newInstance(realHeroCardClass, entity);
		card.setCard(cardService.get(cardCode));
		repository().save(card);
	}
}
