package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import window.SceneManager;

public class Knight extends Hero {
	Random random = new Random();
	private Image heroImg;

	
	public Knight(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("Knight1.png");

	}
	

	public void draw(GraphicsContext gc, double x, double y) {
		
		gc.drawImage(heroImg, x, y, heroImg.getWidth()/3, heroImg.getHeight()/3);
	}
	
	
	public void skill() {
		int rnd = random.nextInt(2);
		if(rnd==1) {
			this.atk*=this.growthRateAtk;
		}
		
	}
}
