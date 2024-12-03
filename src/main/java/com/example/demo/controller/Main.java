package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.ui.MainMenu;
import com.example.demo.utilities.UserSettings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Platform;

/**
 * Entry point for the game.
 * Initializes the main game window and launches the first level.
 */

public class Main extends Application {

	private static Main instance;
	private static final String TITLE = "Sky Battle";
	public static Controller myController;

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		instance = this;

		stage.setTitle(TITLE);
		stage.setResizable(false);

		myController = new Controller(stage);
		myController.launchGame(); // Ensure this sets up the initial level properly

		// Apply saved display settings before launching the game
		String displayMode = UserSettings.getDisplayMode();
		setStageDisplayMode(stage, displayMode);

		// Add listener to handle fullscreen exit resizing properly
		stage.fullScreenProperty().addListener((observable, oldValue, isFullScreen) -> {
			if (!isFullScreen) {
				setWindowToWindowedMode(stage);
			}
		});

		// Launch the main menu instead of the game directly
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(stage);  // Start the main menu
	}

	public static Main getInstance() {
		return instance;  // Return the singleton instance of Main
	}

	public static void setStageDisplayMode(Stage stage, String mode) {
		switch (mode) {
			case "Fullscreen":
				stage.setFullScreen(true);
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

		// Wait for a moment to let JavaFX update
		PauseTransition waitTransition = new PauseTransition(Duration.millis(200));
		waitTransition.setOnFinished(event -> {
			if (myController != null && myController.getCurrentLevel() != null) {
				// Update the scene instead of replacing it
				myController.getCurrentLevel().resizeElements(stage.getWidth(), stage.getHeight());
			}
		});
		waitTransition.play();
	}


	public void returnToMainMenu(Stage stage) {
		if (stage.isFullScreen()) {
			stage.setFullScreen(false);
		}

		// After fully exiting fullscreen, change to main menu scene
		PauseTransition delay = new PauseTransition(Duration.millis(200));
		delay.setOnFinished(event -> {
			Platform.runLater(() -> {
				MainMenu mainMenu = new MainMenu();
				mainMenu.start(stage);
			});
		});
		delay.play();
	}


	// New Method: Resize the Stage Properly for Windowed Mode
	public void setWindowToWindowedMode(Stage stage) {
		stage.setMaximized(false);
		stage.setFullScreen(false);
		stage.setWidth(1350);
		stage.setHeight(750);
		stage.centerOnScreen(); // Ensure the window is centered on the screen

		// Adjust the game elements based on the new size
		if (myController != null && myController.getCurrentLevel() != null) {
			Platform.runLater(() -> myController.getCurrentLevel().resizeElements(stage.getWidth(), stage.getHeight()));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}