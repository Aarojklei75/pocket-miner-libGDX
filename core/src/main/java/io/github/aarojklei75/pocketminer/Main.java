package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.Input;

import static io.github.aarojklei75.pocketminer.Textures.*;

public class Main implements ApplicationListener {
    Texture currentToolTexture;
    Texture[] resourceTextures;
    Texture backgroundTexture;

    HealthBar healthBar;
    FitViewport viewport;

    SpriteBatch spriteBatch;
    SpriteManager spriteManager;

    BitmapFont font;
    int score;
    int currentResourceIndex = 1;
    int currentTool = 1;

    float pickaxeAngle = 0f;
    boolean isSwinging = false;
    float swingTime = 0f;
    final float SWING_DURATION = 0.2f;

    float timeSeconds = 0f;
    float period = 20f;

    Preferences prefs;
    private static final String SAVE_FILE = "savegame.json";
    private Json json;

    private void saveGame() {
        SaveData data = new SaveData();
        data.score = score;
        data.currentTool = currentTool;
        data.currentResource = currentResourceIndex;

        FileHandle file = Gdx.files.local(SAVE_FILE);
        file.writeString(json.toJson(data), false);
    }

    @Override
    public void create() {
        Textures.load();

        resourceTextures = new Texture[] {
            resourceTexture1, resourceTexture2, resourceTexture3,
            resourceTexture4, resourceTexture5, resourceTexture6
        };

        backgroundTexture = Textures.backgroundTexture;
        menuTexture = Textures.menuTexture;
        currentToolTexture = toolTexture1;

        viewport = new FitViewport(640, 480);
        spriteBatch = new SpriteBatch();

        spriteManager = new SpriteManager(viewport, resourceTextures, currentToolTexture, currentTool, currentResourceIndex);

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        prefs = Gdx.app.getPreferences("GameSave");
        json = new Json();

        try {
            loadGame();
        } catch (Exception e) {
            Gdx.app.error("Main", "Failed to load game: " + e.getMessage());
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPos = new Vector2(screenX, screenY);
                viewport.unproject(touchPos);

                if (spriteManager.resourceChangeBounds.contains(touchPos.x, touchPos.y)) {
                    changeResource();
                    saveGame();
                    return true;
                }

                if (spriteManager.toolChangeBounds.contains(touchPos.x, touchPos.y)) {
                    if (currentTool == 1) {
                        currentToolTexture = toolTexture2;
                        currentTool = 2;
                    } else {
                        currentToolTexture = toolTexture1;
                        currentTool = 1;
                    }
                    spriteManager.toolSprite.setTexture(currentToolTexture);
                    saveGame();
                    return true;
                }

                if (spriteManager.resetScoreBounds.contains(touchPos.x, touchPos.y)) {
                    score = 0;
                }

                if (spriteManager.upgradeBounds.contains(touchPos.x, touchPos.y)) {
                    moveOut();
                    upgradeMenu();
                    saveGame();
                    return true;
                }

                if (spriteManager.returnBounds.contains(touchPos.x, touchPos.y)) {
                    moveIn();
                    saveGame();
                    return true;
                }

                if (spriteManager.resourceBounds.contains(touchPos.x, touchPos.y)) {
                    if (!spriteManager.getHealthBar().isEmpty()) {
                        spriteManager.getHealthBar().reduceHealth();
                    }

                    if (spriteManager.getHealthBar().isEmpty()) {
                        spriteManager.getHealthBar().reset();
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

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spriteManager.getHealthBar().draw(spriteBatch);
        spriteManager.minerSprite.draw(spriteBatch);
        spriteManager.toolSprite.draw(spriteBatch);
        spriteManager.resourceSprite.draw(spriteBatch);
        spriteManager.toolChangeSprite.draw(spriteBatch);
        spriteManager.resourceChangeSprite.draw(spriteBatch);
        spriteManager.resetScoreSprite.draw(spriteBatch);
        spriteManager.upgradeSprite.draw(spriteBatch);
        spriteManager.menuSprite.draw(spriteBatch);
        spriteManager.returnSprite.draw(spriteBatch);
        font.draw(spriteBatch, "Score: " + score, 15, 455);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timeSeconds += delta;
        updateAnimation(delta);
        draw();
        input();
        logic(timeSeconds, period);

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            saveGame();
            Gdx.app.log("Main", "Game saved.");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            loadGame();
            Gdx.app.log("Main", "Game loaded.");
        }
    }

    @Override
    public void pause() {
        saveGame();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        spriteManager.getHealthBar().dispose();
        Textures.dispose();
    }

    public void input() {
        // Empty, as InputProcessor is in create()
    }

    public void logic(float timeSeconds, float period) {
        if (this.timeSeconds > period) {
            this.timeSeconds -= period;
            changeResource();
        }
    }

    public void changeResource() {
        currentResourceIndex = (currentResourceIndex + 1) % resourceTextures.length;
        spriteManager.resourceSprite.setTexture(resourceTextures[currentResourceIndex]);
    }

    private void updateAnimation(float delta) {
        if (isSwinging) {
            swingTime += delta;
            float progress = swingTime / SWING_DURATION;

            if (progress < 0.5f) {
                pickaxeAngle = -90 * (progress * 2);
            } else if (progress < 1f) {
                pickaxeAngle = -90 * (2 - progress * 2);
            } else {
                isSwinging = false;
                pickaxeAngle = 0f;
                swingTime = 0f;
            }
            spriteManager.toolSprite.setRotation(pickaxeAngle);
        }
    }

    private void loadGame() {
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (file.exists()) {
            SaveData data = json.fromJson(SaveData.class, file.readString());
            score = data.score;
            currentTool = data.currentTool;
            currentResourceIndex = data.currentResource;

            currentToolTexture = (currentTool == 1) ? toolTexture1 : toolTexture2;
            spriteManager.toolSprite.setTexture(currentToolTexture);
            spriteManager.resourceSprite.setTexture(resourceTextures[currentResourceIndex]);
        }
    }

    public void moveOut(){
        spriteManager.resetScoreSprite.setPosition(75, 500);
        spriteManager.resetScoreBounds.setPosition(spriteManager.resetScoreSprite.getX(), spriteManager.resetScoreSprite.getY());
        spriteManager.resourceChangeSprite.setPosition(75, 500);
        spriteManager.resourceChangeBounds.setPosition(spriteManager.resourceChangeSprite.getX(), spriteManager.resourceChangeSprite.getY());
        spriteManager.resourceSprite.setPosition(75, 500);
        spriteManager.resourceBounds.setPosition(spriteManager.resourceSprite.getX(),spriteManager.resourceSprite.getY());
        spriteManager.toolChangeSprite.setPosition(75, 500);
        spriteManager.toolChangeBounds.setPosition(spriteManager.toolSprite.getX(), spriteManager.toolSprite.getY());
        spriteManager.upgradeSprite.setPosition(75, 500);

    }

    public void moveIn(){
        spriteManager.resetScoreSprite.setPosition(viewport.getWorldWidth() / 2 + 75, 420);
        spriteManager.resetScoreBounds.setPosition(spriteManager.resetScoreSprite.getX(), spriteManager.resetScoreSprite.getY());
        spriteManager.resourceChangeSprite.setPosition(viewport.getWorldWidth() / 2 - 175, 420);
        spriteManager.resourceChangeBounds.setPosition(spriteManager.resourceChangeSprite.getX(), spriteManager.resourceChangeSprite.getY());
        spriteManager.resourceSprite.setPosition(Settings.RS_X, Settings.RS_Y);
        spriteManager.resourceBounds.setPosition(spriteManager.resourceSprite.getX(),spriteManager.resourceSprite.getY());
        spriteManager.toolChangeSprite.setPosition(viewport.getWorldWidth() / 2 - 50, 420);
        spriteManager.toolChangeBounds.setPosition(spriteManager.toolChangeSprite.getX(), spriteManager.toolChangeSprite.getY());
        spriteManager.upgradeSprite.setPosition(viewport.getWorldWidth() / 2 + 200, 420);
        spriteManager.returnSprite.setPosition(75, -500);
        spriteManager.returnBounds.setPosition(spriteManager.returnBounds.getX(), spriteManager.returnBounds.getY());
        spriteManager.menuSprite.setPosition(75,-1000);

    }

    public void upgradeMenu(){
        spriteManager.menuSprite.setPosition(20,20);
        spriteManager.returnSprite.setPosition(20, 20);
        spriteManager.returnBounds.setPosition(spriteManager.returnSprite.getX(), spriteManager.returnSprite.getY());

    }
}
