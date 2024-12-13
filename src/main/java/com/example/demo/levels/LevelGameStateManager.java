package com.example.demo.levels;

/**
 * Manages level transitions and game states (win/lose).
 */
public class LevelGameStateManager {
    private final LevelParentBase level;
    private boolean gameOver;

    public LevelGameStateManager(LevelParentBase level) {
        this.level = level;
        this.gameOver = false;
    }

    public void checkWinCondition() {
        if (level.getRoot().getChildren().size() == 0) { // Check if all actors are removed
            level.stopGame();
            gameOver = true;
        }
    }

    public void checkLoseCondition() {
        if (level.getUserPlane().getHealth() <= 0) { // Check if the player's health is 0
            level.stopGame();
            gameOver = true;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}