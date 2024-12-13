package com.example.demo.projectiles;

import javafx.scene.Group;

/**
 * Represents projectiles fired by the player.
 */

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int HORIZONTAL_VELOCITY = 30;
	private final Group root;

	public UserProjectile(double initialXPos, double initialYPos, Group root) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		if (root == null) {
			throw new IllegalArgumentException("Root cannot be null");
		}
		this.root = root;
	}

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
	
	@Override
	public void updateActor() {
		updatePosition();
	}
}
