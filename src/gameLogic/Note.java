package gameLogic;


import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sharedObject.IRenderable;

public class Note implements IRenderable {
	private Integer type;
	private KeyCode direction;
	private Double startTime;

	public Note(Integer type, KeyCode direction) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.direction = direction;
	}

	public Double getStartTime() {
		return startTime;
	}

	public void setStartTime(Double startTime) {
		this.startTime = startTime;
	}

	public int getZ() {
		return 1;
	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.setTextBaseline(VPos.CENTER);
		gc.setLineWidth(2);
		gc.setFill(Color.BLACK);
		gc.strokeRoundRect(x, 200, 50, 50, 50, 50);
		gc.fillText(direction.toString(), x + 10, 225);

	}

	public boolean isVisible() {
		return false;
	}

	public KeyCode getDirection() {
		return direction;
	}

	public Integer getType() {
		return type;
	}

}
