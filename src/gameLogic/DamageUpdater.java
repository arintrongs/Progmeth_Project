package gameLogic;

import java.util.ArrayList;

import javafx.application.Platform;
import scene.GamePlayScreen;

public class DamageUpdater extends Thread {

	private GamePlayScreen gamePlayScreen;
	private ArrayList<Integer> judgeResult;

	public DamageUpdater(GamePlayScreen gamePlayScreen) {
		this.gamePlayScreen = gamePlayScreen;
	}

	public void run() {
		try {
			while (true) {
				this.sleep(1000);
				this.judgeResult = MusicControl.getJudgeResult();
				Platform.runLater(() -> {
					GameManager.update(this.judgeResult, gamePlayScreen);
				});
				MusicControl.setJudgeResult();
			}
		} catch (InterruptedException e) {
			this.judgeResult = MusicControl.getJudgeResult();
			Platform.runLater(() -> {
				GameManager.update(judgeResult, gamePlayScreen);
			});
			MusicControl.setJudgeResult();
			this.interrupt();
		}

	}
}
