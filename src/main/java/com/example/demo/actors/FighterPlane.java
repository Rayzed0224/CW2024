package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import javafx.scene.image.ImageView;

/**
 * Abstract base class for all fighter planes (player and enemies).
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The visual representation of the plane.
	 */
	protected ImageView planeView;

	/**
	 * The health of the fighter plane.
	 */
	int health;

	/**
	 * Constructs a FighterPlane with the specified properties.
	 *
	 * @param imageName   The name of the image representing the plane.
	 * @param imageHeight The height of the image representing the plane.
	 * @param initialXPos The initial X position of the plane.
	 * @param initialYPos The initial Y position of the plane.
	 * @param health      The initial health of the plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method that all subclasses must implement to fire projectiles.
	 *
	 * @return The projectile fired by the fighter plane.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the plane by one. Destroys the plane if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position for spawning a projectile, based on an offset.
	 *
	 * @param xPositionOffset The horizontal offset for the projectile's position.
	 * @return The X position for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position for spawning a projectile, based on an offset.
	 *
	 * @param yPositionOffset The vertical offset for the projectile's position.
	 * @return The Y position for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks whether the plane's health has reached zero.
	 *
	 * @return {@code true} if the health is zero or less, {@code false} otherwise.
	 */
	private boolean healthAtZero() {
		return health <= 0;
	}

	/**
	 * Retrieves the current health of the plane.
	 *
	 * @return The current health of the plane.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Adjusts the position of the fighter plane based on the new screen dimensions.
	 *
	 * @param newWidth  The new screen width.
	 * @param newHeight The new screen height.
	 */
	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Use this to adjust the position
		this.setTranslateX(this.getTranslateX() * widthRatio);
		this.setTranslateY(this.getTranslateY() * heightRatio);
	}
}
