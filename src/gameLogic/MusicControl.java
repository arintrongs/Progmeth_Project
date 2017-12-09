package gameLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.JudgeStyle;
import scene.GamePlayScreen;
import scene.ResultScreen;
import window.SceneManager;

public class MusicControl extends AnimationTimer {

	private double speed, temps, startTime;
	private MusicChart musicChart;
	private File filestring;
	private Media file;
	private GamePlayScreen gamePlayScreen;
	private ArrayList<Note> render, notes;
	private static ArrayList<Integer> judgeResult;
	private Note current_note;
	private int idx, toRenderIdx = 0, offsetCheck = 0, judges = 0, isSleep = 0, combo = 0;
	private static int currentCombo = 0;
	public static final double NANO = 1000000000.0;
	private MediaPlayer mediaPlayer, knock;
	private DamageUpdater damageUpdater;
	private boolean isComboBreak = false;
	Random random = new Random();

	public MusicControl(Pane pane) {

		musicChart = new MusicChart("test2", 146.0, 4);
		filestring = new File("res/song/test2.wav");
		file = new Media(filestring.toURI().toString());
		mediaPlayer = new MediaPlayer(file);
		knock = new MediaPlayer(new Media((new File("res/song/Knock.wav")).toURI().toString()));

		this.gamePlayScreen = (GamePlayScreen) pane;
		this.judgeResult = new ArrayList<>(Collections.nCopies(5, 0));
		this.render = new ArrayList<>();
		this.notes = new ArrayList<>();
		this.damageUpdater = new DamageUpdater(judgeResult, gamePlayScreen);

		for (int i = 0; i < musicChart.getChart().size(); i++) {
			current_note = musicChart.getChart().get(i);
			if (current_note.getType() == 1) {
				current_note.setStartTime(i * this.musicChart.getDelayPerHit() - 2.0);
				this.notes.add(current_note);
			}
		}

	}

	@Override
	public void handle(long now) {
		double current_time = (now - startTime) / NANO;
		if (current_time >= musicChart.getNotesPerBar() * musicChart.getDelayPerHit() && offsetCheck == 0) {
			offsetCheck = 1;
			mediaPlayer.play();
			damageUpdater.start();
		}

		while (toRenderIdx < notes.size() && current_time >= notes.get(toRenderIdx).getStartTime()) {
			gamePlayScreen.getChildren().add(notes.get(toRenderIdx++).getCanvas());
		}
		if (current_time >= musicChart.currentNoteIdx * musicChart.getDelayPerHit() + 0.15) {
			musicChart.currentNoteIdx++;
		}
		current_note = (idx < notes.size()) ? notes.get(idx) : null;
		if (current_note != null && current_note.getStartTime() <= current_time && current_note.getType() == 1) {
			render.add(current_note);
			idx++;

		}
		for (int i = 0; i < render.size(); i++) {
			Note current_render = render.get(i);
			double pos_x = 665 * (current_time - current_render.getStartTime()) / 2.0;
			// System.out.println(pos_x + " " + i);
			if (pos_x >= 0 && pos_x <= 750) {
				current_render.getCanvas().setTranslateX(pos_x);
			}
			if (pos_x >= 750) {
				new JudgeStyle(4, gamePlayScreen).show();
				judgeResult.set(4, judgeResult.get(4) + 1);
				isComboBreak = true;
				currentCombo = 0;
				gamePlayScreen.getChildren().remove(current_render.getCanvas());
				render.remove(current_render);
				gamePlayScreen.getChildren().remove(gamePlayScreen.getCombo());
				gamePlayScreen.updateCombo();

			}
		}

	}

	public void run() {
		startTime = System.nanoTime();
		this.start();
	}

	public void judge(KeyEvent e) {

		int idx = musicChart.getCurrentNoteIdx();
		double current_tapped_time = (System.nanoTime() - startTime) / NANO;
		Note current_note = musicChart.getChart().get(idx);
		double judge_time = idx * musicChart.getDelayPerHit();
		double lower_bound = judge_time - 0.025;
		double upper_bound = judge_time + 0.025;
		System.out.println(idx + " " + judge_time + " " + current_tapped_time);
		if (current_note.getType() == 1 && e.getCode() == current_note.getDirection()
				&& current_tapped_time >= judge_time - 2.0 && current_tapped_time <= judge_time + 2.0) {
			if (current_tapped_time >= lower_bound && current_tapped_time <= upper_bound) {
				System.out.println("CriTical - PerFect~~~");
				judgeResult.set(0, judgeResult.get(0) + 1);
				judges = 0;

			} else if (current_tapped_time >= lower_bound - 0.075 && current_tapped_time <= upper_bound + 0.075) {
				System.out.println("PerFect~~~");
				judgeResult.set(1, judgeResult.get(1) + 1);
				judges = 1;

			} else if (current_tapped_time >= lower_bound - 0.125 && current_tapped_time <= upper_bound + 0.125) {
				System.out.println("Great~~~");
				judgeResult.set(2, judgeResult.get(2) + 1);
				judges = 2;

			} else if (current_tapped_time >= lower_bound - 0.2 && current_tapped_time <= upper_bound + 0.2) {
				System.out.println("Good~~~");
				judgeResult.set(3, judgeResult.get(3) + 1);
				judges = 3;

			}
			currentCombo++;
			musicChart.currentNoteIdx++;
			render.remove(current_note);
			gamePlayScreen.getChildren().remove(current_note.getCanvas());
			new JudgeStyle(judges, gamePlayScreen).show();
			gamePlayScreen.updateCombo();
			System.out.println(judgeResult);
		}

	}

	public void end() {
		mediaPlayer.stop();
		this.stop();
		damageUpdater.interrupt();
		SceneManager.gotoSceneOf(new ResultScreen());
		System.out.println(mediaPlayer.getStatus());
	}

	public static void setJudgeResult() {
		judgeResult = new ArrayList<>(Collections.nCopies(5, 0));
	}

	public MusicChart getMusicChart() {
		return musicChart;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;

	}

	public static int getCurrentCombo() {
		return currentCombo;
	}

}
