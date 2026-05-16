package ru.custom.azilla.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static ru.custom.azilla.GameSettings.*;

public class PhysicalObject {

    public Body body;
    public Texture texture;

    // Используется при создании объекта.
    // После нужно использовать body.getPosition().x
    protected float width;
    protected float height;

    public float drawableX;
    public float drawableY;
    protected boolean isVisible = true;

    public PhysicalObject(float x, float y, float width, float height,
        /*float density, float friction,*/
        World world, BodyDef.BodyType bodyType, String texture, short mask) {

        this.width = width;
        this.height = height;

        Shape shape;

        this.texture = new Texture(texture);

        if (bodyType == BodyDef.BodyType.DynamicBody) {
            shape = new CircleShape();
            shape.setRadius(width / 2 * SCALE);
        } else {
            shape = new PolygonShape();
            ((PolygonShape) shape).setAsBox(width / 2 * SCALE,
                height / 2 * SCALE);
        }

        createBody(x, y, /*density, friction,*/ shape, mask, world, bodyType);
    }

    public void draw(SpriteBatch batch) {
        if (!isVisible) return;
        drawableX = body.getPosition().x / SCALE - width / 2;
        drawableY = body.getPosition().y / SCALE - height / 2;

        batch.draw(texture, drawableX, drawableY, width, height);
    }

    public void setIsVisible(boolean value) {
        isVisible = value;
    }

    void createBody(float x, float y, /*float density, float friction,*/
        Shape shape, short mask, World world, BodyDef.BodyType bodyType) {

        BodyDef def = new BodyDef();
        def.type = bodyType;
        def.fixedRotation = true;
        body = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        if (shape != null) fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        if (shape != null) shape.dispose();
        for (Fixture fixture : body.getFixtureList()) {
            fixture.getFilterData().categoryBits = mask;
        }


        body.setTransform(x * SCALE, y * SCALE, 0);
        body.setLinearDamping(2f);

        // body.setLinearDamping(10);
    }

    public void dispose() {
        texture.dispose();
    }
}
