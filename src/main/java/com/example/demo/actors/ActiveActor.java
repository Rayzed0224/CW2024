package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.*;

/**
 * Base class for all active entities in the game, providing movement and image setup.
 */

public abstract class ActiveActor extends ImageView {

	protected static final String IMAGE_LOCATION = "/com/example/demo/images/";


	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		String fullImagePath = IMAGE_LOCATION + imageName;

		try {
			// Use getResourceAsStream to load the image
			if (getClass().getResource(fullImagePath) == null) {
				throw new NullPointerException("Resource not found: " + fullImagePath);
			}

			Image image = new Image(getClass().getResource(fullImagePath).toExternalForm());
			this.setImage(image);
		} catch (NullPointerException e) {
			System.err.println("Error: Image not found - " + fullImagePath);
		} catch (IllegalArgumentException e) {
			System.err.println("Error: Illegal argument for image loading - " + e.getMessage());
		}

		// Initialize ImageView properties regardless of image loading success
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}


	// Ensure this method is defined and properly accessible
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		setTranslateX(getTranslateX() * widthRatio);
		setTranslateY(getTranslateY() * heightRatio);
	}
}
