package com.example.demo.levels;

import com.example.demo.actors.EnemyPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.ui.GameOverUI;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.MainMenu;
import javafx.scene.Scene;

import java.util.List;

/**
 * Implements the first level's logic, including enemy spawning, health tracking,
 * and game-over mechanics. Uses `GameOverUI` for modular game-over UI management.
 */
public class LevelOne extends LevelParentBase {
	// Constants for level configuration
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final int TOTAL_ENEMIES = 3;
	private static final int KILLS_TO_ADVANCE = 5;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.10;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	// UI and game state management
	private HeartDisplay heartDisplay;
	private GameOverUI gameOverUI;

	/**
	 * Constructor for LevelOne.
	 *
	 * @param screenWidth  The width of the screen.
	 * @param screenHeight The height of the screen.
	 */
	public LevelOne(double screenWidth, double screenHeight) {
		super(BACKGROUND_IMAGE_NAME, screenWidth, screenHeight, PLAYER_INITIAL_HEALTH);
		System.out.println("LevelOne initialized.");
	}

	/**
	 * Initializes friendly units and associated UI elements.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		System.out.println("Initializing Friendly Units...");

		// Position the UserPlane based on screen dimensions
		double sceneHeight = (getScene() != null) ? getScene().getHeight() : 0;
		double initialXPos = 50.0; // Near the left edge
		double initialYPos = (sceneHeight > 0) ? sceneHeight / 2.0 : 200.0; // Fallback if dimensions are unavailable

		// Create and configure UserPlane
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

		// Set action for "Return to Main Menu" button
		gameOverUI.getReturnToMainMenuButton().setOnAction(e -> {
			System.out.println("Main Menu button clicked.");
			returnToMainMenu();
		});

		// Set action for "Restart" button
		gameOverUI.getRestartButton().setOnAction(e -> restartLevel());

		System.out.println("Friendly Units and UI initialized successfully.");
	}

	/**
	 * Returns the player to the main menu.
	 */
	private void returnToMainMenu() {
		System.out.println("Returning to Main Menu...");
		stopGame(); // Stop the game timeline

		// Retrieve the stage from the current scene
		if (getScene().getWindow() instanceof javafx.stage.Stage stage) {
			MainMenu mainMenu = new MainMenu();
			try {
				mainMenu.start(stage); // Load the main menu onto the same stage
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error returning to main menu: " + e.getMessage());
			}
		} else {
			System.err.println("Stage reference is null. Cannot return to Main Menu.");
		}
	}

	/**
	 * Updates the game state, including actor actions, collisions, and win/lose conditions.
	 */
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
	protected void spawnEnemyUnits() {
		System.out.println("Spawning enemy units...");
		int currentNumberOfEnemies = getActorManager().getEnemies().size();

		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				System.out.println("Creating new EnemyPlane...");
				double newEnemyInitialYPosition = Math.random() * getScene().getHeight();

				EnemyPlane newEnemy = new EnemyPlane(
						getScene().getWidth()- i * 200,
						newEnemyInitialYPosition,
						getActorManager(),
						getUserPlane(),
						1
				);
				newEnemy.setParentLevel(this); // Set parent level
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

	/**
	 * Checks if the player has achieved the required kills to advance to the next level.
	 */
	private void checkWinCondition() {
		if (getUserPlane().getNumberOfKills() >= KILLS_TO_ADVANCE) {
			System.out.println("Congratulations! Moving to Level Two.");
			stopGame(); // Stop the current level
			notifyObservers("com.example.demo.levels.LevelTwo");
		}
	}

	/**
	 * Checks if the player's health has dropped to zero and triggers game-over logic.
	 */
	private void checkLoseCondition() {
		if (getUserPlane().getHealth() <= 0) {
			System.out.println("Game Over!");
			stopGame(); // Stop the game timeline
			gameOverUI.showGameOverUI(); // Display the Game Over UI
		}
	}

	/**
	 * Restarts the level by reinitializing game state and UI elements.
	 */
	private void restartLevel() {
		stopGame(); // Stop the game timeline

		// Clear all actors and UI elements
		getRoot().getChildren().clear();
		getActorManager().clearAllActors();

		// Reinitialize background and friendly units
		initializeBackground(getScene().getWidth(), getScene().getHeight());
		initializeFriendlyUnits();

		// Hide Game Over UI and restart the game
		gameOverUI.hideGameOverUI();
		startGame();

		System.out.println("Level restarted!");
	}

	public void updateHeartDisplay(int newHealth) {
		System.out.println("Updating Heart Display. Current Health: " + newHealth);

		if (heartDisplay != null) {
			while (heartDisplay.getContainer().getChildren().size() > newHealth) {
				heartDisplay.removeHeart(); // Remove hearts as health decreases
			}
		}
	}

	/**
	 * Initializes the scene and sets up key event listeners.
	 *
	 * @return The initialized scene.
	 */
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

	/**
	 * Resizes all elements to adapt to the new screen dimensions.
	 *
	 * @param newWidth  The new width of the screen.
	 * @param newHeight The new height of the screen.
	 */
	@Override
	public void resizeElements(double newWidth, double newHeight) {
		super.resizeElements(newWidth, newHeight);

		if (heartDisplay != null) {
			heartDisplay.adjustPositionForResize(newWidth, newHeight);
		}

		if (gameOverUI != null) {
			gameOverUI.adjustPositionForResize(newWidth, newHeight);
		}
	}

	@Override
	protected void checkIfGameOver() {
		if (getUserPlane().getHealth() <= 0) {
			System.out.println("Game Over!");
			stopGame();
			gameOverUI.showGameOverUI();
		} else if (getUserPlane().getNumberOfKills() >= KILLS_TO_ADVANCE) {
			System.out.println("Congratulations! Moving to the next level.");
			notifyObservers("com.example.demo.levels.LevelTwo");
		}
	}
}