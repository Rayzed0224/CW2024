package com.example.demo.levels;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 * Manages the level's background, including resizing and scrolling.
 */
public class LevelBackgroundManager {
    private final ImageView background;

    public LevelBackgroundManager(String backgroundImageName, Group root, double screenWidth, double screenHeight) {
        this.background = new ImageView(getClass().getResource(backgroundImageName).toExternalForm());
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        root.getChildren().add(background);
    }

    public void resizeBackground(double newWidth, double newHeight) {
        background.setFitWidth(newWidth);
        background.setFitHeight(newHeight);
    }

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