package model;

import gameLogic.GameManager;
import gameLogic.MusicControl;
import gameLogic.SkillUpdater;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.GamePlayScreen;
import sharedObject.ThreadHolder;

public class SpellCaster extends Hero {

	private Image heroImg, bg;
	private Thread skill;

	public SpellCaster(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("SpellCaster1.png");
		bg = new Image("bg4.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.clearRect(0, 0, 300, 300);
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = this.random.nextInt(4);
		if (rnd == 1 && isSkillActivated == false) {
			this.isSkillActivated = true;
			skill = new Thread(() -> {
				Platform.runLater(() -> {
					GameManager.setCurrentCha(name, false);
					GamePlayScreen.showSkillActivated();
				});

				MusicControl.setIsGuarantee(true);
				System.out.println("SpellCaster Skill Activated!!");
				try {
					Thread.sleep(5000);
					deactivate();
				} catch (Exception e) {
				}
			});
			ThreadHolder.instance.getThreads().add(skill);
			SkillUpdater.getSkills().add(skill);
		} else {
			this.isSkillActivated = true;
			System.out.println("SpellCaster Fail!!");
			skill = new Thread(() -> {
				try {

					Thread.sleep(15000);
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

			MusicControl.setIsGuarantee(false);
			System.out.println("SpellCaster Skill Deactivated!!");
			skill = new Thread(() -> {
				try {
					Thread.sleep(15000);
					this.isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.instance.getThreads().add(skill);
			SkillUpdater.getSkills().add(skill);

		}
	}

}
