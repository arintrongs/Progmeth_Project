package model;

import gameLogic.GameManager;
import gameLogic.SkillUpdater;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.GamePlayScreen;
import sharedObject.ThreadHolder;

public class Knight extends Hero {

	private Image heroImg;
	private Thread skill;

	public Knight(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Knight1.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.clearRect(0, 0, 300, 300);
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = this.random.nextInt(2);

		if (rnd == 1 && isSkillActivated == false) {
			this.isSkillActivated = true;
			skill = new Thread(() -> {
				Platform.runLater(() -> {
					GameManager.setCurrentCha(name, false);
					GamePlayScreen.showSkillActivated();
				});

				this.atk *= this.GROWTH_RATE_ATK;
				System.out.println("Knight Skill Activated!!");
				try {
					Thread.sleep(5000);
					deactivate();
				} catch (Exception e) {
				}
			});
			ThreadHolder.instance.getThreads().add(skill);
			SkillUpdater.getSkills().add(skill);

		} else {
			System.out.println("Knight Fail!!");
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

	public void deactivate() {
		if (this.isSkillActivated == true) {

			this.atk = this.originalAtk;
			System.out.println("Knight Skill Deactivated!!");
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
