package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the boss's shield status.
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 200;

	// Store original positions for proper resizing
	private double originalXPosition;
	private double originalYPosition;

	public ShieldImage(double xPosition, double yPosition) {
		this.originalXPosition = xPosition;
		this.originalYPosition = yPosition;

		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		this.setVisible(true);
	}

	public void hideShield() {
		this.setVisible(false);
	}

	/**
	 * Adjusts the position and size of the shield image when resizing.
	 * @param newWidth New width of the screen.
	 * @param newHeight New height of the screen.
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		setTranslateX(getTranslateX() * widthRatio);
		setTranslateY(getTranslateY() * heightRatio);
	}


	public void adjustPosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}
