package com.example.demo.levels;

import com.example.demo.actors.EnemyPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.ui.GameOverUI;
import com.example.demo.ui.HeartDisplay;
import javafx.scene.Scene;

import java.util.List;

/**
 * Implements Level Two logic with tougher enemies and win conditions.
 */
public class LevelTwo extends LevelParentBase {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 20; // Transition after 20 kills
    private static final double ENEMY_SPAWN_PROBABILITY = 0.15;
    private static final int ENEMY_HEALTH = 2; // Tougher enemies
    private static final int PLAYER_INITIAL_HEALTH = 5; // Reduced player health

    private HeartDisplay heartDisplay;
    private GameOverUI gameOverUI;

    public LevelTwo(double screenWidth, double screenHeight) {
        super(BACKGROUND_IMAGE_NAME, screenWidth, screenHeight, PLAYER_INITIAL_HEALTH);
        System.out.println("LevelTwo initialized.");
    }

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

        // Initialize HeartDisplay for player health
        heartDisplay = new HeartDisplay(5.0, 25.0, PLAYER_INITIAL_HEALTH);
        getRoot().getChildren().add(heartDisplay.getContainer());

        // Initialize GameOverUI for modular game-over handling
        gameOverUI = new GameOverUI(getRoot(), getScene().getWidth(), getScene().getHeight());
        gameOverUI.getReturnToMainMenuButton().setOnAction(e -> returnToMainMenu());
        gameOverUI.getRestartButton().setOnAction(e -> restartLevel());

        System.out.println("Friendly Units and UI initialized successfully.");
    }

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

                // Optional: Fire a projectile during enemy spawn for debugging
                getActorManager().createEnemyProjectile(
                        newEnemy.getLayoutX(),
                        newEnemy.getLayoutY(),
                        getUserPlane(),
                        "/com/example/demo/images/enemyFire.png"
                );
            }
        }
    }

    @Override
    public Scene initializeScene() {
        super.initializeScene();
        Scene scene = getScene();

        // Handle resizing events
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            if (getUserPlane() != null) {
                getUserPlane().setBounds(0, newHeight.doubleValue() - getUserPlane().getFitHeight());
            }
        });

        // Configure key press actions
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> getUserPlane().moveUp();
                case S -> getUserPlane().moveDown();
                case A -> getUserPlane().moveLeft();
                case D -> getUserPlane().moveRight();
                case SPACE -> getUserPlane().fireProjectile();
            }
        });

        // Configure key release actions
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W, S -> getUserPlane().stopVertical();
                case A, D -> getUserPlane().stopHorizontal();
            }
        });

        return scene;
    }

    public void updateHeartDisplay(int newHealth) {
        System.out.println("Updating Heart Display. Current Health: " + newHealth);

        if (heartDisplay != null) {
            while (heartDisplay.getContainer().getChildren().size() > newHealth) {
                heartDisplay.removeHeart(); // Remove hearts as health decreases
            }
        }
    }

    @Override
    protected void update() {
        getActorManager().updateAll(); // Update all actors
        getUserPlane().updatePosition(); // Update user plane's position

        // Spawn enemy units dynamically
        spawnEnemyUnits();

        // Handle collisions
        if (getUserPlane() != null) {
            getCollisionManager().handleCollisions(getActorManager().getEnemyProjectiles(), List.of(getUserPlane()));
            getCollisionManager().handleCollisions(getActorManager().getUserProjectiles(), getActorManager().getEnemies());
            getCollisionManager().handleCollisions(getActorManager().getEnemies(), List.of(getUserPlane()));
        }

        // Remove destroyed actors and check game conditions
        getActorManager().removeDestroyedActors();
        checkWinCondition();
        checkLoseCondition();
    }

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

    private void checkWinCondition() {
        if (getUserPlane().getNumberOfKills() >= KILLS_TO_ADVANCE) {
            System.out.println("LevelTwo cleared! Transitioning to LevelThree...");
            stopGame();
            notifyObservers("com.example.demo.levels.LevelThree"); // Transition to LevelThree
        }
    }

    private void checkLoseCondition() {
        if (getUserPlane().getHealth() <= 0) {
            System.out.println("Game Over!");
            stopGame();
            gameOverUI.showGameOverUI();
        }
    }

    private void returnToMainMenu() {
        System.out.println("Returning to Main Menu...");
        stopGame(); // Stop the game timeline
        notifyObservers("MainMenu"); // Notify observers to switch to the main menu
    }

    private void restartLevel() {
        try {
            stopGame(); // Stop the game timeline

            // Clear all actors and UI elements
            getRoot().getChildren().clear();
            getActorManager().clearAllActors();

            // Reinitialize background and friendly units
            initializeBackground(getScene().getWidth(), getScene().getHeight());
            initializeFriendlyUnits();

            // Reinitialize game variables (if applicable)
            if (gameOverUI != null) {
                gameOverUI.hideGameOverUI();
            }

            // Restart the game timeline
            startGame();
            System.out.println("LevelTwo restarted successfully!");
        } catch (Exception e) {
            System.err.println("Error restarting LevelTwo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}