package com.serge45497.game.drops;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by serge.lu on 2015/3/23.
 */
public class WaterDrop implements ApplicationListener {
    private Texture mDropImage;
    private Texture mBucketImage;
    private Sound mDropSound;
    private Music mRainMusic;
    private SpriteBatch mBatch;
    private OrthographicCamera mCamera;
    private Rectangle mBucket;
    private Array<Rectangle> mRainrrops;
    private long mLastDropTime;

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 480 - 64);
        raindrop.y = 800;
        raindrop.width = 64;
        raindrop.height = 64;
        mRainrrops.add(raindrop);
        mLastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void create() {
        mRainrrops = new Array<Rectangle>();
        spawnRaindrop();
        mDropImage = new Texture(Gdx.files.internal("droplet.png"));
        mBucketImage = new Texture(Gdx.files.internal("bucket.png"));

        mDropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        mRainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));
        mRainMusic.setLooping(true);
        mRainMusic.play();

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 480, 800);
        mBatch = new SpriteBatch();

        mBucket = new Rectangle();
        mBucket.x = 480 / 2 - 64 / 2;
        mBucket.y = 20;
        mBucket.width = 64;
        mBucket.height = 64;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl20.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mCamera.update();
        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.begin();
        mBatch.draw(mBucketImage, mBucket.x, mBucket.y);

        for (Rectangle raindrop : mRainrrops) {
            mBatch.draw(mDropImage, raindrop.x, raindrop.y);
        }
        mBatch.end();

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
                continue;
            }

            if (raindrop.overlaps(mBucket)) {
                mDropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        mDropImage.dispose();
        mBucketImage.dispose();
        mDropSound.dispose();
        mRainMusic.dispose();
        mBatch.dispose();
    }
}
