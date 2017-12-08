package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import window.SceneManager;

public class SpellCaster extends Hero{
	
	private Image heroImg , bg;
	//private ImageView ivHero,ivBg;
	
	public SpellCaster(String name, int level, String skillName) {
		super(name, level, skillName);
		heroImg = new Image("SpellCaster1.png");
		bg = new Image("bg4.png");	
		/*
		ivHero = new ImageView(heroImg);
		ivHero.setFitWidth(heroImg.getWidth() / 3);
		ivHero.setFitHeight(heroImg.getHeight() / 3);
		ivBg.setImage(bg);
		ivBg.setFitWidth(bg.getWidth());
		ivBg.setFitHeight(bg.getHeight());
		*/
		
	}
	
	
	
	public void draw(GraphicsContext gc, double x, double y) {
		/*
		ivBg.setTranslateX(0);
		ivBg.setTranslateY(0);
		ivHero.setTranslateX(x);
		ivHero.setTranslateY(y);
		*/
		//gc.drawImage(bg, 0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		gc.drawImage(heroImg, x, y, heroImg.getWidth()/3, heroImg.getHeight()/3);
	}
	
	
	
	public void skill() {
		// dont know
		
	}
	
	
	
}
