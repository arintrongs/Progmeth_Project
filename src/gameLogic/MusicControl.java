package gameLogic;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import scene.GamePlayScreen;

public class MusicControl extends AnimationTimer {

	private static double mediaStartTime;
	private static double offset;
	private double speed;
	public static double startTime;
	private MusicChart musicChart;
	private File filestring;
	private Media file;
	private GamePlayScreen gamePlayScreen;
	private ArrayList<Note> render, notes;
	private Note current_note;
	private int idx;
	private int t = 0;
	public static final double NANO = 1000000000.0;

	private MediaPlayer mediaPlayer, knock;

	public MusicControl(Pane pane) {

		musicChart = new MusicChart("test2", 146.0, 1);
		filestring = new File("res/song/test2.wav");
		file = new Media(filestring.toURI().toString());
		mediaPlayer = new MediaPlayer(file);
		knock = new MediaPlayer(new Media((new File("res/song/Knock.wav")).toURI().toString()));

		this.gamePlayScreen = (GamePlayScreen) pane;
		this.render = new ArrayList<>();
		this.notes = new ArrayList<>();

		for (int i = 0; i < musicChart.getChart().size(); i++) {
			current_note = musicChart.getChart().get(i);
			if (current_note.getType() == 1) {
				current_note.setStartTime(i * this.musicChart.getDelayPerHit() - 2.0);
				this.notes.add(current_note);
				pane.getChildren().add(current_note.getCanvas());
			}
		}

	}

	@Override
	public void handle(long now) {
		if (mediaPlayer.getStatus() == Status.PLAYING && t++ == 0) {
			startTime = System.nanoTime();
		}
		double current_time = (now - startTime) / NANO;
		if (current_time >= musicChart.currentNoteIdx * musicChart.getDelayPerHit() + 0.3) {
			musicChart.currentNoteIdx++;
		}
		current_note = (idx < notes.size()) ? notes.get(idx) : null;
		if (current_note.getStartTime() <= current_time && current_note.getType() == 1 && current_note != null) {
			render.add(current_note);
			idx++;

		}
		for (int i = 0; i < render.size(); i++) {
			Note current_render = render.get(i);
			double pos_x = 665 * (current_time - current_render.getStartTime()) / 2.0;
			// System.out.println(pos_x + " " + i);
			if (pos_x >= 0 && pos_x <= 800) {
				current_render.getCanvas().setTranslateX(pos_x);
			}
			if (pos_x >= 800) {
				gamePlayScreen.ivCriTap.setVisible(false);
				gamePlayScreen.ivPerfectTap.setVisible(false);
				gamePlayScreen.ivGreatTap.setVisible(false);
				gamePlayScreen.ivGoodTap.setVisible(false);
				gamePlayScreen.ivMissTap.setVisible(false);
			}
			if (pos_x >= 800) {
				gamePlayScreen.getChildren().remove(current_render.getCanvas());
				render.remove(current_render);
			}
		}
	}

	public void run() {

		mediaPlayer.play();
		this.start();
	}

	public void judge(KeyEvent e) {

		int idx = musicChart.getCurrentNoteIdx();
		double current_tapped_time = (System.nanoTime() - startTime) / NANO;
		Note current_note = musicChart.getChart().get(idx);
		double judge_time = idx * musicChart.getDelayPerHit();
		System.out.println(
				idx + " " + judge_time + " " + current_tapped_time + " " + mediaPlayer.getCurrentTime().toSeconds());
		if (current_note.getType() == 1 && e.getCode() == current_note.getDirection()) {
			double lower_bound = judge_time - 0.025;
			double upper_bound = judge_time + 0.025;
			if (current_tapped_time >= lower_bound && current_tapped_time <= upper_bound) {
				System.out.println("CriTical - PerFect~~~");
				gamePlayScreen.ivCriTap.setVisible(true);
			} else if (current_tapped_time >= lower_bound - 0.075 && current_tapped_time <= upper_bound + 0.075) {
				System.out.println("PerFect~~~");
				gamePlayScreen.ivPerfectTap.setVisible(true);
			} else if (current_tapped_time >= lower_bound - 0.125 && current_tapped_time <= upper_bound + 0.125) {
				System.out.println("Great~~~");
				gamePlayScreen.ivGreatTap.setVisible(true);
			} else if (current_tapped_time >= lower_bound - 0.25 && current_tapped_time <= upper_bound + 0.25) {
				System.out.println("Good~~~");
				gamePlayScreen.ivGoodTap.setVisible(true);
			}

		}

	}

	public static double getMediaStartTime() {
		return mediaStartTime;
	}

	public double getOffset() {
		return offset;
	}

	public static void setOffset(double offsett) {
		offset = offsett;
	}

	public double getSpeed() {
		return speed;
	}

	public MusicChart getMusicChart() {
		return musicChart;
	}

	public File getFilestring() {
		return filestring;
	}

	public Media getFile() {
		return file;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;

	}

	public static void setStartTime(double starttime) {
		startTime = starttime;
	}

}
