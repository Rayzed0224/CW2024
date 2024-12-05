package com.example.demo.controller;

import com.example.demo.ui.MainMenu;
import com.example.demo.utilities.UserSettings;
import com.example.demo.utilities.WindowUtils;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private static Main instance;
	private static final String TITLE = "Sky Battle";
	public static Controller myController;

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

	public static void startGame(Stage stage) {
		try {
			myController = new Controller(stage);
			myController.launchGame();  // Start the game here when called explicitly
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Main getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch(args);
	}
}