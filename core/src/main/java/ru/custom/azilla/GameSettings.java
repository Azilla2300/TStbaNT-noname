package ru.custom.azilla;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class GameSettings {

    public static float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static boolean IS_ON_DESKTOP = Gdx.app.getType() == Application.ApplicationType.Desktop;

    // Текстуры
    public static String BUTTON_START = "buttons/buttonStart.png";
    public static String CIRCLE150 = "circle150R.png";
    public static String CONCRETE = "CONCRETE.PNG";
    public static String PLAYER_CHARACTER = "stickman_general.png";

    // Game related
    public static float WORLD_STEP_SIZE = 0.01f;

    public static float TILE_SCALE = SCREEN_HEIGHT / 10 / 16;
    public static float SCALE = 10 / SCREEN_HEIGHT;

    public static float PLAYER_CIRCLE_WIDTH = SCREEN_HEIGHT / 4;

    public static float GRAVITY_COEFFICIENT = -50;
    public static float JUMP_SPEED = 25;
    public static float MOVE_COEFFICIENT = 50;

    // Громкость звука
    public static final float SET_VOLUME = 1;
}
