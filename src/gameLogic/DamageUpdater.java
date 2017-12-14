package gameLogic;

import java.util.ArrayList;

import javafx.application.Platform;
import scene.GamePlayScreen;

public class DamageUpdater extends Thread {

	private ArrayList<Integer> judgeResult;

	public void run() {
		try {
			while (true) {
				this.sleep(1000);
				this.judgeResult = MusicControl.getJudgeResult();
				Platform.runLater(() -> {
					GameManager.update(this.judgeResult, GamePlayScreen.instance);
				});
				MusicControl.resetJudgeResult();
			}
		} catch (InterruptedException e) {
			this.judgeResult = MusicControl.getJudgeResult();
			Platform.runLater(() -> {
				GameManager.update(judgeResult, GamePlayScreen.instance);
			});
			MusicControl.resetJudgeResult();
			this.interrupt();
		}

	}
}
