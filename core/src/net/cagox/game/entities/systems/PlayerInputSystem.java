package net.cagox.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import net.cagox.game.entities.components.PlayerCharacterComponent;
import net.cagox.game.entities.components.PositionComponent;

/**
 *  This class will be the system that handles player input.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     1.0
 * @since       1.0
 */

public class PlayerInputSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<PlayerCharacterComponent> pcm = ComponentMapper.getFor(PlayerCharacterComponent.class);

    public PlayerInputSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, PlayerCharacterComponent.class).get());

        Entity mainCharacter;

        for(Entity tmpEntity : entities ) {
            PlayerCharacterComponent pcm = tmpEntity.getComponent(PlayerCharacterComponent.class);
            if (pcm.isMainCharacter) {
                mainCharacter = tmpEntity;
                break;
            }
        }



    }

    public void update(float deltaTime) {

    }

}
