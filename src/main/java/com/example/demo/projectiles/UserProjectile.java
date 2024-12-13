package com.example.demo.projectiles;

import javafx.scene.Group;

/**
 * Represents projectiles fired by the player.
 */
public class UserProjectile extends Projectile {

	/**
	 * The image file name for the user projectile.
	 */
	private static final String IMAGE_NAME = "userfire.png";

	/**
	 * The height of the projectile image.
	 */
	private static final int IMAGE_HEIGHT = 15;

	/**
	 * The horizontal velocity of the projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = 30;

	/**
	 * The root group where the projectile is added.
	 */
	private final Group root;

	/**
	 * Constructs a UserProjectile with the specified initial position and root group.
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 * @param root        The root group to add the projectile to.
	 * @throws IllegalArgumentException If the root group is null.
	 */
	public UserProjectile(double initialXPos, double initialYPos, Group root) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		if (root == null) {
			throw new IllegalArgumentException("Root cannot be null");
		}
		this.root = root;
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 * If the projectile moves out of bounds, it is removed from the scene.
	 */
	@Override
	public void updatePosition() {
		double newX = getLayoutX() + HORIZONTAL_VELOCITY;

		System.out.println("Projectile moving from X: " + getLayoutX() + " to X: " + newX);

		// Update position
		setLayoutX(newX);

		// Get the actual scene width dynamically
		double sceneWidth = root.getScene().getWidth(); // Dynamically fetch scene width
		if (newX > sceneWidth) {
			System.out.println("Projectile out of bounds at X: " + newX + ". Removing from scene.");
			setVisible(false); // Hide the projectile when it goes off-screen
			destroy();
		}
	}

	/**
	 * Updates the state of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
