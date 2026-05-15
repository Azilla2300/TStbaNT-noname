package ru.custom.azilla;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static ru.custom.azilla.GameSettings.*;

import ru.custom.azilla.screens.GameScreen;
import ru.custom.azilla.screens.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGdxGame extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public GameScreen gameScreen;

    MenuScreen menuScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        setScreen(menuScreen);
    }
    public void dispose() {
        batch.dispose();
    }
}
