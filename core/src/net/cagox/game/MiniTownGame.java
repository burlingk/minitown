package net.cagox.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MiniTownGame extends ApplicationAdapter implements InputProcessor {
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	SpriteBatch sb;
	Texture texture;
	Sprite sprite;


	float gameScale;
	float cellSize;
	float w, h;

	@Override
	public void create () {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		gameScale = w/320;
		cellSize = gameScale*32;

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("minitown.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, gameScale);
		Gdx.input.setInputProcessor(this);

		sb = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("sprite.png"));
		sprite = new Sprite(texture);
		sprite.setScale(gameScale);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		sprite.setX(w/2);
		sprite.setY(w/2);
		sb.begin();
		sprite.draw(sb);
		sb.end();

	}

	public void resize() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		gameScale = w/320;
		cellSize = gameScale*32;

		sprite.setScale(gameScale);

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {  //TODO:  Figure out how to do movement from touchDown.
		if(keycode == Input.Keys.LEFT)
			camera.translate((-32*gameScale),0);
		if(keycode == Input.Keys.RIGHT)
			camera.translate((32*gameScale),0);
		if(keycode == Input.Keys.UP)
			camera.translate(0,(32*gameScale));
		if(keycode == Input.Keys.DOWN)
			camera.translate(0,(-32*gameScale));
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