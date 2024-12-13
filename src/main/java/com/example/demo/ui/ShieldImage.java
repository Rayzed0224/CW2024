package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a shield image with health for the boss.
 */
public class ShieldImage extends ImageView {

	/**
	 * The current health of the shield.
	 */
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

	/**
	 * Loads the shield image.
	 *
	 * @return The loaded shield image.
	 */
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

	/**
	 * Makes the shield visible.
	 */
	public void showShield() {
		setVisible(true);
		System.out.println("Shield is now visible.");
	}

	/**
	 * Makes the shield invisible.
	 */
	public void hideShield() {
		setVisible(false);
		System.out.println("Shield is now hidden.");
	}

	/**
	 * Adjusts the position of the shield when the screen is resized.
	 *
	 * @param newWidth  The new width of the screen.
	 * @param newHeight The new height of the screen.
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double xRatio = newWidth / 1920.0; // Assuming original width is 1920
		double yRatio = newHeight / 1080.0; // Assuming original height is 1080

		setLayoutX(getLayoutX() * xRatio);
		setLayoutY(getLayoutY() * yRatio);

		System.out.println("Shield resized to X: " + getLayoutX() + ", Y: " + getLayoutY());
	}

	/**
	 * Adjusts the position of the shield by a specified offset.
	 *
	 * @param offsetX The offset to add to the current X position.
	 * @param offsetY The offset to add to the current Y position.
	 */
	public void adjustPosition(double offsetX, double offsetY) {
		setLayoutX(getLayoutX() + offsetX);
		setLayoutY(getLayoutY() + offsetY);

		System.out.println("Shield position adjusted to X: " + getLayoutX() + ", Y: " + getLayoutY());
	}
}
