package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;

	// Original positions for scaling reference
	private static final double ORIGINAL_X_POSITION = 5.0;
	private static final double ORIGINAL_Y_POSITION = 25.0;

	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;

	/**
	 * Manages and displays player health using heart icons.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the display.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Gets the container for the heart display.
	 *
	 * @return The HBox container containing the heart images.
	 */
	public HBox getContainer() {
		return container;
	}

	/**
	 * Adjusts the position of the heart display when resizing.
	 *
	 * @param newWidth  The new width of the screen.
	 * @param newHeight The new height of the screen.
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Update layout positions according to the new screen ratios
		container.setLayoutX(ORIGINAL_X_POSITION * widthRatio);
		container.setLayoutY(ORIGINAL_Y_POSITION * heightRatio);
	}

	public void adjustPosition(double xPosition, double yPosition) {
		container.setLayoutX(xPosition);
		container.setLayoutY(yPosition);
	}
}
