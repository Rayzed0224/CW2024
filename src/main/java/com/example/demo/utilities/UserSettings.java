package com.example.demo.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UserSettings {
    private static final String SETTINGS_FILE_PATH = "config/settings.properties";
    private static Properties properties = new Properties();
    private boolean isTransitioning = false;

    private static UserSettings instance = new UserSettings(); // Singleton instance

    static {
        try (FileInputStream input = new FileInputStream(SETTINGS_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Unable to load settings file: " + e.getMessage());
        }
    }

    // Singleton accessor
    public static UserSettings getInstance() {
        return instance;
    }

    public static boolean isTransitioning() {
        return getInstance().isTransitioning;
    }

    public static void setTransitioning(boolean transitioning) {
        getInstance().isTransitioning = transitioning;
    }

    public static String getDisplayMode() {
        String mode = properties.getProperty("displayMode", "Windowed");
        System.out.println("Display Mode Retrieved: " + mode);
        return mode;
    }

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