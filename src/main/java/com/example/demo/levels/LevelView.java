package com.example.demo.levels;

import com.example.demo.ui.GameOverImage;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.ShieldImage;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;

/**
 * Manages the main level UI, such as health and win/loss screens.
 *
 * <p>This class is responsible for handling the display of various UI elements,
 * including the player's health (hearts), the win screen, the game over screen,
 * and the shield. It also supports dynamic resizing of UI elements to adapt
 * to changes in screen dimensions.</p>
 */
public class LevelView {

	/**
	 * The original X position for the heart display.
	 */
	private static final double ORIGINAL_HEART_DISPLAY_X_POSITION = 5.0;

	/**
	 * The original Y position for the heart display.
	 */
	private static final double ORIGINAL_HEART_DISPLAY_Y_POSITION = 25.0;

	/**
	 * The original X position for the win image.
	 */
	private static final double ORIGINAL_WIN_IMAGE_X_POSITION = 355.0;

	/**
	 * The original Y position for the win image.
	 */
	private static final double ORIGINAL_WIN_IMAGE_Y_POSITION = 175.0;

	/**
	 * The original X position for the game over screen.
	 */
	private static final double ORIGINAL_LOSS_SCREEN_X_POSITION = -160.0;

	/**
	 * The original Y position for the game over screen.
	 */
	private static final double ORIGINAL_LOSS_SCREEN_Y_POSITION = -375.0;

	/**
	 * The original X position for the shield image.
	 */
	private static final double ORIGINAL_SHIELD_X_POSITION = 450;

	/**
	 * The original Y position for the shield image.
	 */
	private static final double ORIGINAL_SHIELD_Y_POSITION = 300;

	/**
	 * The root group containing all UI elements.
	 */
	private final Group root;

	/**
	 * The win image displayed when the player wins the level.
	 */
	private final WinImage winImage;

	/**
	 * The game over image displayed when the player loses the level.
	 */
	private final GameOverImage gameOverImage;

	/**
	 * The heart display for representing the player's health.
	 */
	private final HeartDisplay heartDisplay;

	/**
	 * The shield image displayed for the player or boss.
	 */
	private final ShieldImage shieldImage;

	/**
	 * The current X position of the heart display.
	 */
	private double heartDisplayX;

	/**
	 * The current Y position of the heart display.
	 */
	private double heartDisplayY;

	/**
	 * The current X position of the win image.
	 */
	private double winImageX;

	/**
	 * The current Y position of the win image.
	 */
	private double winImageY;

	/**
	 * The current X position of the game over screen.
	 */
	private double lossScreenX;

	/**
	 * The current Y position of the game over screen.
	 */
	private double lossScreenY;

	/**
	 * Constructs a LevelView with the specified root group and initial number of hearts.
	 *
	 * @param root            the root group containing all UI elements
	 * @param heartsToDisplay the initial number of hearts to display
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;

		// Initialize positions based on the original constants
		this.heartDisplayX = ORIGINAL_HEART_DISPLAY_X_POSITION;
		this.heartDisplayY = ORIGINAL_HEART_DISPLAY_Y_POSITION;
		this.winImageX = ORIGINAL_WIN_IMAGE_X_POSITION;
		this.winImageY = ORIGINAL_WIN_IMAGE_Y_POSITION;
		this.lossScreenX = ORIGINAL_LOSS_SCREEN_X_POSITION;
		this.lossScreenY = ORIGINAL_LOSS_SCREEN_Y_POSITION;

		this.heartDisplay = new HeartDisplay(heartDisplayX, heartDisplayY, heartsToDisplay);
		this.winImage = new WinImage(winImageX, winImageY);
		this.gameOverImage = new GameOverImage(lossScreenX, lossScreenY);
		this.shieldImage = new ShieldImage(ORIGINAL_SHIELD_X_POSITION, ORIGINAL_SHIELD_Y_POSITION);
	}

	/**
	 * Displays the heart display on the screen.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game over image on the screen.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Displays the shield image on the screen.
	 */
	public void showShield() {
		root.getChildren().add(shieldImage);
	}

	/**
	 * Removes hearts from the heart display to reflect the remaining health.
	 *
	 * @param heartsRemaining the number of hearts to keep displayed
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Adjusts the UI elements to fit the new screen dimensions.
	 *
	 * @param newWidth  the new width of the screen
	 * @param newHeight the new height of the screen
	 */
	public void adjustUIForResize(double newWidth, double newHeight) {
		// Calculate width and height ratios based on original screen dimensions
		double widthRatio = newWidth / LevelParent.ORIGINAL_SCREEN_WIDTH;
		double heightRatio = newHeight / LevelParent.ORIGINAL_SCREEN_HEIGHT;

		// Update positions based on new screen size
		heartDisplayX = ORIGINAL_HEART_DISPLAY_X_POSITION * widthRatio;
		heartDisplayY = ORIGINAL_HEART_DISPLAY_Y_POSITION * heightRatio;
		winImageX = ORIGINAL_WIN_IMAGE_X_POSITION * widthRatio;
		winImageY = ORIGINAL_WIN_IMAGE_Y_POSITION * heightRatio;
		lossScreenX = ORIGINAL_LOSS_SCREEN_X_POSITION * widthRatio;
		lossScreenY = ORIGINAL_LOSS_SCREEN_Y_POSITION * heightRatio;
		double shieldX = ORIGINAL_SHIELD_X_POSITION * widthRatio;
		double shieldY = ORIGINAL_SHIELD_Y_POSITION * heightRatio;

		// Adjust UI elements to the new positions
		heartDisplay.adjustPosition(heartDisplayX, heartDisplayY);
		winImage.adjustPosition(winImageX, winImageY);
		gameOverImage.adjustPosition(lossScreenX, lossScreenY);
		shieldImage.adjustPosition(shieldX, shieldY);

		// Additionally, update the elements themselves (in case they have internal resizing)
		heartDisplay.adjustPositionForResize(newWidth, newHeight);
		winImage.adjustPositionForResize(newWidth, newHeight);
		gameOverImage.adjustPositionForResize(newWidth, newHeight);
		shieldImage.adjustPositionForResize(newWidth, newHeight);
	}
}
