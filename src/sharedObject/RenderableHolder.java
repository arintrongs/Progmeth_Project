package sharedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenderableHolder {
	private static final RenderableHolder instance = new RenderableHolder();
	private List<IRenderable> iRenderable;
	private Comparator<IRenderable> comparator;

	public RenderableHolder() {
		iRenderable = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};

	}

	public void add(IRenderable entity) {
		iRenderable.add(entity);
		Collections.sort(iRenderable, comparator);

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
