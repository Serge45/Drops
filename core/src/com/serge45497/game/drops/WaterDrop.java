package com.serge45497.game.drops;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by serge.lu on 2015/3/23.
 */
public class WaterDrop extends Game {
    private SpriteBatch mBatch;
    private BitmapFont mFont;
    private ShapeRenderer mShape;

    public SpriteBatch batch() {
        return mBatch;
    }

    public BitmapFont font() {
        return mFont;
    }

    public ShapeRenderer shapeRenderer() {
        return mShape;
    }

    @Override
    public void create() {
        mBatch = new SpriteBatch();
        mFont = new BitmapFont();
        mShape = new ShapeRenderer();
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
        mShape.dispose();
    }
}
