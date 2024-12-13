package com.example.demo.levels;

import com.example.demo.actors.EnemyPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.ui.GameOverUI;
import com.example.demo.ui.HeartDisplay;
import javafx.scene.Scene;

import java.util.List;

/**
 * Implements Level Two logic with tougher enemies and win conditions.
 *
 * <p>LevelTwo introduces more challenging gameplay with stronger enemies,
 * a reduced player health pool, and a requirement to achieve a certain
 * number of kills to advance to the next level. It manages spawning
 * enemies, handling collisions, and updating the game state based on
 * win or lose conditions.</p>
 */
public class LevelTwo extends LevelParentBase {

    /**
     * The background image file for Level Two.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";

    /**
     * The total number of enemies to maintain on the screen.
     */
    private static final int TOTAL_ENEMIES = 5;

    /**
     * The number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 20;

    /**
     * The probability of spawning an enemy in each update cycle.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.15;

    /**
     * The health of each enemy in Level Two.
     */
    private static final int ENEMY_HEALTH = 2;

    /**
     * The initial health of the player in Level Two.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The display for the player's health.
     */
    private HeartDisplay heartDisplay;

    /**
     * The UI for displaying the game-over screen.
     */
    private GameOverUI gameOverUI;

    /**
     * Constructs LevelTwo with the specified screen dimensions.
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public LevelTwo(double screenWidth, double screenHeight) {
        super(BACKGROUND_IMAGE_NAME, screenWidth, screenHeight, PLAYER_INITIAL_HEALTH);
        System.out.println("LevelTwo initialized.");
    }

    /**
     * Initializes friendly units, including the player's plane and UI elements.
     */
    @Override
    protected void initializeFriendlyUnits() {
        System.out.println("Initializing Friendly Units for Level Two...");
        double sceneHeight = (getScene() != null) ? getScene().getHeight() : 0;
        double initialXPos = 50.0;
        double initialYPos = (sceneHeight > 0) ? sceneHeight / 2.0 : 200.0;

        UserPlane userPlane = new UserPlane(initialXPos, initialYPos, getRoot(), getActorManager(), PLAYER_INITIAL_HEALTH, this);
        userPlane.setBounds(0, getScene().getHeight() - userPlane.getFitHeight());
        userPlane.updateBounds(getScene().getWidth(), getScene().getHeight());
        setUserPlane(userPlane);
        getActorManager().setUserPlane(userPlane);
        getRoot().getChildren().add(userPlane);

        heartDisplay = new HeartDisplay(5.0, 25.0, PLAYER_INITIAL_HEALTH);
        getRoot().getChildren().add(heartDisplay.getContainer());

        gameOverUI = new GameOverUI(getRoot(), getScene().getWidth(), getScene().getHeight());
        gameOverUI.getReturnToMainMenuButton().setOnAction(e -> returnToMainMenu());
        gameOverUI.getRestartButton().setOnAction(e -> restartLevel());
        System.out.println("Friendly Units and UI initialized successfully.");
    }

    /**
     * Spawns enemy units dynamically based on current conditions.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getActorManager().getEnemies().size();
        System.out.println("Current enemies: " + currentNumberOfEnemies);

        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getScene().getHeight();
                EnemyPlane newEnemy = new EnemyPlane(
                        getScene().getWidth(),
                        newEnemyInitialYPosition,
                        getActorManager(),
                        getUserPlane(),
                        ENEMY_HEALTH
                );
                System.out.println("Spawning enemy at Y: " + newEnemyInitialYPosition);

                newEnemy.setParentLevel(this);
                getActorManager().addEnemy(newEnemy);

                getActorManager().createEnemyProjectile(
                        newEnemy.getLayoutX(),
                        newEnemy.getLayoutY(),
                        getUserPlane(),
                        "/com/example/demo/images/enemyFire.png"
                );
            }
        }
    }

    /**
     * Initializes the scene and sets up event handlers for resizing and input.
     *
     * @return the initialized scene
     */
    @Override
    public Scene initializeScene() {
        super.initializeScene();
        Scene scene = getScene();

        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            if (getUserPlane() != null) {
                getUserPlane().setBounds(0, newHeight.doubleValue() - getUserPlane().getFitHeight());
            }
        });

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> getUserPlane().moveUp();
                case S -> getUserPlane().moveDown();
                case A -> getUserPlane().moveLeft();
                case D -> getUserPlane().moveRight();
                case SPACE -> getUserPlane().fireProjectile();
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W, S -> getUserPlane().stopVertical();
                case A, D -> getUserPlane().stopHorizontal();
            }
        });

        return scene;
    }

    /**
     * Updates the heart display based on the player's current health.
     *
     * @param newHealth the player's current health
     */
    public void updateHeartDisplay(int newHealth) {
        System.out.println("Updating Heart Display. Current Health: " + newHealth);

        if (heartDisplay != null) {
            while (heartDisplay.getContainer().getChildren().size() > newHealth) {
                heartDisplay.removeHeart();
            }
        }
    }

    /**
     * Updates the game state, including actor updates, enemy spawning, and collision handling.
     */
    @Override
    protected void update() {
        getActorManager().updateAll();
        getUserPlane().updatePosition();

        spawnEnemyUnits();

        if (getUserPlane() != null) {
            getCollisionManager().handleCollisions(getActorManager().getEnemyProjectiles(), List.of(getUserPlane()));
            getCollisionManager().handleCollisions(getActorManager().getUserProjectiles(), getActorManager().getEnemies());
            getCollisionManager().handleCollisions(getActorManager().getEnemies(), List.of(getUserPlane()));
        }

        getActorManager().removeDestroyedActors();
        checkWinCondition();
        checkLoseCondition();
    }

    /**
     * Checks if the game is over and handles the game-over state.
     */
    @Override
    protected void checkIfGameOver() {
        if (getUserPlane().getHealth() <= 0) {
            System.out.println("Game Over!");
            stopGame();
            if (gameOverUI != null) gameOverUI.showGameOverUI();
        } else if (getUserPlane().getNumberOfKills() >= KILLS_TO_ADVANCE) {
            System.out.println("LevelTwo cleared! Transitioning to LevelThree...");
            stopGame();
            notifyObservers("com.example.demo.levels.LevelThree");
        }
    }

    /**
     * Checks if the win condition is met and transitions to the next level.
     */
    private void checkWinCondition() {
        if (getUserPlane().getNumberOfKills() >= KILLS_TO_ADVANCE) {
            System.out.println("LevelTwo cleared! Transitioning to LevelThree...");
            stopGame();
            notifyObservers("com.example.demo.levels.LevelThree");
        }
    }

    /**
     * Checks if the lose condition is met and displays the game-over screen.
     */
    private void checkLoseCondition() {
        if (getUserPlane().getHealth() <= 0) {
            System.out.println("Game Over!");
            stopGame();
            gameOverUI.showGameOverUI();
        }
    }

    /**
     * Returns to the main menu.
     */
    private void returnToMainMenu() {
        System.out.println("Returning to Main Menu...");
        stopGame();
        notifyObservers("MainMenu");
    }

    /**
     * Restarts the level by reinitializing all components and restarting the game loop.
     */
    private void restartLevel() {
        try {
            stopGame();
            getRoot().getChildren().clear();
            getActorManager().clearAllActors();

            initializeBackground(getScene().getWidth(), getScene().getHeight());
            initializeFriendlyUnits();

            if (gameOverUI != null) {
                gameOverUI.hideGameOverUI();
            }

            startGame();
            System.out.println("LevelTwo restarted successfully!");
        } catch (Exception e) {
            System.err.println("Error restarting LevelTwo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
