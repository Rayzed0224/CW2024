package com.example.demo.utilities;

import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A utility class for managing window and display settings for the application.
 */
public class WindowUtils {

    /**
     * Applies fullscreen mode to the specified stage.
     *
     * @param stage      The stage to apply fullscreen mode to.
     * @param fullscreen {@code true} to enable fullscreen mode; {@code false} to disable it.
     */
    public static void applyFullscreen(Stage stage, boolean fullscreen) {
        if (stage == null) {
            System.err.println("Stage is null. Cannot apply fullscreen.");
            return;
        }
        PauseTransition delay = new PauseTransition(Duration.millis(100)); // Allow JavaFX to initialize
        delay.setOnFinished(e -> {
            try {
                stage.setFullScreen(fullscreen);
                System.out.println("Fullscreen set to: " + fullscreen);
            } catch (NullPointerException ex) {
                System.err.println("Failed to apply fullscreen: " + ex.getMessage());
            }
        });
        delay.play();
    }

    /**
     * Sets the stage to windowed mode with predefined dimensions.
     *
     * @param stage The stage to set to windowed mode.
     */
    public static void setWindowed(Stage stage) {
        stage.setFullScreen(false);
        stage.setMaximized(false);
        stage.setWidth(1350);
        stage.setHeight(750);
        stage.centerOnScreen();
    }

    /**
     * Sets the stage to windowed borderless mode, maximizing it to fit the screen bounds.
     *
     * @param stage The stage to set to windowed borderless mode.
     */
    public static void setWindowedBorderless(Stage stage) {
        stage.setFullScreen(false);
        stage.setMaximized(true);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setFullScreenExitHint("");
    }

    /**
     * Sets the display mode of the stage based on the specified mode.
     *
     * @param stage The stage to set the display mode for.
     * @param mode  The display mode to apply ("Fullscreen", "Windowed Borderless", or "Windowed").
     */
    public static void setStageDisplayMode(Stage stage, String mode) {
        System.out.println("Applying display mode: " + mode);

        UserSettings.setTransitioning(true);
        try {
            switch (mode) {
                case "Fullscreen":
                    if (!stage.isFullScreen()) {
                        stage.setFullScreen(true);
                    }
                    break;
                case "Windowed Borderless":
                    setWindowedBorderless(stage);
                    break;
                case "Windowed":
                    if (stage.isFullScreen()) {
                        stage.setFullScreen(false);
                    }
                    stage.setMaximized(false);
                    stage.setWidth(1350);
                    stage.setHeight(750);
                    stage.centerOnScreen();
                    break;
            }
        } catch (Exception e) {
            System.err.println("Failed to set display mode: " + e.getMessage());
        } finally {
            UserSettings.setTransitioning(false);
        }
    }
}