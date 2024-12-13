package com.example.demo.ui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class GameOverUI {
    private final ImageView gameOverImage;
    private final Button returnToMainMenuButton;
    private final Button restartButton;

    public GameOverUI(Group root, double screenWidth, double screenHeight) {
        // Initialize GameOverImage
        gameOverImage = new ImageView(getClass().getResource("/com/example/demo/images/gameover.png").toExternalForm());
        gameOverImage.setFitWidth(320); // Set desired width
        gameOverImage.setPreserveRatio(true); // Maintain aspect ratio
        gameOverImage.setLayoutX(screenWidth / 2.0 - 160.0); // Center horizontally
        gameOverImage.setLayoutY(screenHeight / 2.0 - 200.0); // Position vertically
        gameOverImage.setVisible(false); // Initially hidden

        // Initialize buttons
        returnToMainMenuButton = new Button("Return to Main Menu");
        restartButton = new Button("Restart");

        returnToMainMenuButton.setLayoutX(screenWidth / 2.0 - 100.0);
        returnToMainMenuButton.setLayoutY(screenHeight / 2.0 + 50.0);
        returnToMainMenuButton.setVisible(false);

        restartButton.setLayoutX(screenWidth / 2.0 - 100.0);
        restartButton.setLayoutY(screenHeight / 2.0 + 100.0);
        restartButton.setVisible(false);

        // Add components to the root
        root.getChildren().addAll(gameOverImage, returnToMainMenuButton, restartButton);
    }

    public void showGameOverUI() {
        gameOverImage.setVisible(true);
        returnToMainMenuButton.setVisible(true);
        restartButton.setVisible(true);
    }

    public void hideGameOverUI() {
        gameOverImage.setVisible(false);
        returnToMainMenuButton.setVisible(false);
        restartButton.setVisible(false);
    }

    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public void adjustPositionForResize(double newWidth, double newHeight) {
        gameOverImage.setLayoutX(newWidth / 2.0 - 160.0);
        gameOverImage.setLayoutY(newHeight / 2.0 - 200.0);

        returnToMainMenuButton.setLayoutX(newWidth / 2.0 - 100.0);
        returnToMainMenuButton.setLayoutY(newHeight / 2.0 + 50.0);

        restartButton.setLayoutX(newWidth / 2.0 - 100.0);
        restartButton.setLayoutY(newHeight / 2.0 + 100.0);
    }
}