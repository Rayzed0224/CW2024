package com.example.demo.ui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Represents the game-over UI, including an image and buttons for returning to the main menu or restarting the game.
 */
public class GameOverUI {

    /**
     * The image displayed when the game is over.
     */
    private final ImageView gameOverImage;

    /**
     * The button for returning to the main menu.
     */
    private final Button returnToMainMenuButton;

    /**
     * The button for restarting the game.
     */
    private final Button restartButton;

    /**
     * Constructs the game-over UI and adds it to the specified root group.
     *
     * @param root        The root group to which the UI components are added.
     * @param screenWidth The width of the screen.
     * @param screenHeight The height of the screen.
     */
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

    /**
     * Displays the game-over UI by making its components visible.
     */
    public void showGameOverUI() {
        gameOverImage.setVisible(true);
        returnToMainMenuButton.setVisible(true);
        restartButton.setVisible(true);
    }

    /**
     * Hides the game-over UI by making its components invisible.
     */
    public void hideGameOverUI() {
        gameOverImage.setVisible(false);
        returnToMainMenuButton.setVisible(false);
        restartButton.setVisible(false);
    }

    /**
     * Retrieves the button for returning to the main menu.
     *
     * @return The button for returning to the main menu.
     */
    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
    }

    /**
     * Retrieves the button for restarting the game.
     *
     * @return The button for restarting the game.
     */
    public Button getRestartButton() {
        return restartButton;
    }

    /**
     * Adjusts the position of the game-over UI components based on new screen dimensions.
     *
     * @param newWidth  The new width of the screen.
     * @param newHeight The new height of the screen.
     */
    public void adjustPositionForResize(double newWidth, double newHeight) {
        gameOverImage.setLayoutX(newWidth / 2.0 - 160.0);
        gameOverImage.setLayoutY(newHeight / 2.0 - 200.0);

        returnToMainMenuButton.setLayoutX(newWidth / 2.0 - 100.0);
        returnToMainMenuButton.setLayoutY(newHeight / 2.0 + 50.0);

        restartButton.setLayoutX(newWidth / 2.0 - 100.0);
        restartButton.setLayoutY(newHeight / 2.0 + 100.0);
    }
}
