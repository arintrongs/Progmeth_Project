package model;

import gameLogic.MusicControl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
			this.isSkillActivated = true;
			MusicControl.setIsGuarantee(true);
			System.out.println("Skill Activated!!");
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true) {
			new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
				}
				this.isSkillActivated = false;
				MusicControl.setIsGuarantee(false);
				System.out.println("Skill Deactivated!!");
			}).start();
		}
	}

}
