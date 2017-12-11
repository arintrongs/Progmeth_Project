package gameLogic;

import javafx.application.Platform;
import model.Boss;

public class SkillUpdater extends Thread {

	public SkillUpdater() {

	}

	public void run() {

		try {
			while (true) {
				this.sleep(5000);
				Platform.runLater(() -> {
					if (GameManager.getCurrentMode().compareTo("Boss") == 0) {
						((Boss) GameManager.getCurrentMon()).activate();

					}
					GameManager.getCurrentCha().activate();
					GameManager.getCurrentCha().deactivate();
				});
			}
		} catch (InterruptedException e) {
			this.interrupt();
		}

	}
}
