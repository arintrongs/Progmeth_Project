package gameLogic;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Note {
	private Integer type;
	private KeyCode direction;
	private Double startTime;
	private Canvas canvas;
	private Image image;

	public Note(Integer type, KeyCode direction) {
		// TODO Auto-generated constructor stub
		if (direction == KeyCode.UP) 
			
			this.image = new Image("up-arrow.png");
			
		if (direction == KeyCode.DOWN) 
			
			this.image = new Image("down-arrow.png");
			
		if (direction == KeyCode.RIGHT) 
			
			this.image = new Image("right-arrow.png");
			
		if (direction == KeyCode.LEFT) 
		
			this.image = new Image("left-arrow.png");
			
		this.type = type;
		this.direction = direction;
		this.canvas = new Canvas(70, 70);
		this.canvas.getGraphicsContext2D().drawImage(this.image, 0, 0, 70, 70);
		this.canvas.setTranslateY(472);
		this.canvas.setTranslateX(-100);

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

	public Canvas getCanvas() {
		return this.canvas;
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

	public void setDirection(KeyCode e) {
		direction = e;
		if (direction == KeyCode.UP)
			this.image = new Image("up-arrow.png");
		if (direction == KeyCode.DOWN)
			this.image = new Image("down-arrow.png");
		if (direction == KeyCode.RIGHT)
			this.image = new Image("right-arrow.png");
		if (direction == KeyCode.LEFT)
			this.image = new Image("left-arrow.png");
		this.canvas = new Canvas(70, 70);
		this.canvas.getGraphicsContext2D().drawImage(this.image, 0, 0, 70, 70);
		this.canvas.setTranslateY(472);
		this.canvas.setTranslateX(-100);
	}

}
