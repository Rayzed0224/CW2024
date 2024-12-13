package com.example.demo.controller;

import com.example.demo.levels.LevelParentBase;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

/**
 * Handles game state transitions between levels.
 * Observes levels to detect completion or game-over events.
 */
public class Controller implements Observer {

	/**
	 * The fully qualified class name of Level One.
	 */
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";

	/**
	 * The primary stage for the game.
	 */
	private final Stage stage;

	/**
	 * The current level being played.
	 */
	private LevelParentBase currentLevel;

	/**
	 * Constructs the Controller with a reference to the main stage.
	 *
	 * @param stage the primary stage for the game
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by transitioning to the first level.
	 *
	 * @throws SecurityException if a security violation occurs
	 * @throws IllegalArgumentException if an illegal argument is provided
	 */
	public void launchGame() throws SecurityException, IllegalArgumentException {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			showError(e);
		}
	}

	/**
	 * Transitions to the specified level.
	 *
	 * @param className the fully qualified class name of the level
	 * @throws ClassNotFoundException if the level class is not found
	 * @throws NoSuchMethodException if the constructor for the level class is not found
	 * @throws SecurityException if a security violation occurs
	 * @throws InstantiationException if the level class cannot be instantiated
	 * @throws IllegalAccessException if the constructor is not accessible
	 * @throws IllegalArgumentException if an illegal argument is provided
	 * @throws InvocationTargetException if the constructor throws an exception
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if ("GAME_COMPLETED".equals(className)) {
			System.out.println("Game Completed! Displaying end screen.");
			displayWinScreen();
			return;
		}

		Class<?> levelClass = Class.forName(className);
		LevelParentBase level = (LevelParentBase) levelClass
				.getConstructor(double.class, double.class)
				.newInstance(stage.getWidth(), stage.getHeight());
		level.addObserver(this::handleLevelTransition);

		currentLevel = level;

		Scene scene = level.initializeScene();
		stage.setScene(scene);
		level.startGame();
	}

	/**
	 * Displays the win screen when the game is completed.
	 */
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

	/**
	 * Handles transitions between levels by transitioning to the next level.
	 *
	 * @param nextLevel the fully qualified class name of the next level
	 */
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

	/**
	 * Updates the controller when a level notifies observers of a change.
	 *
	 * @param arg0 the observable object
	 * @param arg1 the argument passed to the observers
	 */
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

	/**
	 * Displays an error alert with the exception message.
	 *
	 * @param e the exception to display
	 */
	private void showError(Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(e.getMessage());
		alert.show();
	}

	/**
	 * Retrieves the current level being played.
	 *
	 * @return the current level
	 */
	public LevelParentBase getCurrentLevel() {
		return currentLevel;
	}
}
