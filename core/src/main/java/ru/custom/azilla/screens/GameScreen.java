package ru.custom.azilla.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.custom.azilla.GameSettings;
import ru.custom.azilla.MyGdxGame;
import ru.custom.azilla.characters.Platform;
import ru.custom.azilla.characters.PlatformInteractable;
import ru.custom.azilla.characters.Player;
import ru.custom.azilla.managers.MapManager;
import ru.custom.azilla.managers.VOManager;

public class GameScreen implements Screen {

    OrthographicCamera camera;

    float minY = GameSettings.SCREEN_HEIGHT / 2;
    float minX = GameSettings.SCREEN_WIDTH / 2;
    float maxX;
    float maxY;
    boolean transitionReady = false;
    int currentLevel = 0;

    SpriteBatch batch;
    MyGdxGame myGdxGame;
    VOManager voManager;
    Box2DDebugRenderer debugRenderer;
    MapManager mapManager;
    public World world;

    ArrayList<Platform> platforms;
    ArrayList<PlatformInteractable> interactables;
    Player player;

    float worldStepCounter;

    float width = GameSettings.SCREEN_WIDTH;
    float height = GameSettings.SCREEN_HEIGHT;

    Vector3 previousCameraPosition;

    public GameScreen(MyGdxGame myGdxGame) {
        Box2D.init();
        world = new World(new Vector2(0,
            GameSettings.GRAVITY_COEFFICIENT), true);

        mapManager = new MapManager();

        this.myGdxGame = myGdxGame;
        this.batch = myGdxGame.batch;
        this.camera = myGdxGame.camera;

        voManager = new VOManager();

        platforms = new ArrayList<>(/*4*/);
        /*platforms.add(new Platform(width / 2, 5, width, 20, world));
        platforms.add(new Platform(5, height / 2, 20, height, world));
        platforms.add(new Platform(width / 2, height - 5, width, 20, world));
        platforms.add(new Platform(width - 5, height / 2, 20, height, world));*/
        interactables = new ArrayList<>();

        debugRenderer = new Box2DDebugRenderer();

        player = new Player(width / 6, height / 3.5f, world);
        previousCameraPosition = new Vector3(
            player.body.getPosition().scl(1 / GameSettings.SCALE), 0);
    }

    @Override
    public void show() {
        adaptLevel();
    }

    void adaptLevel() {
        currentLevel++;
        System.out.println("Changed currentLevel: " + currentLevel);
        maxX = GameSettings.TILE_SCALE * 16
            * (float) mapManager.map.getProperties().get("width", Integer.class)
            - GameSettings.SCREEN_WIDTH / 2;
        maxY = GameSettings.TILE_SCALE * 16
            * (float) mapManager.map.getProperties().get("height", Integer.class)
            - GameSettings.SCREEN_HEIGHT / 2;
        if (platforms != null) {
            for (Platform platform : platforms) {
                platform.body.getWorld().destroyBody(platform.body);
            }
            platforms.clear();
        }
        if (interactables != null) {
            for (PlatformInteractable interactable : interactables) {
                if (!interactable.getIsDestroyed()) {
                    interactable.body.getWorld().destroyBody(interactable.body);
                }
            }
            interactables.clear();
        }
        if (currentLevel > 1) voManager.play(currentLevel);
        mapManager.getPlatforms(platforms, interactables, world);
        player.body.getWorld().destroyBody(player.body);
        player = new Player(width / 6, height / 3.5f, world);
        transitionReady = false;
    }

    public void manageInteractables() {
        for (PlatformInteractable interactable : interactables) {
            interactable.draw(batch);
            short[] colisions = interactable.checkActOfContact(world.getContactList());
            boolean is12 = false;
            boolean is11 = false;
            for (short interaction : colisions) {
                if (interaction == 12) is12 = true;
                if (interaction == 11) is11 = true;
            }
            if (interactable.actionId == 4) {
                interactable.setIsVisible(transitionReady);
                if (is12) {
                    mapManager.loadNextLevel();
                    adaptLevel();
                    break;
                }
            } else if (interactable.actionId == 3) {
                if (is11) {
                    transitionReady = true;
                    interactable.act();
                    if (currentLevel == 1) voManager.play(1);
                }
            } else if (interactable.actionId == 2 && transitionReady
                && !voManager.isPlaying(currentLevel)) {
                interactable.act();
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0x30 / 255f, 0x20 / 255f, 0x10 / 255f, 1);
        batch.begin();
        manageInteractables();
        player.draw(batch, delta);
        mapManager.draw(camera);
        batch.end();

        Matrix4 matrix4 = camera.combined.cpy().scale(
            1 / GameSettings.SCALE,
            1 / GameSettings.SCALE,
            1 / GameSettings.SCALE);
        debugRenderer.render(world, matrix4);
        moveCamera();

        handleInput();

        worldStepCounter += delta;
        while (worldStepCounter >= GameSettings.WORLD_STEP_SIZE) {
            world.step(GameSettings.WORLD_STEP_SIZE, 6, 6);
            worldStepCounter -= GameSettings.WORLD_STEP_SIZE;
        }
    }

    private void moveCamera() {

        Vector3 vector3 = (new Vector3(
            player.body.getPosition().scl(1 / GameSettings.SCALE), 0))
            .sub(previousCameraPosition);
        camera.position.add(vector3.scl(.1f));

        camera.position.x = Math.max(minX, Math.min(camera.position.x, maxX));
        camera.position.y = Math.max(minY, Math.min(camera.position.y, maxY));
        previousCameraPosition = new Vector3(camera.position);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    void handleInput() {
        if (GameSettings.IS_ON_DESKTOP) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.moveSide(-1);
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.moveSide(1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                player.moveUp();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                mapManager.loadNextLevel();
                adaptLevel();
            }
        } else if (Gdx.input.isTouched()) {
            Vector3 vector3 = camera.unproject(new Vector3(
                Gdx.input.getX(), Gdx.input.getY(), 0));
            float x = vector3.x;

            if (x <= GameSettings.SCREEN_WIDTH / 4) {
                player.body.applyForceToCenter(-GameSettings.MOVE_COEFFICIENT,
                    player.body.getLinearVelocity().y, true);
            } else if (x > GameSettings.SCREEN_WIDTH / 4
            && x <= GameSettings.SCREEN_WIDTH / 2) {
                player.body.applyForceToCenter(GameSettings.MOVE_COEFFICIENT,
                    player.body.getLinearVelocity().y,true);
            }
        }
    }

    @Override
    public void dispose() {
        for (Platform platform : platforms) {
            platform.dispose();
        }
        for (PlatformInteractable interacable : interactables) {
            interacable.dispose();
        }
        player.dispose();
        voManager.dispose();
    }
}
