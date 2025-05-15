package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class Initializer {
    private final GameState gameState;

    public Initializer(GameState gameState) {
        this.gameState = gameState;
    }

    public void init(Main main) {
        Textures.load();

        Texture[] resourceTextures = new Texture[]{
            Textures.resourceTexture1, Textures.resourceTexture2, Textures.resourceTexture3,
            Textures.resourceTexture4, Textures.resourceTexture5, Textures.resourceTexture6
        };

        Texture backgroundTexture = Textures.backgroundTexture;
        Textures.menuTexture = Textures.menuTexture;
        Texture currentToolTexture = Textures.toolTexture1;

        BitmapFont font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        SpriteBatch spriteBatch = new SpriteBatch();

        SpriteManager spriteManager = new SpriteManager(
            Settings.viewport, resourceTextures, currentToolTexture,
            gameState.currentTool, gameState.currentResourceIndex
        );

        Renderer renderer = new Renderer(spriteBatch, Settings.viewport, spriteManager, font, backgroundTexture);

        UpgradeMenuMove upgradeMenuMove = new UpgradeMenuMove(spriteManager);

        Preferences prefs = Gdx.app.getPreferences("GameSave");
        Json json = new Json();

        // Assign back to main
        main.resourceTextures = resourceTextures;
        main.backgroundTexture = backgroundTexture;
        main.currentToolTexture = currentToolTexture;
        main.font = font;
        main.spriteBatch = spriteBatch;
        main.spriteManager = spriteManager;
        main.renderer = renderer;
        main.upgradeMenuMove = upgradeMenuMove;
        main.prefs = prefs;
        main.json = json;

        try {
            main.loadGame();
        } catch (Exception e) {
            Gdx.app.error("Main", "Failed to load game: " + e.getMessage());
        }
    }
}
