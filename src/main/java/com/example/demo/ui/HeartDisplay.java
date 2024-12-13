package com.example.demo.ui;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Manages and displays player health using heart icons.
 *
 * <p>This class dynamically adjusts the number of hearts displayed to
 * represent the player's current health. It supports adding or removing
 * hearts and adjusting its position when the screen is resized.</p>
 */
public class HeartDisplay {

	/**
	 * The file path of the heart image resource.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/**
	 * The height of the heart image.
	 */
	private static final int HEART_HEIGHT = 50;

	/**
	 * The index of the first item in the container, used for removal.
	 */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/**
	 * The original X position of the heart display for scaling reference.
	 */
	private static final double ORIGINAL_X_POSITION = 5.0;

	/**
	 * The original Y position of the heart display for scaling reference.
	 */
	private static final double ORIGINAL_Y_POSITION = 25.0;

	/**
	 * The container holding the heart icons.
	 */
	private HBox container;

	/**
	 * The X position of the heart display container.
	 */
	private double containerXPosition;

	/**
	 * The Y position of the heart display container.
	 */
	private double containerYPosition;

	/**
	 * The number of hearts to display initially.
	 */
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay and initializes its components.
	 *
	 * @param xPosition       the initial X position of the heart display
	 * @param yPosition       the initial Y position of the heart display
	 * @param heartsToDisplay the number of hearts to display initially
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for the heart icons.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the heart icons in the display.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Updates the heart display to match the current health.
	 *
	 * @param currentHealth the player's current health
	 */
	public void updateHealth(int currentHealth) {
		int displayedHearts = container.getChildren().size();

		if (currentHealth < displayedHearts) {
			// Remove excess hearts
			int heartsToRemove = displayedHearts - currentHealth;
			for (int i = 0; i < heartsToRemove; i++) {
				removeHeart();
			}
		} else if (currentHealth > displayedHearts) {
			// Add missing hearts
			int heartsToAdd = currentHealth - displayedHearts;
			for (int i = 0; i < heartsToAdd; i++) {
				ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
				heart.setFitHeight(HEART_HEIGHT);
				heart.setPreserveRatio(true);
				container.getChildren().add(heart);
			}
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
	 * @return the HBox container containing the heart images
	 */
	public HBox getContainer() {
		return container;
	}

	/**
	 * Adjusts the position of the heart display when resizing.
	 *
	 * @param newWidth  the new width of the screen
	 * @param newHeight the new height of the screen
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Update layout positions according to the new screen ratios
		container.setLayoutX(ORIGINAL_X_POSITION * widthRatio);
		container.setLayoutY(ORIGINAL_Y_POSITION * heightRatio);
	}

	/**
	 * Adjusts the position of the heart display to specified coordinates.
	 *
	 * @param xPosition the new X position of the heart display
	 * @param yPosition the new Y position of the heart display
	 */
	public void adjustPosition(double xPosition, double yPosition) {
		container.setLayoutX(xPosition);
		container.setLayoutY(yPosition);
	}
}
