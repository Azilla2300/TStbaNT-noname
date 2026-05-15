package ru.custom.azilla.managers;

import ru.custom.azilla.elements.Level;

public class LevelManager {

    private final static Level[] levels = new Level[]{
        null,
        new Level("maps/level1.tmx", 1),
        new Level("maps/level2.tmx", 2),
        new Level("maps/level3.tmx", 3),
        new Level("maps/level4.tmx", 4),
        new Level("maps/level5.tmx", 5),
        new Level("maps/level6.tmx", 6),
        new Level("maps/level7.tmx", 7),
        new Level("maps/level8.tmx", 8),
        new Level("maps/level9.tmx", 9),
        new Level("maps/level10.tmx", 10),
        new Level("maps/levelSecret.tmx", 11)
    };

    public static Level getLevel(int id) {
        return levels[id];
    }
}
