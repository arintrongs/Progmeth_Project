package scene;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import window.SceneManager;

public class MainMenuScreen extends Pane {
	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font MENU_FONT = new Font("Monospace", 20);
	private Canvas cha1;
	private Canvas cha2;
	private Canvas cha3;
	private Canvas cha4;
	private Canvas farm;
	private Canvas boss;
	private int width = SceneManager.SCENE_WIDTH;

	
	public MainMenuScreen(){
		super();
		cha1 = drawButton("Knight",width/4 ,150, 0, 0);
		addCanvasEvents(cha1,"Knight");
		cha2 = drawButton("SpellCaster",width/4 ,150, width/4, 0);
		addCanvasEvents(cha2,"SpellCaster");
		cha3 = drawButton("Clown",width/4 ,150, width/2, 0);
		addCanvasEvents(cha3,"Clown");
		cha4 = drawButton("Priest",width/4 ,150, width*3/4, 0);
		addCanvasEvents(cha4,"Priest");
		farm = drawButton("FARM",width/3 ,width*2/5, width/8, width/4);
		addCanvasEvents(farm,"FARM");
		boss = drawButton("BOSS",width/3 ,width*2/5, width-width/8-width/3, width/4);
		addCanvasEvents(boss,"BOSS");
		
		getChildren().add(cha1);
		getChildren().add(cha2);
		getChildren().add(cha3);
		getChildren().add(cha4);
		getChildren().add(farm);
		getChildren().add(boss);
		
		
	}
	
	private Canvas drawButton(String name, double width, double height, int posX ,int posY) {
		Canvas btn = new Canvas(width,height);
		GraphicsContext gc = btn.getGraphicsContext2D();
		
		btn.setTranslateX(posX);
		btn.setTranslateY(posY);
		
		if(name!="FARM" && name!="BOSS") {
			gc.setFill(Color.DARKSALMON);
			//gc.setFill(Color.CORAL);
			gc.fillRect(0, 0, width, height);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(7);
			gc.strokeLine(0, 0, width, 0);
			gc.strokeLine(0, 0, 0, height);
			gc.strokeLine(0, height, width, height);
			if(name=="Priest") {
				gc.strokeLine(width, 0, width, height);
			}
			gc.setFont(MENU_FONT);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, width/2, height/8);
			gc.fillText("Level: \nAtk: \nExp: \n", width*7/10, height*2/3);
		}
		
		else {
			gc.setFill(Color.CORAL);
			gc.fillRoundRect(0, 0, width, height, 50, 50);
			gc.setFont(TITLE_FONT);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, width/2, height/2);
		}
		
		
		
		
		
		
		
		
		return btn;
	}
	
	public void drawHoverIndicator(Canvas canvas, String name) {
		//TODO Fill Code
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setFill(Color.GOLD);
		//gc.setFill(Color.RED);
		
		if(name!="FARM" && name!="BOSS") {
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(7);
			gc.strokeLine(0, 0, canvas.getWidth(), 0);
			gc.strokeLine(0, 0, 0, canvas.getHeight());
			gc.strokeLine(0, canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
			if(name=="Priest") {
				gc.strokeLine(canvas.getWidth(), 0, canvas.getWidth(), canvas.getHeight());
			}
			gc.setFont(MENU_FONT);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()/2, canvas.getHeight()/8);
			gc.fillText("Level: \nAtk: \nExp: \n", canvas.getWidth()*7/10, canvas.getHeight()*2/3);
		}
		
		else {
		
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()/2, canvas.getHeight()/2);
			
		}
		

		
			
	}

	public void undrawHoverIndicator(Canvas canvas, String name) {
		//TODO Fill Code
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		if(name!="FARM" && name!="BOSS") {
			gc.setFill(Color.DARKSALMON);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(7);
			gc.strokeLine(0, 0, canvas.getWidth(), 0);
			gc.strokeLine(0, 0, 0, canvas.getHeight());
			gc.strokeLine(0, canvas.getHeight(), canvas.getWidth(), canvas.getHeight());
			if(name=="Priest") {
				gc.strokeLine(canvas.getWidth(), 0, canvas.getWidth(), canvas.getHeight());
			}
			gc.setFont(MENU_FONT);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()/2, canvas.getHeight()/8);
			gc.fillText("Level: \nAtk: \nExp: \n", canvas.getWidth()*7/10, canvas.getHeight()*2/3);
		}
		
		else {
			gc.setFill(Color.CORAL);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()/2, canvas.getHeight()/2);
			
		}
	
		
		
	}
	
	private void addCanvasEvents(Canvas canvas, String buttonName) {
		//TODO Fill Code
		canvas.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Pane chaselect = new CharacterSelectScreen();
				Pane gamePlayScreen = new GamePlayScreen();
				if(buttonName=="FARM") SceneManager.gotoSceneOf(chaselect);
				if(buttonName=="BOSS") SceneManager.gotoSceneOf(gamePlayScreen);
			}
		});
		
		canvas.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				drawHoverIndicator(canvas,buttonName); 
			}	
		});
		
		canvas.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				undrawHoverIndicator(canvas,buttonName);
			}
			
		});
	}
}
