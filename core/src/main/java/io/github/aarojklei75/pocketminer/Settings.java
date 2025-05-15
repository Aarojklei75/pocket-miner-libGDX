package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.utils.viewport.FitViewport;

public final class Settings {

    private Settings () {}


    public static final FitViewport viewport            = new FitViewport(640, 480);
    public static float worldHeight                     = viewport.getWorldHeight();
    public static float worldWidth                      = viewport.getWorldWidth();

    public static final String mineableobject1          = "mineableobject1";
    public static final String mineableobject2          = "mineableobject2";
    public static final String mineableobject3          = "mineableobject3";
    public static final String mineableobject4          = "mineableobject4";
    public static final String mineableobject5          = "mineableobject5";
    public static final String mineableobject6          = "mineableobject6";

    // MP = MINER_POSITION
    public static final int M_HEIGHT                    = 200;
    public static final int M_WEIGHT                    = 200;
    public static final int MP_X                        = 100;
    public static final int MP_Y                        = 150;

    // RS = RESOURCE_SPRITE
    public static final int RS_HEIGHT                   = 70;
    public static final int RS_WIDTH                    = 70;
    public static final int RS_X                        = 500;
    public static final int RS_Y                        = 150;

    // RST = Reset Score Sprite
    public static final float RST_X                     = (worldWidth / 2 + 75);
    public static final float RST_Y                     = 420;

    // TCS = Tool Change Sprite
    public static final float TCS_X                     = (worldWidth / 2 - 50);
    public static final float TCS_Y                     = 420;

    // RCS = Resource Change Sprite
    public static final float RCS_X                     = (worldWidth / 2 - 175);
    public static final float RCS_Y                     = 420;

    // UPGR = Upgrade Sprite
    public static final float UPGR_X                    = (worldWidth / 2 + 200);
    public static final float UPGR_Y                    = 420;

}
