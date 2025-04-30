package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.Input;
import static io.github.aarojklei75.pocketminer.Textures.*;

public class Main implements ApplicationListener {

    Texture currentToolTexture;
    Texture currentResourceTexture;
    Texture[] resourceTextures;
    Texture backgroundTexture;

    // Health Bar
    HealthBar healthBar;

    // Viewport style
    FitViewport viewport;

    // Sprites
    SpriteBatch spriteBatch;
    Sprite resourceSprite;
    Sprite minerSprite;
    Sprite toolSprite;
    Sprite resourceChangeSprite;
    Sprite toolChangeSprite;
    Sprite resetScoreSprite;

    // Rectangles for click detection
    Rectangle minerRectangle;
    Rectangle resourceBounds;
    Rectangle resourceChangeBounds;
    Rectangle toolChangeBounds;
    Rectangle resetScoreBounds;

    // Score
    BitmapFont font;
    int score;
    // Switches
    int currentResourceIndex = 1;
    int currentTool = 1;
    // Swing Animation
    float pickaxeAngle = 0f;
    boolean isSwinging = false;
    float swingTime = 0f;
    final float SWING_DURATION = 0.2f;//200 ms

    // Timer Settings for resource change
    float timeSeconds = 0f;
    float period = 20f;

    // LibGDX save-system
    Preferences prefs;

    // JSON-based saving
    private static final String SAVE_FILE = "savegame.json";
    private Json json;

    // Method that saves the game
    private void saveGame() {
        SaveData data = new SaveData();
        data.score = score;
        data.currentTool = currentTool;
        data.currentResource = currentResourceIndex;

        FileHandle file = Gdx.files.local(SAVE_FILE);
        file.writeString(json.toJson(data), false);
    }

    // Method that loads the game


    @Override
    public void create () {
        // Load assets
        Textures.load();

        resourceTextures = new Texture[] {
            Textures.resourceTexture1,
            Textures.resourceTexture2,
            Textures.resourceTexture3,
            Textures.resourceTexture4,
            Textures.resourceTexture5,
            Textures.resourceTexture6
        };

        backgroundTexture = Textures.backgroundTexture;
        Texture minerTexture = Textures.minerTexture;
        currentResourceTexture = resourceTextures[currentResourceIndex];
        Texture toolTexture1 = Textures.toolTexture1;
        Texture toolTexture2 = Textures.toolTexture2;
        currentToolTexture = toolTexture1;
        Texture resourceChangeTexture = Textures.resourceChangeTexture;
        Texture toolChangeTexture = Textures.toolChangeTexture;
        Texture resetScoreTexture = Textures.resetScoreTexture;
        Texture healthBarTexture = Textures.healthBarTexture;



        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(640, 480);

        //Miner Sprite
        minerSprite = new Sprite(minerTexture);
        minerSprite.setSize(200, 200);
        minerSprite.setPosition(100, 150);

        //Pickaxe Sprites
        toolSprite = new Sprite(currentToolTexture);
        toolSprite.setSize(150, 190);
        if (currentTool == 1) {
            toolSprite.setRotation(0);
            toolSprite.setPosition(minerSprite.getX() + 85, minerSprite.getY() + 50);
        }
        else if (currentTool == 2) {
            toolSprite.setRotation(150);
            toolSprite.setPosition(185, 200);
        }


        // Button Sprite
        resourceSprite = new Sprite(currentResourceTexture);
        resourceSprite.setSize(70, 70);
        resourceSprite.setPosition(500, 150);

        // HealthBar Sprite
        healthBar = new HealthBar
            (10,
                resourceSprite.getX(),
                resourceSprite.getY() + resourceSprite.getHeight(),
                resourceSprite.getWidth(),
                15,
                healthBarTexture
            );

        //Resource change Sprite
        resourceChangeSprite = new Sprite(resourceChangeTexture);
        resourceChangeSprite.setSize(100, 50);
        resourceChangeSprite.setPosition(viewport.getWorldWidth() / 2 - 100, 400);

        //Tool change Sprite
        toolChangeSprite = new Sprite(toolChangeTexture);
        toolChangeSprite.setSize(100, 50);
        toolChangeSprite.setPosition(viewport.getWorldWidth() / 2 + 25, 400);

        //Reset Score Button
        resetScoreSprite = new Sprite(resetScoreTexture);
        resetScoreSprite.setSize(100, 50);
        resetScoreSprite.setPosition(viewport.getWorldWidth() / 2 + 150, 400);


        //Rectangle logic
        minerRectangle = new Rectangle();
        resourceBounds = new Rectangle(
            resourceSprite.getX(), resourceSprite.getY(),
            resourceSprite.getWidth(), resourceSprite.getHeight()
        );
        resourceChangeBounds = new Rectangle(
            resourceChangeSprite.getX(), resourceChangeSprite.getY(),
            resourceChangeSprite.getWidth(), resourceChangeSprite.getHeight()
        );
        toolChangeBounds = new Rectangle(
            toolChangeSprite.getX(), toolChangeSprite.getY(),
            toolChangeSprite.getWidth(), toolChangeSprite.getHeight()
        );
        resetScoreBounds = new Rectangle(
            resetScoreSprite.getX(), resetScoreSprite.getY(),
            resetScoreSprite.getWidth(), resetScoreSprite.getHeight()
        );
        toolSprite.setOrigin(
            toolSprite.getX() - toolSprite.getY()/2,
            toolSprite.getX() - toolSprite.getY()/2
        );
        minerSprite.setOrigin(
            minerSprite.getX()- minerSprite.getY()/2,
            minerSprite.getX() - minerSprite.getY()/2
        );

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        // Preferences-based saving
        prefs = Gdx.app.getPreferences("GameSave");

        // JSON-based saving
        json = new Json();

        try {
            loadGame();
        } catch (Exception e) {
            e.printStackTrace();
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
        healthBar.draw(spriteBatch);
        minerSprite.draw(spriteBatch);
        toolSprite.draw(spriteBatch);
        resourceSprite.draw(spriteBatch);
        toolChangeSprite.draw(spriteBatch);
        resourceChangeSprite.draw(spriteBatch);
        resetScoreSprite.draw(spriteBatch);

        font.draw(spriteBatch, "Score: " + score, 50,430);

        spriteBatch.end();


    }

    @Override
    public void resize (int width, int height){
        viewport.update(width, height, true);

    }

    @Override
    public void render () {
        float delta = Gdx.graphics.getDeltaTime();
        timeSeconds += delta;
        updateAnimation(delta);
        draw();
        input();
        logic(timeSeconds,period);

        // Manual Save/Load via key input (for debugging)
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            saveGame();
            System.out.println("Game saved.");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            loadGame();
            System.out.println("Game loaded.");
        }

        // Clear the screen
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing with the sprite batch
        //batch.begin();

        // Draw the sprite (it will rotate based on the number of clicks)
        //sprite.draw(batch);

        // End drawing with the sprite batch
        //batch.end
    }

