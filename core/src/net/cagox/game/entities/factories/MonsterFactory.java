package net.cagox.game.entities.factories;

import com.badlogic.ashley.core.Entity;

import net.cagox.game.entities.components.CharacterSheetComponent;
import net.cagox.game.entities.components.HostileMobComponent;
import net.cagox.game.entities.components.PositionComponent;
import net.cagox.game.entities.components.RenderableComponent;
import net.cagox.game.entities.components.SpriteComponent;

/**
 *  This class will be a utility class to create monsters and enemies.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */

public class MonsterFactory {

    public MonsterFactory() {  }


    /**
     * Method to create a Stationary Monster entity.  As the system evolves, so will this method.
     *
     * @param imgFile
     * @param spriteWidth
     * @param spriteHeight
     * @param frameDuration
     * @return
     */
    Entity createStationaryMonster(String imgFile, int spriteWidth, int spriteHeight, float frameDuration){
        Entity entity = new Entity();
        entity.add(new HostileMobComponent());
        entity.add(new CharacterSheetComponent());

        entity.add(new RenderableComponent());
        entity.add(new PositionComponent());
        entity.add(createSpriteComponent(imgFile, spriteWidth, spriteHeight, frameDuration));

        return entity;
    }


    /**
     *   Creates a new sprite and returns it.
     *   <p>
     *   This will be used with the various character Entity Helper classes to keep code repetition to
     *   a minimum in line with the DRY principle.
     *
     * @param imgFile
     * @param spriteWidth
     * @param spriteHeight
     * @param frameDuration
     * @return
     */
    private SpriteComponent createSpriteComponent(String imgFile, int spriteWidth, int spriteHeight, float frameDuration) {
        return new SpriteComponent(imgFile, spriteWidth, spriteHeight, frameDuration);
    }


}
