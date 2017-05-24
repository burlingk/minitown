package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;


/**
 *  A Component that identifies the Entity as a player controlled character.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */
public class PlayerCharacterComponent implements Component {
    public boolean isMainCharacter = false;

    public PlayerCharacterComponent() {
        this.isMainCharacter = false;
    }

    public PlayerCharacterComponent(boolean isMainCharacter) {
        this.isMainCharacter = isMainCharacter;
    }
}
