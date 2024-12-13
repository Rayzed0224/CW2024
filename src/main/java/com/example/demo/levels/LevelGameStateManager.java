package com.example.demo.levels;

/**
 * Manages level transitions and game states (win/lose).
 *
 * <p>This class provides functionality to monitor the game's progress,
 * including checking for win and lose conditions and managing the
 * game's overall state.</p>
 */
public class LevelGameStateManager {

    /**
     * The current level being managed.
     */
    private final LevelParentBase level;

    /**
     * Indicates whether the game is over.
     */
    private boolean gameOver;

    /**
     * Constructs a LevelGameStateManager for the specified level.
     *
     * @param level the level to manage
     */
    public LevelGameStateManager(LevelParentBase level) {
        this.level = level;
        this.gameOver = false;
    }

    /**
     * Checks if the win condition is met.
     *
     * <p>The win condition is satisfied when all actors are removed from
     * the level's root node. If this condition is met, the game is stopped
     * and marked as over.</p>
     */
    public void checkWinCondition() {
        if (level.getRoot().getChildren().size() == 0) { // Check if all actors are removed
            level.stopGame();
            gameOver = true;
        }
    }

    /**
     * Checks if the lose condition is met.
     *
     * <p>The lose condition is satisfied when the player's health reaches
     * zero. If this condition is met, the game is stopped and marked as over.</p>
     */
    public void checkLoseCondition() {
        if (level.getUserPlane().getHealth() <= 0) { // Check if the player's health is 0
            level.stopGame();
            gameOver = true;
        }
    }

    /**
     * Determines if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
