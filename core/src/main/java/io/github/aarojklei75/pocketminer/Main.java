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

    SpriteBatch spriteBatch;
    SpriteManager spriteManager;
    BitmapFont font;
    InputHandler inputHandler;
    GameState gameState = new GameState();
    Renderer renderer;
    UpgradeMenuMove upgradeMenuMove;
    SaveSystem saveSystem;

    final float SWING_DURATION = 0.2f;
    float timeSeconds = 0f;
    float period = 20f;

    Preferences prefs;
    private static final String SAVE_FILE = "savegame.json";
    Json json;

    @Override
    public void create() {
        saveSystem = new SaveSystem();
        Initializer initializer = new Initializer(gameState);
        initializer.init(this);
        inputHandler = new InputHandler(
            gameState,
            spriteManager,
            upgradeMenuMove,
            new Texture[]{toolTexture1, toolTexture2},
            this::changeResource,
            this::saveGame
        );
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void resize(int width, int height) {
        Settings.viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timeSeconds += delta;
        updateAnimation(delta);
        renderer.draw(gameState.score);
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

    private void logic(float timeSeconds, float period) {
        if (this.timeSeconds > period) {
            this.timeSeconds -= period;
            changeResource();
        }
    }

    private void changeResource() {
        gameState.currentResourceIndex = (gameState.currentResourceIndex + 1) % resourceTextures.length;
        spriteManager.resourceSprite.setTexture(resourceTextures[gameState.currentResourceIndex]);
        spriteManager.getHealthBar().reset();
    }

    private void updateAnimation(float delta) {
        if (gameState.isSwinging) {
            gameState.swingTime += delta;
            float progress = gameState.swingTime / SWING_DURATION;
            float pickaxeAngle;

            if (progress < 0.5f) {
                pickaxeAngle = -90 * (progress * 2);
            } else if (progress < 1f) {
                pickaxeAngle = -90 * (2 - progress * 2);
            } else {
                gameState.isSwinging = false;
                pickaxeAngle = 0f;
                gameState.swingTime = 0f;
            }
            spriteManager.toolSprite.setRotation(pickaxeAngle);
        }
    }

    public void saveGame() {
        // Save core game state using JSON
        SaveData data = new SaveData();
        data.score = gameState.score;
        data.currentTool = gameState.currentTool;
        data.currentResource = gameState.currentResourceIndex;
        data.level = gameState.level;

        FileHandle file = Gdx.files.local(SAVE_FILE);
        file.writeString(json.toJson(data), false);

        // Save additional data using SaveSystem
        saveSystem.savePlayerData(gameState.score, "Player", gameState.level);
    }

    public void loadGame() {
        // Load core game state
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (file.exists()) {
            SaveData data = json.fromJson(SaveData.class, file.readString());
            gameState.score = data.score;
            gameState.currentTool = data.currentTool;
            gameState.currentResourceIndex = data.currentResource;
            gameState.level = data.level;

            currentToolTexture = (gameState.currentTool == 1) ? toolTexture1 : toolTexture2;
            spriteManager.toolSprite.setTexture(currentToolTexture);
            spriteManager.resourceSprite.setTexture(resourceTextures[gameState.currentResourceIndex]);
        }

        // Load additional data from SaveSystem
        gameState.score = saveSystem.getScore();
        gameState.level = saveSystem.getLevel();
    }
}
