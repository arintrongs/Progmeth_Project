package gameLogic;

import java.util.ArrayList;

import javafx.application.Platform;
import scene.GamePlayScreen;

public class DamageUpdater extends Thread {

	private ArrayList<Integer> judgeResult;
	private GamePlayScreen gamePlayScreen;

	public DamageUpdater(ArrayList<Integer> judgeResult, GamePlayScreen gamePlayScreen) {
		this.gamePlayScreen = gamePlayScreen;
		this.judgeResult = judgeResult;
	}

	public void run() {
		try {
			while (true) {
				this.sleep(5000);
				Platform.runLater(() -> {
					GameManager.update(judgeResult, gamePlayScreen);
				});
				MusicControl.setJudgeResult();
			}
		} catch (InterruptedException e) {
			this.interrupt();
		}

	}
}
