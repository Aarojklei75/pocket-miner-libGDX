package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Renderer {

    private final SpriteBatch spriteBatch;
    private final FitViewport viewport;
    private final SpriteManager spriteManager;
    private final BitmapFont font;
    private final Texture backgroundTexture;

    public Renderer(SpriteBatch spriteBatch, FitViewport viewport, SpriteManager spriteManager,
                    BitmapFont font, Texture backgroundTexture) {
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        this.spriteManager = spriteManager;
        this.font = font;
        this.backgroundTexture = backgroundTexture;
    }

    public void draw (int score) {
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

}
