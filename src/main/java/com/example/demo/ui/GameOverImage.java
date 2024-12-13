package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the game-over screen.
 */
public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final double ORIGINAL_X_POSITION = -160.0; // Original x position for scaling reference
	private static final double ORIGINAL_Y_POSITION = -375.0; // Original y position for scaling reference

	private double originalX; // To store the initial X position during instantiation
	private double originalY; // To store the initial Y position during instantiation

	public GameOverImage(double xPosition, double yPosition) {
		// Load and set the game-over image
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));

		// Scale the image
		setFitWidth(400);  // Set the desired width
		setPreserveRatio(true);  // Maintain aspect ratio

		// Set position
		setLayoutX(xPosition);
		setLayoutY(yPosition);

		// Store the original positions for later use during resizing
		this.originalX = xPosition;
		this.originalY = yPosition;
	}

	/**
	 * Adjusts the position of the game-over image when the window is resized.
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Update layout positions according to the new screen ratios
		setLayoutX(originalX * widthRatio);
		setLayoutY(originalY * heightRatio);
	}

	public void adjustPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
