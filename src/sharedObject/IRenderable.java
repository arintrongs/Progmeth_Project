package sharedObject;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
	public int getZ();
	public void draw(GraphicsContext gc , double x ,double y);
	public boolean isVisible();
}
