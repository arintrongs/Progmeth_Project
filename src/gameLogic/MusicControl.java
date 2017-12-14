package gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
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

	private static final ArrayList<Double> BPM_LIST = new ArrayList<>();
	public static final double NANO = 1000000000.0;
	private static ArrayList<Note> firstTypeNotes;
	private static ArrayList<Integer> judgeResult;
	private static boolean guaranteePerfect = false;
	private static int currentCombo = 0, toRenderIdx = 0;
	private double startTime;
	private MusicChart musicChart;
	private Media file;
	private MediaPlayer mediaPlayer;
	private ArrayList<Note> toRender;
	private Note currentNote;
	private int currentNoteIdx, offsetCheck = 0, judges = 0;
	private DamageUpdater damageUpdater;
	private boolean isComboBreak = false;
	private SkillUpdater skillUpdater;
	private Random random = new Random();
	public int musicNumber;
	private double duration;

	public MusicControl() {
		generateBPMList();
		randomMusic();
		mediaPlayer = new MediaPlayer(file);
		this.judgeResult = new ArrayList<>(Collections.nCopies(5, 0));
		this.toRender = new ArrayList<>();
		this.firstTypeNotes = new ArrayList<>();
		this.damageUpdater = new DamageUpdater();
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
		while (toRenderIdx < firstTypeNotes.size() && current_time >= firstTypeNotes.get(toRenderIdx).getStartTime()) {
			GamePlayScreen.instance.getChildren().add(firstTypeNotes.get(toRenderIdx++).getCanvas());
		}
		if (current_time >= musicChart.getCurrentNoteIdx() * musicChart.getDelayPerHit() + 0.15) {
			musicChart.setCurrentNoteIdx(musicChart.getCurrentNoteIdx() + 1);
		}
		currentNote = (currentNoteIdx < firstTypeNotes.size()) ? firstTypeNotes.get(currentNoteIdx) : null;
		if (currentNote != null && currentNote.getStartTime() <= current_time && currentNote.getType() == 1) {
			toRender.add(currentNote);
			currentNoteIdx++;

		}

		for (int i = 0; i < toRender.size(); i++) {
			Note current_render = toRender.get(i);
			double pos_x = 665 * (current_time - current_render.getStartTime()) / 2.0;

			if (pos_x >= 0 && pos_x <= 750) {
				current_render.getCanvas().setTranslateX(pos_x);
			}
			if (pos_x >= 750) {
				new JudgeStyle(4, GamePlayScreen.instance).show();
				judgeResult.set(4, judgeResult.get(4) + 1);
				isComboBreak = true;
				currentCombo = 0;
				GamePlayScreen.instance.getChildren().remove(current_render.getCanvas());
				toRender.remove(current_render);
				GamePlayScreen.instance.getChildren().remove(GamePlayScreen.instance.getCombo());
				GamePlayScreen.instance.updateCombo();

			}
		}

	}

	public void run() {
		startTime = System.nanoTime();
		this.start();
	}

	public void judge(KeyEvent e) {

		int currentNoteIdx = musicChart.getCurrentNoteIdx();
		if (currentNoteIdx < musicChart.getChart().size()) {
			double current_tapped_time = (System.nanoTime() - startTime) / NANO;
			Note currentNote = musicChart.getChart().get(currentNoteIdx);
			double judge_time = currentNoteIdx * musicChart.getDelayPerHit();
			double lower_bound = judge_time - 0.015;
			double upper_bound = judge_time + 0.015;
			if (currentNote.getType() == 1 && e.getCode() == currentNote.getDirection()
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
				musicChart.setCurrentNoteIdx(musicChart.getCurrentNoteIdx() + 1);
				toRender.remove(currentNote);
				GamePlayScreen.instance.getChildren().remove(currentNote.getCanvas());
				new JudgeStyle(judges, GamePlayScreen.instance).show();
				GamePlayScreen.instance.updateCombo();
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

	public static void resetJudgeResult() {
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

	public static ArrayList<Note> getfirstTypeNotes() {
		return firstTypeNotes;
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
			musicChart = new MusicChart(285.0, 4, duration);
		else
			musicChart = new MusicChart(BPM_LIST.get(musicNumber), 4, duration);
		for (int i = 0; i < musicChart.getChart().size(); i++) {
			currentNote = musicChart.getChart().get(i);
			if (currentNote.getType() == 1) {
				currentNote.setStartTime(i * this.musicChart.getDelayPerHit() - 2.0);
				this.firstTypeNotes.add(currentNote);
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