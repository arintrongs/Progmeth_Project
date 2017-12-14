package model;

import gameLogic.GameManager;
import gameLogic.SkillUpdater;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.GamePlayScreen;
import sharedObject.ThreadHolder;

public class Priest extends Hero {

	private Image heroImg, bg;
	private Thread skill;

	public Priest(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Priest1.png");
		bg = new Image("bg4.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.clearRect(0, 0, 800, 800);
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		if (GameManager.getCurrentMode().compareTo("Boss") == 0) {
			int rnd = random.nextInt(3);
			if (rnd == 1 && isSkillActivated == false) {
				isSkillActivated = true;
				skill = new Thread(() -> {
					Platform.runLater(() -> {
						GameManager.setCurrentCha(name, false);
						GamePlayScreen.showSkillActivated();
					});
					Boss boss = ((Boss) GameManager.getCurrentMon());
					if (boss.getisActivated() == true && boss.getIsSilence() == false)
						boss.deactivate();
					boss.setSilence(true);
					System.out.println("Silence!!!!");
					try {
						Thread.sleep(5000);
						deactivate();
					} catch (Exception e) {
					}
				});
				ThreadHolder.instance.getThreads().add(skill);
				SkillUpdater.getSkills().add(skill);
			} else {
				System.out.println("Priest Fail!!");
				this.isSkillActivated = true;
				skill = new Thread(() -> {
					try {

						Thread.sleep(10000);
						this.isSkillActivated = false;
					} catch (Exception e) {
					}
				});
				ThreadHolder.instance.getThreads().add(skill);
				SkillUpdater.getSkills().add(skill);
			}
		}
	}

	public void deactivate() {

		System.out.println("Cancel Silence!!!");
		((Boss) GameManager.getCurrentMon()).setSilence(false);
		skill = new Thread(() -> {
			try {
				Thread.sleep(10000);
				isSkillActivated = false;
			} catch (Exception e) {
			}
		});
		ThreadHolder.instance.getThreads().add(skill);
		SkillUpdater.getSkills().add(skill);
	}
}
