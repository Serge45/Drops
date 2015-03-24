package com.serge45497.game.drops;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by serge.lu on 2015/3/23.
 */
public class WaterDrop extends Game {
    private SpriteBatch mBatch;
    private BitmapFont mFont;

    public SpriteBatch batch() {
        return mBatch;
    }

    public BitmapFont font() {
        return mFont;
    }

    @Override
    public void create() {
        mBatch = new SpriteBatch();
        mFont = new BitmapFont();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        mBatch.dispose();
        mFont.dispose();
    }
}
