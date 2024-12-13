package com.example.demo.utilities;

/**
 * Interface defining destructible entities.
 *
 * <p>Classes implementing this interface represent objects that can
 * take damage and be destroyed. This is commonly used in game
 * environments for actors, obstacles, or other destructible items.</p>
 */
public interface Destructible {

	/**
	 * Reduces the health or integrity of the entity.
	 * This method is invoked when the entity takes damage.
	 */
	void takeDamage();

	/**
	 * Completely destroys the entity.
	 * This method is invoked when the entity's health reaches zero or
	 * under specific conditions requiring its removal.
	 */
	void destroy();

}
