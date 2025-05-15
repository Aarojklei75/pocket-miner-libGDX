package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class InputHandler extends InputAdapter {
    private final GameState state;
    private final SpriteManager spriteManager;
    private final UpgradeMenuMove upgradeMenuMove;
    private final Runnable changeResource;
    private final Runnable saveGame;
    private final Texture[] toolTextures;

    public InputHandler(GameState state,
                        SpriteManager spriteManager,
                        UpgradeMenuMove upgradeMenuMove,
                        Texture[] toolTextures,
                        Runnable changeResource,
                        Runnable saveGame) {
        this.state = state;
        this.spriteManager = spriteManager;
        this.upgradeMenuMove = upgradeMenuMove;
        this.toolTextures = toolTextures;
        this.changeResource = changeResource;
        this.saveGame = saveGame;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touchPos = new Vector2(screenX, screenY);
        Settings.viewport.unproject(touchPos);

        if (spriteManager.resourceChangeBounds.contains(touchPos.x, touchPos.y)) {
            changeResource.run();
            saveGame.run();
            return true;
        }

        if (spriteManager.toolChangeBounds.contains(touchPos.x, touchPos.y)) {
            state.currentTool = (state.currentTool == 1) ? 2 : 1;
            spriteManager.toolSprite.setTexture(toolTextures[state.currentTool - 1]);
            saveGame.run();
            return true;
        }

        if (spriteManager.resetScoreBounds.contains(touchPos.x, touchPos.y)) {
            state.score = 0;
            saveGame.run();
            return true;
        }

        if (spriteManager.upgradeBounds.contains(touchPos.x, touchPos.y)) {
            upgradeMenuMove.moveOut();
            saveGame.run();
            return true;
        }

        if (spriteManager.returnBounds.contains(touchPos.x, touchPos.y)) {
            upgradeMenuMove.moveIn();
            saveGame.run();
            return true;
        }

        if (spriteManager.resourceBounds.contains(touchPos.x, touchPos.y)) {
            if (!spriteManager.getHealthBar().isEmpty()) {
                spriteManager.getHealthBar().reduceHealth();
            }

            if (spriteManager.getHealthBar().isEmpty()) {
                spriteManager.getHealthBar().reset();
                state.score++;
                changeResource.run();
            }

            if (!state.isSwinging) {
                state.isSwinging = true;
                state.swingTime = 0f;
            }

            return true;
        }

        return false;
    }
}
