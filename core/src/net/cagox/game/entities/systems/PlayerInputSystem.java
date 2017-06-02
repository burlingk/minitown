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
import com.badlogic.gdx.graphics.Camera;

import net.cagox.game.entities.EntityManager;
import net.cagox.game.entities.components.CameraComponent;
import net.cagox.game.entities.components.MainCharacterComponent;
import net.cagox.game.entities.components.PlayerCharacterComponent;
import net.cagox.game.entities.components.PositionComponent;
import net.cagox.game.entities.components.TouchpadComponent;
import net.cagox.game.entities.systems.RenderSystem;
import net.cagox.game.entities.systems.helpers.MovementFlags;

/**
 *  This class will be the system that handles player input.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */

public class PlayerInputSystem extends EntitySystem implements InputProcessor {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<PlayerCharacterComponent> pcm;
    private ComponentMapper<MainCharacterComponent> mcm;

    private Engine engine;
    private  EntityManager entityManager;

    private MovementFlags Moving = new MovementFlags();

    private TouchpadComponent touchpadComponent;


    public PlayerInputSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
        pm = ComponentMapper.getFor(PositionComponent.class);
        pcm = ComponentMapper.getFor(PlayerCharacterComponent.class);
        mcm = ComponentMapper.getFor(MainCharacterComponent.class);
    }



    public void addedToEngine(Engine engine) {
        this.engine = engine;
        touchpadComponent = getTouchpadComponent();
        entityManager.addInputProcessor(touchpadComponent.stage);
        entityManager.addInputProcessor(this);
    }

    public void update(float deltaTime) {
        Entity playerCharacter = engine.getEntitiesFor(Family.all(MainCharacterComponent.class).get()).first();
        Entity cameraEntity = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).first();
        PositionComponent playerPosition = playerCharacter.getComponent(PositionComponent.class);
        CameraComponent cameraComponent = cameraEntity.getComponent(CameraComponent.class);


        float speed = 3;

        float x, y;
        x = touchpadComponent.touchpad.getKnobPercentX()*speed*deltaTime;
        y = touchpadComponent.touchpad.getKnobPercentY()*speed*deltaTime;

        setPlayerDirection(x,y);
        cameraComponent.camera.translate(x,y);

        // blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);
        //blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY()*blockSpeed);



        //New Movement Code Bellow
        if (Moving.RIGHT) {
            playerPosition.x += speed * deltaTime * entityManager.getRenderSystem().getTileW();
            cameraComponent.camera.translate((speed * deltaTime), 0);

        }
        if (Moving.LEFT) {
            playerPosition.x -= speed * deltaTime * entityManager.getRenderSystem().getTileW();
            cameraComponent.camera.translate(-(speed * deltaTime), 0);
        }
        if (Moving.UP) {
            playerPosition.y += speed * deltaTime * entityManager.getRenderSystem().getTileH();
            cameraComponent.camera.translate(0, (speed * deltaTime));
        }
        if (Moving.DOWN) {
            playerPosition.y -= speed * deltaTime * entityManager.getRenderSystem().getTileH();
            cameraComponent.camera.translate(0, -(speed * deltaTime));
        }
        
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.LEFT) {
            Moving.LEFT = false;
        }

        if(keycode == Input.Keys.RIGHT) {
            Moving.RIGHT = false;
        }

        if(keycode == Input.Keys.UP) {
            Moving.UP = false;
        }

        if(keycode == Input.Keys.DOWN) {
            Moving.DOWN = false;
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {  //TODO:  Figure out how to do movement from touchDown.
        PositionComponent position = getPlayerPosition();

        RenderSystem renderSys = engine.getSystem(RenderSystem.class);
        Integer camMaxX = renderSys.mapWidth;  // - (int)renderSys.camera.viewportWidth/2;
        Integer camMaxY = renderSys.mapHeight; // - (int)renderSys.camera.viewportHeight/2;
        Integer pcMaxY = renderSys.mapWidth;   //+(int)(renderSys.camera.viewportHeight/2);
        Integer pcMaxX = renderSys.mapHeight; //+(int)(renderSys.camera.viewportWidth/2);


        CameraComponent cameraPosition = renderSys.getCameraComponent();


        //TODO fix camera positioning code after testing.
        if(keycode == Input.Keys.LEFT) {
            //pcWalkDirection = "LEFT";
            //camera.translate(-1,0);
            position.direction = "LEFT";
            if (position.x > 0) {
                Moving.LEFT = true;
            }
            else {
                Moving.LEFT = false;
                position.x = 0;
            }
        }
        if(keycode == Input.Keys.RIGHT) {
            //pcWalkDirection = "RIGHT";
            //camera.translate(1,0);
            position.direction = "RIGHT";
            if (position.x < pcMaxX-1) {
                Moving.RIGHT = true;
            }
            else {
                Moving.RIGHT = false;
                position.x = pcMaxX-1;
            }
        }
        if(keycode == Input.Keys.UP) {
            //pcWalkDirection = "UP";
            //camera.translate(0, 1);
            position.direction = "UP";
            if (position.y < pcMaxY-1) {
                Moving.UP = true;
            }
            else {
                Moving.UP = false;
                position.y = pcMaxY-1;
            }
        }
        if(keycode == Input.Keys.DOWN) {
            //pcWalkDirection = "DOWN";
           //camera.translate(0,-1);
            position.direction = "DOWN";
            if (position.y > 0) {
                Moving.DOWN = true;
            }
            else {
                Moving.DOWN = false;
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
        /*
        int x, y;

        int h = (int)Gdx.graphics.getHeight();

        x = screenX/32;
        y = -1*(screenY - h)/32;

        System.out.print("X:");
        System.out.print(x);
        System.out.print(" Y:");
        System.out.print(y);
        System.out.print("\n");
        */
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



    //helpers
    TouchpadComponent getTouchpadComponent() {
        Entity tmpTpEntity = engine.getEntitiesFor(Family.all(TouchpadComponent.class).get()).first();
        return tmpTpEntity.getComponent(TouchpadComponent.class);
    }

    PositionComponent getPlayerPosition() {
        Entity playerCharacter = engine.getEntitiesFor(Family.all(MainCharacterComponent.class).get()).first();
        return playerCharacter.getComponent(PositionComponent.class);
    }

    void setPlayerDirection(float x, float y){
        PositionComponent position = getPlayerPosition();
        if(x > 0 && y > 0){
            if(x>y) {
                position.direction = "RIGHT";
            }
            else{
                position.direction = "UP";
            }
            return;
        }

        if(x > 0 && y < 0){
            if(x>y) {
                position.direction = "RIGHT";
            }
            else{
                position.direction = "DOWN";
            }
            return;

        }
        if(x < 0 && y < 0){
            if(x<y) {
                position.direction = "LEFT";
            }
            else{
                position.direction = "DOWN";
            }
            return;

        }

        if(x < 0 && y > 0){
            if(x<y) {
                position.direction = "LEFT";
            }
            else{
                position.direction = "UP";
            }
            return;

        }



    }

}

//TODO:  Movement is a little broken.  It is currently set to detect if the key is up or down and move accordingly.  It doesn't consistently do so.