package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Priest extends Hero {
	Random random = new Random();
	private Image heroImg, bg;
	// private ImageView ivHero,ivBg;

	public Priest(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Priest1.png");
		bg = new Image("bg4.png");
		/*
		 * ivHero = new ImageView(heroImg); ivHero.setFitWidth(heroImg.getWidth() / 3);
		 * ivHero.setFitHeight(heroImg.getHeight() / 3); ivBg.setImage(bg);
		 * ivBg.setFitWidth(bg.getWidth()); ivBg.setFitHeight(bg.getHeight());
		 */

	}

	public void draw(GraphicsContext gc, double x, double y) {
		/*
		 * ivBg.setTranslateX(0); ivBg.setTranslateY(0); ivHero.setTranslateX(x);
		 * ivHero.setTranslateY(y);
		 */
		// gc.drawImage(bg, 0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		System.out.println("eyeyeye");
		gc.drawImage(heroImg, x, y, heroImg.getWidth() / 3, heroImg.getHeight() / 3);
	}

	public void activate() {
		int rnd = random.nextInt(3);
		if (rnd != 1) {
			this.currentExp += 0.1 * (this.currentMaxExp - this.currentExp);
		}
	}

	public void deactivate() {

	}
}