    @Override
    public void pause () {
        saveGame();
    }

    @Override
    public void resume () {

    }

    @Override
    public void dispose () {
        spriteBatch.dispose();
        font.dispose();
        Textures.dispose();
        //prefs.flush();
    }

    public void input() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPos = new Vector2(screenX, screenY);
                viewport.unproject(touchPos);

                // Resource change button logic
                if (resourceChangeBounds.contains(touchPos.x, touchPos.y)) {
                    changeResource();
                    saveGame();
                    return true;
                }

                //Tool change button logic
                if (toolChangeBounds.contains(touchPos.x, touchPos.y)) {
                    if (currentTool == 1) {
                        currentToolTexture = toolTexture2;
                        currentTool = 2;
                    } else {
                        currentToolTexture = toolTexture1;
                        currentTool = 1;
                    }
                    toolSprite.setTexture(currentToolTexture);
                    saveGame();
                    return true;
                }
                if (resetScoreBounds.contains(touchPos.x, touchPos.y)) {
                    score = 0;
                }

                // Resource click logic
                if (resourceBounds.contains(touchPos.x, touchPos.y)) {
                    if (!healthBar.isEmpty()) {
                        healthBar.reduceHealth();
                    }

                    if (healthBar.isEmpty()) {
                        healthBar.reset();
                        score++;
                        changeResource();
                    }

                    if (!isSwinging) {
                        isSwinging = true;
                        swingTime = 0f;
                    }

                    return true;
                }


                return false;
            }
        });
    }
    public void logic(float timeSeconds, float period) {
        if (this.timeSeconds > period) {
            this.timeSeconds -= period;
            changeResource();
        }
    }
    public void changeResource() {
        currentResourceIndex = (currentResourceIndex + 1) % resourceTextures.length;
        currentResourceTexture = resourceTextures[currentResourceIndex];
        resourceSprite.setTexture(currentResourceTexture);

    }
    private void updateAnimation(float delta) {
        if (isSwinging) {
            swingTime += delta;

            float progress = swingTime / SWING_DURATION;
            System.out.print("Progress = " + progress);

            if (progress < 0.5f) {
                pickaxeAngle = -90 * (progress * 2); // swing down
            } else if (progress < 1f) {
                pickaxeAngle = -90 * (2 - progress * 2); // swing back up
            } else {
                isSwinging = false;
                pickaxeAngle = 0f;
                swingTime = 0f;
            }
            toolSprite.setRotation(pickaxeAngle); // update rotation
        }
    }

    private void loadGame() {
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (file.exists()) {
            SaveData data = json.fromJson(SaveData.class, file.readString());
            score = data.score;
            currentTool = data.currentTool;
            currentResourceIndex = data.currentResource;

            currentResourceTexture = resourceTextures[currentResourceIndex];
            resourceSprite.setTexture(currentResourceTexture);

            currentToolTexture = (currentTool == 1) ? Textures.toolTexture1 : Textures.toolTexture2;
            toolSprite.setTexture(currentToolTexture);

        }
    }
}
