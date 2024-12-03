package com.example.demo.actors;

import com.example.demo.levels.LevelParent;
import com.example.demo.utilities.Destructible;

/**
 * Extends ActiveActor, adding destructible functionality for actors.
 */

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	@Override
	public void adjustPositionForResize(double newWidth, double newHeight) {
		// Call the superclass implementation to adjust position
		super.adjustPositionForResize(newWidth, newHeight);
	}
}
