package gameLogic;

import java.io.File;
import java.net.URISyntaxException;
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
import sharedObject.ThreadHolder;
import window.SceneManager;

public class MusicControl extends AnimationTimer {

	private double speed, temps, startTime;
	private MusicChart musicChart;
	private File filestring;
	private Media file;
	private MediaPlayer mediaPlayer;
	private GamePlayScreen gamePlayScreen;
	private ArrayList<Note> render;
	private static ArrayList<Note> notes;
	private static ArrayList<Integer> judgeResult;
	private Note current_note;
	private int idx, offsetCheck = 0, judges = 0, isSleep = 0, combo = 0;
	private static int currentCombo = 0, toRenderIdx = 0;
	public static final double NANO = 1000000000.0;
	private static boolean guaranteePerfect = false;

	private DamageUpdater damageUpdater;
	private boolean isComboBreak = false;
	private SkillUpdater skillUpdater;
	private Random random = new Random();
	public int check = 0;

	public MusicControl(Pane pane) {

		musicChart = new MusicChart("test2", 146.0, 8);
		musicChart = new MusicChart("test", 175.0, 4);
		try {
			file = new Media(ClassLoader.getSystemResource("test.wav").toURI().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		mediaPlayer = new MediaPlayer(file);

		this.gamePlayScreen = (GamePlayScreen) pane;
		this.judgeResult = new ArrayList<>(Collections.nCopies(5, 0));
		this.render = new ArrayList<>();
		this.notes = new ArrayList<>();
		this.damageUpdater = new DamageUpdater(gamePlayScreen);
		this.skillUpdater = new SkillUpdater();
		this.currentCombo = 0;
		this.toRenderIdx = 0;

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
			skillUpdater.start();
			ThreadHolder.threads.add(damageUpdater);
			ThreadHolder.threads.add(skillUpdater);
		}
		if (current_time >= file.getDuration().toSeconds() + 2) {
			end();
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

			if (pos_x >= 0 && pos_x <= 750) {
				current_render.getCanvas().setTranslateX(pos_x);
			}
			if (pos_x >= 750) {
				System.out.println(check++);
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

		System.out.println(notes.size());
		this.start();

	}

	public void judge(KeyEvent e) {

		int idx = musicChart.getCurrentNoteIdx();
		if (idx < musicChart.getChart().size()) {
			double current_tapped_time = (System.nanoTime() - startTime) / NANO;
			Note current_note = musicChart.getChart().get(idx);
			double judge_time = idx * musicChart.getDelayPerHit();
			double lower_bound = judge_time - 0.015;
			double upper_bound = judge_time + 0.015;
			if (current_note.getType() == 1 && e.getCode() == current_note.getDirection()
					&& current_tapped_time >= judge_time - 2.0 && current_tapped_time <= judge_time + 2.0) {
				if (current_tapped_time >= lower_bound && current_tapped_time <= upper_bound) {
					System.out.println("CriTical - PerFect~~~");
					judgeResult.set(0, judgeResult.get(0) + 1);
					judges = 0;

				} else if (current_tapped_time >= lower_bound - 0.03 && current_tapped_time <= upper_bound + 0.03) {
					System.out.println("PerFect~~~");
					judgeResult.set(1, judgeResult.get(1) + 1);
					judges = 1;

				} else if (current_tapped_time >= lower_bound - 0.10 && current_tapped_time <= upper_bound + 0.10) {
					if (guaranteePerfect == true) {
						System.out.println("PerFect~~~");
						judgeResult.set(1, judgeResult.get(1) + 1);
						judges = 1;
					} else {
						System.out.println("Great~~~");
						judgeResult.set(2, judgeResult.get(2) + 1);
						judges = 2;
					}

				} else if (current_tapped_time >= lower_bound - 0.15 && current_tapped_time <= upper_bound + 0.15) {
					if (guaranteePerfect == true) {
						System.out.println("PerFect~~~");
						judgeResult.set(1, judgeResult.get(1) + 1);
						judges = 1;
					} else {
						System.out.println("Good~~~");
						judgeResult.set(3, judgeResult.get(3) + 1);
						judges = 3;
					}
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

	}

	public void end() {
		mediaPlayer.stop();
		this.stop();
		damageUpdater.interrupt();
		skillUpdater.interrupt();

		Thread nextScene = new Thread(() -> {
			try {
				damageUpdater.join();
				skillUpdater.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SceneManager.gotoSceneOf(new ResultScreen());
		});
		nextScene.start();
		ThreadHolder.threads.add(nextScene);

	}

	public static void setJudgeResult() {
		judgeResult = new ArrayList<>(Collections.nCopies(5, 0));

	}

	public static ArrayList<Integer> getJudgeResult() {
		return judgeResult;
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

	public static ArrayList<Note> getNotes() {
		return notes;
	}

	public static int gettoRenderIdx() {
		return toRenderIdx;
	}

	public static void setIsGuarantee(boolean x) {
		guaranteePerfect = x;
	}
}
