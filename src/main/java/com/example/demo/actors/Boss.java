package com.example.demo.actors;

import com.example.demo.levels.ActorManager;
import com.example.demo.levels.LevelParent;
import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the boss logic, including shields and movement patterns.
 */
public class Boss extends FighterPlane {

	/**
	 * The name of the image file representing the boss.
	 */
	private static final String IMAGE_NAME = "bossplane.png";

	/**
	 * The initial X position of the boss.
	 */
	private static final double INITIAL_X_POSITION = LevelParent.ORIGINAL_SCREEN_WIDTH - 150.0;

	/**
	 * The initial Y position of the boss.
	 */
	private static final double INITIAL_Y_POSITION = LevelParent.ORIGINAL_SCREEN_HEIGHT / 2;

	/**
	 * The Y position offset for projectiles fired by the boss.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;

	/**
	 * The probability of the boss firing a projectile in a given frame.
	 */
	private static final double BOSS_FIRE_RATE = 0.04;

	/**
	 * The probability of the boss activating a shield in a given frame.
	 */
	private static final double BOSS_SHIELD_PROBABILITY = 0.2;

	/**
	 * The height of the boss image.
	 */
	private static final int IMAGE_HEIGHT = 100;

	/**
	 * The vertical velocity of the boss.
	 */
	private static final int VERTICAL_VELOCITY = 8;

	/**
	 * The initial health of the boss.
	 */
	private static final int HEALTH = 20;

	/**
	 * The frequency of movements per cycle in the boss's movement pattern.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;

	/**
	 * Constant for zero velocity.
	 */
	private static final int ZERO = 0;

	/**
	 * The maximum number of consecutive frames the boss can move in the same direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

	/**
	 * The upper Y boundary for the boss's movement.
	 */
	private static double Y_POSITION_UPPER_BOUND = -100.0;

	/**
	 * The lower Y boundary for the boss's movement.
	 */
	private static double Y_POSITION_LOWER_BOUND = LevelParent.ORIGINAL_SCREEN_HEIGHT - 100.0;

	/**
	 * The maximum number of frames a shield can remain active.
	 */
	private static final int MAX_FRAMES_WITH_SHIELD = 500;

	/**
	 * List of active shields for the boss.
	 */
	private List<ShieldImage> shields = new ArrayList<>();

	/**
	 * The movement pattern of the boss.
	 */
	private final List<Integer> movePattern;

	/**
	 * Flag indicating whether the boss currently has a shield activated.
	 */
	private boolean isShielded;

	/**
	 * The number of consecutive frames the boss has moved in the same direction.
	 */
	private int consecutiveMovesInSameDirection;

	/**
	 * The index of the current move in the boss's movement pattern.
	 */
	private int indexOfCurrentMove;

	/**
	 * The number of frames the shield has been active.
	 */
	private int framesWithShieldActivated;

	/**
	 * The manager responsible for managing actors in the game.
	 */
	private ActorManager actorManager;

	/**
	 * Reference to the user's plane.
	 */
	private final UserPlane userPlane;

	/**
	 * The root node of the scene graph.
	 */
	private final Group root;

	/**
	 * The boss's health.
	 */
	private int health;

	/**
	 * The shield image for the boss.
	 */
	private ShieldImage shieldImage;

	/**
	 * Constructs a Boss object with specified parameters.
	 *
	 * @param root        the root node of the scene graph
	 * @param shields     the list of shields
	 * @param actorManager the manager for managing actors
	 * @param userPlane   the user's plane
	 */
	public Boss(Group root, List<ShieldImage> shields, ActorManager actorManager, UserPlane userPlane) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.root = root;
		this.actorManager = actorManager;
		System.out.println("ActorManager initialized in Boss: " + this.actorManager);
		this.userPlane = userPlane;
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;

		movePattern = new ArrayList<>();
		initializeMovePattern();

		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		shieldImage.setVisible(false);
		root.getChildren().add(shieldImage);
		shields.add(shieldImage);
	}

	/**
	 * Updates the position of the boss based on its movement pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}

		shieldImage.setTranslateX(this.getTranslateX());
		shieldImage.setTranslateY(this.getTranslateY());
	}

	/**
	 * Updates the state of the boss, including position, shield, and firing logic.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		fireProjectile();
	}

	/**
	 * Fires a projectile from the boss.
	 *
	 * @return null, as projectiles are managed externally
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		System.out.println("Boss attempting to fire projectile...");
		if (Math.random() < BOSS_FIRE_RATE) {
			actorManager.createEnemyProjectile(
					this.getLayoutX(),
					this.getLayoutY() + this.getFitHeight() / 2,
					userPlane,
					"/com/example/demo/images/fireball.png"
			);
			System.out.println("Boss fired projectile.");
		} else {
			System.out.println("Boss chose not to fire this time.");
		}
		return null;
	}

	/**
	 * Handles damage taken by the boss, considering shields.
	 */
	@Override
	public void takeDamage() {
		if (!shields.isEmpty()) {
			ShieldImage shield = shields.get(0);
			System.out.println("Shield is absorbing damage...");
			shield.reduceHealth(1);

			if (shield.getHealth() <= 0) {
				shields.remove(shield);
				root.getChildren().remove(shield);
				System.out.println("Shield destroyed!");
			} else {
				System.out.println("Shield health remaining: " + shield.getHealth());
			}
		} else {
			super.takeDamage();
			System.out.println("Boss took damage!");
		}
	}

	/**
	 * Retrieves the current health of the boss.
	 *
	 * @return the boss's health
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 * Initializes the movement pattern for the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield state of the boss.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield();
		} else if (shieldShouldBeActivated()) {
			activateShield();
			shieldImage.showShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
			shieldImage.hideShield();
		}
	}

	/**
	 * Retrieves the next move for the boss based on its movement pattern.
	 *
	 * @return the next move velocity
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;

		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}

		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}

		return currentMove;
	}

	/**
	 * Determines whether the shield should be activated based on probability.
	 *
	 * @return true if the shield should be activated, false otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines whether the shield is exhausted based on active frames.
	 *
	 * @return true if the shield is exhausted, false otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield for the boss.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the shield for the boss.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Adjusts the position and shield of the boss for a resized screen.
	 *
	 * @param newWidth  the new screen width
	 * @param newHeight the new screen height
	 */
	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		setTranslateX(getTranslateX() * widthRatio);
		setTranslateY(getTranslateY() * heightRatio);

		shieldImage.adjustPositionForResize(newWidth, newHeight);

		Y_POSITION_UPPER_BOUND *= heightRatio;
		Y_POSITION_LOWER_BOUND = newHeight - 100;
	}
}
