package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import com.example.demo.projectiles.EnemyProjectile;

/**
 * Represents enemy fighter planes.
 * Inherits from FighterPlane.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";  // Correct path for resource
	private static final int IMAGE_HEIGHT = 150;
	private static final int INITIAL_HEALTH = 1;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final double FIRE_RATE = 0.01;  // Probability of firing in each update

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Fires a projectile if the random condition is met.
	 * Uses inherited methods to calculate projectile position.
	 *
	 * @return The newly fired projectile or null if no projectile is fired.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	public void updatePosition() {
		// Moves the plane horizontally (towards the left, as an example)
		double currentX = getTranslateX();
		setTranslateX(currentX - 6);  // Moves left by 6 units
	}

	@Override
	public void updateActor() {
		// Update the position of the plane
		updatePosition();

		// Fire a projectile if conditions are met
		ActiveActorDestructible projectile = fireProjectile();
		if (projectile != null) {
			// Add the projectile to the game, assuming there's logic to handle this
			// For example, you might add it to a list of active projectiles or to the game scene
		}
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Adjust position directly using `this`
		this.setTranslateX(this.getTranslateX() * widthRatio);
		this.setTranslateY(this.getTranslateY() * heightRatio);
	}
}
