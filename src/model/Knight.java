package model;

import gameLogic.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sharedObject.ThreadHolder;

public class Knight extends Hero {

	private Image heroImg;

	public Knight(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Knight1.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = this.random.nextInt(2);
		if (rnd == 1 && isSkillActivated == false) {
			GameManager.setCurrentCha(name);
			this.isSkillActivated = true;
			this.atk *= this.growthRateAtk;
			System.out.println("Knight Skill Activated!!");
			Thread skill = new Thread(() -> {
				try {
					Thread.sleep(5000);
					deactivate();
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			skill.start();
		} else {
			System.out.println("Knight Fail!!");
			Thread skill = new Thread(() -> {
				try {
					this.isSkillActivated = true;
					Thread.sleep(10000);
					this.isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			skill.start();
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true) {

			this.atk = this.originalAtk;
			System.out.println("Knight Skill Deactivated!!");
			Thread skill = new Thread(() -> {
				try {
					Thread.sleep(10000);
					this.isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			skill.start();

		}

	}
}
