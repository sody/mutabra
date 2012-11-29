package com.mutabra.domain.security;

import com.mutabra.domain.BaseEntityImpl;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = "DATABASE_CHANGE_LOG")
public class ChangeSetImpl extends BaseEntityImpl implements ChangeSet {

    private String title;

    private String author;

    private String location;

    private String comment;

    private Date executedAt;

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
