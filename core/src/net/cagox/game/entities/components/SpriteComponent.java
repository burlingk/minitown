package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 *  A Component that gives an entity a sprite.
 *  <p>
 *  This component will hold the various data pertaining to the sprite and animation.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */

public class SpriteComponent implements Component {
    //TODO:  There will be a lot to do here as I move the current sprite code into the sprite component.

    private boolean isAnimated;     //TODO:  Implement code to make the isAnimated flag meaningful.
    private boolean isDirectional;  //TODO: Implement code to make the isDirectional flag meaningful.
    private float frameDuration;
    private HashMap<String, Animation<Sprite>> entitySprite = new HashMap<String, Animation<Sprite>>();
    public float stateTime=0;


    public SpriteComponent() {

    }


    public SpriteComponent(String filename, int spriteWidth, int spriteHeight, float frameDuration) {

        isAnimated = true;
        isDirectional = true;

        Texture walkSheet = new Texture(Gdx.files.internal(filename));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, spriteWidth, spriteHeight);
        int h = walkSheet.getHeight()/spriteHeight;
        int w = walkSheet.getWidth()/spriteWidth;

        Sprite[][] tmpSprite = new Sprite[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                tmpSprite[y][x] = new Sprite(tmp[y][x]);
                tmpSprite[y][x].setSize(1.8f,1.8f);
            }

        }
        entitySprite.put("UP", new Animation<Sprite>(frameDuration, tmpSprite[0]));
        entitySprite.put("LEFT", new Animation<Sprite>(frameDuration, tmpSprite[1]));
        entitySprite.put("DOWN", new Animation<Sprite>(frameDuration, tmpSprite[2]));
        entitySprite.put("RIGHT", new Animation<Sprite>(frameDuration, tmpSprite[3]));
        stateTime = 0f;
    }



    /**
     *  A method to expose the component's sprite.  It takes no arguments.
     *
     * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
     * @version     0.1
     * @since       0.1
     * @return HashMap<String, Animation<Sprite>>
     */
    public HashMap<String, Animation<Sprite>> getSprite() {
        return entitySprite;
    }





}
