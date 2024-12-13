package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.*;

/**
 * Base class for all active entities in the game, providing movement and image setup.
 */

public abstract class ActiveActor extends ImageView {

	protected static final String IMAGE_LOCATION = "/com/example/demo/images/";

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
