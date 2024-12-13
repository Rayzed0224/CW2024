package com.example.demo.projectiles;

/**
 * Represents projectiles fired by the boss.
 */
public class BossProjectile extends Projectile {

	/**
	 * The image file name for the boss projectile.
	 */
	private static final String IMAGE_NAME = "fireball.png";

	/**
	 * The height of the projectile image.
	 */
	private static final int IMAGE_HEIGHT = 40;

	/**
	 * The horizontal velocity of the projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = -15;

	/**
	 * Constructs a BossProjectile with the specified initial position.
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public BossProjectile(double initialXPos, double initialYPos) {
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
