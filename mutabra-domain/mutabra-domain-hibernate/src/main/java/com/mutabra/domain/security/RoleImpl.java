package com.mutabra.domain.security;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = Tables.ROLE)
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "ROLE_ID"))
})
public class RoleImpl extends CodedEntityImpl implements Role {
	public RoleImpl() {
		super(Tables.ROLE, TranslationType.STANDARD);
	}

	@ManyToMany(mappedBy = "roles", targetEntity = AccountImpl.class)
	private Set<Account> accounts = new HashSet<Account>();

	@ManyToMany(targetEntity = PermissionImpl.class)
	@JoinTable(name = "ROLE_PERMISSION",
			joinColumns = @JoinColumn(name = "ROLE_ID", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID", nullable = false))
	private Set<Permission> permissions = new HashSet<Permission>();

	public Set<Account> getAccounts() {
		return accounts;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}