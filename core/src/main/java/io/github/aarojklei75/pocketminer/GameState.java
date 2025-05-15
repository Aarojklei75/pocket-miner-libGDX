package io.github.aarojklei75.pocketminer;

public class GameState {
    public int score = 0;
    public int currentTool = 1;
    public int currentResourceIndex = 1;
    public int level = 1; // Added for SaveSystem
    public boolean isSwinging = false;
    public float swingTime = 0f;
}
