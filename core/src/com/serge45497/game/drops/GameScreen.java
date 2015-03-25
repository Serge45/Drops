package com.serge45497.game.drops;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by serge.lu on 2015/3/24.
 */
public class GameScreen implements Screen {
    final WaterDrop mGame;
    private Texture mDropImage;
    private Texture mBucketImage;
    private Sound mDropSound;
    private Music mRainMusic;
    private OrthographicCamera mCamera;
    private Rectangle mBucket;
    private Array<Rectangle> mRainrrops;
    private long mLastDropTime;
    private int mScore;
    private String mScorePrefix = "Score: ";
    private float mWaterHeight;
    private float[] mWaterSurface = new float[124];
    private int[] mWaterX = new int[60];
    private int[] mWaterY = new int[60];
    private float mTime;


    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 480 - 64);
        raindrop.y = 800;
        raindrop.width = 64;
        raindrop.height = 64;
        mRainrrops.add(raindrop);
        mLastDropTime = TimeUtils.nanoTime();

    }

    public GameScreen(final WaterDrop game) {
        mGame = game;
        mScore = 0;
        mWaterHeight = 0.f;
        mRainrrops = new Array<Rectangle>();
        spawnRaindrop();
        mDropImage = new Texture(Gdx.files.internal("droplet.png"));
        mBucketImage = new Texture(Gdx.files.internal("bucket.png"));

        mDropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        mRainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));
        mRainMusic.setLooping(true);

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 480, 800);

        mBucket = new Rectangle();
        mBucket.x = 480 / 2 - 64 / 2;
        mBucket.y = 40;
        mBucket.width = 64;
        mBucket.height = 64;

        for (int i = 2; i < mWaterSurface.length; ++i) {
            if (i % 2 == 0) {
                mWaterSurface[i] = 4 * i;
            }
        }

        mWaterSurface[0] = 0;
        mWaterSurface[1] = 0;
        mWaterSurface[2] = 480;
        mWaterSurface[3] = 0;

        for (int i = 2; i < mWaterX.length; ++i) {
            mWaterX[i] = 2 * i;
        }

        for (int i = 2; i < mWaterY.length; ++i) {
            mWaterY[i] = 2 * i + 1;
        }
    }

    @Override
    public void show() {
        mRainMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mCamera.update();
        mGame.batch().setProjectionMatrix(mCamera.combined);
        mGame.shapeRenderer().setProjectionMatrix(mCamera.combined);

        mTime += delta;

        for (int y : mWaterY) {
            mWaterSurface[y] = mWaterHeight + 4 * (float)Math.sin((y + mTime) * Math.PI);
        }

        mGame.shapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        mGame.shapeRenderer().setColor(0, 0, 0.5f, 0.8f);
        mGame.shapeRenderer().polygon(mWaterSurface);
        //mGame.shapeRenderer().rect(0, 0, 480, mWaterHeight);
        mGame.shapeRenderer().end();

        //painting
        mGame.batch().begin();
        mGame.batch().draw(mBucketImage, mBucket.x, mBucket.y);

        for (Rectangle raindrop : mRainrrops) {
            mGame.batch().draw(mDropImage, raindrop.x, raindrop.y);
        }

        mGame.font().draw(mGame.batch(), mScorePrefix + mScore, 0, 20);
        mGame.batch().end();
        //end

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mCamera.unproject(touchPos);
            mBucket.x = Math.max(Math.min(touchPos.x - 64 / 2, 480 - 64), 0);
        }

        if (TimeUtils.nanoTime() - mLastDropTime > 1000000000) {
            spawnRaindrop();
        }

        Iterator<Rectangle> iter = mRainrrops.iterator();

        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

            if (raindrop.y + 64 < 0) {
                iter.remove();
                mWaterHeight += 5.0f;
                continue;
            }

            if (raindrop.overlaps(mBucket)) {
                mDropSound.play();
                iter.remove();
                ++mScore;
            }
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
        mDropImage.dispose();
        mBucketImage.dispose();
        mDropSound.dispose();
        mRainMusic.dispose();
    }
}
