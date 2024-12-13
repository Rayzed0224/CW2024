package com.example.demo.actors;

import com.example.demo.levels.ActorManager;
import com.example.demo.levels.LevelParentBase;
import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.projectiles.HomingProjectile;
import com.example.demo.projectiles.Projectile;

/**
 * Represents enemy fighter planes.
 * Inherits from FighterPlane.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";  // Correct path for resource
	private static final int IMAGE_HEIGHT = 150;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final double FIRE_RATE = 0.01;  // Probability of firing in each update

	private final ActorManager actorManager;
	private final UserPlane userPlane;

	public EnemyPlane(double initialXPos, double initialYPos, ActorManager actorManager, UserPlane userPlane, int health) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, health);
		this.actorManager = actorManager; // Store ActorManager for projectile management
		this.userPlane = userPlane;      // Initialize UserPlane reference
	}

	public void setHealth(int health) {
		this.health = health; // Update the health field
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updateActor() {
		// Update position and fire projectiles
		updatePosition();
		fireProjectile();
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParentBase.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParentBase.ORIGINAL_SCREEN_HEIGHT;

		this.setTranslateX(this.getTranslateX() * widthRatio);
		this.setTranslateY(this.getTranslateY() * heightRatio);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		System.out.println("EnemyPlane attempting to fire projectile...");
		if (Math.random() < FIRE_RATE) { // Ensure FIRE_RATE is high enough for testing
			actorManager.createEnemyProjectile(
					this.getLayoutX(),
					this.getLayoutY(),
					userPlane,
					"enemyFire.png"
			);
			System.out.println("Projectile fired.");
			return null; // Return the projectile if needed
		}
		return null;
	}

	@Override
	public void updatePosition() {
		// Moves the plane horizontally (towards the left)
		double currentX = getTranslateX();
		setTranslateX(currentX - 2); // Moves left by 2 units
	}
}
