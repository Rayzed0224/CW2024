package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;
import com.example.demo.projectiles.HomingProjectile;
import com.example.demo.projectiles.Projectile;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all active actors (enemies, projectiles, etc.) in a level.
 */
public class ActorManager {
    private final Group root;
    private final List<ActiveActorDestructible> enemies;
    private final List<Projectile> userProjectiles;
    private final List<Projectile> enemyProjectiles;
    private UserPlane userPlane;

    public ActorManager(Group root) {
        this.root = root;
        this.enemies = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
    }

    // Add this method in ActorManager.java
    public Group getRoot() {
        return root;
    }

    public void addEnemy(ActiveActorDestructible enemy) {
        enemies.add(enemy);
        root.getChildren().add(enemy);
    }

    public void addUserProjectile(Projectile projectile) {
        if (!userProjectiles.contains(projectile)) {
            userProjectiles.add(projectile);
            root.getChildren().add(projectile);
            System.out.println("Projectile added to root: " + projectile);
        } else {
            System.err.println("Duplicate projectile ignored.");
        }
    }

    public void createEnemyProjectile(double x, double y, ActiveActorDestructible target, String imageName) {
        // Pass only the filename
        System.out.println("Creating projectile with image: " + imageName);
        HomingProjectile projectile = new HomingProjectile(x, y, null, target, imageName); // Use filename
        addEnemyProjectile(projectile);
        System.out.println("Projectile created and added to manager.");
    }

    public void addEnemyProjectile(Projectile projectile) {
        if (!enemyProjectiles.contains(projectile)) {
            enemyProjectiles.add(projectile);
            if (!root.getChildren().contains(projectile)) {
                root.getChildren().add(projectile);
                System.out.println("Projectile added to root: " + projectile);
                // Log all root children
                root.getChildren().forEach(child -> {
                    System.out.println("Child: " + child +
                            ", LayoutX=" + child.getLayoutX() +
                            ", LayoutY=" + child.getLayoutY() +
                            ", Opacity=" + child.getOpacity() +
                            ", Visible=" + child.isVisible());
                });
            } else {
                System.err.println("Duplicate projectile addition prevented: " + projectile);
            }
        }
    }

    public List<ActiveActorDestructible> getEnemies() {
        return enemies;
    }

    public List<Projectile> getUserProjectiles() {
        return userProjectiles;
    }

    public List<Projectile> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public void setUserPlane(UserPlane userPlane) {
        this.userPlane = userPlane;
        System.out.println("UserPlane set in ActorManager: " + userPlane);
    }

    public UserPlane getUserPlane() {
        return userPlane;
    }

    public void updateAll() {
        System.out.println("Updating all projectiles and actors...");
        userProjectiles.forEach(Projectile::updateActor);
        enemyProjectiles.forEach(Projectile::updateActor); // Includes HomingProjectile
        enemies.forEach(ActiveActorDestructible::updateActor);
    }

    // Remove all destroyed actors from the scene and list
    public void removeDestroyedActors() {
        removeDestroyed(enemies);
        removeDestroyed(userProjectiles);
        removeDestroyed(enemyProjectiles);
    }

    // Generic method to remove destroyed actors
    private <T extends ActiveActorDestructible> void removeDestroyed(List<T> actors) {
        List<T> destroyed = new ArrayList<>(actors.stream().filter(ActiveActorDestructible::isDestroyed).toList());
        root.getChildren().removeAll(destroyed);
        actors.removeAll(destroyed);
    }

    // Clear all actors and projectiles from the level
    public void clearAllActors() {
        root.getChildren().removeAll(enemies);
        root.getChildren().removeAll(userProjectiles);
        root.getChildren().removeAll(enemyProjectiles);

        enemies.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
    }
}