package com.mutabra.db;

import org.greatage.db.ChangeLog;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Release_1_0 extends ChangeLog {

	@Override
	protected void init() {
		location("mutabra/release_1.0");

		begin("2011-10-11/roles", "sody").comment("roles data")
				.insert(Tables.ROLE).set("code", "admin").end()
				.insert(Tables.ROLE).set("code", "user").end()
				.end();

		begin("2011-10-11/levels", "sody").comment("levels data")
				.insert(Tables.LEVEL).set("code", "newbie").set("rating", 10l).end()
				.end();

		begin("2011-10-11/races", "sody").comment("races data")
				.insert(Tables.RACE).set("code", "elf").end()
				.insert(Tables.RACE).set("code", "orc").end()
				.end();

		begin("2011-10-14/translations", "sody").comment("translations added")
				.insert(Tables.TRANSLATION)
				.set("type", Tables.ROLE)
				.set("code", "admin")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Administrator").end()
				.insert(Tables.TRANSLATION)
				.set("type", Tables.ROLE)
				.set("code", "user")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "User").end()
				.insert(Tables.TRANSLATION)
				.set("type", Tables.LEVEL)
				.set("code", "newbie")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Newbie").end()
				.insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "orc")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Orc").end()
				.insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.set("code", "elf")
				.set("variant", "description")
				.set("locale", "")
				.set("value", "Elf").end()
				.end();

		begin("2011-10-17/admin_account", "sody").comment("admin account added")
				.insert(Tables.ACCOUNT).set("email", "admin@mutabra.com").set("password", "21232f297a57a5a743894a0e4a801fc3").end();

		begin("2011-11-02/admin_account_role", "sody").comment("admin account role added")
				.update(Tables.ACCOUNT)
				.where("email").equal("admin@mutabra.com").end()
				.setFrom("roles", Tables.ROLE).where("code").equal("admin").end().end()
				.end();
	}
}
