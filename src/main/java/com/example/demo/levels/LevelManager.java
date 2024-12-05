package com.example.demo.controller;

import com.example.demo.levels.LevelParent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LevelManager {
    private final Stage stage;

    public LevelManager(Stage stage) {
        this.stage = stage;
    }

    public void loadLevel(LevelParent level) {
        try {
            Scene levelScene = level.initializeScene();
            stage.setScene(levelScene);
            level.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transitionToNextLevel(LevelParent nextLevel) {
        System.out.println("Transitioning to the next level...");
        loadLevel(nextLevel);
    }
}