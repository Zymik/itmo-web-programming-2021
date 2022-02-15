package ru.itmo.wp.form;

import org.hibernate.annotations.CreationTimestamp;
import ru.itmo.wp.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostCredentials extends JwtCredentials {

    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 10000)
    @Lob
    private String text;

    private String jwt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}
