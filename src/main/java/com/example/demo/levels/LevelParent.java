package com.example.demo.levels;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * Refactored LevelParent class using modular components.
 *
 * <p>This abstract class serves as the base for game levels, providing modular
 * management for actors, collisions, and game state. It integrates
 * functionalities like updating actors, handling collisions, and checking
 * game conditions (win/lose).</p>
 */
public abstract class LevelParent extends LevelParentBase {

	/**
	 * Manages all actors in the level, including the player, enemies, and projectiles.
	 */
	private final ActorManager actorManager;

	/**
	 * Handles collision detection between actors in the level.
	 */
	private final CollisionManager collisionManager;

	/**
	 * Manages the game state, including win and lose conditions.
	 */
	private final LevelGameStateManager gameStateManager;

	/**
	 * Constructs a LevelParent with specified parameters.
	 *
	 * @param stage               the primary stage of the game
	 * @param backgroundImageName the file name of the background image
	 * @param playerInitialHealth the initial health of the player
	 */
	public LevelParent(Stage stage, String backgroundImageName, int playerInitialHealth) {
		super(backgroundImageName, ORIGINAL_SCREEN_WIDTH, ORIGINAL_SCREEN_HEIGHT, playerInitialHealth);

		this.actorManager = new ActorManager(root);
		this.collisionManager = new CollisionManager();
		this.gameStateManager = new LevelGameStateManager(this);
	}

	/**
	 * Updates the level by managing actors, handling collisions, and checking game conditions.
	 */
	@Override
	protected void update() {
		actorManager.updateAll(); // Update all actors (player, enemies, projectiles)

		// Handle collisions between user projectiles and enemies
		collisionManager.handleCollisions(actorManager.getUserProjectiles(), actorManager.getEnemies());

		// Handle collisions between enemies and the user plane
		collisionManager.handleCollisions(actorManager.getEnemies(), List.of(userPlane));

		actorManager.removeDestroyedActors(); // Remove destroyed actors
		gameStateManager.checkWinCondition(); // Check if the level is won
		gameStateManager.checkLoseCondition(); // Check if the level is lost
	}

	/**
	 * Retrieves the ActorManager for managing all actors in the level.
	 *
	 * @return the actor manager
	 */
	public ActorManager getActorManager() {
		return actorManager;
	}

	/**
	 * Retrieves the CollisionManager for handling collisions in the level.
	 *
	 * @return the collision manager
	 */
	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	/**
	 * Checks if the game is over (either won or lost).
	 *
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		return gameStateManager.isGameOver();
	}

	/**
	 * Retrieves the scene associated with the level.
	 *
	 * @return the scene of the level
	 */
	@Override
	public Scene getScene() {
		return scene;
	}
}
