package ru.custom.azilla.elements;

public class Level {
    private final int id;
    private final String path;

    public Level(String path, int id) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }
    public String getPath() {
        return path;
    }
}
