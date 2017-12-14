package gameLogic;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.KeyCode;

public class MusicChart {
	private ArrayList<Note> chart;
	private Double bpm;
	private Integer notesPerBar;
	private Double hitPerSecond;
	private Double delayPerHit;
	private Double duration;
	private static int currentNoteIdx;
	public static final double NANO = 1000000000.0;

	public MusicChart(Double bpm, int notesperbar, double duration) {
		this.currentNoteIdx = 0;
		this.notesPerBar = notesperbar;
		this.bpm = bpm * notesperbar / 4;
		hitPerSecond = this.bpm / 60;
		delayPerHit = 1 / hitPerSecond;
		chart = new ArrayList<>();
		this.duration = duration;
		generateChart();
	}

	public ArrayList<Note> getChart() {
		return chart;
	}

	public void setChart(ArrayList<Note> chart) {
		this.chart = chart;
	}

	public Double getBpm() {
		return bpm;
	}

	public void setBpm(Double bpm) {
		this.bpm = bpm;
	}

	public Integer getNotesPerBar() {
		return notesPerBar;
	}

	public void setNotesPerBar(Integer notesPerBar) {
		this.notesPerBar = notesPerBar;
	}

	public Double getHitPerSecond() {
		return hitPerSecond;
	}

	public void setHitPerSecond(Double hitPerSecond) {
		this.hitPerSecond = hitPerSecond;
	}

	public Double getDelayPerHit() {
		return delayPerHit;
	}

	public void setDelayPerHit(Double delayPerHit) {
		this.delayPerHit = delayPerHit;
	}

	public int getCurrentNoteIdx() {
		return currentNoteIdx;
	}

	public void setCurrentNoteIdx(int currentNoteIdx) {
		this.currentNoteIdx = currentNoteIdx;
	}

	public void generateChart() {
		ArrayList<KeyCode> direction = new ArrayList<>();
		direction.add(KeyCode.UP);
		direction.add(KeyCode.DOWN);
		direction.add(KeyCode.LEFT);
		direction.add(KeyCode.RIGHT);
		int noteCount = (int) (duration / delayPerHit);
		Random random = new Random();

		for (int i = 0; i < notesPerBar; i++) {
			chart.add(new Note(0, KeyCode.LEFT));
		}
		for (int i = 0; i < noteCount; i++) {
			int d = random.nextInt(4);
			int c = random.nextInt(2);
			chart.add(new Note(c, direction.get(d)));
		}

	}
}
