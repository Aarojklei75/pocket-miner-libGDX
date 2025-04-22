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

        // Flush the preferences to ensure the data is written
        prefs.flush();
    }

    public int getScore() {
        // Retrieve data using the key with
        // a default value if the key doesn't exist
        return prefs.getInteger(KEY_SCORE, 0);
    }

    public int getLevel() {
        return prefs.getInteger(KEY_LEVEL, 0); // Default is 0
    }

    public String getPlayerName() {
        return prefs.getString(KEY_PLAYER_NAME, ""); // Default player name
    }
}
