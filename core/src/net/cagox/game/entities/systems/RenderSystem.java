package net.cagox.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import net.cagox.game.entities.components.CameraComponent;
import net.cagox.game.entities.components.MainCharacterComponent;
import net.cagox.game.entities.components.PlayerCharacterComponent;
import net.cagox.game.entities.components.PositionComponent;
import net.cagox.game.entities.components.RenderableComponent;

import java.util.HashMap;

/**
 *  This class will be the system that handles rendering of graphics.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     1.0
 * @since       1.0
 */

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private Engine engine;



    SpriteBatch sb;
    Texture img;
    TiledMap tiledMap;
    public OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    MapProperties mapProperties;
    public Integer mapHeight;  //TODO: create Setters and Getters for map height/width and set them to private.
    public Integer mapWidth;

    Float cameraX;
    Float cameraY;



    MapProperties prop;

    HashMap<String, Animation<Sprite>> pcSprite = new HashMap<String, Animation<Sprite>>();
    Texture pcWalkSheet;
    String pcWalkDirection;
    float stateTime;

    final float VIRTUAL_HEIGHT = 10f;
    ResolutionFileResolver fileResolver;

    Float scale;

    public RenderSystem() {
        super();

        fileResolver = new ResolutionFileResolver(new InternalFileHandleResolver(), new ResolutionFileResolver.Resolution(800, 480, "480"),
                new ResolutionFileResolver.Resolution(1280, 720, "720"), new ResolutionFileResolver.Resolution(1920, 1080, "1080"));

        scale = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();


        sb = new SpriteBatch();

        camera = new OrthographicCamera();
        //camera.setToOrtho(false,w,h);
        camera.update();

        tiledMap = new TmxMapLoader().load("minitown.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scale * 1/32f);
        mapProperties = tiledMap.getProperties();
        mapWidth = mapProperties.get("width", Integer.class);
        mapHeight = mapProperties.get("height", Integer.class);


        prop = tiledMap.getProperties();


        //pcSprite ;

        pcWalkSheet = new Texture(Gdx.files.internal("barbarian.png"));
        TextureRegion[][] tmp = TextureRegion.split(pcWalkSheet, 64, 64);
        Sprite[][] tmpSprite = new Sprite[4][9];

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 9; y++) {
                tmpSprite[x][y] = new Sprite(tmp[x][y]);
                tmpSprite[x][y].setSize(1.8f,1.8f);
            }

        }


        pcSprite.put("UP", new Animation<Sprite>(0.1f, tmpSprite[0]));
        pcSprite.put("LEFT", new Animation<Sprite>(0.1f, tmpSprite[1]));
        pcSprite.put("DOWN", new Animation<Sprite>(0.1f, tmpSprite[2]));
        pcSprite.put("RIGHT", new Animation<Sprite>(0.1f, tmpSprite[3]));



        stateTime = 0f;

        pcWalkDirection = "RIGHT";





    }



    public void addedToEngine(Engine engine) {
        this.engine = engine;
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RenderableComponent.class).get());
        //TODO: Update this method once Renderables have a material component added.  Systems should map all components they use often.




    }

    public void update(float deltaTime) {

        CameraComponent cameraPosition = getCameraComponent();

        Float drawX;
        float drawY;
        float tileStepX = camera.viewportWidth/32f;
        float tileStepY = camera.viewportHeight/32f;

        //For the moment, this just gets the PC
        ImmutableArray<Entity> tmpArray = engine.getEntitiesFor(Family.all(MainCharacterComponent.class).get());
        Entity playerCharacter = tmpArray.first();
        PositionComponent position = playerCharacter.getComponent(PositionComponent.class);


        //Camera positioniong
        //TODO:  Read these comments:

        //Temporarily moved from the render() section of the code.
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        Animation<Sprite> walkAnimation = pcSprite.get(position.direction);
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        //TODO: write propper code to track camera position in the world in relation to the main character and constrain it to the map.
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();
        Float cameraW = camera.viewportWidth;
        Float cameraH = camera.viewportHeight;

        drawX = (position.x) * tileStepX; //*scale; //position.x * 32;
        drawY = (position.y) * tileStepY; //*scale; //position.y * 32;

        //camera.position.x = cameraW/2 + cameraPosition.x;
        //camera.position.y = cameraH/2 + cameraPosition.y;
        //camera.position.x = position.x;
        //camera.position.y = position.y;


        sb.begin();
        sb.draw(currentFrame, drawX, drawY);
        sb.end();

        /**
        System.out.print(camera.position.x);
        System.out.print(", ");
        System.out.print(camera.position.y);
        System.out.print("\n");
        **/



    }


    public void resize(int width, int height) {
        camera.setToOrtho(false, VIRTUAL_HEIGHT * width / (float)height, VIRTUAL_HEIGHT);
        scale = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
    }

    public CameraComponent getCameraComponent(){
        Entity tmpEntity = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).first();
        return tmpEntity.getComponent(CameraComponent.class);
    }



}
