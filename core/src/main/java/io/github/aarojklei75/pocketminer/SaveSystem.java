package io.github.aarojklei75.pocketminer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveSystem {
    private Preferences prefs;
    static final String KEY_SCORE = "score";
    static final String KEY_LEVEL = "level";
    static final String KEY_PLAYER_NAME = "playerName";

    public SaveSystem() {
        prefs = Gdx.app.getPreferences("PocketMiner");
    }

    // Save system - Isabel
    public void savePlayerData(int score, int level, String playerName) {
        // Store data using the appropriate data types
        prefs.putInteger(KEY_SCORE, score);
        prefs.putInteger(KEY_LEVEL, level);
        prefs.putString(KEY_PLAYER_NAME, playerName);

    }
}
