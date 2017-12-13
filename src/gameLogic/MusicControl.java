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
import model.Boss;
import model.Hero;
import model.JudgeStyle;
import scene.GamePlayScreen;
import scene.ResultScreen;
import scene.SceneManager;
import sharedObject.ThreadHolder;

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
	private static final ArrayList<Double> BPM_LIST = new ArrayList<>();
	private Note current_note;
	private int idx, offsetCheck = 0, judges = 0, isSleep = 0, combo = 0;
	private static int currentCombo = 0, toRenderIdx = 0;
	public static final double NANO = 1000000000.0;
	private static boolean guaranteePerfect = false;
	private DamageUpdater damageUpdater;
	private boolean isComboBreak = false;
	private SkillUpdater skillUpdater;
	private Random random = new Random();
	public int check = 0, musicNumber;
	private double duration;

	public MusicControl(Pane pane) {
		generateBPMList();
		randomMusic();
		mediaPlayer = new MediaPlayer(file);
		this.gamePlayScreen = (GamePlayScreen) pane;
		this.judgeResult = new ArrayList<>(Collections.nCopies(5, 0));
		this.render = new ArrayList<>();
		this.notes = new ArrayList<>();
		this.damageUpdater = new DamageUpdater(gamePlayScreen);
		this.skillUpdater = new SkillUpdater();
		this.currentCombo = 0;
		this.toRenderIdx = 0;

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
		if (current_time >= duration + 2 && GameManager.isGameFinished() == false
				&& GameManager.getCurrentMon().getCurrentHp() > 0 && GameManager.getCurrentMon() instanceof Boss) {
			GameManager.setIsGameFinished(true);
			GamePlayScreen.showFail();
		}
		if (current_time >= duration + 4) {
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
		if (idx < musicChart.getChart().size()) {
			double current_tapped_time = (System.nanoTime() - startTime) / NANO;
			Note current_note = musicChart.getChart().get(idx);
			double judge_time = idx * musicChart.getDelayPerHit();
			double lower_bound = judge_time - 0.015;
			double upper_bound = judge_time + 0.015;
			if (current_note.getType() == 1 && e.getCode() == current_note.getDirection()
					&& current_tapped_time >= judge_time - 2.0 && current_tapped_time <= judge_time + 2.0) {
				if (current_tapped_time >= lower_bound && current_tapped_time <= upper_bound) {
					judgeResult.set(0, judgeResult.get(0) + 1);
					judges = 0;

				} else if (current_tapped_time >= lower_bound - 0.03 && current_tapped_time <= upper_bound + 0.03) {
					judgeResult.set(1, judgeResult.get(1) + 1);
					judges = 1;

				} else if (current_tapped_time >= lower_bound - 0.10 && current_tapped_time <= upper_bound + 0.10) {
					if (guaranteePerfect == true) {
						judgeResult.set(1, judgeResult.get(1) + 1);
						judges = 1;
					} else {
						judgeResult.set(2, judgeResult.get(2) + 1);
						judges = 2;
					}

				} else if (current_tapped_time >= lower_bound - 0.15 && current_tapped_time <= upper_bound + 0.15) {
					if (guaranteePerfect == true) {
						judgeResult.set(1, judgeResult.get(1) + 1);
						judges = 1;
					} else {
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
			}
		}

	}

	public void end() {

		mediaPlayer.stop();
		this.stop();
		damageUpdater.interrupt();
		skillUpdater.interrupt();
		for (Thread i : ThreadHolder.threads) {
			i.interrupt();
		}
		ThreadHolder.threads.clear();
		for (Hero i : GameManager.getHeroes())
			i.setIsSkillActive(false);
		Thread nextScene = new Thread(() -> {
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

	public void setDuration(double x) {
		this.duration = x;
	}

	public void setMusicChart() {
		if (GameManager.getCurrentMode().compareTo("Boss") == 0)
			musicChart = new MusicChart("Good Night Bad Luck", 285.0, 4, duration);
		else
			musicChart = new MusicChart(Integer.toString(musicNumber), BPM_LIST.get(musicNumber), 4, duration);
		for (int i = 0; i < musicChart.getChart().size(); i++) {
			current_note = musicChart.getChart().get(i);
			if (current_note.getType() == 1) {
				current_note.setStartTime(i * this.musicChart.getDelayPerHit() - 2.0);
				this.notes.add(current_note);
			}
		}
	}

	public void randomMusic() {
		try {
			if (GameManager.getCurrentMode().compareTo("Boss") == 0) {
				file = new Media(ClassLoader.getSystemResource("boss.wav").toURI().toString());
			} else {
				musicNumber = random.nextInt(4);
				file = new Media(ClassLoader.getSystemResource(musicNumber + ".wav").toURI().toString());
			}
		} catch (Exception e) {

		}
	}

	public void generateBPMList() {
		BPM_LIST.add(175.0);
		BPM_LIST.add(146.0);
		BPM_LIST.add(185.0);
		BPM_LIST.add(200.0);
	}
}
