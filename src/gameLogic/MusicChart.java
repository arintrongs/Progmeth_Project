package gameLogic;

import java.util.ArrayList;

public class MusicChart {
	private ArrayList<Note> chart;
	private String name;
	private Double bpm;
	private Integer notesPerBar;
	private Double hitPerSecond;
	private Double delayPerHit;
	private long mediaStartTime;
	private int currentNoteIdx;

	public MusicChart(String name, Double bpm, int notesperbar) {
		// TODO Auto-generated constructor stub
		this.currentNoteIdx = 0;
		this.name = name;
		this.bpm = bpm;
		this.notesPerBar = notesperbar;
		hitPerSecond = bpm / 60;
		delayPerHit = 1 / hitPerSecond;
		chart = new ArrayList<>();

		chart.add(new Note(0, null));
		chart.add(new Note(0, null));
		chart.add(new Note(0, null));
		chart.add(new Note(0, null));

	}

	public long getMediaStartTime() {
		return mediaStartTime;
	}

	public ArrayList<Note> getChart() {
		return chart;
	}

	public void setChart(ArrayList<Note> chart) {
		this.chart = chart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
