package com.example.demo.projectiles;

/**
 * Represents projectiles fired by enemies.
 */
public class EnemyProjectile extends Projectile {

	/**
	 * The image file name for the enemy projectile.
	 */
	private static final String IMAGE_NAME = "enemyFire.png";

	/**
	 * The height of the projectile image.
	 */
	private static final int IMAGE_HEIGHT = 30;

	/**
	 * The horizontal velocity of the projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an EnemyProjectile with the specified initial position.
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
