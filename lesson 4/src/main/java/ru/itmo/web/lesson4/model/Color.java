package ru.itmo.web.lesson4.model;

public enum Color {
    RED("red"), BLUE("blue"), GREEN("green");

    String colorName;
    Color(String colorName) {
        this.colorName = colorName;
    }


    @Override
    public String toString() {
        return colorName;
    }
}
