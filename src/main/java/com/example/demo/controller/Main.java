package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.ui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the game.
 * Initializes the main game window and launches the first level.
 */

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	//private Controller myController;

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		//Launch Main Menu
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(stage);
		//myController = new Controller(stage);
		//myController.launchGame();
	}

	public static void main(String[] args) {
		launch();
	}
}