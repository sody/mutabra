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

		begin("2011-11-15/test_cards_and_faces").comment("some test cards added");
		insert(Tables.CARD)
				.set("type", CardType.EFFECT.name())
				.set("code", "ec1")
				.set("effect.targetType", TargetType.PLAYER_ENEMY.name())
				.set("effect.attack", 10)
				.set("effect.defence", 0)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
				);
		insert(Tables.CARD)
				.set("type", CardType.SUMMON.name())
				.set("code", "sc1")
				.set("summon.attack", 3)
				.set("summon.defence", 10)
				.set("level",
						select(Tables.LEVEL).unique().where(condition("code").equal("newbie"))
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
	}
}
