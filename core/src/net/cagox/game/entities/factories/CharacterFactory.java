package net.cagox.game.entities.factories;

import com.badlogic.ashley.core.Entity;

import net.cagox.game.entities.components.CharacterSheetComponent;
import net.cagox.game.entities.components.MainCharacterComponent;
import net.cagox.game.entities.components.PlayerCharacterComponent;
import net.cagox.game.entities.components.PositionComponent;
import net.cagox.game.entities.components.RenderableComponent;
import net.cagox.game.entities.components.SpriteComponent;

/**
 *  This class will be a utility class to create characters.
 *  <p>
 *  Eventually it might be refactored to work for characters in general, but for now I will keep it simple.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */


public class CharacterFactory {

    public CharacterFactory() {}

    /**
     * This method creates and returns a completed Character entity that the Engine may add.
     *
     * @param isMainCharacter
     * @param imgFile
     * @param spriteWidth
     * @param spriteHeight
     * @param frameDuration
     * @return Entity
     */
    public Entity createPlayerCharacter(boolean isMainCharacter, String imgFile, int spriteWidth, int spriteHeight, float frameDuration)
    {
        Entity entity = new Entity();
        entity.add(new PositionComponent());
        entity.add(new PlayerCharacterComponent(isMainCharacter));
        entity.add(new CharacterSheetComponent());
        entity.add(new RenderableComponent());
        entity.add(createSpriteComponent(imgFile, spriteWidth, spriteHeight, frameDuration));
        if (isMainCharacter) {
            entity.add(new MainCharacterComponent());
        }


        return entity;

        //TODO:  Flesh out PositionComponent constructores then update this method.
        //TODO:  Flesh out PlayerCharacterComponent constructors then update this method.

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

