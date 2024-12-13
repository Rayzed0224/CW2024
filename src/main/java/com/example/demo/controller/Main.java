package com.example.demo.controller;

import com.example.demo.ui.MainMenu;
import com.example.demo.utilities.UserSettings;
import com.example.demo.utilities.WindowUtils;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the Sky Battle application. Handles initialization and starting the game.
 */
public class Main extends Application {

	/**
	 * Singleton instance of the Main class.
	 */
	private static Main instance;

	/**
	 * The title of the application window.
	 */
	private static final String TITLE = "Sky Battle";

	/**
	 * Reference to the main game controller.
	 */
	public static Controller myController;

	/**
	 * Entry point of the JavaFX application. Initializes the main menu and applies user settings.
	 *
	 * @param stage The primary stage for this application.
	 * @throws Exception If an error occurs during initialization.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		instance = this;

		stage.setTitle(TITLE);
		stage.setResizable(false);

		// Load user's saved display mode
		String savedMode = UserSettings.getDisplayMode();
		System.out.println("Starting with display mode: " + savedMode);
		WindowUtils.setStageDisplayMode(stage, savedMode);

		MainMenu mainMenu = new MainMenu();
		mainMenu.start(stage);

		// Ensure fullscreen state is applied after everything is ready
		boolean shouldBeFullscreen = "Fullscreen".equals(savedMode);
		WindowUtils.applyFullscreen(stage, shouldBeFullscreen);

		// Listener for fullscreen transitions
		stage.fullScreenProperty().addListener((observable, oldValue, isFullScreen) -> {
			if (!UserSettings.isTransitioning()) {
				String currentMode = UserSettings.getDisplayMode();
				if (!isFullScreen && "Fullscreen".equals(currentMode)) {
					System.out.println("Fullscreen exit detected but mode is Fullscreen. Reapplying fullscreen.");
					WindowUtils.applyFullscreen(stage, true); // Force fullscreen back
				} else if (!isFullScreen) {
					System.out.println("Exiting fullscreen: reverting to Windowed mode");
					UserSettings.setDisplayMode("Windowed");
					WindowUtils.setWindowed(stage);
				}
			} else {
				System.out.println("Transition in progress; ignoring fullscreen exit.");
			}
		});
	}

	/**
	 * Starts the game by initializing the controller and launching the game logic.
	 *
	 * @param stage The primary stage to be used for the game.
	 */
	public static void startGame(Stage stage) {
		try {
			myController = new Controller(stage);
			myController.launchGame(); // Start the game here when called explicitly
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the singleton instance of the Main class.
	 *
	 * @return The singleton instance of Main.
	 */
	public static Main getInstance() {
		return instance;
	}

	/**
	 * The main entry point for the application. Launches the JavaFX application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
