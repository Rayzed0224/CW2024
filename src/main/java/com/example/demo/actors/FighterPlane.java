package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract base class for all fighter planes (player and enemies).
 */

public abstract class FighterPlane extends ActiveActorDestructible {

	protected ImageView planeView;
	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	// Abstract method that all subclasses must implement to fire projectiles.
	public abstract ActiveActorDestructible fireProjectile();

	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health <= 0;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Use this to adjust the position
		this.setTranslateX(this.getTranslateX() * widthRatio);
		this.setTranslateY(this.getTranslateY() * heightRatio);
	}

}