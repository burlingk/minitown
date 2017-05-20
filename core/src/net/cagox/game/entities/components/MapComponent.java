package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 *  A Component that identifies the Entity as containing map data.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     1.0
 * @since       1.0
 */

public class MapComponent implements Component {
    //TODO:  Eventually this will be built up to handle information for loading and unloading the maps.

    String mapName;
    String mapExtension;

    public String map_file_name()
    {
        String name;
        name = mapName + ".";
        name += mapExtension;
        return name;
    }


}
