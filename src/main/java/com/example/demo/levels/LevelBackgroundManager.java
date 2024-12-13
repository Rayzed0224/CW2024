package com.example.demo.levels;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 * Manages the level's background, including resizing and scrolling.
 *
 * <p>This class provides functionality to handle the background image of a game level,
 * including initializing it, resizing it to match the screen dimensions, and managing
 * its position within the scene graph.</p>
 */
public class LevelBackgroundManager {

    /**
     * The background image view.
     */
    private final ImageView background;

    /**
     * Constructs a LevelBackgroundManager with the specified background image and dimensions.
     *
     * @param backgroundImageName the file path of the background image
     * @param root                the root node to which the background will be added
     * @param screenWidth         the initial width of the screen
     * @param screenHeight        the initial height of the screen
     */
    public LevelBackgroundManager(String backgroundImageName, Group root, double screenWidth, double screenHeight) {
        this.background = new ImageView(getClass().getResource(backgroundImageName).toExternalForm());
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        root.getChildren().add(background);
    }

    /**
     * Resizes the background to fit the new screen dimensions.
     *
     * @param newWidth  the new width of the screen
     * @param newHeight the new height of the screen
     */
    public void resizeBackground(double newWidth, double newHeight) {
        background.setFitWidth(newWidth);
        background.setFitHeight(newHeight);
    }

    /**
     * Initializes the background image and ensures it is set at the correct size and position.
     *
     * @param root         the root node to which the background will be added
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public void initializeBackground(Group root, double screenWidth, double screenHeight) {
        // Remove existing background (if any)
        if (root.getChildren().contains(background)) {
            root.getChildren().remove(background);
        }

        // Reset background dimensions
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);

        // Add background to root
        root.getChildren().add(0, background); // Ensure it’s added at the back layer
    }
}
