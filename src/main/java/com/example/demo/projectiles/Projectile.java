package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;
import javafx.geometry.Point2D;

/**
 * Base class for all projectiles.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Indicates whether the projectile has homing behavior.
	 */
	private boolean isHoming = false;

	/**
	 * The target for homing behavior.
	 */
	private ActiveActorDestructible target;

	/**
	 * Constructs a Projectile with specified image, size, and initial position.
	 *
	 * @param imageName   the name of the image file representing the projectile
	 * @param imageHeight the height of the projectile image
	 * @param initialXPos the initial X position of the projectile
	 * @param initialYPos the initial Y position of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);

		setLayoutX(initialXPos);
		setLayoutY(initialYPos);

		System.out.println("Projectile initialized at LayoutX: " + initialXPos + ", LayoutY: " + initialYPos);
	}

	/**
	 * Enables homing behavior for the projectile, targeting a specific actor.
	 *
	 * @param target the target actor for the homing behavior
	 */
	public void enableHoming(ActiveActorDestructible target) {
		this.isHoming = true;
		this.target = target;
		System.out.println("Homing enabled for target: " + target);
	}

	/**
	 * Destroys the projectile when it takes damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile. If homing is enabled, the projectile
	 * moves towards its target. Otherwise, it moves in a default straight trajectory.
	 */
	@Override
	public void updatePosition() {
		if (isHoming && target != null) {
			// Calculate direction to target
			Point2D targetCoords = target.localToScene(target.getLayoutX(), target.getLayoutY());
			Point2D projectileCoords = localToScene(getLayoutX(), getLayoutY());

			double deltaX = targetCoords.getX() - projectileCoords.getX();
			double deltaY = targetCoords.getY() - projectileCoords.getY();
			double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

			// Normalize direction vector and scale by speed
			double speed = 5.0; // Adjust speed as needed
			double velocityX = (deltaX / distance) * speed;
			double velocityY = (deltaY / distance) * speed;

			// Update position
			setLayoutX(getLayoutX() + velocityX);
			setLayoutY(getLayoutY() + velocityY);
		} else {
			// Default movement (straight)
			setLayoutX(getLayoutX() + 5);
			setLayoutY(getLayoutY() - 5);
		}

		// Check for parent
		if (getParent() == null) {
			System.out.println("Warning: Projectile has no parent!");
		}
	}
}
