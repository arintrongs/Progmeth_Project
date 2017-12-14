package gameLogic;

import java.util.ArrayList;

import javafx.application.Platform;
import model.Boss;
import model.Hero;
import sharedObject.ThreadHolder;

public class SkillUpdater extends Thread {

	private static ArrayList<Thread> skills;
	private boolean stillActivate = false;

	public void run() {
		skills = new ArrayList<>();
		stillActivate = false;
		activateSkills();
		try {
			while (true) {
				Thread.sleep(150);
				if (GameManager.getCurrentMode().compareTo("Boss") == 0) {
					for (Hero i : GameManager.getHeroes()) {
						if (i.getIsSkillActivated() == false) {
							Platform.runLater(() -> {
								i.activate();
							});
						}
					}
					if (((Boss) GameManager.getCurrentMon()).getisActivated() == false) {
						Platform.runLater(() -> {
							((Boss) GameManager.getCurrentMon()).activate();
						});
					}

				} else {
					if (GameManager.getCurrentCha().getIsSkillActivated() == false)
						Platform.runLater(() -> {
							GameManager.getCurrentCha().activate();
						});
				}

			}
		} catch (InterruptedException e) {
			this.interrupt();
		}

	}

	public void activateSkills() {
		Thread thread = new Thread(() -> {
			int idx = 0, check = 0;
			try {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						break;
					}
					while (idx < skills.size()) {
						skills.get(idx).start();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							break;
						}
						idx++;

					}
				}
			} catch (Exception e) {
				Thread.interrupted();
			}

		});
		thread.start();
		ThreadHolder.threads.add(thread);

	}

	public static ArrayList<Thread> getSkills() {
		return skills;
	}
}
