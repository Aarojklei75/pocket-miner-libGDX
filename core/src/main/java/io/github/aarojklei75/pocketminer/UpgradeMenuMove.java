package io.github.aarojklei75.pocketminer;

public class UpgradeMenuMove {
    SpriteManager spriteManager;

    public UpgradeMenuMove(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
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

        spriteManager.menuSprite.setPosition(20,20);
        spriteManager.returnSprite.setPosition(20, 20);
        spriteManager.returnBounds.setPosition(spriteManager.returnSprite.getX(), spriteManager.returnSprite.getY());

    }

    public void moveIn(){
        spriteManager.resetScoreSprite.setPosition(Settings.viewport.getWorldWidth() / 2 + 75, 420);
        spriteManager.resetScoreBounds.setPosition(spriteManager.resetScoreSprite.getX(), spriteManager.resetScoreSprite.getY());
        spriteManager.resourceChangeSprite.setPosition(Settings.viewport.getWorldWidth() / 2 - 175, 420);
        spriteManager.resourceChangeBounds.setPosition(spriteManager.resourceChangeSprite.getX(), spriteManager.resourceChangeSprite.getY());
        spriteManager.resourceSprite.setPosition(Settings.RS_X, Settings.RS_Y);
        spriteManager.resourceBounds.setPosition(spriteManager.resourceSprite.getX(),spriteManager.resourceSprite.getY());
        spriteManager.toolChangeSprite.setPosition(Settings.viewport.getWorldWidth() / 2 - 50, 420);
        spriteManager.toolChangeBounds.setPosition(spriteManager.toolChangeSprite.getX(), spriteManager.toolChangeSprite.getY());
        spriteManager.upgradeSprite.setPosition(Settings.viewport.getWorldWidth() / 2 + 200, 420);
        spriteManager.returnSprite.setPosition(75, -500); // -500
        spriteManager.returnBounds.setPosition(spriteManager.returnBounds.getX(), spriteManager.returnBounds.getY());
        spriteManager.menuSprite.setPosition(75,-1000); // -1000
    }

}
