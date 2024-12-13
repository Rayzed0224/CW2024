package com.example.demo.ui;

import com.example.demo.controller.Main;
import com.example.demo.utilities.UserSettings;
import com.example.demo.utilities.WindowUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates the main menu screen for the game.
 * Provides options to start the game, show credits, access settings, or exit the game.
 */
public class MainMenu extends Application {

    /**
     * Button to apply settings in the settings menu.
     */
    Button applyButton = new Button("Apply Settings");

    /**
     * The main menu scene, saved for reuse when navigating back to the main menu.
     */
    private Scene mainMenuScene;

    /**
     * Starts the application by creating and displaying the main menu.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Menu");

        // Load and configure the background image
        ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/com/example/demo/images/menu_background.png").toExternalForm()));
        backgroundImage.setPreserveRatio(false);
        backgroundImage.setFitWidth(primaryStage.getWidth());
        backgroundImage.setFitHeight(primaryStage.getHeight());

        // Add listeners to dynamically resize the background image
        primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> backgroundImage.setFitWidth(newWidth.doubleValue()));
        primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> backgroundImage.setFitHeight(newHeight.doubleValue()));

        // Create menu layout with buttons
        VBox menuLayout = new VBox(20);
        Button startButton = new Button("Start Game");
        Button creditsButton = new Button("Credits");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        menuLayout.getChildren().addAll(startButton, settingsButton, creditsButton, exitButton);
        menuLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, menuLayout);

        // Save the main menu scene
        mainMenuScene = new Scene(root, 1350, 750);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        // Configure button actions

        // "Start Game" button action
        startButton.setOnAction(event -> Main.startGame(primaryStage));

        // "Settings" button action
        settingsButton.setOnAction(event -> showSettings(primaryStage));

        // "Exit" button action
        exitButton.setOnAction(event -> primaryStage.close());
    }

    /**
     * Displays the settings menu.
     *
     * @param primaryStage The primary stage for the application.
     */
    private void showSettings(Stage primaryStage) {
        VBox settingsLayout = new VBox(20);
        settingsLayout.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");

        // Initialize dropdown for display modes
        ComboBox<String> displayModeBox = new ComboBox<>();
        displayModeBox.getItems().addAll("Windowed", "Fullscreen", "Windowed Borderless");
        displayModeBox.setValue(UserSettings.getDisplayMode()); // Default to saved display mode

        // Configure apply button action
        applyButton.setOnAction(event -> {
            String selectedMode = displayModeBox.getValue();
            String currentMode = UserSettings.getDisplayMode();

            if (!selectedMode.equals(currentMode)) {
                System.out.println("Changing display mode to: " + selectedMode);
                UserSettings.setDisplayMode(selectedMode); // Save mode
                WindowUtils.setStageDisplayMode(primaryStage, selectedMode); // Apply mode
            } else {
                System.out.println("Display mode unchanged: " + currentMode);
            }
        });

        // Configure back button action
        backButton.setOnAction(event -> {
            System.out.println("Returning to main menu, mode remains unchanged: " + UserSettings.getDisplayMode());
            primaryStage.setScene(mainMenuScene); // Reuse the stored main menu scene

            // Reapply fullscreen state if necessary
            boolean shouldBeFullscreen = "Fullscreen".equals(UserSettings.getDisplayMode());
            WindowUtils.applyFullscreen(primaryStage, shouldBeFullscreen);
        });

        settingsLayout.getChildren().addAll(new javafx.scene.control.Label("Settings"), displayModeBox, applyButton, backButton);

        Scene settingsScene = new Scene(settingsLayout, 1350, 750);
        primaryStage.setScene(settingsScene);

        // Apply fullscreen after switching to the settings scene
        boolean wasFullScreen = primaryStage.isFullScreen();
        WindowUtils.applyFullscreen(primaryStage, wasFullScreen);
    }

    /**
     * The main entry point for the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
