package ru.custom.azilla.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

import ru.custom.azilla.GameSettings;

public class SFXManager {
    final Sound[] jumpSounds;
    final Sound pickupSound;
    final Sound dashSound;

    Random random = new Random();

    public SFXManager(){
        jumpSounds = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/stone1.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/stone2.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/stone3.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/stone4.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/stone5.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/stone6.ogg"))};

        pickupSound = Gdx.audio.newSound(
            Gdx.files.internal("sounds/SFX/recorder_pickup.wav"));
        dashSound = Gdx.audio.newSound(
            Gdx.files.internal("sounds/SFX/hiss.wav"));
    }

    public void playJump(){
        jumpSounds[random.nextInt(jumpSounds.length)].play();
    }

    public void playPickup() {
        pickupSound.play();
    }

    public void playDash() {
        dashSound.play(GameSettings.SET_VOLUME);
    }
}
