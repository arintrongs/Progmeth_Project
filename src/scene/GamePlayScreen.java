
package scene;

import gameLogic.GameManager;
import gameLogic.MusicControl;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;
import model.Boss;
import model.Field;
import model.Hero;
import model.Monster;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class GamePlayScreen extends Pane {

	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font BTN_FONT = new Font("Monospace", 15);
	private static final Font MENU_FONT = new Font("Monospace", 17);
	private static final Font COMBO_FONT = Font.loadFont("file:res/font/Education-Pencil.ttf", 30);
	private static final Image SKILL_ACTIVATED = new Image("skill.png");
	private static final Image BOSS_SKILL_ACTIVATED = new Image("boss_skill.png");
	private static final Image FAIL = new Image("fail.png");
	private static final Image LEVEL_UP = new Image("level.png");
	private static int width = SceneManager.SCENE_WIDTH;
	private static int height = SceneManager.SCENE_HEIGHT;
	private boolean singlepulse = false;
	private static boolean isCreate = false;
	private static Canvas bg;
	private static Canvas monsInfo, monCanvas;
	private static Canvas heroInfo, heroCanvas;
	private static Canvas eventCanvas, skillCanvas, bossSkillCanvas;
	private Canvas comboCanvas;
	private Canvas gamePlay;
	private Canvas exitMenu, yesBtn, noBtn;
	private Image playzoneImg, tapZone;
	private ImageView ivPlayzone = new ImageView();
	private ImageView ivTapZone = new ImageView();
	private static MusicControl musicControl;
	public static GamePlayScreen instance;
	private static GraphicsContext gcBg, gcMon, gcHero;

	public GamePlayScreen() {

		super();
		instance = this;
		isCreate = true;
		bg = new Canvas(width, height);
		skillCanvas = new Canvas(400, 200);
		bossSkillCanvas = new Canvas(400, 200);
		eventCanvas = new Canvas(600, 300);
		eventCanvas.setTranslateY(height / 3 - 80);
		eventCanvas.setTranslateX(width / 2 - 300);

		skillCanvas.setTranslateX(90);
		skillCanvas.setTranslateY(40);

		bossSkillCanvas.setTranslateX(500);
		bossSkillCanvas.setTranslateY(60);

		heroCanvas = new Canvas(width / 2, height * 2 / 3);

		monCanvas = new Canvas(width, height);
		paint();

		monsInfo = draw("MonsterInfo", width / 2, height / 10, width / 2, 0);
		heroInfo = draw("HeroInfo", width / 2, height / 10, 0, 0);
		exitMenu = draw("Exit", width / 3 + 40, height / 6, width / 3 - 20, height / 3);
		addCanvasEvents(exitMenu, "Exit");
		exitMenu.setVisible(false);

		yesBtn = draw("Yes", width / 12, height / 18, width / 3 + 60, height / 3 + 50);
		addCanvasEvents(yesBtn, "Yes");
		yesBtn.setVisible(false);

		noBtn = draw("No", width / 12, height / 18, width / 3 + width / 3 - width / 12 - 60, height / 3 + 50);
		addCanvasEvents(noBtn, "No");
		noBtn.setVisible(false);

		gamePlay = draw("gamePlay", width, height * 1 / 3, 0, height * 2 / 3);
		gamePlay.setFocusTraversable(true);
		addCanvasEvents(gamePlay, "gamePlay");

		setImage();
		setIv();
		this.getChildren().clear();
		this.getChildren().addAll(bg, heroCanvas, monCanvas, ivPlayzone, gamePlay, ivTapZone, monsInfo, heroInfo,
				exitMenu, yesBtn, noBtn, eventCanvas, skillCanvas, bossSkillCanvas);

	}

	public GraphicsContext getGcBg() {
		return bg.getGraphicsContext2D();
	}

	public GraphicsContext getGcHero() {
		return heroCanvas.getGraphicsContext2D();
	}

	public GraphicsContext getGcMon() {
		return monCanvas.getGraphicsContext2D();
	}

	public static void paint() {

		gcBg = bg.getGraphicsContext2D();
		gcHero = heroCanvas.getGraphicsContext2D();
		gcMon = monCanvas.getGraphicsContext2D();
		gcHero.clearRect(0, 0, 800, 800);

		for (IRenderable e : RenderableHolder.getInstance().getiRenderable()) {

			if (e.isVisible()) {

				if (e instanceof Field) {
					((Field) e).setBg();
					e.draw(gcBg, 0, 0);

				} else if (e instanceof Hero) {

					e.draw(gcHero, width / 7, height / 6);
				} else if (e instanceof Boss) {
					e.draw(gcMon, width / 3 * 2 - 50, height / 3 - 60);
				} else if (e instanceof Monster) {

					e.draw(gcMon, width / 3 * 2 - 20, height / 3);

				}
			}
		}
	}

	public synchronized void changeHero(Hero back) {

		Canvas backCanvas = new Canvas(width / 2, height * 2 / 3);
		GraphicsContext backGC = backCanvas.getGraphicsContext2D();
		back.draw(backGC, width / 7, height / 6);

		ScaleTransition stHideFront = new ScaleTransition(Duration.millis(300), heroCanvas);
		stHideFront.setFromX(1);
		stHideFront.setToX(0);
		backCanvas.setScaleX(0);

		ScaleTransition stShowBack = new ScaleTransition(Duration.millis(300), backCanvas);
		stShowBack.setFromX(0);
		stShowBack.setToX(1);

		stHideFront.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stShowBack.play();
			}
		});
		stShowBack.setOnFinished((ActionEvent event) -> {

			this.getChildren().remove(heroCanvas);
			heroCanvas = backCanvas;
			this.getChildren().remove(backCanvas);
			this.getChildren().add(heroCanvas);

		});
		this.getChildren().add(backCanvas);
		stHideFront.play();

	}

	public static Canvas getMonCanvas() {
		return monCanvas;
	}

	public Canvas getHeroCanvas() {
		return heroCanvas;
	}

	public void setImage() {
		this.playzoneImg = new Image("Playzone.png");
		this.tapZone = new Image("Tapzone.png");
	}

	public void setIv() {

		ivPlayzone.setImage(playzoneImg);
		ivTapZone.setImage(tapZone);
		ivPlayzone.setTranslateY(height * 2 / 3 - 20);
		ivTapZone.setTranslateX(700 - 59);
		ivTapZone.setTranslateY(600 - 170);

	}

	public static Canvas draw(String name, double width, double height, double posX, double posY) {
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
			gc.fillText("Name : " + GameManager.getCurrentMon().getName(), 20, height / 2 - 15);
			gc.fillText("Level : " + GameManager.getCurrentMon().getLevel(), 200, height / 2 - 15);
			gc.fillText("Hp : " + GameManager.getCurrentMon().getCurrentHp().intValue(), 20, height / 2 + 10);

		} else if (name == "HeroInfo") {

			gc.setFill(Color.BURLYWOOD);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 0);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(MENU_FONT);
			gc.fillText("Name : " + GameManager.getCurrentCha().getName(), 20, height / 2 - 15);
			gc.fillText("Level : " + GameManager.getCurrentCha().getLevel(), 200, height / 2 - 15);
			gc.fillText("Atk : " + String.format("%.2f", GameManager.getCurrentCha().getAtk()), 20, height / 2 + 10);
			gc.fillText("Exp : " + GameManager.getCurrentCha().getCurrentExp(), 200, height / 2 + 10);

		}

		else if (name == "gamePlay" || name == "Combo") {

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

	public void start() {
		musicControl = new MusicControl(this);
		musicControl.getMediaPlayer().setOnReady(() -> {
			musicControl.setDuration(musicControl.getMediaPlayer().getTotalDuration().toSeconds());
			musicControl.setMusicChart();
			musicControl.run();
		});

	}

	private void addCanvasEvents(Canvas canvas, String name) {
		canvas.setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode().isArrowKey() == true && singlepulse == false) {
				musicControl.judge(e);
				singlepulse = true;
			}
			if (e.getCode() == KeyCode.ESCAPE) {
				exitMenu.toFront();
				yesBtn.toFront();
				noBtn.toFront();
				this.exitMenu.setVisible(true);
				this.yesBtn.setVisible(true);
				this.noBtn.setVisible(true);
			}
			if (e.getCode() == KeyCode.E) {
				musicControl.end();
			}

		});

		canvas.setOnKeyReleased((KeyEvent e) -> {
			if (e.getCode().isArrowKey() == true) {
				singlepulse = false;
			}
		});

		canvas.setOnMouseClicked((MouseEvent e) -> {
			Pane mainMenu = new MainMenuScreen();

			if (name == "No") {

				this.exitMenu.setVisible(false);
				this.yesBtn.setVisible(false);
				this.noBtn.setVisible(false);

			} else if (name == "Yes") {
				musicControl.end();
				SceneManager.gotoSceneOf(mainMenu);
			}
		});
	}

	public void setMonsInfo() {
		this.getChildren().remove(monsInfo);
		monsInfo = draw("MonsterInfo", width / 2, height / 10, width / 2, 0);
		this.getChildren().add(monsInfo);
	}

	public void setHeroInfo() {
		this.getChildren().remove(heroInfo);
		heroInfo = draw("HeroInfo", width / 2, height / 10, 0, 0);
		this.getChildren().add(heroInfo);
	}

	public Canvas getCombo() {
		return comboCanvas;
	}

	public void updateCombo() {
		this.getChildren().remove(comboCanvas);
		comboCanvas = draw("Combo", 200, 200, 0, height * 2 / 3);
		GraphicsContext gc = comboCanvas.getGraphicsContext2D();
		if (MusicControl.getCurrentCombo() >= 5) {
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(COMBO_FONT);
			gc.fillText(MusicControl.getCurrentCombo() + " Combo!", 110, 20);
			this.getChildren().add(comboCanvas);
		}
	}

	public static boolean getIsCreated() {
		return isCreate;
	}

	public static void showSkillActivated() {
		GraphicsContext gc = skillCanvas.getGraphicsContext2D();
		skillCanvas.toFront();
		new Thread(() -> {
			gc.drawImage(SKILL_ACTIVATED, 0, 0, 200, 100);
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gc.clearRect(0, 0, 200, 100);
		}).start();

	}

	public static void showBossSkillActivated() {
		GraphicsContext gc = bossSkillCanvas.getGraphicsContext2D();
		bossSkillCanvas.toFront();
		new Thread(() -> {
			gc.drawImage(BOSS_SKILL_ACTIVATED, 0, 0, 200, 100);
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gc.clearRect(0, 0, 200, 100);
		}).start();

	}

	public static void showLevelUP() {
		GraphicsContext gc = eventCanvas.getGraphicsContext2D();
		eventCanvas.toFront();
		new Thread(() -> {
			gc.drawImage(LEVEL_UP, 0, 0, 600, 300);
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gc.clearRect(0, 0, 600, 300);
		}).start();
	}

	public static void showFail() {
		GraphicsContext gc = eventCanvas.getGraphicsContext2D();
		eventCanvas.toFront();
		new Thread(() -> {
			gc.drawImage(FAIL, 0, 0, 600, 300);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gc.clearRect(0, 0, 600, 300);
		}).start();
	}

	public static void isCreated(boolean x) {
		isCreate = x;
	}

	public static MusicControl getMusicControl() {
		return musicControl;
	}

}
