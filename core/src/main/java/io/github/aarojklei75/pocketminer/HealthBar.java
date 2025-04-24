package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar {
    private int maxHealth;
    private int currentHealth;
    private float x, y, width, height;
    private final Texture texture;

    public HealthBar(int maxHealth, float x, float y, float width, float height, Texture texture) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture; // could be a 1x1 white texture for coloring
    }

    public void draw(SpriteBatch spriteBatch) {
        float healthPercent = (float) currentHealth / maxHealth;

        // Overlay of hp
        BitmapFont font;

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1);

        // Draw red background
        spriteBatch.setColor(Color.RED);
        spriteBatch.draw(texture, x, y, width, height);

        // Draw green foreground
        spriteBatch.setColor(Color.GREEN);
        spriteBatch.draw(texture, x, y, width * healthPercent, height);

        font.draw(spriteBatch, currentHealth + "/" + maxHealth, x + width / 4 , y + height - 1);

        spriteBatch.setColor(Color.WHITE);
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
