package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;

/**
 * Handles game state transitions between levels.
 * Observes levels to detect completion or game-over events.
 */

public class Controller implements Observer {

	// Updated with correct package for LevelOne
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	private static final String LEVEL_TWO_CLASS_NAME = "com.example.demo.levels.LevelTwo";
	private final Stage stage;

	// Field to keep track of the current level
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Corrected fully qualified class name
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);

		// Assign the created level to currentLevel
		currentLevel = myLevel;

		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame(); // Assuming startGame() begins animations or game logic
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

	// Getter for the current level
	public LevelParent getCurrentLevel() {
		return currentLevel;
	}
}
