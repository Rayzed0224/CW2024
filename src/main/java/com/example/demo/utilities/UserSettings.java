package com.example.demo.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A utility class to manage user settings for the game.
 * Provides methods to load, save, and modify settings stored in a properties file.
 */
public class UserSettings {

    /**
     * The file path to the settings properties file.
     */
    private static final String SETTINGS_FILE_PATH = "config/settings.properties";

    /**
     * Properties object to hold the settings.
     */
    private static Properties properties = new Properties();

    /**
     * Flag indicating whether a transition is in progress.
     */
    private boolean isTransitioning = false;

    /**
     * Singleton instance of UserSettings.
     */
    private static UserSettings instance = new UserSettings();

    // Static block to load settings from the file during class initialization.
    static {
        try (FileInputStream input = new FileInputStream(SETTINGS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Unable to load settings file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the singleton instance of UserSettings.
     *
     * @return The singleton instance of UserSettings.
     */
    public static UserSettings getInstance() {
        return instance;
    }

    /**
     * Checks if a transition is currently in progress.
     *
     * @return {@code true} if a transition is in progress, {@code false} otherwise.
     */
    public static boolean isTransitioning() {
        return getInstance().isTransitioning;
    }

    /**
     * Sets the transitioning state.
     *
     * @param transitioning {@code true} to indicate a transition is in progress, {@code false} otherwise.
     */
    public static void setTransitioning(boolean transitioning) {
        getInstance().isTransitioning = transitioning;
    }

    /**
     * Retrieves the current display mode from the settings.
     *
     * @return The display mode, defaulting to "Windowed" if not set.
     */
    public static String getDisplayMode() {
        String mode = properties.getProperty("displayMode", "Windowed");
        System.out.println("Display Mode Retrieved: " + mode);
        return mode;
    }

    /**
     * Sets the display mode in the settings and saves it to the file.
     *
     * @param mode The display mode to set.
     */
    public static void setDisplayMode(String mode) {
        properties.setProperty("displayMode", mode);
        System.out.println("Display Mode Set to: " + mode);

        try (FileOutputStream output = new FileOutputStream(SETTINGS_FILE_PATH)) {
            properties.store(output, "User Settings");
            System.out.println("Settings saved successfully.");
        } catch (IOException e) {
            System.out.println("Unable to save settings: " + e.getMessage());
        }
    }
}
