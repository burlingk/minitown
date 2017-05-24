package net.cagox.game.entities.factories;

import com.badlogic.ashley.core.Entity;

import net.cagox.game.entities.components.CameraComponent;


/**
 *  This class will be a utility class to create camera entities.
 *  <p>
 *  For now, it is just super simple.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */

public class CameraFactory {


    public Entity createCameraEntity() {
        Entity entity = new Entity();
        entity.add(new CameraComponent());
        return entity;
    }


}
