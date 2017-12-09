package gameLogic;

import javafx.application.Platform;

public class SkillUpdater extends Thread {

	public SkillUpdater() {

	}

	public void run() {

		try {
			while (true) {
				this.sleep(5000);
				Platform.runLater(() -> {
					GameManager.getCurrentCha().activate();
					GameManager.getCurrentCha().deactivate();
				});
			}
		} catch (InterruptedException e) {
			this.interrupt();
		}

	}
}
