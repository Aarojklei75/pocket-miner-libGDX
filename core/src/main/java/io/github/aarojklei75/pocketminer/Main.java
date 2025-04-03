package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Preferences;

public class Main implements ApplicationListener {

    //Textures
    Texture backgroundTexture;
    Texture minerTexture;
    Texture buttonTexture;
    Texture toothpickTexture;

    // Viewport style
    FitViewport viewport;

    // Sprites
    Array<Sprite> dropSprites;
    SpriteBatch spriteBatch;
    Sprite buttonSprite;
    Sprite minerSprite;
    Sprite toothpickSprite;

    // Rectangles for click detection
    Rectangle minerRectangle;
    Rectangle buttonBounds;

    // Score
    BitmapFont font;
    int score;

    // Save System
    private Preferences prefs;
    static final String KEY_SCORE = "score";
    static final String KEY_LEVEL = "level";
    static final String KEY_PLAYER_NAME = "playerName";


    @Override
    public void create () {
        backgroundTexture = new Texture("simple-level.png");
        minerTexture = new Texture("miner.png");
        buttonTexture = new Texture("mineableobject-1.png");
        toothpickTexture = new Texture("pickaxe-1.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(640, 480);

        //Miner Sprite
        minerSprite = new Sprite(minerTexture);
        minerSprite.setSize(100, 100);
        minerSprite.setPosition(100, 150);

        //Pickaxe Sprites
        toothpickSprite = new Sprite(toothpickTexture);
        toothpickSprite.setSize(100, 100);
        toothpickSprite.setPosition(150, 150);
        toothpickSprite.setOrigin(150,150);
        toothpickSprite.setRotation(90);

        //Button Sprite
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setSize(70, 70);
        buttonSprite.setPosition(500, 150);

        //Rectangle logic
        minerRectangle = new Rectangle();
        buttonBounds = new Rectangle(
            buttonSprite.getX(), buttonSprite.getY(),
            buttonSprite.getWidth(), buttonSprite.getHeight()
            );

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);




    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        minerSprite.setOrigin(100,150);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        toothpickSprite.draw(spriteBatch);
        minerSprite.draw(spriteBatch);
        buttonSprite.draw(spriteBatch);
        font.draw(spriteBatch, "Score: " + score, 50,430);

        spriteBatch.end();

        buttonBounds.setPosition(buttonSprite.getX(), buttonSprite.getY());
    }

    // Save system
    public void savePlayerData(int score, int level, String playerName) {
        // Store data using the appropriate data types
        prefs.putInteger(KEY_SCORE, score);
        prefs.putInteger(KEY_LEVEL, level);
        prefs.putString(KEY_PLAYER_NAME, playerName);

    }





    @Override
    public void resize (int width, int height){
        viewport.update(width, height, true);

    }

    @Override
    public void render () {
        draw();
        input();
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing with the sprite batch
        batch.begin();

        // Draw the sprite (it will rotate based on the number of clicks)
        sprite.draw(batch);

        // End drawing with the sprite batch
        batch.end
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void dispose () {
        backgroundTexture.dispose();
        spriteBatch.dispose();
        font.dispose();
    }

    public void input() {

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown (int screenX,int screenY,int pointer,int button) {
                float worldX = screenX;
                float worldY = viewport.getScreenHeight() - screenY;


                if (buttonBounds.contains(worldX, worldY)) {
                    score++;
                    if (score % 2 == 0) {
                        minerSprite.rotate(90);
                    }
                    if (score % 2 == 1) {
                        minerSprite.setSize(100, 100);
                        minerSprite.rotate(90);
                    }

                }
                return true;

            }


        });

    }
    public void logic() {

    }
}


/*import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
//hello
//Test for audrey
//Test for isabel

public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sound dropSound;
    Music music;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Sprite bucketSprite;
    Vector2 touchPos;
    Array<Sprite> dropSprites;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;

    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);
        touchPos = new Vector2();
        dropSprites = new Array<>();
        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketSprite.translateX(-speed * delta);
        }

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

        float delta = Gdx.graphics.getDeltaTime();
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (bucketRectangle.overlaps(dropRectangle)) {
                dropSprites.removeIndex(i);
                dropSound.play();
            }
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createDroplet();
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        bucketSprite.draw(spriteBatch);

        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    private void createDroplet() {
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
*/
