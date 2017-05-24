package net.cagox.game;



import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;


import net.cagox.game.entities.EntityManager;

public class MiniTownGame extends ApplicationAdapter  {


	Engine engine = new Engine();
	private EntityManager entityManager;


	@Override
	public void create () {

		entityManager = new EntityManager(engine);
	}

	@Override
	public void render () {

		entityManager.update();

	}

	@Override
	public void resize (int width, int height) {
		entityManager.resize(width, height);
	}



}



//TODO: https://github.com/libgdx/libgdx/wiki/2D-Animation shows how to handle animation of the sprite.

