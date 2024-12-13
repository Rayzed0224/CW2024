package com.example.demo.actors;

import com.example.demo.levels.LevelParentBase;
import com.example.demo.utilities.Destructible;

/**
 * Extends ActiveActor, adding destructible functionality for actors.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * Indicates whether the actor is destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * Reference to the parent level of the actor.
	 */
	private LevelParentBase parentLevel;

	/**
	 * Constructs an ActiveActorDestructible with the specified properties.
	 *
	 * @param imageName   The name of the image representing the actor.
	 * @param imageHeight The height of the image representing the actor.
	 * @param initialXPos The initial X position of the actor.
	 * @param initialYPos The initial Y position of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor. Subclasses should implement this method.
	 */
	@Override
	public void updatePosition() {
	}

	/**
	 * Sets the parent level for the actor.
	 *
	 * @param level The parent level to set.
	 */
	public void setParentLevel(LevelParentBase level) {
		this.parentLevel = level;
	}

	/**
	 * Retrieves the parent level of the actor.
	 *
	 * @return The parent level of the actor.
	 */
	public LevelParentBase getParentLevel() {
		return this.parentLevel;
	}

	/**
	 * Updates the actor's state. Subclasses should implement this method.
	 */
	public abstract void updateActor();

	/**
	 * Applies damage to the actor. Subclasses should implement this method.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destruction state of the actor.
	 *
	 * @param isDestroyed The new destruction state of the actor.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks whether the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed; {@code false} otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Adjusts the actor's position based on the new width and height of the parent level.
	 *
	 * @param newWidth  The new width of the parent level.
	 * @param newHeight The new height of the parent level.
	 */
	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		// Call the superclass implementation to adjust position
		super.adjustPositionForResize(newWidth, newHeight);
	}
}
