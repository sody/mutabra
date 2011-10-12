package com.mutabra.db;

import com.mutabra.domain.Tables;
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
	}
}
