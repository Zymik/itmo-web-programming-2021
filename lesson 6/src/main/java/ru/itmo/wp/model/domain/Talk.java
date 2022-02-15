package ru.itmo.wp.model.domain;

import java.sql.ResultSet;
import java.util.Date;

public class Talk {
    private long id;
    private String text;
    private long sourceUserId;
    private long targetUserId;
    private Date creationTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getText() {
        return text;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public long getId() {
        return id;
    }

}
