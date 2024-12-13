package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

import java.util.List;

/**
 * Handles collision detection between actors.
 *
 * <p>This class provides functionality to detect and handle collisions
 * between two groups of destructible actors, applying damage and
 * managing kill counts for enemies.</p>
 */
public class CollisionManager {

    /**
     * Detects and handles collisions between two groups of destructible actors.
     *
     * <p>For each pair of actors from the provided groups, this method checks
     * if their bounding boxes intersect. If a collision is detected, both
     * actors take damage. If the second actor is an enemy plane and is destroyed,
     * the user's kill count is incremented.</p>
     *
     * @param group1 the first group of destructible actors
     * @param group2 the second group of destructible actors
     */
    public void handleCollisions(List<? extends ActiveActorDestructible> group1, List<? extends ActiveActorDestructible> group2) {
        for (ActiveActorDestructible actor1 : group1) {
            for (ActiveActorDestructible actor2 : group2) {
                if (actor1.getBoundsInParent().intersects(actor2.getBoundsInParent())) {
                    actor1.takeDamage();
                    actor2.takeDamage();
                    // Increment kills if enemy is destroyed
                    if (actor2.isDestroyed() && actor2 instanceof EnemyPlane) {
                        LevelParentBase level = ((EnemyPlane) actor2).getParentLevel();
                        level.getUserPlane().incrementKillCount();
                        System.out.println("Kill Count: " + level.getUserPlane().getNumberOfKills());
                    }
                }
            }
        }
    }
}
