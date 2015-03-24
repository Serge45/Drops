package com.serge45497.game.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by serge.lu on 2015/3/24.
 */
public class MainMenuScreen implements Screen {
    final WaterDrop mGame;
    private OrthographicCamera mCamera;

    public MainMenuScreen(final WaterDrop game) {
        mGame = game;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 480, 800);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mCamera.update();
        mGame.batch().setProjectionMatrix(mCamera.combined);

        mGame.batch().begin();
        mGame.font().draw(mGame.batch(), "Welcome to Waterdrop!", 150, 450);
        mGame.font().draw(mGame.batch(), "Tap to begin!", 150, 400);
        mGame.batch().end();

        if (Gdx.input.isTouched()) {
            mGame.setScreen(new GameScreen(mGame));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
