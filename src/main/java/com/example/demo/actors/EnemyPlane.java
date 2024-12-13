package com.example.demo.actors;

import com.example.demo.levels.ActorManager;
import com.example.demo.levels.LevelParentBase;

/**
 * Represents enemy fighter planes.
 * Inherits from FighterPlane.
 */
public class EnemyPlane extends FighterPlane {

	/**
	 * The image file name for the enemy plane.
	 */
	private static final String IMAGE_NAME = "enemyplane.png";  // Correct path for resource

	/**
	 * The height of the enemy plane image.
	 */
	private static final int IMAGE_HEIGHT = 150;

	/**
	 * The horizontal offset for projectile spawning.
	 */
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

	/**
	 * The vertical offset for projectile spawning.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

	/**
	 * The probability of firing a projectile in each update.
	 */
	private static final double FIRE_RATE = 0.01;  // Probability of firing in each update

	/**
	 * Manages the actors within the game, used for creating projectiles.
	 */
	private final ActorManager actorManager;

	/**
	 * Reference to the user's plane.
	 */
	private final UserPlane userPlane;

	/**
	 * Constructs an EnemyPlane with the specified properties.
	 *
	 * @param initialXPos  The initial X position of the enemy plane.
	 * @param initialYPos  The initial Y position of the enemy plane.
	 * @param actorManager The manager responsible for actor creation and handling.
	 * @param userPlane    The user's plane that the enemy interacts with.
	 * @param health       The initial health of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos, ActorManager actorManager, UserPlane userPlane, int health) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, health);
		this.actorManager = actorManager; // Store ActorManager for projectile management
		this.userPlane = userPlane;      // Initialize UserPlane reference
	}

	/**
	 * Sets the health of the enemy plane.
	 *
	 * @param health The new health value to set.
	 */
	public void setHealth(int health) {
		this.health = health; // Update the health field
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally and firing projectiles.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		fireProjectile();
	}

	/**
	 * Adjusts the position of the enemy plane based on the new screen dimensions.
	 *
	 * @param newWidth  The new screen width.
	 * @param newHeight The new screen height.
	 */
	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParentBase.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParentBase.ORIGINAL_SCREEN_HEIGHT;

		this.setTranslateX(this.getTranslateX() * widthRatio);
		this.setTranslateY(this.getTranslateY() * heightRatio);
	}

	/**
	 * Attempts to fire a projectile from the enemy plane.
	 *
	 * @return The projectile created, or null if no projectile is fired.
	 */
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

	/**
	 * Updates the position of the enemy plane by moving it to the left.
	 */
	@Override
	public void updatePosition() {
		double currentX = getTranslateX();
		setTranslateX(currentX - 2); // Moves left by 2 units
	}
}
