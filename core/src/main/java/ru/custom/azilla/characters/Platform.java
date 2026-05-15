package ru.custom.azilla.characters;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import ru.custom.azilla.GameSettings;
import ru.custom.azilla.elements.PhysicalObject;

public class Platform extends PhysicalObject {

    public Platform(float x, float y, float width, float height, World world, short mask) {
        super(x, y, width, height, world,
            BodyDef.BodyType.StaticBody, GameSettings.CONCRETE, mask);
    }
}
