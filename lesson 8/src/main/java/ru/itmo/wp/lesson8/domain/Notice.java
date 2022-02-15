package ru.itmo.wp.lesson8.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(
        indexes = @Index(columnList = "creationTime")
)
public class Notice {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    @NotNull
    @Size(min = 5)
    @NotBlank
    private String content;

    @CreationTimestamp
    private Date creationTime;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreationTime() {
        return creationTime;
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content.trim();
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
