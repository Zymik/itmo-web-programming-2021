package ru.itmo.web.lesson4.model;

public class Post {
    private final long id;
    private final String title;
    private final String text;
    private final long userId;

    public Post(long id, String title, String text, long user_id) {
        this.id = id;
        this.title = title;
        this.userId = user_id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getUserId() {
        return userId;
    }
}
