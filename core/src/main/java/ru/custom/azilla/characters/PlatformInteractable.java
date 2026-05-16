package ru.custom.azilla.characters;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class PlatformInteractable extends Platform{
    public final int actionId;
    boolean isDestroyed = false;

    public enum Actions {
        NONE(1, ""),
        DISSAPEAR(2, "disapear"),
        PICKUP(3, "pickup"),
        ENDLEVEL(4, "endlevel");

        public final int value;

        public final String name;
        Actions(int value, String name) {
            this.value = value;
            this.name = name;
        }

    }
    public PlatformInteractable(float x, float y, float width, float height,
                                short actionId, World world) {
        super(x, y, width, height, world, actionId);
        this.actionId = actionId;
    }

    public short[] checkActOfContact(Array<Contact> contacts) {
        short[] interactions = new short[contacts.size];
        int i = 0;
        for (Array.ArrayIterator<Contact> iterator = contacts.iterator(); iterator.hasNext(); ) {
            Contact contact = iterator.next();
            interactions[i] = (short) (contact.getFixtureA().getFilterData().categoryBits
                | contact.getFixtureB().getFilterData().categoryBits);
            if (interactions[i] == 0b1011 || interactions[i] == 0b1010) {
                act();
            }
            i++;
        }
        return interactions;
    }

    public void act() {
        if (actionId == 2 || actionId == 3) {
            setIsVisible(false);
            if (!isDestroyed) {
                body.getWorld().destroyBody(body);
                isDestroyed = true;
            }
        }
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }
}
