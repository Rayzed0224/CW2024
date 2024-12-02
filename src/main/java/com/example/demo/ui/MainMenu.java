package com.example.demo.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

/**
 * Creates the main menu screen for the game.
 * Provides options to start the game, show credits, or exit the game.
 */
public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up the title and window size
        primaryStage.setTitle("Main Menu");
        primaryStage.setWidth(1300);
        primaryStage.setHeight(750);

        // Set up background image using resource loading
        ImageView backgroundImage = new ImageView(new Image(getClass().getResource("/com/example/demo/images/menu_background.png").toExternalForm()));
        backgroundImage.setFitWidth(1300);
        backgroundImage.setFitHeight(750);


        // Set up buttons
        Button startButton = new Button("Start Game");
        Button creditsButton = new Button("Credits");
        Button exitButton = new Button("Exit");

        // Add button event handlers
        startButton.setOnAction(event -> {
            try {
                com.example.demo.controller.Controller gameController = new com.example.demo.controller.Controller(primaryStage);
                gameController.launchGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        creditsButton.setOnAction(event -> {
            // Logic to display credits, this could lead to another scene showing credits
            showCredits(primaryStage);
        });

        exitButton.setOnAction(event -> {
            // Logic to exit the game
            primaryStage.close();
        });

        // Set up layout
        VBox menuLayout = new VBox(20, startButton, creditsButton, exitButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setTranslateY(100);  // Adjust menu position to move it lower on the screen

        // Set up root layout
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImage, menuLayout);

        // Create scene and show the primary stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showCredits(Stage primaryStage) {
        VBox creditsLayout = new VBox(20);
        creditsLayout.setAlignment(Pos.CENTER);
        creditsLayout.getChildren().add(new javafx.scene.control.Label("Game Developed by Jason\nSpecial Thanks to ChatGPT"));

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            // Go back to the main menu
            start(primaryStage);
        });
        creditsLayout.getChildren().add(backButton);

        Scene creditsScene = new Scene(creditsLayout, 800, 600);
        primaryStage.setScene(creditsScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
