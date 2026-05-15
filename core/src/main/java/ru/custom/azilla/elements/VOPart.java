package ru.custom.azilla.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class VOPart {
    public Music theVO;
    final int playedAtID;

    public VOPart(String VOPath, int playedAtID) {
        this.playedAtID = playedAtID;
        theVO = Gdx.audio.newMusic(Gdx.files.internal(VOPath));
        theVO.setLooping(false);
    }
    public void play() {
        theVO.play();
    }

    public void dispose() {
        theVO.dispose();
    }
}
