package scene;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import window.SceneManager;

public class CharacterSelectScreen extends Pane {
	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font BTN_FONT = new Font("Monospace", 30);
	private static final Font MENU_FONT = new Font("Monospace", 20);
	private Canvas title;
	private Canvas cha1,cha2,cha3,cha4;
	private Canvas backBtn;
	private Canvas startBtn;
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	
	public CharacterSelectScreen() {
		super();
		
		
		title = drawButton("Select Character", width , height/5 ,0 ,0);
		addCanvasEvents(title,"Select Character");
	
		cha1 = drawButton("Knight",			width/3 ,	height*2/7, width/8, 				height/4-30);
		addCanvasEvents(cha1,"Knight");
		cha2 = drawButton("SpellCaster",	width/3 ,	height*2/7, width-width/8-width/3, 	height/4-30);
		addCanvasEvents(cha2,"SpellCaster");
		cha3 = drawButton("Clown",			width/3 ,	height*2/7, width/8, 				height/2+10);
		addCanvasEvents(cha3,"Clown");
		cha4 = drawButton("Priest",			width/3 ,	height*2/7,  width-width/8-width/3, height/2+10);
		addCanvasEvents(cha4,"Priest");
		startBtn = drawButton("Start",	width/6 ,	height/9, 	width-width/4-width/6, 	height*5/6+10);
		addCanvasEvents(startBtn,"Start");
		backBtn = drawButton("Back",		width/6 ,	height/9, 	width/4, 				height*5/6+10);
		addCanvasEvents(backBtn,"Back");
		
		this.getChildren().addAll(title,cha1,cha2,cha3,cha4,startBtn,backBtn);
		
		
		
	}
	
	public Canvas drawButton(String name, double width, double height, double posX ,double posY) {
		Canvas canvas = new Canvas(width,height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);
		
		if(name=="Select Character") {
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(TITLE_FONT);
			gc.fillText(name, width/2 ,height/2);
		}
		else if(name=="Start" || name=="Back") {
			gc.setFill(Color.LIGHTGREY);
			gc.fillRoundRect(0, 0, width, height, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, width/2 ,height/2);
		}
		else {
			gc.setFill(Color.BLACK);
			gc.fillRoundRect(5, 5, width-10, height-10, 50, 50);
			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width-10, height-10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, width/2, height/4);
		}
		return canvas;
		
	}
	
	public void drawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if(name=="Start" || name=="Back") {
			gc.setFill(Color.LIGHTGREY.darker());
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT); 
			gc.fillText(name, canvas.getWidth()/2 ,canvas.getHeight()/2);
		}
		
		else if(name!="Select Character") {
			gc.setFill(Color.BLACK.darker());
			gc.fillRoundRect(5, 5, canvas.getWidth()-10, canvas.getHeight()-10, 50, 50);
			gc.setStroke(Color.TAN.darker());
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, canvas.getWidth()-10, canvas.getHeight()-10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()/2, canvas.getHeight()/4);
		}
		
		
	}

	public void undrawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		 if(name=="Start" || name=="Back") {
			gc.setFill(Color.LIGHTGREY);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth()/2 ,canvas.getHeight()/2);
		}
		else if(name!="Select Character") {
			gc.setFill(Color.BLACK);
			gc.fillRoundRect(5, 5, canvas.getWidth()-10, canvas.getHeight()-10, 50, 50);
			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, canvas.getWidth()-10, canvas.getHeight()-10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()/2, canvas.getHeight()/4);
		}
		
	}
	
	
	private void addCanvasEvents(Canvas canvas, String name) {
		canvas.setOnMouseClicked((MouseEvent event)->{
			Pane gamePlay = new GamePlayScreen();
			Pane mainMenu = new MainMenuScreen();
			Pane test = new ResultScreen();
			if(name=="Start") {
				// if current cha != NULL 
				//SceneManager.gotoSceneOf(gamePlay);
				// test
				SceneManager.gotoSceneOf(gamePlay);
				gamePlay.setFocusTraversable(true);
			}
			else if(name=="Back") {
				SceneManager.gotoSceneOf(mainMenu);
			}
			else {
				// set current character
			}
		});
		
		canvas.setOnMouseEntered((MouseEvent e)->{
			drawHoverIndicator(canvas,name);
		});
		
		canvas.setOnMouseExited((MouseEvent event)->{
			undrawHoverIndicator(canvas,name);
		});
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}