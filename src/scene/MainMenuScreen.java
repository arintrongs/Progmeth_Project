package scene;

import com.sun.corba.se.spi.ior.Writeable;

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
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import window.SceneManager;

public class MainMenuScreen extends Pane {
	private static final Font TITLE_FONT = Font.loadFont("file:res/font/south park.ttf", 80);
	private static final Font MENU_FONT = Font.loadFont("file:res/font/Inconsolata-Regular.ttf", 18);
	private static final Font MENU_FONT_BOLD = Font.loadFont("file:res/font/Inconsolata-Bold.ttf", 20);
	private Canvas cha1, cha2, cha3, cha4, farm, boss;
	private Canvas boardCha,boardFarm, boardBoss;


	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	
	private Image bg = new Image("bg22.png");
	private WritableImage cropBg = new WritableImage(bg.getPixelReader(), (int) bg.getHeight() / 3 * 4 / 3, 0, (int) bg.getHeight() / 3 * 4, (int) bg.getHeight());
	private ImageView ivBg = new ImageView(bg);

	public MainMenuScreen() {
		super();
		ivBg.setFitWidth(width);
		ivBg.setFitHeight(height);
		ivBg.setTranslateX(0);
		ivBg.setTranslateY(0);

		boardCha = drawButton(boardCha, "BoardCha", width, 150, 0, 0);
		
		boardFarm = drawButton(boardFarm, "BoardFarm", width / 3, width * 2 / 5, width / 8, width / 4);
		boardBoss = drawButton(boardBoss, "BoardFarm", width / 3, width * 2 / 5, width - width / 8 - width / 3,
				width / 4);
				

		cha1 = drawButton(cha1, "Knight", width / 4, 150, 0, 0);
		cha2 = drawButton(cha2, "SpellCaster", width / 4, 150, width / 4, 0);
		cha3 = drawButton(cha3, "Clown", width / 4, 150, width / 2, 0);
		cha4 = drawButton(cha4, "Priest", width / 4, 150, width * 3 / 4, 0);

		farm = drawButton(farm, "FARM", width / 3, width * 2 / 5, width / 8, width / 4);
		addCanvasEvents(farm, "FARM");
		
		boss = drawButton(boss, "BOSS", width / 3, width * 2 / 5, width - width / 8 - width / 3, width / 4);
		addCanvasEvents(boss, "BOSS");

		getChildren().addAll(ivBg, boardCha, boardFarm, boardBoss, cha1, cha2, cha3, cha4, farm, boss);

	}

	private Canvas drawButton(Canvas canvas, String name, double width, double height, int posX, int posY) {

		Canvas btn = new Canvas(width, height);
		GraphicsContext gc = btn.getGraphicsContext2D();

		btn.setTranslateX(posX);
		btn.setTranslateY(posY);


		 if (name == "BoardFarm" || name == "BoardBoss") {
			gc.setFill(Color.CORAL.desaturate());
			gc.fillRoundRect(5, 5, width - 10, height - 10, 50, 50);
			btn.setOpacity(0.5);
			
		}

		else if (name == "FARM" || name == "BOSS") {
			 gc.setStroke(Color.BLACK);
			 gc.setLineWidth(5);
			 gc.strokeRoundRect(5, 5, width-10, height-10, 50, 50);
			 gc.setFont(TITLE_FONT); 
			 gc.setFill(Color.BLACK);
			 gc.setTextAlign(TextAlignment.CENTER); 
			 gc.setTextBaseline(VPos.CENTER);
			 gc.fillText(name, width/2, height/2);
			 
		}
		else if(name=="BoardCha")  {
				gc.setFill(Color.BISQUE);
				gc.fillRect(0, 0, width, height);
				//btn.setOpacity(1);
				 

			}
		
		else {
			
			Image img;
			if(name=="Knight") img = new Image("Knight1_head.png");
			else if(name=="SpellCaster") img = new Image("Spellcaster1_head.png");
			else if(name=="Clown") img = new Image("Clown1_head.png");
			else img = new Image("Priest1_head.png");
			
			gc.drawImage(img, 15, 45, 90, 90);
			
			gc.setStroke(Color.BLACK); 
			gc.setLineWidth(7); 
			gc.strokeLine(0, 0, width, 0);
			gc.strokeLine(0, 0, 0, height); 
			gc.strokeLine(0, height, width, height);
			if(name=="Priest") 
				{ gc.strokeLine(width, 0, width, height); }
			
			gc.setFont(MENU_FONT_BOLD); 
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(name, width/2, height/8+5); 
			gc.setTextAlign(TextAlignment.LEFT);
			gc.setFont(MENU_FONT);
			if(name=="Knight") {
				gc.fillText("Level: "+GameManager.getKnight().getLevel()+'\n'+"Atk: "+GameManager.getKnight().getAtk()+'\n'
						+"Exp: "+GameManager.getKnight().getCurrentExp()+'\n',width*6/10-5, height*2/3);
			}
			else if(name=="SpellCaster") {
				gc.fillText("Level: "+GameManager.getSpellCaster().getLevel()+'\n'+"Atk: "+GameManager.getSpellCaster().getAtk()+'\n'
						+"Exp: "+GameManager.getSpellCaster().getCurrentExp()+'\n',width*6/10-5, height*2/3);
			}
			else if(name=="Clown") {
				gc.fillText("Level: "+GameManager.getClown().getLevel()+'\n'+"Atk: "+GameManager.getClown().getAtk()+'\n'
						+"Exp: "+GameManager.getClown().getCurrentExp()+'\n',width*6/10-5, height*2/3);
			}
			else   {
				gc.fillText("Level: "+GameManager.getPriest().getLevel()+'\n'+"Atk: "+GameManager.getPriest().getAtk()+'\n'
						+"Exp: "+GameManager.getPriest().getCurrentExp()+'\n',width*6/10-5, height*2/3);
			}
		}

			
		
		return btn;
	}

	public void drawHoverIndicator(Canvas canvas, String name) {
		// TODO Fill Code
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.CORAL);
		gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);


	}

	public void undrawHoverIndicator(Canvas canvas, String name) {
		// TODO Fill Code
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.CORAL.desaturate());
		gc.fillRoundRect(5, 5, canvas.getWidth() - 10, canvas.getHeight() - 10, 50, 50);
		



	}

	private void addCanvasEvents(Canvas canvas, String buttonName) {
		// TODO Fill Code
		canvas.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				// Wait for fix Boss gameplay
				Pane chaselect = new CharacterSelectScreen();

				//Pane gamePlayScreen = new GamePlayScreen(); 
				if (buttonName == "FARM") {
					GameManager.setCurrentMode("Farm");
					
					SceneManager.gotoSceneOf(chaselect);
				}
				else if (buttonName == "BOSS") {
					GameManager.setCurrentMode("Boss");
					//SceneManager.gotoSceneOf(gamePlayScreen);
				}

			}
		});

		canvas.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub

				if(buttonName=="FARM")
					drawHoverIndicator(boardFarm, buttonName);
				else {
					drawHoverIndicator(boardBoss, buttonName);
				}

			}
		});

		canvas.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub

				if(buttonName=="FARM")
					undrawHoverIndicator(boardFarm, buttonName);
				else {
					undrawHoverIndicator(boardBoss, buttonName);
				}

			}

		});
	}
}