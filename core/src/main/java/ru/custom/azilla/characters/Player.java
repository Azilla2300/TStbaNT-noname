package ru.custom.azilla.characters;

import static ru.custom.azilla.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ru.custom.azilla.GameSettings;
import ru.custom.azilla.elements.PhysicalObject;

public class Player extends PhysicalObject {

    boolean fliped;

    int jumpAmount = 1;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> doubleJumpAnimation;
    private Animation<TextureRegion> dashAnimation;
    private Animation<TextureRegion> danceAnimation;
    private Animation<TextureRegion> curentAnimation;
    private float timer;

    public Player(float x, float y, World world) {
        super(x, y, GameSettings.PLAYER_CIRCLE_WIDTH,
            GameSettings.PLAYER_CIRCLE_WIDTH,
            world, BodyDef.BodyType.DynamicBody,
            GameSettings.CONCRETE, (short) 8);
        createAnimations();
    }

    public Player(float x, float y, World world, int jumpAmount) {
        this(x, y, world);
        this.jumpAmount = jumpAmount;
    }
    public void moveUp() {
        body.setLinearVelocity(body.getLinearVelocity().x, GameSettings.JUMP_SPEED);
    }

    public void moveSide(int coefficient) {
        body.applyForceToCenter(GameSettings.MOVE_COEFFICIENT * coefficient,
            0, true);
        fliped = coefficient < 0;
    }

    public void draw(SpriteBatch batch, float delta) {
        if (!isVisible) return;
        drawableX = body.getPosition().x / SCALE - width / 2;
        drawableY = body.getPosition().y / SCALE - height / 2;

        checkAnimation(delta);

        if (fliped) {
            batch.draw(curentAnimation.getKeyFrame(timer),
                drawableX + width, drawableY, -width, height);
        } else {
            batch.draw(curentAnimation.getKeyFrame(timer),
                drawableX, drawableY, width, height);
        }
    }

    private void createAnimations() {
        Texture texture = new Texture(GameSettings.PLAYER_CHARACTER);

        idleAnimation = getAnimation(texture, 5, 20 / 60f, 0);
        runAnimation = getAnimation(texture, 2, 10 / 60f, 15);
        jumpAnimation = getAnimation(texture, 4, .1f, 30);
        doubleJumpAnimation = getAnimation(texture, 4, .1f, 45);
        dashAnimation = getAnimation(texture, 1, .5f, 60);
        danceAnimation = getAnimation(texture, 6, .3f, 75);
    }

    private Animation<TextureRegion> getAnimation(
        Texture texture, int amount, float time, int y) {
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < amount; i++) {
            frames.add(new TextureRegion(texture, i * 9 + i, y, 9, 14));
        }
        return new Animation<>(time, frames, Animation.PlayMode.LOOP);
    }

    private void checkAnimation(float delta) {
/*        if (body.getLinearVelocity().y > -1.5) {
            setAnimation(jumpAnimation);
        } else */if (body.getLinearVelocity().x == 0) {
            setAnimation(idleAnimation);
        } else setAnimation(runAnimation);

        timer += delta;
    }

    private void setAnimation(Animation<TextureRegion> animation) {
        if (curentAnimation != animation) {
            timer = 0;
            curentAnimation = animation;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        disposeOne(idleAnimation);
        disposeOne(runAnimation);
        disposeOne(jumpAnimation);
        disposeOne(doubleJumpAnimation);
        disposeOne(dashAnimation);
        disposeOne(danceAnimation);
    }

    private void disposeOne(Animation<TextureRegion> array) {
        for (TextureRegion region : array.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }
}
