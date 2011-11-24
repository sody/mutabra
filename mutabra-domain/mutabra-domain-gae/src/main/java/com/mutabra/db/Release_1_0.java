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

		begin("2011-11-23/plunger_and_flyer_cards").comment("plunger and flyer cards added");
		insert(Tables.CARD)
				.set("type", CardType.SUMMON.name())
				.set("level", select(Tables.LEVEL).unique().where(condition("code").equal("newbie")))
				.into("code", "bloodCost", "summon.strength", "summon.health")
				.values("electric-ray", 2, 3, 5)
				.values("seahorse", 2, 1, 3)
				.values("mermaid", 2, 0, 4)
				.values("chamois", 2, 2, 6)
				.values("carrion-vulture", 2, 2, 5)
				.values("chives", 2, 0, 1);
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("level", select(Tables.LEVEL).unique().where(condition("code").equal("newbie")))
				.into("code", "bloodCost", "effect.strength", "effect.duration", "effect.targetType")
				.values("calm", 1, 1, 3, TargetType.ALL_FRIEND.name())
				.values("wave", 2, 4, 1, TargetType.SINGLE_ENEMY.name())
				.values("whirlpool", 1, 0, 1, TargetType.SINGLE_ENEMY.name())
				.values("trident-blow", 2, 6, 1, TargetType.SINGLE_ENEMY.name())
				.values("swim-away", 0, 0, 1, TargetType.SINGLE_FRIEND.name())
				.values("storm", 2, 0, 1, TargetType.ALL_FRIEND.name())
				.values("drop-of-the-ocean", 2, 0, 2, TargetType.ALL_ENEMY.name())
				.values("scramble", 1, 0, 1, TargetType.PLAYER_FRIEND.name())
				.values("scratch", 1, 5, 1, TargetType.PLAYER_ENEMY.name())
				.values("snowball", 1, 2, 1, TargetType.ALL_ENEMY.name())
				.values("throw", 1, 3, 1, TargetType.SINGLE_ENEMY.name())
				.values("falling-boulder", 1, 7, 1, TargetType.SINGLE_ENEMY.name())
				.values("echo-mountain", 2, 4, 1, TargetType.ALL.name())
				.values("decompression", 1, 1, 2, TargetType.ALL_ENEMY.name())

		;
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.into("code", "locale", "variant", "value")
				.values("electric-ray", "", "name", "Electric Ray")
				.values("electric-ray", "ru", "name", "Электрический Скат")
				.values("seahorse", "", "name", "Seahorse")
				.values("seahorse", "ru", "name", "Морской Конек")
				.values("mermaid", "", "name", "Mermaid")
				.values("mermaid", "ru", "name", "Русалка")
				.values("calm", "", "name", "Calm")
				.values("calm", "ru", "name", "Штиль")
				.values("wave", "", "name", "Wave")
				.values("wave", "ru", "name", "Волна")
				.values("whirlpool", "", "name", "Whirlpool")
				.values("whirlpool", "ru", "name", "Водоворот")
				.values("trident-blow", "", "name", "Trident Blow")
				.values("trident-blow", "ru", "name", "Удар трезубцем")
				.values("swim-away", "", "name", "Swim Away")
				.values("swim-away", "ru", "name", "Уплыть")
				.values("storm", "", "name", "Storm")
				.values("storm", "ru", "name", "Шторм")
				.values("drop-of-the-ocean", "", "name", "Drop of the Ocean")
				.values("drop-of-the-ocean", "ru", "name", "Капля океана")

				.values("chamois", "", "name", "Chamois")
				.values("chamois", "ru", "name", "Серна")
				.values("carrion-vulture", "", "name", "Carrion Vulture")
				.values("carrion-vulture", "ru", "name", "Гриф Падальщик")
				.values("chives", "", "name", "Chives")
				.values("chives", "ru", "name", "Лук Скорода")
				.values("scramble", "", "name", "Scramble")
				.values("scramble", "ru", "name", "Вскарабкаться")
				.values("scratch", "", "name", "Scratch")
				.values("scratch", "ru", "name", "Поцарапать")
				.values("snowball", "", "name", "Snowball")
				.values("snowball", "ru", "name", "Снежный Ком")
				.values("throw", "", "name", "Throw")
				.values("throw", "ru", "name", "Сбросить")
				.values("falling-boulder", "", "name", "Falling Boulder")
				.values("falling-boulder", "ru", "name", "Падение валуна")
				.values("echo-mountain", "", "name", "Echo Mountain")
				.values("echo-mountain", "ru", "name", "Эхо горы")
				.values("decompression", "", "name", "Decompression")
				.values("decompression", "ru", "name", "Понижение давления")
		;
	}
}
