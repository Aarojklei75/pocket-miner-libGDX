package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Textures {
    protected static Texture backgroundTexture;
    protected static Texture minerTexture;
    protected static Texture resourceTexture1;
    protected static Texture resourceTexture2;
    protected static Texture resourceTexture3;
    protected static Texture resourceTexture4;
    protected static Texture resourceTexture5;
    protected static Texture resourceTexture6;
    protected static Texture toolTexture1;
    protected static Texture toolTexture2;
    protected static Texture resourceChangeTexture;
    protected static Texture toolChangeTexture;
    protected static Texture resetScoreTexture;
    protected static Texture healthBarTexture;
    protected static Texture upgradeTexture;
    protected static Texture returnTexture;
    protected static Texture menuTexture;

    public static void load() {
        try {
            backgroundTexture = new Texture("pocketMinerBackground01_alt3.png");
            Gdx.app.log("Textures", "Loaded backgroundTexture");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load backgroundTexture: " + e.getMessage());
        }

        try{
            menuTexture = new Texture("menuBackground.png");
            Gdx.app.log("Textures", "Loaded menuTexture");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load menuTexture" + e.getMessage());
        }

        try {
            minerTexture = new Texture("miner.png");
            Gdx.app.log("Textures", "Loaded minerTexture");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load minerTexture: " + e.getMessage());
        }

        try {
            resourceTexture1 = new Texture("mineableobject1.png");
            Gdx.app.log("Textures", "Loaded resourceTexture1");
            resourceTexture2 = new Texture("mineableobject2.png");
            Gdx.app.log("Textures", "Loaded resourceTexture2");
            resourceTexture3 = new Texture("mineableobject3.png");
            Gdx.app.log("Textures", "Loaded resourceTexture3");
            resourceTexture4 = new Texture("mineableobject4.png");
            Gdx.app.log("Textures", "Loaded resourceTexture4");
            resourceTexture5 = new Texture("mineableobject5.png");
            Gdx.app.log("Textures", "Loaded resourceTexture5");
            resourceTexture6 = new Texture("mineableobject6.png");
            Gdx.app.log("Textures", "Loaded resourceTexture6");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load resource textures: " + e.getMessage());
        }

        try {
            toolTexture1 = new Texture("pickaxe-1.png");
            Gdx.app.log("Textures", "Loaded toolTexture1");
            toolTexture2 = new Texture("pickaxe-2.png");
            Gdx.app.log("Textures", "Loaded toolTexture2");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load tool textures: " + e.getMessage());
        }

        try {
            resourceChangeTexture = new Texture("resource-switch.png");
            Gdx.app.log("Textures", "Loaded resourceChangeTexture");
            toolChangeTexture = new Texture("tool-switch.png");
            Gdx.app.log("Textures", "Loaded toolChangeTexture");
            resetScoreTexture = new Texture("resetScore.png");
            Gdx.app.log("Textures", "Loaded resetScoreTexture");
            upgradeTexture = new Texture("PocketMinerButton.png");
            Gdx.app.log("Textures", "Loaded upgradeTexture");
            returnTexture = new Texture("PocketMinerButton2.png");
            Gdx.app.log("Textures", "Loaded returnTexture");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load button textures: " + e.getMessage());
        }

        try {
            healthBarTexture = new Texture("healthBarTexture.png");
            Gdx.app.log("Textures", "Loaded healthBarTexture");
        } catch (Exception e) {
            Gdx.app.error("Textures", "Failed to load healthBarTexture: " + e.getMessage());
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(1, 1, 1, 1); // White
            pixmap.fill();
            healthBarTexture = new Texture(pixmap);
            pixmap.dispose();
            Gdx.app.log("Textures", "Created fallback healthBarTexture");
        }

    }

    public static void dispose() {
        backgroundTexture.dispose();
        minerTexture.dispose();
        resourceTexture1.dispose();
        resourceTexture2.dispose();
        resourceTexture3.dispose();
        resourceTexture4.dispose();
        resourceTexture5.dispose();
        resourceTexture6.dispose();
        toolTexture1.dispose();
        toolTexture2.dispose();
        resourceChangeTexture.dispose();
        toolChangeTexture.dispose();
        resetScoreTexture.dispose();
        healthBarTexture.dispose();
        upgradeTexture.dispose();
    }
}
