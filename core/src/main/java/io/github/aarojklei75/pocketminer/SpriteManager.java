// SpriteManager.java
package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SpriteManager {

    private final HealthBar healthBar;
    public Sprite minerSprite, toolSprite, resourceSprite;
    public Sprite resourceChangeSprite, toolChangeSprite, resetScoreSprite;

    public Rectangle resourceBounds, resourceChangeBounds, toolChangeBounds, resetScoreBounds;

    public SpriteManager(Viewport viewport, Texture[] resourceTextures, Texture currentToolTexture, int currentTool, int currentResourceIndex) {
        // Miner
        minerSprite = new Sprite(Textures.minerTexture);
        minerSprite.setSize(200, 200);
        minerSprite.setPosition(100, 150);
        minerSprite.setOrigin(
                minerSprite.getX() - minerSprite.getY() / 2,
                minerSprite.getX() - minerSprite.getY() / 2
        );

        // Tool
        toolSprite = new Sprite(currentToolTexture);
        toolSprite.setSize(150, 190);
        if (currentTool == 1) {
            toolSprite.setRotation(0);
            toolSprite.setPosition(minerSprite.getX() + 85, minerSprite.getY() + 50);
        } else {
            toolSprite.setRotation(150);
            toolSprite.setPosition(185, 200);
        }
        toolSprite.setOrigin(
                toolSprite.getX() - toolSprite.getY() / 2,
                toolSprite.getX() - toolSprite.getY() / 2
        );

        // Resource
        resourceSprite = new Sprite(resourceTextures[currentResourceIndex]);
        resourceSprite.setSize(Settings.RS_WIDTH, Settings.RS_HEIGHT);
        resourceSprite.setPosition(Settings.RS_X, Settings.RS_Y);

        // Buttons
        resourceChangeSprite = new Sprite(Textures.resourceChangeTexture);
        toolChangeSprite = new Sprite(Textures.toolChangeTexture);
        resetScoreSprite = new Sprite(Textures.resetScoreTexture);

        resourceChangeSprite.setSize(100, 50);
        toolChangeSprite.setSize(100, 50);
        resetScoreSprite.setSize(100, 50);

        float worldWidth = viewport.getWorldWidth();

        resourceChangeSprite.setPosition(worldWidth / 2 - 100, 400);
        toolChangeSprite.setPosition(worldWidth / 2 + 25, 400);
        resetScoreSprite.setPosition(worldWidth / 2 + 150, 400);

        // Bounds
        resourceBounds = new Rectangle(resourceSprite.getX(), resourceSprite.getY(), resourceSprite.getWidth(), resourceSprite.getHeight());
        resourceChangeBounds = new Rectangle(resourceChangeSprite.getX(), resourceChangeSprite.getY(), resourceChangeSprite.getWidth(), resourceChangeSprite.getHeight());
        toolChangeBounds = new Rectangle(toolChangeSprite.getX(), toolChangeSprite.getY(), toolChangeSprite.getWidth(), toolChangeSprite.getHeight());
        resetScoreBounds = new Rectangle(resetScoreSprite.getX(), resetScoreSprite.getY(), resetScoreSprite.getWidth(), resetScoreSprite.getHeight());

        // HealthBar
        healthBar = new HealthBar
                (10,
                        resourceSprite.getX(),
                        resourceSprite.getY() + resourceSprite.getHeight(),
                        resourceSprite.getWidth(),
                        15,
                        Textures.healthBarTexture
                );

    }
    public HealthBar getHealthBar() {
        return healthBar;
    }
}
