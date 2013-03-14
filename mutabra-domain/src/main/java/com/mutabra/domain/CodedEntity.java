package com.mutabra.domain;

import com.google.code.morphia.annotations.Id;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntity implements Entity<String> {

    @Id
    private String code;

    public String getId() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public boolean isNew() {
        return code != null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodedEntity)) {
            return false;
        }

        final CodedEntity that = (CodedEntity) o;
        return code != null ? code.equals(that.code) : that.code == null;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[code=" + (code != null ? code : "new") + "]";
    }
}
