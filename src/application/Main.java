package application;

import java.util.ArrayList;

import gameInterface.IRenderable;
import gameInterface.TimingLine;
import gameLogic.GameManager;
import gameLogic.Note;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private ArrayList<IRenderable> render = new ArrayList<>();
	private AnimationTimer ai;
	private double startNanoTime = 0;
	public static final int FRAME_RATE = 60;
	public int idx = 0;

	@Override
	public void start(Stage stage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("AnimationTimer");

		Canvas canvas = new Canvas(800, 400);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		root.getChildren().add(canvas);

		TimingLine timingLine = new TimingLine(600);
		GameManager gameManager = new GameManager();
		Timeline timer;
		double speed = 1;
		timer = new Timeline(new KeyFrame(Duration.millis(1000.00 / FRAME_RATE), event -> {
			double currentSongTime = (System.nanoTime() / 1000000000.0 - startNanoTime) - gameManager.getOffset();
			double lastbeat = gameManager.getLastBeat();
			Note currentNote = gameManager.getMusicChart().getChart().get(gameManager.getCurrentNoteIndex());
			ArrayList<Note> chart = gameManager.getMusicChart().getChart();
			if (gameManager.getCurrentNoteIndex() < chart.size()) {
				if (currentSongTime >= lastbeat + gameManager.getMusicChart().getDelayPerHit() - speed) {
					System.out.println(gameManager.getCurrentNoteIndex());
					gameManager.setLastBeat(lastbeat + gameManager.getMusicChart().getDelayPerHit());
					if (currentNote.getType() == 1) {
						currentNote.setStartTime(lastbeat + gameManager.getMusicChart().getDelayPerHit() - speed);
						render.add(currentNote);
					}
					gameManager.setCurrentNoteIndex(gameManager.getCurrentNoteIndex() + 1);
				}
			}
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			timingLine.drawLine(gc);
			for (IRenderable i : render) {
				double x = (canvas.getWidth() - 200) * (currentSongTime - ((Note) i).getStartTime()) / speed;
				((Note) i).draw(gc, x);
			}
		}));

		root.setOnMouseClicked((MouseEvent e) -> {
			startNanoTime = System.nanoTime() / 1000000000.0;
			gameManager.setStartTime(startNanoTime);
			gameManager.setTapEventPressed(scene);
			gameManager.start();
			gameManager.setOffset(gameManager.getMediaStartTime() - startNanoTime);
			timer.setCycleCount(Animation.INDEFINITE);
			timer.play();
		});

		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
