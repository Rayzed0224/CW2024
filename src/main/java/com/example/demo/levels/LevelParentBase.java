package com.example.demo.levels;

import com.example.demo.actors.UserPlane;
import com.example.demo.ui.HeartDisplay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Base class for shared level functionality.
 *
 * <p>This class provides the foundational structure for game levels,
 * including managing actors, collisions, user interface elements,
 * and the game loop.</p>
 */
public abstract class LevelParentBase {

    /**
     * The original width of the game screen.
     */
    public static final double ORIGINAL_SCREEN_WIDTH = 1350.0;

    /**
     * The original height of the game screen.
     */
    public static final double ORIGINAL_SCREEN_HEIGHT = 750.0;

    /**
     * The root group containing all elements of the level.
     */
    protected final Group root;

    /**
     * The game loop timeline.
     */
    protected final Timeline timeline;

    /**
     * The scene representing the level.
     */
    protected final Scene scene;

    /**
     * The background image of the level.
     */
    protected final ImageView background;

    /**
     * The user's plane (player-controlled actor).
     */
    protected UserPlane userPlane;

    /**
     * The manager responsible for handling actors in the level.
     */
    protected final ActorManager actorManager;

    /**
     * The manager responsible for handling collisions in the level.
     */
    protected final CollisionManager collisionManager;

    /**
     * A list of observers notified about level transitions.
     */
    private final List<Consumer<String>> observers = new ArrayList<>();

    /**
     * The heart display for representing the player's health.
     */
    private HeartDisplay heartDisplay;

    /**
     * Constructs a LevelParentBase with the specified parameters.
     *
     * @param backgroundImageName the file name of the background image
     * @param screenWidth         the width of the screen
     * @param screenHeight        the height of the screen
     * @param playerInitialHealth the initial health of the player
     */
    public LevelParentBase(String backgroundImageName, double screenWidth, double screenHeight, int playerInitialHealth) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.background = new ImageView(getClass().getResource(backgroundImageName).toExternalForm());
        this.actorManager = new ActorManager(root);
        this.collisionManager = new CollisionManager();

        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> update()));

        System.out.println("LevelParentBase initialized:");
        System.out.println("Root: " + root);
        System.out.println("Scene: " + scene);
        System.out.println("ActorManager: " + actorManager);

        initializeBackground(screenWidth, screenHeight);
        root.getChildren().add(new Group()); // Add player to the root
    }

    /**
     * Initializes the background image.
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    protected void initializeBackground(double screenWidth, double screenHeight) {
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        if (!root.getChildren().contains(background)) {
            root.getChildren().add(0, background); // Ensure background is at the bottom
        }
    }

    /**
     * Retrieves the heart display for managing player's health.
     *
     * @return the heart display
     */
    protected HeartDisplay getHeartDisplay() {
        return heartDisplay;
    }

    /**
     * Sets the heart display for managing player's health.
     *
     * @param heartDisplay the heart display to set
     */
    protected void setHeartDisplay(HeartDisplay heartDisplay) {
        this.heartDisplay = heartDisplay;
    }

    /**
     * Updates the heart display to reflect the player's current health.
     *
     * @param health the player's current health
     */
    public void updateHeartDisplay(int health) {
        if (heartDisplay != null) {
            heartDisplay.updateHealth(health);
            System.out.println("Heart display updated: Health = " + health);
        }else {
            System.out.println("Heart display is not initialized.");
        }
    }

    /**
     * Retrieves the root group containing all elements of the level.
     *
     * @return the root group
     */
    public Group getRoot() {
        return root;
    }

    /**
     * Retrieves the player's plane.
     *
     * @return the user's plane
     */
    public UserPlane getUserPlane() {
        return userPlane;
    }

    /**
     * Sets the player's plane.
     *
     * @param userPlane the user's plane to set
     */
    public void setUserPlane(UserPlane userPlane) {
        this.userPlane = userPlane;
    }

    /**
     * Retrieves the actor manager for managing actors in the level.
     *
     * @return the actor manager
     */
    public ActorManager getActorManager() {
        return actorManager;
    }

    /**
     * Retrieves the collision manager for handling collisions in the level.
     *
     * @return the collision manager
     */
    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    /**
     * Retrieves the scene representing the level.
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Starts the game by initializing friendly units and starting the game loop.
     */
    public void startGame() {
        System.out.println("Starting the game...");
        initializeFriendlyUnits(); // Initialize friendly units
        timeline.play(); // Start game loop
        System.out.println("Game timeline started.");
    }

    /**
     * Stops the game loop.
     */
    public void stopGame() {
        timeline.stop();
    }

    /**
     * Updates the level state, including actor updates, enemy spawning,
     * and checking win/lose conditions.
     */
    protected void update() {
        System.out.println("Root children count: " + root.getChildren().size());
        for (Node child : root.getChildren()) {
            System.out.println("Child: " + child + " at X=" + child.getLayoutX() + ", Y=" + child.getLayoutY());
        }
        getUserPlane().updatePosition();
        getActorManager().updateAll(); // Update all actors
        spawnEnemyUnits();             // Spawn new enemies
        checkIfGameOver();             // Check win/lose conditions
        getActorManager().removeDestroyedActors(); // Remove destroyed actors
        System.out.println("Root children count: " + root.getChildren().size());
        for (Node child : root.getChildren()) {
            System.out.println("Child: " + child +
                    ", LayoutX=" + child.getLayoutX() +
                    ", LayoutY=" + child.getLayoutY() +
                    ", Opacity=" + child.getOpacity() +
                    ", Visible=" + child.isVisible());
        }}

    /**
     * Resizes elements in the level to match new screen dimensions.
     *
     * @param newWidth  the new width of the screen
     * @param newHeight the new height of the screen
     */
    public void resizeElements(double newWidth, double newHeight) {
        if (background != null) {
            background.setFitWidth(newWidth);
            background.setFitHeight(newHeight);
        }
    }

    /**
     * Initializes friendly units in the level.
     */
    protected abstract void initializeFriendlyUnits();

    /**
     * Checks if the game is over.
     */
    protected abstract void checkIfGameOver();

    /**
     * Spawns enemy units in the level.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Adds an observer to be notified of level transitions.
     *
     * @param observer the observer to add
     */
    public void addObserver(Consumer<String> observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers of a level transition.
     *
     * @param nextLevel the next level to transition to
     */
    protected void notifyObservers(String nextLevel) {
        observers.forEach(observer -> observer.accept(nextLevel));
    }

    /**
     * Initializes and retrieves the scene for the level.
     *
     * @return the initialized scene
     */
    public Scene initializeScene() {
        return scene;
    }
}
