package gameLogic;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameManager {

	private int currentNoteIndex;
	private double mediaStartTime;
	private double offset;
	private double lastBeat;
	private double speed;
	private double startTime;
	private MusicChart musicChart;
	private File filestring;
	private Media file;
	private MediaPlayer mediaPlayer;

	public GameManager() {
		// TODO Auto-generated constructor stub
		musicChart = new MusicChart("test2", 73.0, 1);
		filestring = new File("res/" + "test" + ".wav");
		file = new Media(filestring.toURI().toString());
		mediaPlayer = new MediaPlayer(file);
		lastBeat = 0;
		currentNoteIndex = 0;
	}

	public void checkTiming(KeyCode e, double currentTime) {
		Note currentNote = musicChart.getChart().get(currentNoteIndex);
		System.out.println("Perfect : " + currentTime + " ");
		if (e == currentNote.getDirection()) {
			if (isPerfect(currentTime, (currentNoteIndex - 2) * musicChart.getDelayPerHit()) == true) {
				System.out.println("Perfect : " + currentTime + " " + currentNoteIndex * musicChart.getDelayPerHit());
			}
		}
	}

	public void setTapEventPressed(Scene scene) {
		scene.setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode().isArrowKey()) {
				checkTiming(e.getCode(), System.nanoTime() / 1000000000.0 - startTime - offset);
			}
		});
	}

	public void start() {
		System.out.println("Duration: " + file.getDuration().toSeconds());
		mediaPlayer.play();
		mediaStartTime = System.nanoTime() / 1000000000.0;
	}

	private boolean isPerfect(double tappedTime, double noteTime) {
		double lowerBound = noteTime - 0.20;
		double upperBound = noteTime + 0.20;
		if (tappedTime >= lowerBound && tappedTime <= upperBound)
			return true;
		return false;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getMediaStartTime() {
		return mediaStartTime;
	}

	public double getLastBeat() {
		return lastBeat;
	}

	public void setLastBeat(double lastBeat) {
		this.lastBeat = lastBeat;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public MusicChart getMusicChart() {
		return musicChart;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public int getCurrentNoteIndex() {
		return currentNoteIndex;
	}

	public void setCurrentNoteIndex(int currentNoteIndex) {
		this.currentNoteIndex = currentNoteIndex;
	}

}
