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
	Thread skill;

	public Priest(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Priest1.png");
		bg = new Image("bg4.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		if (GameManager.getCurrentMode().compareTo("Boss") == 0) {
			int rnd = random.nextInt(2);
			if (isSkillActivated == false) {
				isSkillActivated = true;
				skill = new Thread(() -> {
					Platform.runLater(() -> {
						GameManager.setCurrentCha(name);
						GamePlayScreen.showSkillActivated();
					});
					Boss boss = ((Boss) GameManager.getCurrentMon());
					if (boss.getisActivated() == true)
						boss.deactivate();
					boss.setSilence(true);
					System.out.println("Silence!!!!");
					try {
						Thread.sleep(5000);
						deactivate();
					} catch (Exception e) {
					}
				});
				ThreadHolder.threads.add(skill);
				SkillUpdater.getSkills().add(skill);
			} else {
				System.out.println("Priest Fail!!");
				skill = new Thread(() -> {
					try {
						this.isSkillActivated = true;
						Thread.sleep(15000);
						this.isSkillActivated = false;
					} catch (Exception e) {
					}
				});
				ThreadHolder.threads.add(skill);
				skill.start();
			}
		}
	}

	public void deactivate() {

		System.out.println("Cancel Silence!!!");
		((Boss) GameManager.getCurrentMon()).setSilence(false);
		skill = new Thread(() -> {
			try {
				Thread.sleep(15000);
				isSkillActivated = false;
			} catch (Exception e) {
			}
		});
		ThreadHolder.threads.add(skill);
		skill.start();
	}
}
