package model;

import java.util.Random;

import gameLogic.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Priest extends Hero {
	Random random = new Random();
	private Image heroImg, bg;

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
			if (rnd == 1) {
				Boss boss = ((Boss) GameManager.getCurrentMon());
				if (boss.getisActivated() == true)
					boss.deactivate();
				boss.setSilence(true);
				System.out.println("Silence!!!!");
				new Thread(() -> {
					try {
						Thread.sleep(5000);
						deactivate();
					} catch (Exception e) {
					}
				});
			}
		}
	}

	public void deactivate() {
		System.out.println("Cancel Silence!!!");
		((Boss) GameManager.getCurrentMon()).setSilence(false);
	}
}
