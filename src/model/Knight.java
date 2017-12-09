package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Knight extends Hero {
	Random random = new Random();
	private Image heroImg;

	public Knight(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Knight1.png");

	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = random.nextInt(2);
		if (rnd == 1) {
			this.isSkillActivated = true;
			this.atk *= this.growthRateAtk;
			System.out.println("Skill Activated!!" + this.atk);
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true) {
			new Thread(() -> {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
				}
				this.isSkillActivated = false;
				this.atk = this.originalAtk;
				System.out.println("Skill Deactivated!!" + this.atk);
			}).start();
		}

	}
}
