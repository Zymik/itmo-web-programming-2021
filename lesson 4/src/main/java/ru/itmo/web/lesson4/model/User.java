package ru.itmo.web.lesson4.model;

public class User {
    private final long id;
    private final String handle;
    private final Color handleColor;
    private final String name;

    public User(long id, String handle, String name, Color handleColor) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.handleColor = handleColor;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public Color getHandleColor() {return handleColor;}
}
