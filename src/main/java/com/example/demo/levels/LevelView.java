package com.example.demo.levels;

import com.example.demo.ui.GameOverImage;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.ShieldImage;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;

/**
 * Manages the main level UI, such as health and win/loss screens.
 */

public class LevelView {

	// Original positions used for resizing purposes
	private static final double ORIGINAL_HEART_DISPLAY_X_POSITION = 5.0;
	private static final double ORIGINAL_HEART_DISPLAY_Y_POSITION = 25.0;
	private static final double ORIGINAL_WIN_IMAGE_X_POSITION = 355.0;
	private static final double ORIGINAL_WIN_IMAGE_Y_POSITION = 175.0;
	private static final double ORIGINAL_LOSS_SCREEN_X_POSITION = -160.0;
	private static final double ORIGINAL_LOSS_SCREEN_Y_POSITION = -375.0;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final ShieldImage shieldImage;


	private double heartDisplayX;
	private double heartDisplayY;
	private double winImageX;
	private double winImageY;
	private double lossScreenX;
	private double lossScreenY;
	private static final double ORIGINAL_SHIELD_X_POSITION = 450; // Example value, adjust accordingly
	private static final double ORIGINAL_SHIELD_Y_POSITION = 300; // Example value, adjust accordingly


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
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	public void showShield() {
		root.getChildren().add(shieldImage);
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

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
