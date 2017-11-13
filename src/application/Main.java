package application;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {
	String new_code;
	ArrayList<KeyEvent> keys = new ArrayList<>();
	ArrayList<Butt> notes = new ArrayList<>();
	File filestring = new File("res/test.wav");
	Media file = new Media(filestring.toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(file);
	Integer sum = 0;
	Integer idx = 0;

	public class Butt {
		public Double time;
		public Integer direction;

		public Butt(Double time, Integer direction) {
			// TODO Auto-generated constructor stub
			this.time = time;
			this.direction = direction;
		}
	}

	public Integer stringToIntDirection(String s) {
		if (s.equals("LEFT"))
			return 1;
		if (s.equals("UP"))
			return 2;
		if (s.equals("RIGHT"))
			return 3;
		if (s.equals("DOWN"))
			return 4;
		return 0;
	}

	@Override
	public void start(Stage stage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("AnimationTimer");

		Canvas canvas = new Canvas(800, 400);

		notes.add(new Butt(1780.0, 1));
		notes.add(new Butt(1940.0, 2));
		notes.add(new Butt(2100.0, 3));
		notes.add(new Butt(2290.0, 4));

		stage.show();

		scene.setOnKeyPressed((KeyEvent e) -> {
			keys.add(e);
		});

		scene.setOnKeyReleased((KeyEvent e) -> {
			if (keys.size() <= 2 && keys.size() != 0) {

				Butt note = notes.get(idx++);
				double current = mediaPlayer.getCurrentTime().toMillis();
				int key = stringToIntDirection(e.getCode().toString());
				int direction = note.direction;
				double lower_bound = note.time - 200;
				double upper_bound = note.time + 200;
				if (current <= upper_bound && current >= lower_bound && key == direction) {
					sum += 1;
				}
				System.out.println(current);
				System.out.println("sum = " + sum);
			}
			keys.clear();
		});

		mediaPlayer.setOnReady(new Runnable() {
			@Override
			public void run() {
				System.out.println("Duration: " + file.getDuration().toSeconds());
				// play if you want
				mediaPlayer.play();
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}

}
