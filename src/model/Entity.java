package model;

import sharedObject.IRenderable;

public abstract class Entity implements IRenderable {
	protected String name;
	protected int level;
	private int z;
	boolean isVisible;

	public Entity(String name, int level) {
		this.name = name;
		this.level = 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public abstract void levelUp();

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public int getZ() {
		return z;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
