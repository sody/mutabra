package com.mutabra.domain.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum Role {
    ADMIN("*"),
    USER("game:play");

    private final Set<String> permissions;

    Role(final String... permissions) {
        this.permissions = new HashSet<String>(Arrays.asList(permissions));
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
