package net.cagox.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import net.cagox.game.entities.components.MainCharacterComponent;
import net.cagox.game.entities.components.PlayerCharacterComponent;
import net.cagox.game.entities.components.PositionComponent;
import net.cagox.game.entities.systems.RenderSystem;

/**
 *  This class will be the system that handles player input.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     1.0
 * @since       1.0
 */

public class PlayerInputSystem extends EntitySystem implements InputProcessor {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<PlayerCharacterComponent> pcm;
    private ComponentMapper<MainCharacterComponent> mcm;

    private Engine engine;

    public PlayerInputSystem() {
        Gdx.input.setInputProcessor(this);

        pm = ComponentMapper.getFor(PositionComponent.class);
        pcm = ComponentMapper.getFor(PlayerCharacterComponent.class);
        mcm = ComponentMapper.getFor(MainCharacterComponent.class);

    }

    public void addedToEngine(Engine engine) {
        this.engine = engine;
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {  //TODO:  Figure out how to do movement from touchDown.
        ImmutableArray<Entity> tmpArray = engine.getEntitiesFor(Family.all(MainCharacterComponent.class).get());
        Entity playerCharacter = tmpArray.first();

        PositionComponent position = playerCharacter.getComponent(PositionComponent.class);

        RenderSystem renderSys = engine.getSystem(RenderSystem.class);
        Integer maxX = renderSys.mapWidth;
        Integer maxY = renderSys.mapHeight;


        if(keycode == Input.Keys.LEFT) {
            //pcWalkDirection = "LEFT";
            //camera.translate(-1,0);
            position.x -= 1;
            position.direction = "LEFT";
            if (position.x < 0) {
                position.x = 0;
            }
        }
        if(keycode == Input.Keys.RIGHT) {
            //pcWalkDirection = "RIGHT";
            //camera.translate(1,0);
            position.x += 1;
            position.direction = "RIGHT";
            if (position.x >= maxX) {
                position.x = maxX-1;
            }
        }
        if(keycode == Input.Keys.UP) {
            //pcWalkDirection = "UP";
            //camera.translate(0, 1);
            position.y += 1;
            position.direction = "UP";
            if (position.y >= maxY ) {
                position.y = maxY -1;
            }
        }
        if(keycode == Input.Keys.DOWN) {
            //pcWalkDirection = "DOWN";
           //camera.translate(0,-1);
            position.y -= 1;
            position.direction = "DOWN";
            if (position.y < 0) {
                position.y = 0;
            }
        }
        if(keycode == Input.Keys.NUM_1)
            //tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            //tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        if(keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();

        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int x, y;

        int h = (int)Gdx.graphics.getHeight();

        x = screenX/32;
        y = -1*(screenY - h)/32;

        System.out.print("X:");
        System.out.print(x);
        System.out.print(" Y:");
        System.out.print(y);
        System.out.print("\n");

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
