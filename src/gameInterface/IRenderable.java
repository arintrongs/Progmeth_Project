package gameInterface;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
	public int getZ();

	public void draw(GraphicsContext gc, double x);

	public boolean isVisible();
}
