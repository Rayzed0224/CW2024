package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the victory screen with a win image.
 */
public class WinImage extends ImageView {

	/**
	 * The file path of the win image.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	/**
	 * The original height of the win image.
	 */
	private static final int ORIGINAL_HEIGHT = 500;

	/**
	 * The original width of the win image.
	 */
	private static final int ORIGINAL_WIDTH = 600;

	/**
	 * The original X position of the image for proper resizing.
	 */
	private double originalXPosition;

	/**
	 * The original Y position of the image for proper resizing.
	 */
	private double originalYPosition;

	/**
	 * Constructs a WinImage with the specified initial position.
	 *
	 * @param xPosition The initial X position of the win image.
	 * @param yPosition The initial Y position of the win image.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.originalXPosition = xPosition;
		this.originalYPosition = yPosition;

		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(ORIGINAL_HEIGHT);
		this.setFitWidth(ORIGINAL_WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Makes the win image visible.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}

	/**
	 * Adjusts the position and size of the win image when the screen is resized.
	 *
	 * @param newWidth  The new width of the screen.
	 * @param newHeight The new height of the screen.
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Adjust the layout positions to new screen size
		setLayoutX(getLayoutX() * widthRatio);
		setLayoutY(getLayoutY() * heightRatio);

		// Optionally, adjust size if you want it to scale with the window
		setFitWidth(getFitWidth() * widthRatio);
		setFitHeight(getFitHeight() * heightRatio);
	}

	/**
	 * Adjusts the position of the win image to the specified coordinates.
	 *
	 * @param xPosition The new X position of the image.
	 * @param yPosition The new Y position of the image.
	 */
	public void adjustPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}
