
package scene;

import character.Boss;
import character.Hero;
import character.Monster;
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
import window.SceneManager;

public class GamePlayScreen extends Pane {
	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font BTN_FONT = new Font("Monospace", 15);
	private static final Font MENU_FONT = new Font("Monospace", 17);
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	private Canvas monsInfo;
	private Canvas heroInfo;
	private Canvas gamePlay;
	private Canvas exitMenu, yesBtn, noBtn;
	private Image monsterImg, monsterHarmImg, heroImg, backgroundImg, playzoneImg;
	private ImageView ivMon = new ImageView();
	private ImageView ivHero = new ImageView();
	private ImageView ivMonHarm = new ImageView();
	private ImageView ivBackground = new ImageView();
	private ImageView ivPlayzone = new ImageView();
	private Monster currentMon;
	private Hero currentHero;
	private Boss currentBoss;

	public GamePlayScreen() {
		// TODO Auto-generated constructor stub
		super();

		monsInfo = drawButton("MonsterInfo", width / 2, height / 10, 0, 0);

		heroInfo = drawButton("HeroInfo", width / 2, height / 10, width / 2, 0);

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

		this.getChildren().addAll(ivBackground, ivPlayzone, monsInfo, heroInfo, gamePlay, ivMon, ivHero, ivMonHarm,
				exitMenu, yesBtn, noBtn);

	}

	public void setImage() {
		// ask current mon and hero
		this.monsterImg = new Image("Monster1.2.png");
		this.monsterHarmImg = new Image("Monster1.2.png");
		this.heroImg = new Image("Clown1.png");
		this.backgroundImg = new Image("Gameplay_bg1.png");
		this.playzoneImg = new Image("Playzone.png");
	}

	public void setIv() {

		ivMon.setImage(monsterImg);
		ivHero.setImage(heroImg);
		ivMonHarm.setImage(monsterHarmImg);
		ivBackground.setImage(backgroundImg);
		ivPlayzone.setImage(playzoneImg);
		ivMonHarm.setVisible(false);

		ivHero.setFitWidth(heroImg.getWidth() / 3);
		ivHero.setFitHeight(heroImg.getHeight() / 3);
		ivHero.setTranslateX(width / 7);
		ivHero.setTranslateY(height / 6);

		ivMon.setFitWidth(monsterImg.getWidth() / 3);
		ivMon.setFitHeight(monsterImg.getHeight() / 3);

		ivMon.setTranslateX(width / 3 * 2 - 20);
		ivMon.setTranslateY(height / 3);


		ivMonHarm.setFitWidth(monsterHarmImg.getWidth() / 3);
		ivMonHarm.setFitHeight(monsterHarmImg.getHeight() / 3);
		ivMonHarm.setTranslateX(width / 3 * 2);
		ivMonHarm.setTranslateY(height / 6 - 10);


		ivBackground.setFitWidth(width);
		ivBackground.setFitHeight(height);

		ivPlayzone.setTranslateY(height * 2 / 3 - 20);


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
			gc.setFont(MENU_FONT);
			// get method for name,level,hp of mon
			gc.fillText("Name : " + "Level : ", 20, height / 2 - 15);
			gc.fillText("Hp : ", 20, height / 2 + 10);

		} else if (name == "HeroInfo") {

			gc.setFill(Color.BURLYWOOD);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 0);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.RIGHT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(MENU_FONT);
			// get method for name,level,hp of mon
			gc.fillText("Name : " + "Level : ", width, height / 2 - 15);
			gc.fillText("Hp : ", width, height / 2 + 10);

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
