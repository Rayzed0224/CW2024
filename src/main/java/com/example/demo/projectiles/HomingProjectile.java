package com.example.demo.projectiles;

import com.example.demo.actors.ActiveActorDestructible;
import javafx.scene.Group;

/**
 * A homing projectile that tracks its target but includes deviation for dodgeability.
 */
public class HomingProjectile extends Projectile {
    private final ActiveActorDestructible target; // Target to track
    private double deviationAngle; // Angle deviation to make the projectile less accurate

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

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void takeDamage() {
        destroy(); // Destroy projectile on damage
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed(); // Use inherited destruction logic
    }
}