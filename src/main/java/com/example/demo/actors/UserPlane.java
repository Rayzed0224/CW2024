package com.example.demo.actors;

import com.example.demo.levels.ActorManager;
import com.example.demo.levels.LevelParentBase;
import com.example.demo.projectiles.UserProjectile;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * The player-controlled fighter plane.
 */
public class UserPlane extends FighterPlane {

	/**
	 * The name of the image file representing the user plane.
	 */
	private static final String IMAGE_NAME = "userplane.png";

	/**
	 * The height of the user plane image.
	 */
	private static final int IMAGE_HEIGHT = 20;

	/**
	 * The vertical velocity of the user plane.
	 */
	private static final int VERTICAL_VELOCITY = 8;

	/**
	 * The horizontal velocity of the user plane.
	 */
	private static final int HORIZONTAL_VELOCITY = 8;

	/**
	 * Offset for the projectile's X position relative to the plane's position.
	 */
	private final double PROJECTILE_X_POSITION_OFFSET = getFitWidth() / 2.0;

	/**
	 * Offset for the projectile's Y position relative to the plane's position.
	 */
	private final double PROJECTILE_Y_POSITION_OFFSET = getLayoutY() + 20 - getFitHeight() / 2.0;

	/**
	 * Reference to the ActorManager for managing projectiles.
	 */
	private final ActorManager actorManager;

	/**
	 * The root node of the scene graph.
	 */
	private final Group root;

	/**
	 * Reference to the current level the user plane is in.
	 */
	private final LevelParentBase level;

	/**
	 * Multiplier for vertical velocity (Up: -1, Down: +1, Stop: 0).
	 */
	private int verticalVelocityMultiplier = 0;

	/**
	 * Multiplier for horizontal velocity (Left: -1, Right: +1, Stop: 0).
	 */
	private int horizontalVelocityMultiplier = 0;

	/**
	 * The number of kills achieved by the user.
	 */
	private int numberOfKills;

	/**
	 * The upper bound for the plane's Y position.
	 */
	private double yUpperBound;

	/**
	 * The lower bound for the plane's Y position.
	 */
	private double yLowerBound;

	/**
	 * The left bound for the plane's X position.
	 */
	private double xLeftBound;

	/**
	 * The right bound for the plane's X position.
	 */
	private double xRightBound;

	/**
	 * Gets the upper bound for the Y position.
	 *
	 * @return the upper bound for Y position
	 */
	public double getYUpperBound() {
		return yUpperBound;
	}

	/**
	 * Gets the lower bound for the Y position.
	 *
	 * @return the lower bound for Y position
	 */
	public double getYLowerBound() {
		return yLowerBound;
	}

	/**
	 * Constructs a UserPlane with specified position, health, and level.
	 *
	 * @param initialXPos    the initial X position of the plane
	 * @param initialYPos    the initial Y position of the plane
	 * @param root           the root node of the scene graph
	 * @param actorManager   the ActorManager for managing projectiles
	 * @param initialHealth  the initial health of the plane
	 * @param level          the current level the plane is in
	 */
	public UserPlane(double initialXPos, double initialYPos, Group root, ActorManager actorManager, int initialHealth, LevelParentBase level) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, initialHealth);
		this.root = root;
		this.actorManager = actorManager;
		this.level = level;

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

		this.yUpperBound = 0;
		this.yLowerBound = root.getScene().getHeight() - getFitHeight();

		System.out.println("Bounds Set: Upper = " + yUpperBound + ", Lower = " + yLowerBound);

		this.verticalVelocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
	}

	/**
	 * Sets the Y position bounds for the plane.
	 *
	 * @param yUpperBound the upper bound for Y position
	 * @param yLowerBound the lower bound for Y position
	 */
	public void setBounds(double yUpperBound, double yLowerBound) {
		this.yUpperBound = yUpperBound;
		this.yLowerBound = yLowerBound;
		System.out.println("Bounds updated: yUpper=" + yUpperBound + ", yLower=" + yLowerBound);

	}

	/**
	 * Updates the position bounds based on the scene dimensions.
	 *
	 * @param sceneWidth  the width of the scene
	 * @param sceneHeight the height of the scene
	 */
	public void updateBounds(double sceneWidth, double sceneHeight) {
		yUpperBound = 0;
		yLowerBound = sceneHeight - getFitHeight();
		xLeftBound = 0;
		xRightBound = sceneWidth - getFitWidth();

		System.out.println("Updated Bounds: ");
		System.out.println("yUpperBound: " + yUpperBound);
		System.out.println("yLowerBound: " + yLowerBound);
		System.out.println("xLeftBound: " + xLeftBound);
		System.out.println("xRightBound: " + xRightBound);
	}

	/**
	 * Updates the position of the user plane based on velocity and bounds.
	 */
	@Override
	public void updatePosition() {
		super.updatePosition();
		System.out.println("UserPlane: Y=" + getLayoutY());

		double newY = getLayoutY() + verticalVelocityMultiplier * VERTICAL_VELOCITY;
		double newX = getLayoutX() + horizontalVelocityMultiplier * HORIZONTAL_VELOCITY;

		newY = Math.min(Math.max(newY, yUpperBound), yLowerBound);
		newX = Math.min(Math.max(newX, xLeftBound), xRightBound);

		setLayoutY(newY);
		setLayoutX(newX);
		System.out.println("Updated Plane Position: (" + newX + ", " + newY + ")");

	}

	/**
	 * Updates the actor's state (e.g., position).
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 *
	 * @return the fired UserProjectile
	 */
	@Override
	public UserProjectile fireProjectile() {
		double projectileXPosition = getProjectileXPosition(getFitWidth() / 2.0);
		double projectileYPosition = getProjectileYPosition(-getFitHeight() / 2.0);

		UserProjectile projectile = new UserProjectile(projectileXPosition, projectileYPosition, root);
		actorManager.addUserProjectile(projectile);

		return projectile;
	}

	/**
	 * Moves the plane up by setting the vertical velocity multiplier to -1.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane down by setting the vertical velocity multiplier to 1.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Stops vertical movement by setting the vertical velocity multiplier to 0.
	 */
	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Moves the plane left by setting the horizontal velocity multiplier to -1.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane right by setting the horizontal velocity multiplier to 1.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops horizontal movement by setting the horizontal velocity multiplier to 0.
	 */
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Gets the number of kills achieved by the user.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the user's kill count by 1.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Gets the current level the plane is in.
	 *
	 * @return the current level
	 */
	public LevelParentBase getLevel() {
		return level;
	}

	/**
	 * Reduces the plane's health and updates the heart display if health is reduced.
	 */
	@Override
	public void takeDamage() {
		super.takeDamage();

		if (level instanceof LevelParentBase) {
			LevelParentBase parentLevel = (LevelParentBase) level;
			Platform.runLater(() -> parentLevel.updateHeartDisplay(getHealth()));
		}
	}

	/**
	 * Adjusts the position and bounds of the plane when the screen is resized.
	 *
	 * @param newWidth  the new width of the screen
	 * @param newHeight the new height of the screen
	 */
	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		updateBounds(newWidth, newHeight);

		double newX = Math.min(Math.max(getLayoutX(), xLeftBound), xRightBound);
		double newY = Math.min(Math.max(getLayoutY(), yUpperBound), yLowerBound);

		setLayoutX(newX);
		setLayoutY(newY);
	}
}
