package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.ui.WinImage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParentBase;

/**
 * Handles game state transitions between levels.
 * Observes levels to detect completion or game-over events.
 */

public class Controller implements Observer {

	// Updated with correct package for LevelOne
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	private final Stage stage;

	// Field to keep track of the current level
	private LevelParentBase currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws SecurityException, IllegalArgumentException {

		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			showError(e);
		}
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if ("GAME_COMPLETED".equals(className)) {
			System.out.println("Game Completed! Displaying end screen.");
			displayWinScreen();
			return;
		}

		// Corrected fully qualified class name
		Class<?> levelClass = Class.forName(className);
		LevelParentBase level = (LevelParentBase) levelClass
				.getConstructor(double.class, double.class)
				.newInstance(stage.getWidth(), stage.getHeight());
		level.addObserver(this::handleLevelTransition);

		// Assign the created level to currentLevel
		currentLevel = level;

		Scene scene = level.initializeScene();
		stage.setScene(scene);
		level.startGame();
	}

	private void displayWinScreen() {
		System.out.println("Congratulations! You finished the game!");

		Group root = new Group();
		Scene winScene = new Scene(root, stage.getWidth(), stage.getHeight());

		WinImage winImage = new WinImage(
				stage.getWidth() / 2 - 300, // Center horizontally
				stage.getHeight() / 2 - 250 // Center vertically
		);
		winImage.showWinImage();
		root.getChildren().add(winImage);
		winImage.adjustPositionForResize(stage.getWidth(), stage.getHeight());

		stage.setScene(winScene);
	}

	public void handleLevelTransition(String nextLevel) {
		try {
			System.out.println("Transitioning to: " + nextLevel);
			goToLevel(nextLevel);
		} catch (Exception e) {
			System.err.println("Error transitioning to level: " + e.getMessage());
			e.printStackTrace();
			showError(e);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

	private void showError(Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(e.getMessage());
		alert.show();
	}

	// Getter for the current level
	public LevelParentBase getCurrentLevel() {
		return currentLevel;
	}
}
