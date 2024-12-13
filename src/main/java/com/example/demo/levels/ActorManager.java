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

    /**
     * The root group to which all actors are added.
     */
    private final Group root;

    /**
     * List of all active enemy actors.
     */
    private final List<ActiveActorDestructible> enemies;

    /**
     * List of all projectiles fired by the user.
     */
    private final List<Projectile> userProjectiles;

    /**
     * List of all projectiles fired by enemies.
     */
    private final List<Projectile> enemyProjectiles;

    /**
     * The user-controlled plane.
     */
    private UserPlane userPlane;

    /**
     * Constructs an ActorManager with the specified root group.
     *
     * @param root The root group to which actors are added.
     */
    public ActorManager(Group root) {
        this.root = root;
        this.enemies = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
    }

    /**
     * Retrieves the root group.
     *
     * @return The root group.
     */
    public Group getRoot() {
        return root;
    }

    /**
     * Adds an enemy actor to the manager and displays it in the root group.
     *
     * @param enemy The enemy actor to add.
     */
    public void addEnemy(ActiveActorDestructible enemy) {
        enemies.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Adds a user-fired projectile to the manager and displays it in the root group.
     *
     * @param projectile The projectile to add.
     */
    public void addUserProjectile(Projectile projectile) {
        if (!userProjectiles.contains(projectile)) {
            userProjectiles.add(projectile);
            root.getChildren().add(projectile);
            System.out.println("Projectile added to root: " + projectile);
        } else {
            System.err.println("Duplicate projectile ignored.");
        }
    }

    /**
     * Creates and adds a homing projectile fired by an enemy.
     *
     * @param x         The initial X position of the projectile.
     * @param y         The initial Y position of the projectile.
     * @param target    The target actor for the projectile.
     * @param imageName The image file name for the projectile.
     */
    public void createEnemyProjectile(double x, double y, ActiveActorDestructible target, String imageName) {
        System.out.println("Creating projectile with image: " + imageName);
        HomingProjectile projectile = new HomingProjectile(x, y, null, target, imageName);
        addEnemyProjectile(projectile);
        System.out.println("Projectile created and added to manager.");
    }

    /**
     * Adds an enemy-fired projectile to the manager and displays it in the root group.
     *
     * @param projectile The projectile to add.
     */
    public void addEnemyProjectile(Projectile projectile) {
        if (!enemyProjectiles.contains(projectile)) {
            enemyProjectiles.add(projectile);
            if (!root.getChildren().contains(projectile)) {
                root.getChildren().add(projectile);
                System.out.println("Projectile added to root: " + projectile);
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

    /**
     * Retrieves the list of active enemy actors.
     *
     * @return The list of enemies.
     */
    public List<ActiveActorDestructible> getEnemies() {
        return enemies;
    }

    /**
     * Retrieves the list of user-fired projectiles.
     *
     * @return The list of user projectiles.
     */
    public List<Projectile> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Retrieves the list of enemy-fired projectiles.
     *
     * @return The list of enemy projectiles.
     */
    public List<Projectile> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Sets the user-controlled plane.
     *
     * @param userPlane The user-controlled plane.
     */
    public void setUserPlane(UserPlane userPlane) {
        this.userPlane = userPlane;
        System.out.println("UserPlane set in ActorManager: " + userPlane);
    }

    /**
     * Retrieves the user-controlled plane.
     *
     * @return The user-controlled plane.
     */
    public UserPlane getUserPlane() {
        return userPlane;
    }

    /**
     * Updates all actors and projectiles managed by this class.
     */
    public void updateAll() {
        System.out.println("Updating all projectiles and actors...");
        userProjectiles.forEach(Projectile::updateActor);
        enemyProjectiles.forEach(Projectile::updateActor);
        enemies.forEach(ActiveActorDestructible::updateActor);
    }

    /**
     * Removes all destroyed actors and projectiles from the manager and root group.
     */
    public void removeDestroyedActors() {
        removeDestroyed(enemies);
        removeDestroyed(userProjectiles);
        removeDestroyed(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from the specified list and root group.
     *
     * @param actors The list of actors to remove.
     * @param <T>    The type of actors in the list.
     */
    private <T extends ActiveActorDestructible> void removeDestroyed(List<T> actors) {
        List<T> destroyed = new ArrayList<>(actors.stream().filter(ActiveActorDestructible::isDestroyed).toList());
        root.getChildren().removeAll(destroyed);
        actors.removeAll(destroyed);
    }

    /**
     * Clears all actors and projectiles from the manager and root group.
     */
    public void clearAllActors() {
        root.getChildren().removeAll(enemies);
        root.getChildren().removeAll(userProjectiles);
        root.getChildren().removeAll(enemyProjectiles);

        enemies.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
    }
}
