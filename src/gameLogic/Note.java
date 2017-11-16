package gameLogic;

import gameInterface.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Note implements IRenderable {
	private Double time;
	private Integer direction;
	private Double startTime;

	public Double getStartTime() {
		return startTime;
	}

	public void setStartTime(Double startTime) {
		this.startTime = startTime;
	}

	public Note(Double time, Integer direction) {
		// TODO Auto-generated constructor stub
		this.time = time;
		this.direction = direction;
	}

	public int getZ() {
		return 1;
	}

	public void draw(GraphicsContext gc, double x) {
		gc.setLineWidth(2);
		gc.setFill(Color.BLACK);
		gc.strokeRoundRect(x, 200, 50, 50, 50, 50);
	}

	public boolean isVisible() {
		return false;
	}

	public Double getTime() {
		return time;
	}

	public Integer getDirection() {
		return direction;
	}

}
