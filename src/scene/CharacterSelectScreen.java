package scene;

import character.Hero;
import character.Knight;
import gameLogic.GameManager;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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
	private Canvas cha1, cha2, cha3, cha4;
	private Canvas backBtn;
	private Canvas startBtn;
	private String name = "";
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	private Canvas boardCha1,boardCha2,boardCha3,boardCha4;
	private Image bg = new Image("bg24.jpg");
//	private WritableImage cropBg = new WritableImage(bg.getPixelReader(), 0, 0, (int) bg.getHeight() / 3 * 4, (int) bg.getHeight());
	private ImageView ivBg = new ImageView(bg);
	

	public CharacterSelectScreen() {
		super();
		ivBg.setFitWidth(width);
		ivBg.setFitHeight(height);
		ivBg.setTranslateX(0);
		ivBg.setTranslateY(0);
		
		
		title = drawButton("Select Character", width , height/5 ,0 ,0);
		
		boardCha1 = drawButton("BoardKnight",			width/3 ,	height*2/7, width/8, 				height/4-30);
		boardCha2 = drawButton("BoardSpellCaster",	width/3 ,	height*2/7, width-width/8-width/3, 	height/4-30);
		boardCha3 = drawButton("BoardClown",			width/3 ,	height*2/7, width/8, 				height/2+10);
		boardCha4 = drawButton("BoardPriest",			width/3 ,	height*2/7,  width-width/8-width/3, height/2+10);
		
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
		
		this.getChildren().addAll(ivBg,title,boardCha1,boardCha2,boardCha3,boardCha4,cha1,cha2,cha3,cha4,startBtn,backBtn);
		
	
	}

	public Canvas drawButton(String name, double width, double height, double posX, double posY) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);

		
		if (name == "Select Character") {
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(TITLE_FONT);

			gc.fillText(name, width/2 ,height/2);
		}
		
		else if(name=="Start" || name=="Back") {
			gc.setFill(Color.LIGHTBLUE);

			gc.fillRoundRect(0, 0, width, height, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);

			gc.fillText(name, width/2 ,height/2);
		}
		else if(name=="Knight" || name=="SpellCaster" ||name=="Clown" ||name=="Priest" ){
			Image img;
			if(name=="Knight") img = new Image("Knight1_head.png");
			else if(name=="SpellCaster") img = new Image("Spellcaster1_head.png");
			else if(name=="Clown") img = new Image("Clown1_head.png");
			else img = new Image("Priest1_head.png");
			gc.drawImage(img, width/2-50, height/2-35, 100, 100);
			
			
			gc.setStroke(Color.TAN.darker());
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width - 10, height - 10, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setFont(MENU_FONT);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, width / 2, height / 5);
			
		}
		else {
			gc.setFill(Color.WHITE);
			gc.fillRoundRect(5, 5, width-10, height-10, 50, 50);
			canvas.setOpacity(0.8);
		}
		return canvas;

	}

	public void drawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		if(name=="Start" || name=="Back") {
			gc.setFill(Color.LIGHTBLUE.desaturate());

			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth() / 2, canvas.getHeight() / 2);
		}
		
	
		
		else {
			gc.setFill(Color.LIGHTCORAL.desaturate());
			gc.fillRoundRect(5, 5, canvas.getWidth() -10, canvas.getHeight()-10, 50, 50);
			canvas.setOpacity(0.9);
		}

	}

	public void undrawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		 if(name=="Start" || name=="Back") {
			gc.setFill(Color.LIGHTBLUE);

			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth() / 2, canvas.getHeight() / 2);
		} 
		 /*
		 else if(name=="Knight" || name=="SpellCaster" ||name=="Clown" ||name=="Priest" ){
			gc.setStroke(Color.TAN.darker());
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, canvas.getWidth() - 10, canvas.getHeight() - 10, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setFont(MENU_FONT);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, canvas.getWidth()  / 2, canvas.getHeight() / 4);
		}
		*/
		else {
			gc.setFill(Color.WHITE);
			gc.fillRoundRect(5, 5, canvas.getWidth() -10, canvas.getHeight()-10, 50, 50);
			canvas.setOpacity(0.8);
		}
	}

	private void addCanvasEvents(Canvas canvas, String name) {
		canvas.setOnMouseClicked((MouseEvent event) -> {
			Pane gamePlay = new GamePlayScreen();
			Pane mainMenu = new MainMenuScreen();

			if (name == "Start") {
				
				SceneManager.gotoSceneOf(gamePlay);
				gamePlay.setFocusTraversable(true);
			} 
			else if (name == "Back") {
				SceneManager.gotoSceneOf(mainMenu);
			} else {
				// set current character
				
				GameManager.setCurrentCha(name);
				this.name=name;
				
				if(name!="Knight")	undrawHoverIndicator(boardCha1, name);
				if(name!="SpellCaster")	undrawHoverIndicator(boardCha2, name);
				if(name!="Clown")	undrawHoverIndicator(boardCha3, name);
				if(name!="Priest") 	undrawHoverIndicator(boardCha4, name);
				//drawHoverIndicator(canvas, name);
				
			
				
			}
		});

		canvas.setOnMouseEntered((MouseEvent e) -> {
			if(name=="Knight")	drawHoverIndicator(boardCha1, name);
			else if(name=="SpellCaster")	drawHoverIndicator(boardCha2, name);
			else if(name=="Clown")	drawHoverIndicator(boardCha3, name);
			else if(name=="Priest")	drawHoverIndicator(boardCha4, name);
			else drawHoverIndicator(canvas, name);
		});

		
		canvas.setOnMouseExited((MouseEvent event)->{
			if(name=="Knight" && this.name!=name)	undrawHoverIndicator(boardCha1, name);
			else if(name=="SpellCaster"&& this.name!=name)	undrawHoverIndicator(boardCha2, name);
			else if(name=="Clown"&& this.name!=name)	undrawHoverIndicator(boardCha3, name);
			else if(name=="Priest"&& this.name!=name)	undrawHoverIndicator(boardCha4, name);
			else if(name=="Start" ||name=="Back") undrawHoverIndicator(canvas, name);
			//if(this.name!=name) undrawHoverIndicator(canvas,name);

		});

	}

	


}