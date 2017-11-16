package gameLogic;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameManager {
	private ArrayList<KeyEvent> keys = new ArrayList<>();
	private ArrayList<Note> notes = new ArrayList<>();

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	private File filestring = new File("res/test.wav");
	private Media file = new Media(filestring.toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(file);
	private double mediaStartTime;
	private int sum = 0;
	private int idx = 0;

	public GameManager() {
		// TODO Auto-generated constructor stub
		notes.add(new Note(1780.0, 1));
		notes.add(new Note(1940.0, 2));
		notes.add(new Note(2100.0, 3));
		notes.add(new Note(2290.0, 4));
	}

	public void setTapEventRelease(Scene scene) {
		scene.setOnKeyReleased((KeyEvent e) -> {
			if (keys.size() <= 2 && keys.size() != 0) {
				checkTiming(e);
			}
			keys.clear();
		});
	}

	public void checkTiming(KeyEvent e) {
		Note note = notes.get(idx++);
		double current = mediaPlayer.getCurrentTime().toMillis();
		int key = stringToIntDirection(e.getCode().toString());
		int direction = note.getDirection();
		double lower_bound = note.getTime() - 200;
		double upper_bound = note.getTime() + 200;
		if (current <= upper_bound && current >= lower_bound && key == direction) {
			sum += 1;
		}
		System.out.println(current);
		System.out.println("sum = " + sum);
	}

	public void setTapEventPressed(Scene scene) {
		scene.setOnKeyPressed((KeyEvent e) -> {
			keys.add(e);
			System.out.println(e);
		});
	}

	public void setMediaPlayer() {
		mediaPlayer.setOnReady(new Runnable() {
			@Override
			public void run() {
				System.out.println("Duration: " + file.getDuration().toSeconds());
				mediaStartTime = System.nanoTime();
				mediaPlayer.play();
			}
		});
	}

	public double getMediaStartTime() {
		return mediaStartTime;
	}

	public int stringToIntDirection(String s) {
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

	public ArrayList<Note> getNotes() {
		return notes;
	}
}
