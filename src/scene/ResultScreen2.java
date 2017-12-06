package scene;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import window.SceneManager;

public class ResultScreen2 extends Pane {
	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font BTN_FONT = new Font("Monospace", 30);
	private static final Font MENU_FONT = new Font("Monospace", 20);
	private Canvas title;
	private Canvas before;
	private Canvas after;
	private Canvas nextBtn;
	private Canvas backBtn;
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	

	
	
	public ResultScreen2(){
		super();
		title = drawButton("Well Done!", 		width , height/5 ,0 ,0);
		addCanvasEvents(title,"Well Done!");
		
		before = drawButton("Before",		width/3 , height/2, 	width/12, height/4-10);
		addCanvasEvents(before,"Before");
		
		after = drawButton("After",			width/3 , height/2, 	width-width/12-width/3, height/4-10);
		addCanvasEvents(after,"After");
		
		nextBtn = drawButton("Next",		width/6 ,	height/9, 	width-width/4-width/6+60 , height*5/6-20);
		addCanvasEvents(nextBtn,"Next");
		
		backBtn = drawButton("Back",		width/6 ,	height/9, 	width/8+40, 				height*5/6-20);
		addCanvasEvents(backBtn,"Back");
		
		this.getChildren().addAll(title,before,after,nextBtn,backBtn);
		
		
	}
	
	public Canvas drawButton(String name, double width, double height, double posX ,double posY) {
		Canvas canvas = new Canvas(width,height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);
		
		
		if(name=="Well Done!") {
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(TITLE_FONT);
			gc.fillText(name, width/2 ,height/2);
		}
		else if(name=="Before") {
			
			gc.setFill(Color.BLACK);
			gc.fillRoundRect(5, 5, width-10, height-10, 50, 50);
			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width-10, height-10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText("Name : "+'\n'+"Atk : "+'\n'+"Exp : "+'\n'+'\n' ,50, height/2);
			gc.fillRect(50, height/2+30, 150, 20);
			
			
			gc.setFill(Color.SANDYBROWN);
			// let x be old exp
			double x = 50;
			gc.fillRect(50, height/2+30, x, 20);
			
			
			
			
		}
		
		else if(name=="After") {
			
			gc.setFill(Color.BLACK);
			gc.fillRoundRect(5, 5, width-10, height-10, 50, 50);
			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width-10, height-10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText("Name : "+'\n'+"Atk : "+'\n'+"Exp : "+'\n'+'\n' ,50, height/2);
			gc.fillRect(50, height/2+30, 150, 20);
			
			gc.setFill(Color.SANDYBROWN);
			// let x be now exp
			double x = 70;
			gc.fillRect(50, height/2+30, x, 20);
			
			
		}
		
		else {
			gc.setFill(Color.LIGHTGREY.darker());
			gc.fillRoundRect(0, 0, width, height, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, width/2 ,height/2);
		}
		return canvas;
		
	}
	
	public void drawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if(name=="Next" || name=="Back") {
			gc.setFill(Color.LIGHTGREY);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth()/2 ,canvas.getHeight()/2);
		}
		
		
		
		
	}

	public void undrawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		 if(name=="Next" || name=="Back") {
			gc.setFill(Color.LIGHTGREY.darker());
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth()/2 ,canvas.getHeight()/2);
		}
		
		
	}
	
	
	private void addCanvasEvents(Canvas canvas, String name) {
		
		canvas.setOnMouseClicked((MouseEvent event)->{
			Pane mainMenu = new MainMenuScreen();
			Pane resultScreen = new ResultScreen();
			
			if(name=="Next") {
				
				SceneManager.gotoSceneOf(mainMenu);
			}
			else if(name=="Back") {
				
				SceneManager.gotoSceneOf(resultScreen);
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
