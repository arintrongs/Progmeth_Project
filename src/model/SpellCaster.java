package model;

import gameLogic.GameManager;
import gameLogic.MusicControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sharedObject.ThreadHolder;

public class SpellCaster extends Hero {

	private Image heroImg, bg;

	public SpellCaster(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("SpellCaster1.png");
		bg = new Image("bg4.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = this.random.nextInt(4);
		if (rnd == 1 && isSkillActivated == false) {
			GameManager.setCurrentCha(name);
			this.isSkillActivated = true;
			MusicControl.setIsGuarantee(true);
			System.out.println("SpellCaster Skill Activated!!");
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
			System.out.println("SpellCaster Fail!!");
			Thread skill = new Thread(() -> {
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

	public void deactivate() {
		if (this.isSkillActivated == true) {

			MusicControl.setIsGuarantee(false);
			System.out.println("SpellCaster Skill Deactivated!!");
			Thread skill = new Thread(() -> {
				try {
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
