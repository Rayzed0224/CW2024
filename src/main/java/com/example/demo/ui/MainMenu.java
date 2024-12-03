package com.example.demo.ui;

import com.example.demo.controller.Main;
import com.example.demo.utilities.UserSettings;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.util.Duration;

import static com.example.demo.controller.Main.myController;

/**
 * Creates the main menu screen for the game.
 * Provides options to start the game, show credits, access settings, or exit the game.
 */
public class MainMenu extends Application {

    Button applyButton = new Button("Apply Settings"); // Moved out of method to be accessible

    @Override
    public void start(Stage primaryStage) {
        // Set up the title and window size
        primaryStage.setTitle("Main Menu");

        // Set up background image
        ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/com/example/demo/images/menu_background.png").toExternalForm()));
        backgroundImage.setPreserveRatio(false);

        // Set up buttons
        VBox menuLayout = new VBox(20);
        Button startButton = new Button("Start Game");
        Button creditsButton = new Button("Credits");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        menuLayout.getChildren().addAll(startButton, settingsButton, creditsButton, exitButton);
        menuLayout.setAlignment(Pos.CENTER);

        // Create StackPane and add children
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, menuLayout);

        // Create scene
        Scene scene = new Scene(root);

        // Bind the background size to the scene size
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> backgroundImage.setFitWidth(newWidth.doubleValue()));
        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> backgroundImage.setFitHeight(newHeight.doubleValue()));

        // Bind menuLayout width and height to scene
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Settings button handler
        settingsButton.setOnAction(event -> showSettings(primaryStage));

        // Start button handler
        startButton.setOnAction(event -> {
            String displayMode = UserSettings.getDisplayMode();
            Main.setStageDisplayMode(primaryStage, displayMode);

            com.example.demo.controller.Controller gameController = new com.example.demo.controller.Controller(primaryStage);
            try {
                gameController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        creditsButton.setOnAction(event -> {
            System.out.println("Credits button clicked");
            showCredits(primaryStage);
        });

        exitButton.setOnAction(event -> {
            System.out.println("Exit button clicked");
            Platform.exit();
            System.exit(0);
        });
    }

    private void showSettings(Stage primaryStage) {
        VBox settingsLayout = new VBox(20);
        settingsLayout.setAlignment(Pos.CENTER);

        // Initialize dropdown for display modes
        ComboBox<String> displayModeBox = new ComboBox<>();
        displayModeBox.getItems().addAll("Windowed", "Fullscreen", "Windowed Borderless");
        displayModeBox.setValue(UserSettings.getDisplayMode()); // Default to the last saved display mode

        // Initialize apply button
        applyButton.setOnAction(event -> {
            String selectedMode = displayModeBox.getValue();
            UserSettings.setDisplayMode(selectedMode);  // Store the selected mode

            // Apply the display mode changes immediately
            setStageDisplayMode(primaryStage, selectedMode);
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            // Go back to the main menu
            start(primaryStage);
        });

        settingsLayout.getChildren().addAll(new javafx.scene.control.Label("Settings"), displayModeBox, applyButton, backButton);

        Scene settingsScene = new Scene(settingsLayout, 1350, 750);
        primaryStage.setScene(settingsScene);
    }

    private void setStageDisplayMode(Stage stage, String mode) {
        switch (mode) {
            case "Fullscreen":
                stage.setFullScreen(true);
                stage.show();  // Show again to apply fullscreen immediately
                break;
            case "Windowed Borderless":
                stage.setFullScreen(false);
                stage.setMaximized(false);

                // Set stage dimensions to fill the screen without fullscreen
                Screen screen = Screen.getPrimary();
                javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.setFullScreenExitHint("");  // No fullscreen exit hint needed
                break;

            case "Windowed":
            default:
                stage.setFullScreen(false);
                stage.setMaximized(false);
                stage.setWidth(1350);
                stage.setHeight(750);
                stage.centerOnScreen();
                break;
        }
        PauseTransition waitTransition = new PauseTransition(Duration.millis(200));
        waitTransition.setOnFinished(event -> {
            if (myController != null && myController.getCurrentLevel() != null) {
                myController.getCurrentLevel().resizeElements(stage.getWidth(), stage.getHeight());
            }
        });
        waitTransition.play();
    }


    private void showCredits(Stage primaryStage) {
        VBox creditsLayout = new VBox(20);
        creditsLayout.setAlignment(Pos.CENTER);
        creditsLayout.getChildren().add(new javafx.scene.control.Label("Game Developed by Jason"));

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            // Go back to the main menu
            start(primaryStage);
        });
        creditsLayout.getChildren().add(backButton);

        Scene creditsScene = new Scene(creditsLayout, 1350, 750);

        // Update the stage with the new scene (this is crucial)
        primaryStage.setScene(creditsScene);
        primaryStage.show(); // Show the stage to make sure it's updated properly
    }


    public static void main(String[] args) {
        launch(args);
    }
}
