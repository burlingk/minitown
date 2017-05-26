package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 *  This class will be the Component that tracks camera movement.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */

public class CameraComponent implements Component {
    public Float x;
    public Float y;
    public OrthographicCamera camera;

    public CameraComponent(){
        x = 0f;
        y = 0f;
    }

    public CameraComponent(OrthographicCamera camera) {
        x = 0f;
        y = 0f;
        this.camera = camera;
    }
}
