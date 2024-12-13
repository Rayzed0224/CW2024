package com.example.demo.actors;

import com.example.demo.levels.ActorManager;
import com.example.demo.levels.LevelOne;
import com.example.demo.levels.LevelParent;
import com.example.demo.projectiles.UserProjectile;
import com.example.demo.levels.LevelParentBase;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * The player-controlled fighter plane.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private final double PROJECTILE_X_POSITION_OFFSET = getFitWidth() / 2.0; // Center horizontally
	private final double PROJECTILE_Y_POSITION_OFFSET = getLayoutY() + 20 - getFitHeight() / 2.0; // Spawn above the plane

	private final ActorManager actorManager; // Reference to ActorManager
	private final Group root;
	private final LevelParentBase level;

	// Movement multipliers
	private int verticalVelocityMultiplier = 0; // Up (-1), Down (+1), Stop (0)
	private int horizontalVelocityMultiplier = 0; // Left (-1), Right (+1), Stop (0)

	private int numberOfKills;
	private double yUpperBound;
	private double yLowerBound;
	private double xLeftBound;
	private double xRightBound;

	public double getYUpperBound() {
		return yUpperBound;
	}

	public double getYLowerBound() {
		return yLowerBound;
	}

	public UserPlane(double initialXPos, double initialYPos, Group root , ActorManager actorManager, int initialHealth, LevelParentBase level) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, initialHealth);
		this.root = root;
		this.actorManager = actorManager;
		this.level = level; // Use the generalized LevelParentBase type

		if (actorManager == null) {
			throw new IllegalArgumentException("ActorManager cannot be null");
		}

		System.out.println("UserPlane initialized with: ");
		System.out.println("Initial X: " + initialXPos);
		System.out.println("Initial Y: " + initialYPos);
		System.out.println("ActorManager: " + actorManager);
		System.out.println("Initial Health: " + initialHealth);

		if (level == null) {
			throw new IllegalArgumentException("Level cannot be null");
		}

		// Initialize movement bounds
		this.yUpperBound = 0;
		this.yLowerBound = root.getScene().getHeight() - getFitHeight();

		System.out.println("Bounds Set: Upper = " + yUpperBound + ", Lower = " + yLowerBound);

		this.verticalVelocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
	}

	public void setBounds(double yUpperBound, double yLowerBound) {
		this.yUpperBound = yUpperBound;
		this.yLowerBound = yLowerBound;
		System.out.println("Bounds updated: yUpper=" + yUpperBound + ", yLower=" + yLowerBound);
	}

	public void updateBounds(double sceneWidth, double sceneHeight) {
		yUpperBound = 0; // Top of the screen
		yLowerBound = sceneHeight - getFitHeight(); // Bottom of the screen
		xLeftBound = 0; // Leftmost edge
		xRightBound = sceneWidth - getFitWidth(); // Rightmost edge

		System.out.println("Updated Bounds: ");
		System.out.println("yUpperBound: " + yUpperBound);
		System.out.println("yLowerBound: " + yLowerBound);
		System.out.println("xLeftBound: " + xLeftBound);
		System.out.println("xRightBound: " + xRightBound);
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		System.out.println("UserPlane: Y=" + getLayoutY());
		double newY = getLayoutY() + verticalVelocityMultiplier * VERTICAL_VELOCITY;
		double newX = getLayoutX() + horizontalVelocityMultiplier * HORIZONTAL_VELOCITY;

		// Clamp Y position within bounds
		newY = Math.min(Math.max(newY, yUpperBound), yLowerBound);

		// Clamp X position within bounds
		newX = Math.min(Math.max(newX, xLeftBound), xRightBound);

		// Set the updated position
		setLayoutY(newY);
		setLayoutX(newX);

		System.out.println("Updated Plane Position: (" + newX + ", " + newY + ")");
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public UserProjectile fireProjectile() {
		// Use the inherited methods to calculate accurate spawn positions
		double projectileXPosition = getProjectileXPosition(getFitWidth() / 2.0); // Center horizontally
		double projectileYPosition = getProjectileYPosition(-getFitHeight() / 2.0); // Spawn slightly above the plane

		// Use root to add projectile to the scene
		UserProjectile projectile = new UserProjectile(projectileXPosition, projectileYPosition, root);

		// Add the projectile to the ActorManager
		actorManager.addUserProjectile(projectile);

		System.out.println("Firing projectile from X: " + projectileXPosition + ", Y: " + projectileYPosition);

		return projectile;
	}


	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	public LevelParentBase getLevel() {
		return level;
	}

	@Override
	public void takeDamage() {
		super.takeDamage(); // Reduce health

		// Update heart display for all levels
		if (level instanceof LevelParentBase) {
			LevelParentBase parentLevel = (LevelParentBase) level;
			Platform.runLater(() -> parentLevel.updateHeartDisplay(getHealth()));
		}
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		// Update bounds based on new dimensions
		updateBounds(newWidth, newHeight);

		// Clamp current position within updated bounds
		double newX = Math.min(Math.max(getLayoutX(), xLeftBound), xRightBound);
		double newY = Math.min(Math.max(getLayoutY(), yUpperBound), yLowerBound);

		setLayoutX(newX);
		setLayoutY(newY);
	}
}
