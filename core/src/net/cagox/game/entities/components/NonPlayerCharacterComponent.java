package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 *  A Component that identifies the Entity as an NPC character.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */
public class NonPlayerCharacterComponent implements Component {
    //TODO: Add elements needed to flesh out NPCs.  These may be other Components.
    public String name;

    public NonPlayerCharacterComponent() {}

    public NonPlayerCharacterComponent(String name) {
        this.name = name;
    }

}
