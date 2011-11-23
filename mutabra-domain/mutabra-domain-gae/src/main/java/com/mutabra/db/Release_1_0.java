package com.mutabra.db;

import com.mutabra.domain.common.CardType;
import com.mutabra.domain.common.TargetType;
import org.greatage.db.ChangeLog;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Release_1_0 extends ChangeLog {

	@Override
	protected void init() {
		location("mutabra/release_1.0");

		author("sody");
		begin("2011-10-11/roles").comment("roles data");
		insert(Tables.ROLE).set("code", "admin");
		insert(Tables.ROLE).set("code", "user");

		begin("2011-10-11/levels").comment("levels data");
		insert(Tables.LEVEL).set("code", "newbie").set("rating", 10l);

		begin("2011-10-11/races").comment("races data");
		insert(Tables.RACE).set("code", "elf");
		insert(Tables.RACE).set("code", "orc");

		begin("2011-10-14/translations").comment("translations added");
		insert(Tables.TRANSLATION)
				.set("type", Tables.ROLE)
				.set("code", "admin")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Administrator");
		insert(Tables.TRANSLATION)
				.set("type", Tables.ROLE)
				.set("code", "user")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "User");
		insert(Tables.TRANSLATION)
				.set("type", Tables.LEVEL)
				.set("code", "newbie")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Newbie");
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "orc")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Orc");
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "elf")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Elf");

		begin("2011-10-17/admin_account").comment("admin account added");
		insert(Tables.ACCOUNT)
				.set("email", "admin@mutabra.com")
				.set("password", "21232f297a57a5a743894a0e4a801fc3");

		begin("2011-11-02/admin_account_role").comment("admin account role added");
		update(Tables.ACCOUNT)
				.where(condition("email").equal("admin@mutabra.com"))
				.set("roles",
						select(Tables.ROLE).where(condition("code").in("admin", "user"))
				);

		insert(Tables.FACE)
				.set("code", "f1");
		insert(Tables.FACE)
				.set("code", "f2");

		begin("2011-11-17/translations").comment("description translations renamed to name");
		update(Tables.TRANSLATION)
				.set("variant", "name")
				.where(condition("variant").equal("description"));

		begin("2011-11-17/flyer_race").comment("formed flyer race");
		insert(Tables.RACE).set("code", "flyer");
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "flyer")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Flyer");
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "flyer")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Flyers is a race that comes from the world of one mountain...");

		begin("2011-11-17/plunger_race").comment("formed plunger race");
		insert(Tables.RACE).set("code", "plunger");
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "plunger")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Plunger");
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "plunger")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Plunger is a race that comes from the sunny world where there is nothing except water...");

		begin("2011-11-23/races").comment("orc and elf races removed");
		delete(Tables.TRANSLATION).where(condition("type").equal(Tables.RACE).and(condition("code").in("orc", "elf")));
		delete(Tables.RACE).where(condition("code").in("orc", "elf"));

		begin("2011-11-23/cleanup").comment("removed all old data");
		delete(Tables.HERO_CARD);
		delete(Tables.HERO);
		delete(Tables.CARD);

		begin("2011-11-23/plunger_cards").comment("plunger cards added");
		insert(Tables.CARD)
				.set("type", CardType.SUMMON.name())
				.set("code", "electric-ray")
				.set("bloodCost", 2)
				.set("summon.strength", 3)
				.set("summon.health", 5)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.SUMMON.name())
				.set("code", "seahorse")
				.set("bloodCost", 1)
				.set("summon.strength", 1)
				.set("summon.health", 3)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "calm")
				.set("bloodCost", 1)
				.set("effect.targetType", TargetType.ALL_FRIEND.name())
				.set("effect.strength", 1)
				.set("effect.duration", 3)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "wave")
				.set("bloodCost", 2)
				.set("effect.targetType", TargetType.SINGLE_ENEMY.name())
				.set("effect.strength", 4)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "whirlpool")
				.set("bloodCost", 1)
				.set("effect.targetType", TargetType.SINGLE_ENEMY.name())
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "trident-blow")
				.set("bloodCost", 2)
				.set("effect.targetType", TargetType.SINGLE_ENEMY.name())
				.set("effect.strength", 6)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		begin("2011-11-23/plunger_cards_translation").comment("plunger cards translations added");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "electric-ray")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Electric Ray");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "seahorse")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Seahorse");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "calm")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Calm");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "wave")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Wave");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "whirlpool")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Whirlpool");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "trident-blow")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Trident Blow");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "electric-ray")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Электрический Скат");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "seahorse")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Морской Конек");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "calm")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Штиль");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "wave")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Волна");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "whirlpool")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Водоворот");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "trident-blow")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Удар трезубцем");

		begin("2011-11-23/flyer_cards").comment("flyer cards added");
		insert(Tables.CARD)
				.set("type", CardType.SUMMON.name())
				.set("code", "chamois")
				.set("bloodCost", 2)
				.set("summon.strength", 2)
				.set("summon.health", 6)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.SUMMON.name())
				.set("code", "carrion-vulture")
				.set("bloodCost", 2)
				.set("summon.strength", 2)
				.set("summon.health", 5)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "scramble")
				.set("bloodCost", 1)
				.set("effect.targetType", TargetType.PLAYER.name())
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "scratch")
				.set("bloodCost", 1)
				.set("effect.targetType", TargetType.PLAYER_ENEMY.name())
				.set("effect.strength", 5)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "snowball")
				.set("bloodCost", 1)
				.set("effect.targetType", TargetType.ALL_ENEMY.name())
				.set("effect.strength", 2)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "throw")
				.set("bloodCost", 1)
				.set("effect.targetType", TargetType.SINGLE_ENEMY.name())
				.set("effect.strength", 3)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		begin("2011-11-23/flyer_cards_translation").comment("flyer cards translations added");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "chamois")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Chamois");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "carrion-vulture")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Carrion Vulture");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "scramble")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Scramble");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "scratch")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Scratch");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "snowball")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Snowball");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "throw")
				.set("variant", "name")
				.set("locale", "")
				.set("value", "Throw");

		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "chamois")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Серна");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "carrion-vulture")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Гриф Падальщик");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "scramble")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Вскарабкаться");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "scratch")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Поцарапать");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "snowball")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Снежный Ком");
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.set("code", "throw")
				.set("variant", "name")
				.set("locale", "ru")
				.set("value", "Сбросить");
	}
}
