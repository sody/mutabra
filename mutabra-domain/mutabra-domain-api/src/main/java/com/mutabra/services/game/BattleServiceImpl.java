package com.mutabra.services.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityServiceImpl;
import org.greatage.domain.EntityRepository;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleServiceImpl extends BaseEntityServiceImpl<Battle> implements BattleService {

	private Class<? extends BattleMember> realMemberClass;

	public BattleServiceImpl(final EntityRepository repository) {
		super(repository, Battle.class);

		realMemberClass = repository.create(BattleMember.class).getClass();
	}

	public Battle createBattle(final Hero hero1, final Hero hero2) {
		final Battle battle = create();
		save(battle);
		final BattleMember member1 = creteBattleMember(battle, hero1);
		member1.setPosition(102);
		final BattleMember member2 = creteBattleMember(battle, hero2);
		member2.setPosition(100);
		repository().save(member1);
		repository().save(member2);

		hero1.setBattle(battle);
		hero2.setBattle(battle);
		repository().save(hero1);
		repository().save(hero2);

		return battle;
	}

	private BattleMember creteBattleMember(final Battle battle, final Hero hero) {
		return ReflectionUtils.newInstance(realMemberClass, battle, hero);
	}
}
