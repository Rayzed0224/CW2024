package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import com.example.demo.projectiles.BossProjectile;
import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

import java.util.*;

/**
 * Implements the boss logic, including shields and movement patterns.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png"; // Corrected the image path
	private static final double INITIAL_X_POSITION = LevelParent.ORIGINAL_SCREEN_WIDTH - 150.0; // Start near the right
	private static final double INITIAL_Y_POSITION = LevelParent.ORIGINAL_SCREEN_HEIGHT / 2;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = 0.04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.2;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 20;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static double Y_POSITION_UPPER_BOUND = -100.0;
	private static double Y_POSITION_LOWER_BOUND = LevelParent.ORIGINAL_SCREEN_HEIGHT - 100.0;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private ShieldImage shieldImage;

	public Boss(Group root, List<ShieldImage> shields) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;

		//Initialize Movement Pattern
		initializeMovePattern();

		// Initialize and add shield to the root node
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		shieldImage.setVisible(false); // Initially, the shield is not visible.
		root.getChildren().add(shieldImage); // Add shield to the game scene.
		shields.add(shieldImage);
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY); // Reset to the previous position if out of bounds
		}

		// Update shield position whenever the boss moves
		shieldImage.setTranslateX(this.getTranslateX());
		shieldImage.setTranslateY(this.getTranslateY());
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		// Fires a projectile with a given probability
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)) : null;
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		// Create a repeating move pattern and shuffle it
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY); // Move down
			movePattern.add(-VERTICAL_VELOCITY); // Move up
			movePattern.add(ZERO); // Stay still
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield(); // Ensure the shield is visible when activated
		} else if (shieldShouldBeActivated()) {
			activateShield();
			shieldImage.showShield(); // Activate and make shield visible
		}
		if (shieldExhausted()) {
			deactivateShield();
			shieldImage.hideShield(); // Hide the shield when deactivated
		}
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;

		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			// Shuffle the pattern and reset move count
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}

		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0; // Reset pattern index to loop it
		}

		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Adjust translate values for the new screen size
		setTranslateX(getTranslateX() * widthRatio);
		setTranslateY(getTranslateY() * heightRatio);

		// Update shield position for the new screen size
		shieldImage.adjustPositionForResize(newWidth, newHeight);

		// Update Y boundaries accordingly
		Y_POSITION_UPPER_BOUND *= heightRatio;
		Y_POSITION_LOWER_BOUND = newHeight - 100;  // Adapt lower bound based on the resized screen
	}
}
