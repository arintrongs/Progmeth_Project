package gameLogic;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicControl extends Thread {

	private double mediaStartTime;
	private double offset;
	private double speed;
	private double startTime;
	private MusicChart musicChart;
	private File filestring;
	private Media file;
	private MediaPlayer mediaPlayer, knock;

	public MusicControl() {
		musicChart = new MusicChart("test2", 146.0, 1);
		filestring = new File("res/song/test2.wav");
		file = new Media(filestring.toURI().toString());
		mediaPlayer = new MediaPlayer(file);
		knock = new MediaPlayer(new Media((new File("res/song/Knock.wav")).toURI().toString()));

	}

	@Override
	public void run() {
		double starttime = System.nanoTime() / 1000000000.0;
		try {
			this.sleep(1000);
			for (int i = 0; i < 4; i++) {
				System.out.println(i);
				knock.play();
				this.sleep((long) (musicChart.getDelayPerHit() * 1000));
				knock.stop();
			}
			System.out.println(System.nanoTime() / 1000000000.0 - starttime + " " + musicChart.getDelayPerHit());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mediaPlayer.play();

	}

	public double getMediaStartTime() {
		return mediaStartTime;
	}

	public double getOffset() {
		return offset;
	}

	public double getSpeed() {
		return speed;
	}

	public double getStartTime() {
		return startTime;
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

}
