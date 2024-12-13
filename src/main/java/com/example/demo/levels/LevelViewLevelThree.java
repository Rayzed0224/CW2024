package com.example.demo.levels;

import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

/**
 * Extends LevelView to add boss-specific UI elements for Level Three.
 *
 * <p>This class introduces a shield image specifically for the boss,
 * allowing additional functionality to show or hide the shield
 * and ensuring it integrates with the rest of the UI elements.</p>
 */
public class LevelViewLevelThree extends LevelView {

	/**
	 * The X position of the shield image.
	 */
	private static final int SHIELD_X_POSITION = 1150;

	/**
	 * The Y position of the shield image.
	 */
	private static final int SHIELD_Y_POSITION = 500;

	/**
	 * The root group containing all UI elements for the level.
	 */
	private final Group root;

	/**
	 * The shield image displayed for the boss.
	 */
	private final ShieldImage shieldImage;

	/**
	 * Constructs a LevelViewLevelThree with the specified root group and number of hearts.
	 *
	 * @param root            the root group containing all UI elements
	 * @param heartsToDisplay the initial number of hearts to display
	 */
	public LevelViewLevelThree(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root group.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	/**
	 * Displays the shield image.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the shield image.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Adjusts the UI elements to fit the new screen dimensions, including shield-specific adjustments.
	 *
	 * @param newWidth  the new width of the screen
	 * @param newHeight the new height of the screen
	 */
	@Override
	public void adjustUIForResize(double newWidth, double newHeight) {
		super.adjustUIForResize(newWidth, newHeight);
		// Additional adjustments for Level Three specific UI elements if required
	}
}
