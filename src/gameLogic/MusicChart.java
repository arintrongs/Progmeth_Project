package gameLogic;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.KeyCode;

public class MusicChart {
	private ArrayList<Note> chart;
	private String songname;
	private Double bpm;
	private Integer notesPerBar;
	private Double hitPerSecond;
	private Double delayPerHit;
	private double mediaStartTime;
	private double duration;
	public static int currentNoteIdx;
	public static final double NANO = 1000000000.0;
	private Note current_note;
	private double offset;

	public MusicChart(String name, Double bpm, int notesperbar, double duration) {
		this.currentNoteIdx = 0;
		this.songname = name;
		this.notesPerBar = notesperbar;
		this.bpm = bpm * notesperbar / 4;
		hitPerSecond = this.bpm / 60;
		delayPerHit = 1 / hitPerSecond;
		chart = new ArrayList<>();
		this.duration = duration;
		addChart();
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public double getMediaStartTime() {
		return mediaStartTime;
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

	public void setMediaStartTime(double mediaStartTime) {
		this.mediaStartTime = mediaStartTime;
	}

	public void addChart() {
		ArrayList<KeyCode> direction = new ArrayList<>();
		direction.add(KeyCode.UP);
		direction.add(KeyCode.DOWN);
		direction.add(KeyCode.LEFT);
		direction.add(KeyCode.RIGHT);

		int noteCount = (int) (duration / delayPerHit);
		System.out.println("Note count : " + noteCount + " " + duration + " " + delayPerHit);
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
