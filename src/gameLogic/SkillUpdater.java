package gameLogic;

import javafx.application.Platform;
import model.Boss;
import model.Hero;

public class SkillUpdater extends Thread {

	public SkillUpdater() {

	}

	public void run() {

		try {
			while (true) {
				Thread.sleep(150);
				Platform.runLater(() -> {
					if (GameManager.getCurrentMode().compareTo("Boss") == 0) {
						for (Hero i : GameManager.getHeroes()) {
							if (i.getIsSkillActivated() == false)
								i.activate();
						}
						if (((Boss) GameManager.getCurrentMon()).getisActivated() == false)
							((Boss) GameManager.getCurrentMon()).activate();

					} else {
						if (GameManager.getCurrentCha().getIsSkillActivated() == false)
							GameManager.getCurrentCha().activate();
					}

				});
			}
		} catch (InterruptedException e) {
			this.interrupt();
		}

	}
}
