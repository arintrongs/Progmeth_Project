
package scene;

import gameLogic.GameManager;
import gameLogic.MusicControl;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Boss;
import model.Field;
import model.Hero;
import model.Monster;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class GamePlayScreen extends Pane {
	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font BTN_FONT = new Font("Monospace", 15);
	private static final Font MENU_FONT = new Font("Monospace", 17);
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	private Canvas bg = new Canvas(width,height);
	
	private Canvas monsInfo;
	private Canvas heroInfo;
	private Canvas gamePlay;
	private Canvas exitMenu, yesBtn, noBtn;
	
	private Image  playzoneImg, perfectTap, criTap, greatTap,
			goodTap, missTap, tapZone;
	
	
	private ImageView ivPlayzone = new ImageView();
	private ImageView ivCriTap = new ImageView();
	private ImageView ivPerfectTap = new ImageView();
	private ImageView ivGreatTap = new ImageView();
	private ImageView ivGoodTap = new ImageView();
	private ImageView ivMissTap = new ImageView();
	private ImageView ivTapZone = new ImageView();
	/*
	private Monster currentMon;
	private Hero currentHero;
	private Boss currentBoss;
	*/

	private MusicControl musicControl = new MusicControl();

	public GamePlayScreen() {
		// TODO Auto-generated constructor stub
		super();
		paint();
		monsInfo = drawButton("MonsterInfo", width / 2, height / 10, width/2, 0);
		heroInfo = drawButton("HeroInfo", width / 2, height / 10, 0, 0);
		
		exitMenu = drawButton("Exit", width / 3 + 40, height / 6, width / 3 - 20, height / 3);
		addCanvasEvents(exitMenu, "Exit");
		exitMenu.setVisible(false);
		
		yesBtn = drawButton("Yes", width / 12, height / 18, width / 3 + 60, height / 3 + 50);
		addCanvasEvents(yesBtn, "Yes");
		yesBtn.setVisible(false);
		
		noBtn = drawButton("No", width / 12, height / 18, width / 3 + width / 3 - width / 12 - 60, height / 3 + 50);
		addCanvasEvents(noBtn, "No");
		noBtn.setVisible(false);

		gamePlay = drawButton("GamePlay", width, height / 3 - 20, 0, height * 2 / 3 + 20);
		gamePlay.setFocusTraversable(true);
		addCanvasEvents(gamePlay, "GamePlay");

		setImage();
		setIv();

		this.getChildren().addAll(bg, ivPlayzone, ivCriTap, ivPerfectTap, ivGreatTap, ivGoodTap, ivMissTap,
				ivTapZone, monsInfo, heroInfo, gamePlay,exitMenu, yesBtn, noBtn);

		/*
		javafx.application.Platform.runLater(() -> {
			musicControl.start();
		});
		*/
 
	}
	
	public void paint() {
		
		bg = new Canvas(width,height);
		GraphicsContext gc = bg.getGraphicsContext2D();
		
		for(IRenderable e : RenderableHolder.getInstance().getiRenderable()) {
			
			if(e.isVisible()) {
				
				if(e instanceof Field) {
					
					((Field) e).setBg(); 
					e.draw(gc, 0, 0);
					
				}
				else if(e instanceof Hero && GameManager.getCurrentMode()=="Farm" ) {
					
					e.draw(gc, width/7, height/6);
				}
				else if(e instanceof Monster && GameManager.getCurrentMode()=="Farm") {
					e.draw(gc, width / 3 * 2 - 20, height/3);
				}
			}
		}
	}
	
	public void setImage() {
		// ask current mon and hero
		
		this.playzoneImg = new Image("Playzone.png");
		this.criTap = new Image("Cri_Perfect.png");
		this.perfectTap = new Image("Perfect.png");
		this.greatTap = new Image("Great.png");
		this.goodTap = new Image("Good.png");
		this.missTap = new Image("Miss.png");
		this.tapZone = new Image("Tapzone.png");
	}

	public void setIv() {

		
		ivPlayzone.setImage(playzoneImg);
		ivCriTap.setImage(criTap);
		ivPerfectTap.setImage(perfectTap);
		ivGreatTap.setImage(greatTap);
		ivGoodTap.setImage(goodTap);
		ivMissTap.setImage(missTap);
		ivTapZone.setImage(tapZone);

		
		ivCriTap.setVisible(false);
		ivPerfectTap.setVisible(false);
		ivGreatTap.setVisible(false);
		ivGoodTap.setVisible(false);
		ivMissTap.setVisible(false);

		ivPlayzone.setTranslateY(height * 2 / 3 - 20);


		// Set Tap Judge Effect Position
		ivTapZone.setTranslateX(700 - 59);
		ivTapZone.setTranslateY(600 - 170);
		ivCriTap.setTranslateX(700 - 59);
		ivCriTap.setTranslateY(600 - 170);
		ivPerfectTap.setTranslateX(700 - 59);
		ivPerfectTap.setTranslateY(600 - 170);
		ivGreatTap.setTranslateX(700 - 59);
		ivGreatTap.setTranslateY(600 - 170);
		ivGoodTap.setTranslateX(700 - 59);
		ivGoodTap.setTranslateY(600 - 170);
		ivMissTap.setTranslateX(700 - 59);
		ivMissTap.setTranslateY(600 - 170);


	}

	public Canvas drawButton(String name, double width, double height, double posX, double posY) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);

		if (name == "MonsterInfo") {
			gc.setFill(Color.PALEVIOLETRED);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 0);
			gc.setFill(Color.WHITE);
			gc.setTextBaseline(VPos.CENTER);
			gc.setTextAlign(TextAlignment.LEFT);
			gc.setFont(MENU_FONT);
			// get method for name,level,hp of mon
			
			gc.fillText("Name : "+GameManager.getCurrentMon().getName(),20, height / 2 - 15);
			gc.fillText("Level : "+GameManager.getCurrentMon().getLevel(), 200, height / 2 - 15);
			
			gc.fillText("Hp : "+ GameManager.getCurrentMon().getCurrentHp().intValue(), 20, height / 2 + 10);

		} else if (name == "HeroInfo") {

			gc.setFill(Color.BURLYWOOD);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 0);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(MENU_FONT);
			// get method for name,level,hp of mon
			gc.fillText("Name : " +GameManager.getCurrentCha().getName(),20, height / 2 - 15);
			gc.fillText("Level : "+GameManager.getCurrentCha().getLevel(), 200, height / 2 - 15);
			gc.fillText("Exp : "+GameManager.getCurrentCha().getCurrentExp(),20, height / 2 + 10);

		}

		else if (name == "GamePlay") {

		}

		else if (name == "Exit") {
			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width - 10, height - 10, 50, 50);
			gc.setFill(Color.BISQUE);
			gc.fillRoundRect(5, 5, width - 10, height - 10, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(MENU_FONT);
			gc.fillText("Do you want to exit the game ?", width / 2, height / 4);
		}

		else {

			gc.setFill(Color.DARKSALMON);
			gc.fillRoundRect(0, 0, width, height, 30, 30);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, width / 2, height / 2);
		}

		return canvas;

	}

	private void addCanvasEvents(Canvas canvas, String name) {
		canvas.setOnKeyPressed((KeyEvent e) -> {
			System.out.println(e.getCode().getName());
			if (e.getCode() == KeyCode.ESCAPE) {

				this.exitMenu.setVisible(true);
				this.yesBtn.setVisible(true);
				this.noBtn.setVisible(true);
			}
		});

		canvas.setOnMouseClicked((MouseEvent e) -> {
			Pane mainMenu = new MainMenuScreen();

			if (name == "No") {
				this.exitMenu.setVisible(false);
				this.yesBtn.setVisible(false);
				this.noBtn.setVisible(false);
			} else if (name == "Yes") {
				SceneManager.gotoSceneOf(mainMenu);

			}
		});

	}

}
