package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the victory screen.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int ORIGINAL_HEIGHT = 500;
	private static final int ORIGINAL_WIDTH = 600;

	// Store original positions for proper resizing
	private double originalXPosition;
	private double originalYPosition;

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

	public void showWinImage() {
		this.setVisible(true);
	}

	/**
	 * Adjusts the position and size of the win image when resizing.
	 * @param newWidth New width of the screen.
	 * @param newHeight New height of the screen.
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

	public void adjustPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
