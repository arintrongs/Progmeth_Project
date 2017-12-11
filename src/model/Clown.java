package model;

import java.util.ArrayList;

import gameLogic.MusicControl;
import gameLogic.Note;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Clown extends Hero {
	private Image heroImg, bg;
	ArrayList<KeyCode> direction;

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
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = this.random.nextInt(2);
		int d = this.random.nextInt(4);
		int count = 5 + this.random.nextInt(11);
		ArrayList<Note> notes = MusicControl.getNotes();
		if (rnd == 1 && isSkillActivated == false) {
			this.isSkillActivated = true;
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
			System.out.println("Skill Activated!!");
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true) {
			new Thread(() -> {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
				}
				this.isSkillActivated = false;
				System.out.println("Skill Deactivated!!");
			}).start();
		}
	}
}
