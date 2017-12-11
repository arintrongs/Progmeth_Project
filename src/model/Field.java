package model;

import gameLogic.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sharedObject.IRenderable;
import window.SceneManager;

public class Field implements IRenderable {
	private int z =  -999;
	private boolean isVisible ;
	private Image bg ;

	public Field() {
		this.isVisible = true;
		
	}
	
	public void setBg() {
		
		if(GameManager.getCurrentMode()=="Farm") {
			
			if(GameManager.getCurrentCha().getName()=="Knight") {
				
				bg = new Image("Gameplay_bg1.png");
				
			}
			else if(GameManager.getCurrentCha().getName()=="SpellCaster") {
				bg = new Image("bg16.png");
			}
			else if(GameManager.getCurrentCha().getName()=="Clown") {
				bg = new Image("bg7.png");
			}
			else  {
				bg = new Image("bg21.png");
			}
		}
	}
	
	public int getZ() {
		return z;
	}
	public void draw(GraphicsContext gc , double x ,double y) {
		
		gc.drawImage(bg,  0,0,SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		
	}
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
