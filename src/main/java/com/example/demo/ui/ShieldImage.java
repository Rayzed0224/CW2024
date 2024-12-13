package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a shield image with health for the boss.
 */
public class ShieldImage extends ImageView {

	private int health = 3; // Default shield health

	/**
	 * Constructs a ShieldImage with the specified position.
	 *
	 * @param xPosition The x-coordinate of the shield.
	 * @param yPosition The y-coordinate of the shield.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		super(loadShieldImage()); // Call the utility method
		setFitWidth(50);  // Explicitly set the width
		setFitHeight(50); // Explicitly set the height
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		System.out.println("Shield created at X: " + xPosition + ", Y: " + yPosition);
	}

	private static Image loadShieldImage() {
		try {
			return new Image(ShieldImage.class.getResource("/com/example/demo/images/shield.png").toExternalForm());
		} catch (NullPointerException e) {
			System.err.println("Error: Shield image not found. Using placeholder.");
			return new Image(ShieldImage.class.getResource("/com/example/demo/images/placeholder.png").toExternalForm());
		}
	}

	/**
	 * Reduces the shield's health by the specified damage amount.
	 *
	 * @param damage The amount of damage to inflict.
	 */
	public void reduceHealth(int damage) {
		health -= damage;
		if (health < 0) {
			health = 0;
		}
		System.out.println("Shield health reduced. Current health: " + health);
	}

	/**
	 * Gets the current health of the shield.
	 *
	 * @return The shield's current health.
	 */
	public int getHealth() {
		return health;
	}

	public void showShield() {
		setVisible(true); // Make shield visible
		System.out.println("Shield is now visible.");
	}

	public void hideShield() {
		setVisible(false); // Make shield invisible
		System.out.println("Shield is now hidden.");
	}

	public void adjustPositionForResize(double newWidth, double newHeight) {
		double xRatio = newWidth / 1920.0; // Assuming original width is 1920
		double yRatio = newHeight / 1080.0; // Assuming original height is 1080

		setLayoutX(getLayoutX() * xRatio);
		setLayoutY(getLayoutY() * yRatio);

		System.out.println("Shield resized to X: " + getLayoutX() + ", Y: " + getLayoutY());
	}

	public void adjustPosition(double offsetX, double offsetY) {
		setLayoutX(getLayoutX() + offsetX);
		setLayoutY(getLayoutY() + offsetY);

		System.out.println("Shield position adjusted to X: " + getLayoutX() + ", Y: " + getLayoutY());
	}
}