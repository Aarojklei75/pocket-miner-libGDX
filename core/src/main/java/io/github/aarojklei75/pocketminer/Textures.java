package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.graphics.Texture;

public class Textures {
    // Textures
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

    // Load all assets
    public static void load() {
        backgroundTexture = new Texture("pocketMinerBackground01_alt3.png");
        minerTexture = new Texture("miner.png");

        // Resource Textures
        resourceTexture1 = new Texture("mineableobject1.png");
        resourceTexture2 = new Texture("mineableobject2.png");
        resourceTexture3 = new Texture("mineableobject3.png");
        resourceTexture4 = new Texture("mineableobject4.png");
        resourceTexture5 = new Texture("mineableobject5.png");
        resourceTexture6 = new Texture("mineableobject6.png");

        // Tool Textures
        toolTexture1 = new Texture("pickaxe-1.png");
        toolTexture2 = new Texture("pickaxe-2.png");

        // Button Changes
        resourceChangeTexture = new Texture("resource-switch.png");
        toolChangeTexture = new Texture("tool-switch.png");
        resetScoreTexture = new Texture("resetScore.png");

        // Health Bar
        healthBarTexture = new Texture("healthBarTexture.png");
    }

    // Dispose all assets
    public static void dispose() {
        backgroundTexture.dispose();
        minerTexture.dispose();
        resourceTexture1.dispose();
        resourceTexture2.dispose();
        toolTexture1.dispose();
        toolTexture2.dispose();
        resourceChangeTexture.dispose();
        toolChangeTexture.dispose();
        resetScoreTexture.dispose();
        healthBarTexture.dispose();
    }
}
