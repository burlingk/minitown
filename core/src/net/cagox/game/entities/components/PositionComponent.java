package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 *  A Component that tracks where the entity is.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     1.0
 * @since       1.0
 */

public class PositionComponent implements Component {

    public float x;
    public float y;
    public int layer;
    public String mapName;

    public PositionComponent() {
        this.x = 0;
        this.y = 0;
        this.layer = 0;
        this.mapName = "default";
    }

    public PositionComponent(int x, int y, int layer, String mapName){
        this.x = x;
        this.y = y;
        this.layer = layer;
        this.mapName = mapName;
    }

    //TODO:  Some more things may need to be added to this over time.


}
