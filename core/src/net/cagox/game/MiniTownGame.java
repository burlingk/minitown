package net.cagox.game;

import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import net.cagox.game.entities.EntityManager;

public class MiniTownGame extends ApplicationAdapter implements InputProcessor {
	final float VIRTUAL_HEIGHT = 10f;
	ResolutionFileResolver fileResolver;

	Engine engine = new Engine();
	private EntityManager entityManager;

	SpriteBatch sb;



	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;


	HashMap<String, Animation<Sprite>> pcSprite = new HashMap<String, Animation<Sprite>>();
	Texture pcWalkSheet;
	String pcWalkDirection;
	float stateTime;



	@Override
	public void create () {
		sb = new SpriteBatch();
		entityManager = new EntityManager(engine, sb);




		fileResolver = new ResolutionFileResolver(new InternalFileHandleResolver(), new Resolution(800, 480, "480"),
				new Resolution(1280, 720, "720"), new Resolution(1920, 1080, "1080"));


		camera = new OrthographicCamera();
		//camera.setToOrtho(false,w,h);
		camera.update();

		tiledMap = new TmxMapLoader().load("minitown.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
		Gdx.input.setInputProcessor(this);

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

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		Animation<Sprite> walkAnimation = pcSprite.get(pcWalkDirection);
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);


		sb.begin();
		entityManager.update();
		sb.draw(currentFrame, 32, 32);
		sb.end();

	}

	public void resize (int width, int height) {
		camera.setToOrtho(false, VIRTUAL_HEIGHT * width / (float)height, VIRTUAL_HEIGHT);
		//sb.setProjectionMatrix(camera.combined);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {  //TODO:  Figure out how to do movement from touchDown.
		if(keycode == Input.Keys.LEFT) {
			pcWalkDirection = "LEFT";
			camera.translate(-1,0);
		}
		if(keycode == Input.Keys.RIGHT) {
			pcWalkDirection = "RIGHT";
			camera.translate(1,0);
		}
		if(keycode == Input.Keys.UP) {
			pcWalkDirection = "UP";
			camera.translate(0, 1);
		}
		if(keycode == Input.Keys.DOWN) {
			pcWalkDirection = "DOWN";
			camera.translate(0,-1);
		}
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
		if(keycode == Input.Keys.ESCAPE)
			Gdx.app.exit();
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



//TODO: https://github.com/libgdx/libgdx/wiki/2D-Animation shows how to handle animation of the sprite.

