package application;

import java.util.ArrayList;

import gameInterface.IRenderable;
import gameInterface.TimingLine;
import gameLogic.GameManager;
import gameLogic.Note;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	private ArrayList<IRenderable> render = new ArrayList<>();
	private GameManager gameManager = new GameManager();
	private AnimationTimer ai;

	@Override
	public void start(Stage stage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("AnimationTimer");

		Canvas canvas = new Canvas(800, 400);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		root.getChildren().add(canvas);

		TimingLine timingLine = new TimingLine(50);

		final long startNanoTime = System.nanoTime();
		ai = new AnimationTimer() {
			double width = 0;
			double height = 0;
			double x = 700;
			int idx = 0;

			public void handle(long currentNanoTime) {
				double current_time = (currentNanoTime - startNanoTime) / 1000000000.0;
				double startm = (currentNanoTime - gameManager.getMediaStartTime()) / 1000000000.0;
				double offset = current_time - startm;
				int idx = 0;
				double speed = 2;
				Note currentNote = gameManager.getNotes().get(idx);
				double currentNoteTime = currentNote.getTime() / 1000.0 + offset - speed;

				double x = (gc.getCanvas().getWidth() - 100) * (current_time - currentNoteTime) / speed;
				System.out
						.println(current_time + " " + currentNoteTime + " " + " " + (current_time - offset) + " " + x);
				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
				currentNote.draw(gc, x);

			}

		};
		scene.setOnMouseClicked((MouseEvent e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				gameManager.setTapEventRelease(scene);
				gameManager.setTapEventPressed(scene);
				gameManager.setMediaPlayer();
				ai.start();
			}
		});
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
