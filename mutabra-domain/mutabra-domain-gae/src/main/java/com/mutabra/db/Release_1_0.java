package com.mutabra.db;

import com.mutabra.domain.Translations;
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.Cards;
import com.mutabra.domain.common.Levels;
import com.mutabra.domain.common.Races;
import com.mutabra.domain.common.TargetType;
import com.mutabra.domain.security.Roles;
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
		insert(Tables.ROLE)
				.into("code")
				.values(Roles.ADMIN)
				.values(Roles.USER);
		insert(Tables.TRANSLATION)
				.set("type", Tables.ROLE)
				.into("code", "locale", "variant", "value")
				.values(Roles.ADMIN, Translations.DEFAULT, Translations.NAME, "Administrator")
				.values(Roles.ADMIN, Translations.RUSSIAN, Translations.NAME, "Администратор")
				.values(Roles.USER, Translations.DEFAULT, Translations.NAME, "User")
				.values(Roles.USER, Translations.RUSSIAN, Translations.NAME, "Пользователь");

		begin("2011-10-11/admin_account").comment("admin account added");
		insert(Tables.ACCOUNT)
				.set("roles", select(Tables.ROLE).where(condition("code").in(Roles.ADMIN, Roles.USER)))
				.into("email", "password", "name")
				.values("admin@mutabra.com", "21232f297a57a5a743894a0e4a801fc3", "admin");

		begin("2011-10-11/test_accounts").comment("test accounts added");
		insert(Tables.ACCOUNT)
				.set("roles", select(Tables.ROLE).where(condition("code").equal(Roles.USER)))
				.into("email", "password", "name")
				.values("user1@mutabra.com", "21232f297a57a5a743894a0e4a801fc3", "sody")
				.values("user2@mutabra.com", "21232f297a57a5a743894a0e4a801fc3", "hermes")
				.values("user3@mutabra.com", "21232f297a57a5a743894a0e4a801fc3", "user")
				.values("user4@mutabra.com", "21232f297a57a5a743894a0e4a801fc3", "cheater");

		begin("2011-10-11/levels").comment("levels data");
		insert(Tables.LEVEL)
				.into("code", "rating")
				.values(Levels.NEWBIE, 10l);
		insert(Tables.TRANSLATION)
				.set("type", Tables.LEVEL)
				.into("code", "locale", "variant", "value")
				.values(Levels.NEWBIE, Translations.DEFAULT, Translations.NAME, "Newbie")
				.values(Levels.NEWBIE, Translations.RUSSIAN, Translations.NAME, "Нубик");

		begin("2011-10-11/faces").comment("faces data");
		insert(Tables.FACE)
				.into("code")
				.values("f1")
				.values("f2");

		begin("2011-10-11/races").comment("races data");
		insert(Tables.RACE)
				.into("code")
				.values(Races.PLUNGER)
				.values(Races.FLYER);
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.into("code", "locale", "variant", "value")
				.values(Races.PLUNGER, Translations.DEFAULT, Translations.NAME, "Plunger")
				.values(Races.PLUNGER, Translations.RUSSIAN, Translations.NAME, "Ныряльщик")
				.values(Races.PLUNGER, Translations.DEFAULT, Translations.DESCRIPTION, "Plungers is a race that comes from the sunny world where there is nothing except water...")
				.values(Races.PLUNGER, Translations.RUSSIAN, Translations.DESCRIPTION, "Ныряльщики это гордая раса, происходящая из солнечного мира, где нет ничего, кроме Великого Океана...")
				.values(Races.FLYER, Translations.DEFAULT, Translations.NAME, "Flyer")
				.values(Races.FLYER, Translations.RUSSIAN, Translations.NAME, "Летун")
				.values(Races.FLYER, Translations.DEFAULT, Translations.DESCRIPTION, "Flyers is a race that comes from the world of one mountain...")
				.values(Races.FLYER, Translations.RUSSIAN, Translations.DESCRIPTION, "Летуны это создания живущие в мире одной горы, которую величают Мать-Гора...");

		begin("2011-11-23/plunger_and_flyer_cards").comment("plunger and flyer cards added");
		insert(Tables.CARD)
				.set("effectType", EffectType.SUMMON.name())
				.set("level", select(Tables.LEVEL).unique().where(condition("code").equal(Levels.NEWBIE)))
				.into("code", "targetType", "bloodCost", "strength", "health")
				.values(Cards.ELECTRIC_RAY, TargetType.SINGLE_FRIEND_EMPTY.name(), 2, 3, 5)
				.values(Cards.SEAHORSE, TargetType.SINGLE_FRIEND_EMPTY.name(), 2, 1, 3)
				.values(Cards.MERMAID, TargetType.SINGLE_FRIEND_EMPTY.name(), 2, 0, 4)
				.values(Cards.CHAMOIS, TargetType.SINGLE_FRIEND_EMPTY.name(), 2, 2, 6)
				.values(Cards.CARRION_VULTURE, TargetType.SINGLE_FRIEND_EMPTY.name(), 2, 2, 5)
				.values(Cards.CHIVES, TargetType.SINGLE_FRIEND_EMPTY.name(), 2, 0, 1);
		insert(Tables.CARD)
				.set("effectType", EffectType.EFFECT.name())
				.set("level", select(Tables.LEVEL).unique().where(condition("code").equal(Levels.NEWBIE)))
				.into("code", "targetType", "bloodCost", "strength", "health")
				.values(Cards.CALM, TargetType.ALL_FRIEND_BUSY.name(), 1, 1, 3)
				.values(Cards.WAVE, TargetType.SINGLE_ENEMY_BUSY.name(), 2, 4, 1)
				.values(Cards.WHIRLPOOL,TargetType.SINGLE_ENEMY_EMPTY.name(), 1, 0, 1)
				.values(Cards.TRIDENT_BLOW, TargetType.SINGLE_ENEMY_BUSY.name(), 2, 6, 1)
				.values(Cards.SWIM_AWAY, TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 0, 1)
				.values(Cards.STORM, TargetType.ALL_FRIEND_BUSY.name(), 2, 0, 1)
				.values(Cards.DROP_OF_THE_OCEAN, TargetType.SINGLE_ENEMY_BUSY.name(), 2, 0, 2)
				.values(Cards.SCRAMBLE, TargetType.SINGLE_FRIEND_HERO.name(), 1, 0, 1)
				.values(Cards.SCRATCH, TargetType.SINGLE_ENEMY_HERO.name(), 1, 5, 1)
				.values(Cards.SNOWBALL, TargetType.ALL_ENEMY_BUSY.name(), 1, 2, 1)
				.values(Cards.THROW, TargetType.SINGLE_ENEMY_BUSY.name(), 1, 3, 1)
				.values(Cards.FALLING_BOULDER, TargetType.SINGLE_ENEMY_BUSY.name(), 1, 7, 1)
				.values(Cards.ECHO_MOUNTAIN, TargetType.ALL_BUSY.name(), 2, 4, 1)
				.values(Cards.DECOMPRESSION, TargetType.ALL_BUSY.name(), 1, 1, 2);
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.into("code", "locale", "variant", "value")
				.values(Cards.ELECTRIC_RAY, Translations.DEFAULT, Translations.NAME, "Electric Ray")
				.values(Cards.ELECTRIC_RAY, Translations.RUSSIAN, Translations.NAME, "Электрический Скат")
				.values(Cards.SEAHORSE, Translations.DEFAULT, Translations.NAME, "Seahorse")
				.values(Cards.SEAHORSE, Translations.RUSSIAN, Translations.NAME, "Морской Конек")
				.values(Cards.MERMAID, Translations.DEFAULT, Translations.NAME, "Mermaid")
				.values(Cards.MERMAID, Translations.RUSSIAN, Translations.NAME, "Русалка")
				.values(Cards.CALM, Translations.DEFAULT, Translations.NAME, "Calm")
				.values(Cards.CALM, Translations.RUSSIAN, Translations.NAME, "Штиль")
				.values(Cards.WAVE, Translations.DEFAULT, Translations.NAME, "Wave")
				.values(Cards.WAVE, Translations.RUSSIAN, Translations.NAME, "Волна")
				.values(Cards.WHIRLPOOL, Translations.DEFAULT, Translations.NAME, "Whirlpool")
				.values(Cards.WHIRLPOOL, Translations.RUSSIAN, Translations.NAME, "Водоворот")
				.values(Cards.TRIDENT_BLOW, Translations.DEFAULT, Translations.NAME, "Trident Blow")
				.values(Cards.TRIDENT_BLOW, Translations.RUSSIAN, Translations.NAME, "Удар трезубцем")
				.values(Cards.SWIM_AWAY, Translations.DEFAULT, Translations.NAME, "Swim Away")
				.values(Cards.SWIM_AWAY, Translations.RUSSIAN, Translations.NAME, "Уплыть")
				.values(Cards.STORM, Translations.DEFAULT, Translations.NAME, "Storm")
				.values(Cards.STORM, Translations.RUSSIAN, Translations.NAME, "Шторм")
				.values(Cards.DROP_OF_THE_OCEAN, Translations.DEFAULT, Translations.NAME, "Drop of the Ocean")
				.values(Cards.DROP_OF_THE_OCEAN, Translations.RUSSIAN, Translations.NAME, "Капля океана")
				.values(Cards.CHAMOIS, Translations.DEFAULT, Translations.NAME, "Chamois")
				.values(Cards.CHAMOIS, Translations.RUSSIAN, Translations.NAME, "Серна")
				.values(Cards.CARRION_VULTURE, Translations.DEFAULT, Translations.NAME, "Carrion Vulture")
				.values(Cards.CARRION_VULTURE, Translations.RUSSIAN, Translations.NAME, "Гриф Падальщик")
				.values(Cards.CHIVES, Translations.DEFAULT, Translations.NAME, "Chives")
				.values(Cards.CHIVES, Translations.RUSSIAN, Translations.NAME, "Лук Скорода")
				.values(Cards.SCRAMBLE, Translations.DEFAULT, Translations.NAME, "Scramble")
				.values(Cards.SCRAMBLE, Translations.RUSSIAN, Translations.NAME, "Вскарабкаться")
				.values(Cards.SCRATCH, Translations.DEFAULT, Translations.NAME, "Scratch")
				.values(Cards.SCRATCH, Translations.RUSSIAN, Translations.NAME, "Поцарапать")
				.values(Cards.SNOWBALL, Translations.DEFAULT, Translations.NAME, "Snowball")
				.values(Cards.SNOWBALL, Translations.RUSSIAN, Translations.NAME, "Снежный Ком")
				.values(Cards.THROW, Translations.DEFAULT, Translations.NAME, "Throw")
				.values(Cards.THROW, Translations.RUSSIAN, Translations.NAME, "Сбросить")
				.values(Cards.FALLING_BOULDER, Translations.DEFAULT, Translations.NAME, "Falling Boulder")
				.values(Cards.FALLING_BOULDER, Translations.RUSSIAN, Translations.NAME, "Падение валуна")
				.values(Cards.ECHO_MOUNTAIN, Translations.DEFAULT, Translations.NAME, "Echo Mountain")
				.values(Cards.ECHO_MOUNTAIN, Translations.RUSSIAN, Translations.NAME, "Эхо горы")
				.values(Cards.DECOMPRESSION, Translations.DEFAULT, Translations.NAME, "Decompression")
				.values(Cards.DECOMPRESSION, Translations.RUSSIAN, Translations.NAME, "Понижение давления")
		;
	}
}
