package com.example.demo.utilities;

import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UserSettings {
    private static final String SETTINGS_FILE_PATH = "config/settings.properties";
    private static Properties properties = new Properties();
    private boolean isTransitioning = false;

    static {
        // Load properties from the settings file
        try (FileInputStream input = new FileInputStream(SETTINGS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Unable to load settings file: " + e.getMessage());
        }
    }

    public static String getDisplayMode() {
        String mode = properties.getProperty("displayMode", "Windowed");
        System.out.println("Display Mode Retrieved: " + mode); // Debug statement
        return mode;
    }

    public static void setDisplayMode(String mode) {
        properties.setProperty("displayMode", mode);
        System.out.println("Display Mode Set to: " + mode); // Debug statement
        saveProperties();
    }

    private static void saveProperties() {
        try (FileOutputStream output = new FileOutputStream(SETTINGS_FILE_PATH)) {
            properties.store(output, "User Settings");
        } catch (IOException e) {
            System.out.println("Unable to save settings: " + e.getMessage());
        }
    }

    public void setStageDisplayMode(Stage stage, String mode) {
        isTransitioning = true; // Set the flag to avoid additional state changes during transition

        switch (mode) {
            case "Fullscreen":
                stage.setFullScreen(true);
                break;
            case "Windowed Borderless":
                stage.setFullScreen(false);
                stage.setMaximized(true);
                stage.setFullScreenExitHint("");
                break;
            case "Windowed":
            default:
                stage.setFullScreen(false);
                stage.setMaximized(false);
                stage.setWidth(GameConstants.ORIGINAL_SCREEN_WIDTH);
                stage.setHeight(GameConstants.ORIGINAL_SCREEN_HEIGHT);
                stage.centerOnScreen();
                break;
        }

        // After setting the display mode, allow time for fullscreen transition to complete
        PauseTransition delay = new PauseTransition(Duration.millis(300));
        delay.setOnFinished(event -> {
            isTransitioning = false;

            // Only save settings after the transition has fully completed
            UserSettings.setDisplayMode(mode);
        });
        delay.play();
    }
}