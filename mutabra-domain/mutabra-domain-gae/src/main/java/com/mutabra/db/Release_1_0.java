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
	}
}
