package com.noname.domain.security;

import com.noname.domain.CodedEntity;
import com.noname.domain.TranslationType;

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
@Table(name = "PERMISSION")
public class Permission extends CodedEntity {
	private static final String PERMISSION_PREFIX = "PERMISSION_";

	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles = new HashSet<Role>();

	public Permission() {
		super("permission", TranslationType.STANDARD);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAuthority() {
		return PERMISSION_PREFIX + getCode().toUpperCase();
	}

}