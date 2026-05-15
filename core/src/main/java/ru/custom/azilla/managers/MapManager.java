package ru.custom.azilla.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import ru.custom.azilla.characters.Platform;
import ru.custom.azilla.characters.PlatformInteractable;
import ru.custom.azilla.elements.Level;
import static ru.custom.azilla.GameSettings.TILE_SCALE;

public class MapManager {

    public TiledMap map;
    private Level curentLevel;
    OrthoCachedTiledMapRenderer mapRenderer;

    public MapManager() {
        loadLevel(1);
    }

    public void loadLevel(int id) {
        if (id <= 0) return;
        this.curentLevel = LevelManager.getLevel(id);
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(LevelManager.getLevel(id).getPath());
        mapRenderer = new OrthoCachedTiledMapRenderer(map, TILE_SCALE);
    }

    public void loadNextLevel() {
        loadLevel(curentLevel.getId() + 1);
    }

    public void getPlatforms(ArrayList<Platform> platforms,
                             ArrayList<PlatformInteractable> interactables, World world) {
        for (RectangleMapObject object :
            map.getLayers().get("limiters").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            float x = rect.x;
            float y = rect.y;
            float width = rect.width;
            float height = rect.height;
            platforms.add(new Platform((
                x + width / 2) * TILE_SCALE, (y + height / 2) * TILE_SCALE,
                width * TILE_SCALE, height * TILE_SCALE, world, (short) 1));
        }
        if (map.getLayers().get("platforms") != null) {
            for (RectangleMapObject object :
                map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = object.getRectangle();
                float x = rect.x;
                float y = rect.y;
                float width = rect.width;
                float height = rect.height;
                platforms.add(new Platform((
                    x + width / 2) * TILE_SCALE, (y + height / 2) * TILE_SCALE,
                    width * TILE_SCALE, height * TILE_SCALE, world, (short) 1));
            }
        }
        if (map.getLayers().get("interactable") == null) return;
        for (RectangleMapObject object :
            map.getLayers().get("interactable").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            float x = rect.x;
            float y = rect.y;
            float width = rect.width;
            float height = rect.height;
            short type = 0;
            for (PlatformInteractable.Actions action : PlatformInteractable.Actions.values()){
                if (action.name.equals(object.getProperties().get("action"))) {
                    type = (short) action.value;
                }
            }
            System.out.println(type);
                interactables.add(new PlatformInteractable(
                    (x + width / 2) * TILE_SCALE, (y + height / 2) * TILE_SCALE,
                width * TILE_SCALE, height * TILE_SCALE, type, world));
        }
    }

    public void draw(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
