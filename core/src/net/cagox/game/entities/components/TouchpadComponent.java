package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Contains required pieces for the TouchPad.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */
public class TouchpadComponent implements Component {
    public Touchpad touchpad;

    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private TouchpadStyle touchpadStyle;




    /**
     * Loads the current version of the skin and creates the Touchpad.
     * <p>
     * At the moment, the information is loaded directly in code.
     * Eventually I intend to learn how to create a proper UI Skin json file to handle it.
     *
     */
    public TouchpadComponent() {
        //TODO: Learn how to create a proper json skin file.
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(Gdx.files.internal("background.png")));
        touchpadSkin.add("touchKnob", new Texture(Gdx.files.internal("knob.png")));
        touchpadStyle = new TouchpadStyle();

        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(10, touchpadStyle);

        touchpad.setBounds(10, 10, 128, 128);
    }

}
