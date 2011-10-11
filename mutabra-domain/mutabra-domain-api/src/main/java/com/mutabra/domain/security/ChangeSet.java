package com.mutabra.domain.security;

import com.mutabra.domain.BaseEntity;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ChangeSet extends BaseEntity {

	String getTitle();

	String getAuthor();

	String getLocation();

	String getComment();

	Date getExecuted();

	String getCheckSum();
}
