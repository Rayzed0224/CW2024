package com.example.demo.levels;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

import java.util.List;

/**
 * Handles collision detection between actors.
 */
public class CollisionManager {
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