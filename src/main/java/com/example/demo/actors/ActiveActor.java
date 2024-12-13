package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Base class for all active entities in the game, providing movement and image setup.
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * The base directory for image assets used by the active actor.
	 */
	protected static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an ActiveActor with a specific image, size, and initial position.
	 *
	 * @param imageName   the name of the image file to use for this actor
	 * @param imageHeight the height of the image to be displayed
	 * @param initialXPos the initial X position of the actor
	 * @param initialYPos the initial Y position of the actor
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		try {
			Image image = new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm());
			this.setImage(image);
			System.out.println("Image loaded: " + IMAGE_LOCATION + imageName);
		} catch (NullPointerException e) {
			System.err.println("Error: Image not found - " + IMAGE_LOCATION + imageName);
		}

		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor. This method must be implemented by subclasses.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified amount.
	 *
	 * @param horizontalMove the amount to move the actor horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified amount.
	 *
	 * @param verticalMove the amount to move the actor vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	/**
	 * Adjusts the position of the actor when the screen is resized.
	 *
	 * @param newWidth  the new width of the screen
	 * @param newHeight the new height of the screen
	 */
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		setTranslateX(getTranslateX() * widthRatio);
		setTranslateY(getTranslateY() * heightRatio);
	}
}
