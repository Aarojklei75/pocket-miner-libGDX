package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener {

    //Textures
    Texture backgroundTexture;
    Texture minerTexture;
    Texture resourceTexture1;  // First resource texture
    Texture resourceTexture2;  // Second resource texture
    Texture currentResourceTexture;
    Texture toolTexture1;
    Texture toolTexture2;
    Texture currentToolTexture;
    Texture resourceChangeTexture;
    Texture toolChangeTexture;

    // Viewport style
    FitViewport viewport;

    // Sprites
    SpriteBatch spriteBatch;
    Sprite resourceSprite;
    Sprite minerSprite;
    Sprite toolSprite;
    Sprite resourceChangeSprite;
    Sprite toolChangeSprite;

    // Rectangles for click detection
    Rectangle minerRectangle;
    Rectangle resourceBounds;
    Rectangle resourceChangeBounds;
    Rectangle toolChangeBounds;

    // Score
    BitmapFont font;
    int score;
    //Switches
    int currentResource = 1;
    int currentTool = 1;
    //Swing Animation
    float pickaxeAngle = 0f;
    boolean isSwinging = false;
    float swingTime = 0f;
    final float SWING_DURATION = 0.2f; //200 ms



    @Override
    public void create () {
        backgroundTexture = new Texture("simple-level.png");
        minerTexture = new Texture("miner.png");
        resourceTexture1 = new Texture("mineableobject-1.png");
        resourceTexture2 = new Texture("mineableobject2.png");
        currentResourceTexture = resourceTexture1;
        toolTexture1 = new Texture("pickaxe-1.png");
        toolTexture2 = new Texture("pickaxe-2.png");
        currentToolTexture = toolTexture1;
        resourceChangeTexture = new Texture("resource-switch.png");
        toolChangeTexture = new Texture("tool-switch.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(640, 480);

        //Miner Sprite
        minerSprite = new Sprite(minerTexture);
        minerSprite.setSize(200, 200);
        minerSprite.setPosition(100, 150);

        //Pickaxe Sprites
        toolSprite = new Sprite(currentToolTexture);
        toolSprite.setSize(150, 190);
        if (currentTool == 1) {
            toolSprite.setRotation(0);
            toolSprite.setPosition(185,180);
        }
        else if (currentTool == 2) {
            toolSprite.setRotation(150);
            toolSprite.setPosition(185, 200);
        }


        //Button Sprite
        resourceSprite = new Sprite(currentResourceTexture);
        resourceSprite.setSize(70, 70);
        resourceSprite.setPosition(500, 150);

        //Resource change Sprite
        resourceChangeSprite = new Sprite(resourceChangeTexture);
        resourceChangeSprite.setSize(100, 50);
        resourceChangeSprite.setPosition(viewport.getWorldWidth() / 2 - 125, 400);

        //Tool change Sprite
        toolChangeSprite = new Sprite(toolChangeTexture);
        toolChangeSprite.setSize(100, 50);
        toolChangeSprite.setPosition(viewport.getWorldWidth() / 2, 400);


        //Rectangle logic
        minerRectangle = new Rectangle();
        resourceBounds = new Rectangle(
            resourceSprite.getX(), resourceSprite.getY(),
            resourceSprite.getWidth(), resourceSprite.getHeight()
            );
        resourceChangeBounds = new Rectangle(
            resourceChangeSprite.getX(), resourceChangeSprite.getY(),
            resourceChangeSprite.getWidth(), resourceChangeSprite.getHeight()
        );
        toolChangeBounds = new Rectangle(
            toolChangeSprite.getX(), toolChangeSprite.getY(),
            toolChangeSprite.getWidth(), toolChangeSprite.getHeight()
        );
        toolSprite.setOrigin(
            toolSprite.getX() - toolSprite.getY()/2,
            toolSprite.getX() - toolSprite.getY()/2
        );
        minerSprite.setOrigin(
            minerSprite.getX()- minerSprite.getY()/2,
            minerSprite.getX() - minerSprite.getY()/2
        );

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);




    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        minerSprite.draw(spriteBatch);
        toolSprite.draw(spriteBatch);
        resourceSprite.draw(spriteBatch);
        toolChangeSprite.draw(spriteBatch);
        resourceChangeSprite.draw(spriteBatch);
        font.draw(spriteBatch, "Score: " + score, 50,430);

        spriteBatch.end();

    }



    @Override
    public void resize (int width, int height){
        viewport.update(width, height, true);

    }

    @Override
    public void render () {
        float delta = Gdx.graphics.getDeltaTime();
        updateAnimation(delta);
        draw();
        input();
        // Clear the screen
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin drawing with the sprite batch
        //batch.begin();

        // Draw the sprite (it will rotate based on the number of clicks)
        //sprite.draw(batch);

        // End drawing with the sprite batch
        //batch.end
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void dispose () {
        backgroundTexture.dispose();
        minerTexture.dispose();
        resourceTexture1.dispose();
        resourceTexture2.dispose();
        toolTexture1.dispose();
        toolTexture2.dispose();
        resourceChangeTexture.dispose();
        toolChangeTexture.dispose();
        spriteBatch.dispose();
        font.dispose();
        //prefs.flush();
    }

    public void input() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPos = new Vector2(screenX, screenY);
                viewport.unproject(touchPos);

                // Resource change button logic
                if (resourceChangeBounds.contains(touchPos.x, touchPos.y)) {
                    if (currentResource == 1) {
                        currentResourceTexture = resourceTexture2;
                        currentResource = 2;
                    } else {
                        currentResourceTexture = resourceTexture1;
                        currentResource = 1;
                    }
                    resourceSprite.setTexture(currentResourceTexture);
                    return true;
                }

                //Tool change button logic
                if (toolChangeBounds.contains(touchPos.x, touchPos.y)) {
                    if (currentTool == 1) {
                        currentToolTexture = toolTexture2;
                        currentTool = 2;
                    } else {
                        currentToolTexture = toolTexture1;
                        currentTool = 1;
                    }
                    toolSprite.setTexture(currentToolTexture);
                    return true;
                }



                // Resource click logic
                if (resourceBounds.contains(touchPos.x, touchPos.y)) {
                    score++;

                    if (!isSwinging) {
                        isSwinging = true;
                        swingTime = 0f;

                    }
                }

                return false;

            }
        });
    }
    public void logic() {

    }
    private void updateAnimation(float delta) {
        if (isSwinging) {
            swingTime += delta;

            float progress = swingTime / SWING_DURATION;
            System.out.print("Progress = " + progress);

            if (progress < 0.5f) {
                pickaxeAngle = -90 * (progress * 2); // swing down
            } else if (progress < 1f) {
                pickaxeAngle = -90 * (2 - progress * 2); // swing back up
            } else {
                isSwinging = false;
                pickaxeAngle = 0f;
                swingTime = 0f;
            }

            toolSprite.setRotation(pickaxeAngle); // update rotation
        }
    }
}
