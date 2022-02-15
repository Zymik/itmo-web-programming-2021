package ru.itmo.wp.form;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentCredentials extends JwtCredentials {

    @NotEmpty
    @Size(min = 1, max = 10000)
    @Lob
    private String text;

    private String jwt;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
