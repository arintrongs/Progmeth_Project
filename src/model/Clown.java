package model;

import java.util.ArrayList;

import gameLogic.GameManager;
import gameLogic.MusicControl;
import gameLogic.Note;
import gameLogic.SkillUpdater;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import scene.GamePlayScreen;
import sharedObject.ThreadHolder;

public class Clown extends Hero {
	private Image heroImg, bg;
	ArrayList<KeyCode> direction;
	Thread skill;

	public Clown(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Clown1.png");
		bg = new Image("bg4.png");
		direction = new ArrayList<>();
		direction.add(KeyCode.LEFT);
		direction.add(KeyCode.RIGHT);
		direction.add(KeyCode.UP);
		direction.add(KeyCode.DOWN);

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.clearRect(0, 0, 300, 300);
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = this.random.nextInt(2);
		int d = this.random.nextInt(4);
		int count = 5 + this.random.nextInt(11);

		ArrayList<Note> notes = MusicControl.getfirstTypeNotes();
		if (rnd == 1 && isSkillActivated == false) {
			this.isSkillActivated = true;
			skill = new Thread(() -> {

				final int currentidx = MusicControl.gettoRenderIdx();
				for (int i = 0; i < count; i++) {
					final int idx = i + currentidx;
					Platform.runLater(() -> {
						try {
							notes.get(idx).setDirection(direction.get(d));
						} catch (Exception e) {
						}
					});
				}
				Platform.runLater(() -> {
					GameManager.setCurrentCha(name, 1);
					GamePlayScreen.showSkillActivated();
				});
				deactivate();
				System.out.println("Clown Skill Activated!!");
			});
			SkillUpdater.getSkills().add(skill);
		} else {
			System.out.println("Clown Fail!!");
			this.isSkillActivated = true;
			skill = new Thread(() -> {
				try {

					Thread.sleep(13000);
					this.isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			SkillUpdater.getSkills().add(skill);
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true) {

			System.out.println("Clown Skill Deactivated!!");
			Thread skill = new Thread(() -> {
				try {
					Thread.sleep(13000);
					this.isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			SkillUpdater.getSkills().add(skill);

		}
	}
}
