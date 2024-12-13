package com.example.demo.levels;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * Refactored LevelParent class using modular components.
 */
public abstract class LevelParent extends LevelParentBase {
	private final ActorManager actorManager;
	private final CollisionManager collisionManager;
	private final LevelGameStateManager gameStateManager;

	public LevelParent(Stage stage, String backgroundImageName, int playerInitialHealth) {
		super(backgroundImageName, ORIGINAL_SCREEN_WIDTH, ORIGINAL_SCREEN_HEIGHT, playerInitialHealth);

		this.actorManager = new ActorManager(root);
		this.collisionManager = new CollisionManager();
		this.gameStateManager = new LevelGameStateManager(this);
	}

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

	public ActorManager getActorManager() {
		return actorManager;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public boolean isGameOver() {
		return gameStateManager.isGameOver();
	}

	@Override
	public Scene getScene() {
		return scene;
	}
}