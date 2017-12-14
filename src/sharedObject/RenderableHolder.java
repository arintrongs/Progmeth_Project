package sharedObject;

import java.util.ArrayList;
import java.util.List;

public class RenderableHolder {
	private static RenderableHolder instance = new RenderableHolder();
	private List<IRenderable> iRenderable;

	public RenderableHolder() {
		iRenderable = new ArrayList<IRenderable>();
	}

	public void add(IRenderable entity) {
		iRenderable.add(entity);
	}

	public void clear() {
		iRenderable.clear();
	}

	public static RenderableHolder getInstance() {
		return instance;
	}

	public List<IRenderable> getiRenderable() {
		return iRenderable;
	}

}
