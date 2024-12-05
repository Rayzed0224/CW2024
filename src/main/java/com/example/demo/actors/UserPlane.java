package com.example.demo.actors;

import com.example.demo.projectiles.UserProjectile;
import com.example.demo.levels.LevelParent;

/**
 * The player-controlled fighter plane.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png"; // Corrected path for resource
	private static final double ORIGINAL_Y_UPPER_BOUND = -40;  // Original bound value for scaling calculations
	private static final double ORIGINAL_Y_LOWER_BOUND = LevelParent.ORIGINAL_SCREEN_HEIGHT - 100;  // Original lower bound value
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 20;
	private static final int VERTICAL_VELOCITY = 8;
	private static final double PROJECTILE_X_POSITION_OFFSET = 110.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 20.0;

	private int velocityMultiplier; // Multiplies by 1 (move down), -1 (move up), or 0 (stop)
	private int numberOfKills;
	private double yUpperBound; // Dynamically calculated based on screen size
	private double yLowerBound; // Dynamically calculated based on screen size

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		yUpperBound = ORIGINAL_Y_UPPER_BOUND;
		yLowerBound = ORIGINAL_Y_LOWER_BOUND;
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);

			// Calculate the new position
			double newPosition = getLayoutY() + getTranslateY();

			// Keep the plane within bounds
			if (newPosition < yUpperBound) {
				this.setTranslateY(yUpperBound - getLayoutY()); // Reset to the upper bound
			} else if (newPosition > yLowerBound) {
				this.setTranslateY(yLowerBound - getLayoutY()); // Reset to the lower bound
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition(); // Update position based on user input
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		// Calculate projectile starting position based on plane's current position
		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
		return new UserProjectile(projectileXPosition, projectileYPosition);
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Adjust translate values to new screen size
		setTranslateX(getTranslateX() * widthRatio);
		setTranslateY(getTranslateY() * heightRatio);

		// Adjust boundaries to match the new screen size without compounding scaling
		yUpperBound = ORIGINAL_Y_UPPER_BOUND * heightRatio;
		yLowerBound = newHeight - 100;  // Adjust lower bound dynamically based on the new screen height
	}
}
