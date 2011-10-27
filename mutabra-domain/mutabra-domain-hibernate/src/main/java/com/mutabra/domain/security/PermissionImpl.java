package com.mutabra.domain.security;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = Tables.PERMISSION)
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "PERMISSION_ID"))
})
public class PermissionImpl extends CodedEntityImpl implements Permission {

	@ManyToMany(mappedBy = "permissions", targetEntity = RoleImpl.class)
	private Set<Role> roles = new HashSet<Role>();

	public PermissionImpl() {
		super(Tables.PERMISSION, TranslationType.STANDARD);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}