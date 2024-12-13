package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the game-over screen with an image.
 */
public class GameOverImage extends ImageView {

	/**
	 * The file path of the game-over image.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * The original X position of the image for scaling reference.
	 */
	private static final double ORIGINAL_X_POSITION = -160.0;

	/**
	 * The original Y position of the image for scaling reference.
	 */
	private static final double ORIGINAL_Y_POSITION = -375.0;

	/**
	 * The initial X position of the image during instantiation.
	 */
	private double originalX;

	/**
	 * The initial Y position of the image during instantiation.
	 */
	private double originalY;

	/**
	 * Constructs a GameOverImage with the specified initial position.
	 *
	 * @param xPosition The initial X position of the image.
	 * @param yPosition The initial Y position of the image.
	 */
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
	 *
	 * @param newWidth  The new width of the window.
	 * @param newHeight The new height of the window.
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Update layout positions according to the new screen ratios
		setLayoutX(originalX * widthRatio);
		setLayoutY(originalY * heightRatio);
	}

	/**
	 * Adjusts the position of the game-over image to the specified coordinates.
	 *
	 * @param xPosition The new X position of the image.
	 * @param yPosition The new Y position of the image.
	 */
	public void adjustPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
