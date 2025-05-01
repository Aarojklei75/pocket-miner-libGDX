package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar {
    private int maxHealth;
    private int currentHealth;
    private float x, y, width, height;
    private final Texture texture;
    private final BitmapFont font;

    public HealthBar(int maxHealth, float x, float y, float width, float height, Texture texture) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(1);
    }

    public void draw(SpriteBatch spriteBatch) {
        if (texture == null) {
            Gdx.app.log("HealthBar", "Error: texture is null");
            return;
        }
        float healthPercent = (float) currentHealth / maxHealth;

        spriteBatch.setColor(Color.RED);
        spriteBatch.draw(texture, x, y, width, height);

        spriteBatch.setColor(Color.GREEN);
        spriteBatch.draw(texture, x, y, width * healthPercent, height);

        font.draw(spriteBatch, currentHealth + "/" + maxHealth, x + width / 4, y + height - 1);

        spriteBatch.setColor(Color.WHITE);
    }

    public void dispose() {
        font.dispose();
    }

    public void reduceHealth() {
        if (currentHealth > 0) {
            currentHealth--;
        }
    }

    public boolean isEmpty() {
        return currentHealth <= 0;
    }

    public void reset() {
        currentHealth = maxHealth;
    }

    public void updatePosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
