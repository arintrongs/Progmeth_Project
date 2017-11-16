package gameInterface;

import javafx.scene.canvas.GraphicsContext;

public class TimingLine {
	private int x;

	public TimingLine(int x) {
		// TODO Auto-generated constructor stub
		this.x = x;
	}

	public void drawLine(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(x, 0);
		gc.lineTo(x, 400);
		gc.closePath();
		gc.stroke();
	}
}
