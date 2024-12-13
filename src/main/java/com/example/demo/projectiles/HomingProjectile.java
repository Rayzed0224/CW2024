package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;
import javafx.scene.Group;

/**
 * A homing projectile that tracks its target but includes deviation for dodgeability.
 */
public class HomingProjectile extends Projectile {

    /**
     * The target actor that the projectile is tracking.
     */
    private final ActiveActorDestructible target;

    /**
     * The angle deviation to make the projectile less accurate.
     */
    private double deviationAngle;

    /**
     * Constructs a HomingProjectile with the specified initial position, target, and image.
     *
     * @param x         The initial X position of the projectile.
     * @param y         The initial Y position of the projectile.
     * @param root      The root group to add the projectile to.
     * @param target    The target actor for the projectile to track.
     * @param imageName The image file name for the projectile.
     */
    public HomingProjectile(double x, double y, Group root, ActiveActorDestructible target, String imageName) {
        super(imageName.replace("/com/example/demo/images/", ""), 20, x, y); // Remove base path if necessary
        this.target = target;

        // Set dimensions
        this.setFitHeight(50); // Height
        this.setFitWidth(50);  // Width

        // Ensure visibility and layering
        this.setOpacity(1.0);  // Fully visible
        this.setVisible(true); // Ensure it renders
        this.toFront();        // Bring to the front of the scene graph

        if (root != null) {
            root.getChildren().add(this); // Add to the scene
        }

        System.out.println("HomingProjectile initialized with image: " + imageName +
                ", Width: " + getFitWidth() + ", Height: " + getFitHeight());
    }

    /**
     * Updates the position of the projectile by tracking its target and applying deviation.
     */
    @Override
    public void updatePosition() {
        if (target != null && !target.isDestroyed()) {
            double targetY = target.getLayoutY();
            double currentY = getLayoutY();
            double currentX = getLayoutX();

            // Move horizontally left (towards the screen center)
            double horizontalSpeed = 8.0;
            setLayoutX(currentX - horizontalSpeed);

            // Homing vertically
            double verticalSpeed = 0.5;
            if (Math.abs(targetY - currentY) > verticalSpeed) {
                setLayoutY(currentY + Math.signum(targetY - currentY) * verticalSpeed);
            } else {
                setLayoutY(targetY);
            }

            // Debug log for position
            System.out.println("Projectile moved to X=" + getLayoutX() + ", Y=" + getLayoutY());
        } else {
            System.out.println("Projectile has no valid target.");
        }
        setLayoutX(Math.max(0, getLayoutX())); // Prevent moving off-screen
    }

    /**
     * Updates the state of the projectile, including its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Destroys the projectile when it takes damage.
     */
    @Override
    public void takeDamage() {
        destroy(); // Destroy projectile on damage
    }

    /**
     * Checks whether the projectile is destroyed.
     *
     * @return {@code true} if the projectile is destroyed; {@code false} otherwise.
     */
    @Override
    public boolean isDestroyed() {
        return super.isDestroyed(); // Use inherited destruction logic
    }
}
