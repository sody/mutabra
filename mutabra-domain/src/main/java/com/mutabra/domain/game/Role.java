package com.mutabra.domain.game;

import com.mutabra.domain.Translatable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum Role implements Translatable {
    ADMIN("*"),
    USER("game:play");

    public static final String BASENAME = "role";

    private final String code;
    private final Set<String> permissions;

    private Role(final String... permissions) {
        this.permissions = new HashSet<String>(Arrays.asList(permissions));

        code = name().replaceAll("([A-Z])", "-$1").toLowerCase();
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
