package ru.custom.azilla.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import static ru.custom.azilla.GameSettings.*;

import ru.custom.azilla.MyGdxGame;

public class MenuScreen implements Screen {

    Texture startButton;
    MyGdxGame myGdxGame;
    SpriteBatch batch;
    OrthographicCamera camera;

    float startButtonX;
    float startButtonY;

    public MenuScreen(MyGdxGame myGdxGame) {
        startButton = new Texture(BUTTON_START);

        this.myGdxGame = myGdxGame;
        this.batch = myGdxGame.batch;
        this.camera = myGdxGame.camera;

        startButtonX = SCREEN_WIDTH / 2 - 150;
        startButtonY = SCREEN_HEIGHT - 355;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0x30 / 255f, 0x20 / 255f, 0x10 / 255f, 1);

        batch.begin();
        batch.draw(startButton, startButtonX, startButtonY, 300, 300);
        batch.end();

        camera.update();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 vector3 = camera.unproject(new Vector3(
                Gdx.input.getX(), Gdx.input.getY(), 0));
            float x = vector3.x;
            float y = vector3.y;
            if (x > startButtonX && x < startButtonX + 300
            && y > startButtonY && y < startButtonY + 300) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        startButton.dispose();
    }
}
