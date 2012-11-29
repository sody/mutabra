package com.mutabra.db;

import org.greatage.db.ChangeLog;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MutabraChangeLog extends ChangeLog {

    @Override
    protected void init() {
        location("mutabra/changelogs");

        add(new Release_1_0());
    }
}
