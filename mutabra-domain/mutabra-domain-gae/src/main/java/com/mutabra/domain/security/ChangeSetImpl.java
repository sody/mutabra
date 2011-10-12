package com.mutabra.domain.security;

import com.mutabra.domain.BaseEntityImpl;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = "DATABASE_CHANGE_LOG")
public class ChangeSetImpl extends BaseEntityImpl implements ChangeSet {

	@Persistent
	private String title;

	@Persistent
	private String author;

	@Persistent
	private String location;

	@Persistent
	private String comment;

	@Persistent
	private Date executedAt;

	@Persistent
	private String checkSum;

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getLocation() {
		return location;
	}

	public String getComment() {
		return comment;
	}

	public Date getExecutedAt() {
		return executedAt;
	}

	public String getCheckSum() {
		return checkSum;
	}
}
