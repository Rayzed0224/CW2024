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
 */
public abstract class LevelParentBase {
    public static final double ORIGINAL_SCREEN_WIDTH = 1350.0;
    public static final double ORIGINAL_SCREEN_HEIGHT = 750.0;

    protected final Group root;
    protected final Timeline timeline;
    protected final Scene scene;
    protected final ImageView background;
    protected UserPlane userPlane;
    protected final ActorManager actorManager;
    protected final CollisionManager collisionManager;
    private final List<Consumer<String>> observers = new ArrayList<>();
    private HeartDisplay heartDisplay;

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

    protected void initializeBackground(double screenWidth, double screenHeight) {
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        if (!root.getChildren().contains(background)) {
            root.getChildren().add(0, background); // Ensure background is at the bottom
        }
    }

    // Getter for heartDisplay
    protected HeartDisplay getHeartDisplay() {
        return heartDisplay;
    }

    // Setter for heartDisplay
    protected void setHeartDisplay(HeartDisplay heartDisplay) {
        this.heartDisplay = heartDisplay;
    }

    public void updateHeartDisplay(int health) {
        if (heartDisplay != null) {
            heartDisplay.updateHealth(health);
            System.out.println("Heart display updated: Health = " + health);
        } else {
            System.out.println("Heart display is not initialized.");
        }
    }

    public Group getRoot() {
        return root; // Provides access to the root node
    }

    public UserPlane getUserPlane() {
        return userPlane; // Provides access to the user plane
    }

    public void setUserPlane(UserPlane userPlane) {
        this.userPlane = userPlane;
    }

    public ActorManager getActorManager() { // Expose ActorManager through a getter
        return actorManager;
    }

    public CollisionManager getCollisionManager() { // Expose CollisionManager
        return collisionManager;
    }

    public Scene getScene() {
        return scene; // Provides access to the level's scene
    }

    public void startGame() {
        System.out.println("Starting the game...");
        initializeFriendlyUnits(); // Initialize friendly units
        timeline.play(); // Start game loop
        System.out.println("Game timeline started.");
    }

    public void stopGame() {
        timeline.stop();
    }

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
        }
    }

    public void resizeElements(double newWidth, double newHeight) {
        // Default implementation: Adjust background size if needed
        if (background != null) {
            background.setFitWidth(newWidth);
            background.setFitHeight(newHeight);
        }
    }

    protected abstract void initializeFriendlyUnits();
    protected abstract void checkIfGameOver();
    protected abstract void spawnEnemyUnits();

    public void addObserver(Consumer<String> observer) {
        observers.add(observer);
    }

    protected void notifyObservers(String nextLevel) {
        observers.forEach(observer -> observer.accept(nextLevel));
    }

    public Scene initializeScene() {
        return scene;
    }
}
