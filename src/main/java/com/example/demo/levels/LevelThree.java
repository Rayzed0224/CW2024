package com.example.demo.levels;

import com.example.demo.actors.Boss;
import com.example.demo.actors.UserPlane;
import com.example.demo.ui.GameOverUI;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.ShieldImage;
import com.example.demo.ui.WinImage;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the third level's logic, including boss mechanics.
 */
public class LevelThree extends LevelParentBase {

	/**
	 * The file path of the background image for the level.
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";

	/**
	 * The initial health of the player.
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * The initial health of the boss.
	 */
	private static final int BOSS_INITIAL_HEALTH = 20;

	/**
	 * Displays the player's health using heart icons.
	 */
	private HeartDisplay heartDisplay;

	/**
	 * Manages the game-over UI elements.
	 */
	private GameOverUI gameOverUI;

	/**
	 * Represents the boss character in the level.
	 */
	private Boss boss;

	/**
	 * Constructor for LevelThree.
	 *
	 * @param screenWidth  The width of the screen.
	 * @param screenHeight The height of the screen.
	 */
	public LevelThree(double screenWidth, double screenHeight) {
		super(BACKGROUND_IMAGE_NAME, screenWidth, screenHeight, PLAYER_INITIAL_HEALTH);
		System.out.println("LevelThree initialized.");
	}

	/**
	 * Initializes friendly units, including the player-controlled plane and associated UI elements.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		System.out.println("Initializing Friendly Units...");

		if (getScene() == null || getRoot() == null) {
			System.err.println("Scene or Root is null. Cannot initialize friendly units.");
			return;
		}

		// Initialize UserPlane
		double sceneHeight = getScene().getHeight();
		double initialXPos = 50.0;
		double initialYPos = sceneHeight / 2.0;

		UserPlane userPlane = new UserPlane(initialXPos, initialYPos, getRoot(), getActorManager(), PLAYER_INITIAL_HEALTH, this);
		userPlane.setBounds(0, getScene().getHeight() - userPlane.getFitHeight());
		userPlane.updateBounds(getScene().getWidth(), getScene().getHeight());
		setUserPlane(userPlane);
		getActorManager().setUserPlane(userPlane);
		getRoot().getChildren().add(userPlane);

		// Initialize HeartDisplay
		heartDisplay = new HeartDisplay(5.0, 25.0, PLAYER_INITIAL_HEALTH);
		getRoot().getChildren().add(heartDisplay.getContainer());

		// Initialize GameOverUI
		gameOverUI = new GameOverUI(getRoot(), getScene().getWidth(), getScene().getHeight());
		gameOverUI.getReturnToMainMenuButton().setOnAction(e -> returnToMainMenu());
		gameOverUI.getRestartButton().setOnAction(e -> restartLevel());

		System.out.println("Friendly Units and UI initialized successfully.");

		spawnEnemyUnits();
	}

	/**
	 * Spawns the boss character and its associated shields.
	 */
	@Override
	protected void spawnEnemyUnits() {
		System.out.println("Spawning boss...");

		if (boss == null) {
			List<ShieldImage> shields = createShieldImages(3); // Create 3 shields
			boss = new Boss(getRoot(), shields, getActorManager(), getUserPlane());

			// Add boss to actor manager and root
			getActorManager().addEnemy(boss);

			if (!getRoot().getChildren().contains(boss)) {
				getRoot().getChildren().add(boss);
				System.out.println("Boss added to root.");
			}
		} else {
			System.out.println("Boss already exists: " + boss);
		}
	}

	/**
	 * Creates the shield images for the boss.
	 *
	 * @param count The number of shields to create.
	 * @return A list of ShieldImage objects.
	 */
	protected List<ShieldImage> createShieldImages(int count) {
		List<ShieldImage> shields = new ArrayList<>();

		if (getRoot() == null) {
			System.err.println("Root is null. Cannot create shields.");
			return shields;
		}

		for (int i = 0; i < count; i++) {
			double xPosition = getRoot().getLayoutX() + i * 50;
			double yPosition = getRoot().getLayoutY() - 20;

			ShieldImage shield = new ShieldImage(xPosition, yPosition);
			shields.add(shield);

			if (!getRoot().getChildren().contains(shield)) {
				getRoot().getChildren().add(shield);
			}
		}
		return shields;
	}

	/**
	 * Updates the game state, including actor actions, collisions, and game conditions.
	 */
	@Override
	protected void update() {
		try {
			System.out.println("Updating LevelThree...");
			if (getActorManager() == null || getUserPlane() == null) {
				System.err.println("ActorManager or UserPlane is null.");
				return;
			}

			getActorManager().updateAll();
			getUserPlane().updatePosition();

			if (boss != null) {
				System.out.println("Handling collisions with boss...");
				getCollisionManager().handleCollisions(getActorManager().getEnemyProjectiles(), List.of(getUserPlane()));
				getCollisionManager().handleCollisions(getActorManager().getUserProjectiles(), List.of(boss));
			} else {
				System.err.println("Boss is null during update.");
			}

			getActorManager().removeDestroyedActors();
			checkIfGameOver();
		} catch (Exception e) {
			System.err.println("Error during update: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the game is over due to player losing health or defeating the boss.
	 */
	@Override
	protected void checkIfGameOver() {
		if (getUserPlane().getHealth() <= 0) {
			System.out.println("Game Over!");
			stopGame();
			if (gameOverUI != null) gameOverUI.showGameOverUI();
		} else if (boss != null && boss.isDestroyed()) {
			System.out.println("Boss defeated! Congratulations!");
			stopGame();

			WinImage winImage = new WinImage(getScene().getWidth() / 2 - 200, getScene().getHeight() / 2 - 100);
			if (!getRoot().getChildren().contains(winImage)) {
				getRoot().getChildren().add(winImage);
				System.out.println("Win image added to root.");
			} else {
				System.out.println("Win image already exists in root.");
			}
		}
	}

	/**
	 * Returns the player to the main menu.
	 */
	private void returnToMainMenu() {
		System.out.println("Returning to Main Menu...");
		stopGame();
		notifyObservers("MainMenu");
	}

	/**
	 * Restarts the level by clearing the game state and reinitializing all elements.
	 */
	private void restartLevel() {
		stopGame();

		getRoot().getChildren().clear();
		getActorManager().clearAllActors();

		initializeBackground(getScene().getWidth(), getScene().getHeight());
		initializeFriendlyUnits();

		boss = null;
		spawnEnemyUnits();

		if (gameOverUI != null) {
			gameOverUI.hideGameOverUI();
		}
		startGame();

		System.out.println("LevelThree restarted!");
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

		// Configure resizing behavior for the user plane
		scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			if (getUserPlane() != null) {
				getUserPlane().setBounds(0, newHeight.doubleValue() - getUserPlane().getFitHeight());
			}
		});

		// Configure key press actions for controlling the user plane
		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case W -> getUserPlane().moveUp();
				case S -> getUserPlane().moveDown();
				case A -> getUserPlane().moveLeft();
				case D -> getUserPlane().moveRight();
				case SPACE -> getUserPlane().fireProjectile();
			}
		});

		// Configure key release actions for stopping the user plane
		scene.setOnKeyReleased(event -> {
			switch (event.getCode()) {
				case W, S -> getUserPlane().stopVertical();
				case A, D -> getUserPlane().stopHorizontal();
			}
		});

		return scene;
	}

	/**
	 * Resizes all elements in the level to adapt to the new screen dimensions.
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
}
