package ru.custom.azilla.managers;

import com.badlogic.gdx.audio.Sound;

import ru.custom.azilla.elements.VOPart;

public class VOManager {
    VOPart[] voParts;

    public VOManager() {
        voParts = new VOPart[]{
            null,
            new VOPart("sounds/VOs/location_1.ogg", 1),
            new VOPart("sounds/VOs/location_2.ogg", 2),
            new VOPart("sounds/VOs/location_3.ogg", 3),
            new VOPart("sounds/VOs/location_4.ogg", 4),
            new VOPart("sounds/VOs/location_5.ogg", 5),
            new VOPart("sounds/VOs/location_6.ogg", 6),
            new VOPart("sounds/VOs/location_7.ogg", 7),
            new VOPart("sounds/VOs/location_8.ogg", 8),
            new VOPart("sounds/VOs/location_9.ogg", 9),
            new VOPart("sounds/VOs/location_10.ogg", 10),
            new VOPart("sounds/VOs/placeholder.ogg", 11)
        };
    }
    public void play(int id) {
        voParts[id].play();
    }
    public boolean isPlaying(int id) {
        return voParts[id].theVO.isPlaying();
    }

    public void dispose() {
        for (VOPart voPart : voParts) {
            voPart.dispose();
        }
    }
}
