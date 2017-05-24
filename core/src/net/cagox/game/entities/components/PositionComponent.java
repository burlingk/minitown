package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 *  A Component that tracks where the entity is.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */

public class PositionComponent implements Component {

    public float x;
    public float y;
    public int layer;
    public String mapName;
    public String direction;

    public PositionComponent() {
        this.x = 0;
        this.y = 0;
        this.layer = 0;
        this.mapName = "default";
        this.direction = "RIGHT";
    }

    public PositionComponent(int x, int y, int layer, String mapName, String direction){
        this.x = x;
        this.y = y;
        this.layer = layer;
        this.mapName = mapName;
        this.direction = direction;
    }

    //TODO:  Some more things may need to be added to this over time.


}
