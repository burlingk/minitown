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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import net.cagox.game.entities.components.TouchpadComponent;
import net.cagox.game.entities.EntityManager;
import net.cagox.game.entities.components.CameraComponent;
import net.cagox.game.entities.components.MainCharacterComponent;
import net.cagox.game.entities.components.PositionComponent;
import net.cagox.game.entities.components.RenderableComponent;
import net.cagox.game.entities.components.SpriteComponent;

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
    private EntityManager entityManager;

    SpriteBatch sb;
    TiledMap tiledMap;
    public OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    MapProperties mapProperties;
    public Integer mapHeight;  //TODO: create Setters and Getters for map height/width and set them to private.
    public Integer mapWidth;

    private Float tileH, tileW;



    //HashMap<String, Animation<Sprite>> pcSprite = new HashMap<String, Animation<Sprite>>();
    SpriteComponent mainCharacterSpriteComponent;
    float stateTime=0;

    final float VIRTUAL_HEIGHT = 10f;
    ResolutionFileResolver fileResolver;

    private TouchpadComponent touchpadComponent;



    public RenderSystem() {
        super();
    }


    public RenderSystem(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }



    public void addedToEngine(Engine engine) {
        this.engine = engine;

        cameraSetup();
        mapSetup();
        spriteSetup();


    }

    public void update(float deltaTime) {

        CameraComponent cameraPosition = getCameraComponent();

        float centerX = camera.viewportWidth/2f;
        float centerY = camera.viewportHeight/2f;

        //For the moment, this just gets the PC
        ImmutableArray<Entity> tmpArray = engine.getEntitiesFor(Family.all(MainCharacterComponent.class).get());
        Entity playerCharacter = tmpArray.first();
        PositionComponent position = playerCharacter.getComponent(PositionComponent.class);
        //Camera positioniong
        cameraPosition.x = position.x;
        cameraPosition.y = position.y;

        //Temporarily moved from the render() section of the code.
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        Animation<Sprite> walkAnimation = mainCharacterSpriteComponent.getSprite().get(position.direction);
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        //TODO: write propper code to track camera position in the world in relation to the main character and constrain it to the map.

        sb.begin();
        sb.draw(currentFrame, centerX*getTileW(), centerY*getTileH());
        sb.end();
        touchpadComponent.stage.act(Gdx.graphics.getDeltaTime());
        touchpadComponent.stage.draw();
    }


    public void resize(int width, int height) {
        //camera.setToOrtho(false, VIRTUAL_HEIGHT * width / (float)height, VIRTUAL_HEIGHT);
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera.setToOrtho(false, 10*aspectRatio, 10);
        tileW = (float)Gdx.graphics.getWidth()/10;
        tileH = (float)Gdx.graphics.getHeight()/10*aspectRatio;
        touchpadComponent.touchpad.setBounds(Gdx.graphics.getWidth()-138, 32*aspectRatio, 128, 128);


    }

    public CameraComponent getCameraComponent(){
        Entity tmpEntity = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).first();
        return tmpEntity.getComponent(CameraComponent.class);
    }


    //Setup Methods

    void mapSetup(){
        tiledMap = new TmxMapLoader().load("minitown.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
        mapProperties = tiledMap.getProperties();
        mapWidth = mapProperties.get("width", Integer.class);
        mapHeight = mapProperties.get("height", Integer.class);
    }

    void spriteSetup() {
        Entity tmpEntity = engine.getEntitiesFor(Family.all(MainCharacterComponent.class).get()).first();
        mainCharacterSpriteComponent = tmpEntity.getComponent(SpriteComponent.class);
        sb = new SpriteBatch();
    }


    void cameraSetup() {
        fileResolver = new ResolutionFileResolver(new InternalFileHandleResolver(), new ResolutionFileResolver.Resolution(800, 480, "480"),
                new ResolutionFileResolver.Resolution(1280, 720, "720"), new ResolutionFileResolver.Resolution(1920, 1080, "1080"));

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

        camera = new OrthographicCamera(10*aspectRatio, 10);
        camera.setToOrtho(false,10,10);
        engine.addEntity(entityManager.cameraFactory.createCameraEntity(camera));
        camera.update();

        tileH = (float)Gdx.graphics.getWidth()/32*aspectRatio;
        tileW = (float)Gdx.graphics.getHeight()/32;

        touchpadComponent = getTouchpadComponent();
        touchpadComponent.touchpad.setBounds(Gdx.graphics.getWidth()-138, 32*aspectRatio, 128, 128);

    }

    public float getTileH(){
        return tileH;
    }

    public float getTileW(){
        return tileW;
    }

    TouchpadComponent getTouchpadComponent() {
        Entity tmpTpEntity = engine.getEntitiesFor(Family.all(TouchpadComponent.class).get()).first();
        return tmpTpEntity.getComponent(TouchpadComponent.class);
    }


}
