package scene;

import gameLogic.GameManager;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Hero;

public class CharacterSelectScreen extends Pane {
	private static final Font TITLE_FONT = Font.loadFont("file:res/font/south park.ttf", 60);
	private static final Font BTN_FONT = Font.loadFont("file:res/font/crayon kids.ttf", 35);
	private static final Font CHA_NAME_FONT = Font.loadFont("file:res/font/crayon kids.ttf", 30);
	private static final Hero NULL = null;
	private Canvas title;
	private Canvas knightCanvas, spellCasterCanvas, clownCanvas, priestCanvas;
	private Canvas backBtn;
	private Canvas startBtn;
	private String currentCharName = "";
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	private Canvas hoverKnightCanvas, hoverSpellCasterCanvas, hoverClownCanvas, hoverPriestCanvas;
	private Image bg;
	private ImageView ivBg;

	public CharacterSelectScreen() {
		super();

		bg = new Image("bg24.jpg");
		ivBg = new ImageView(bg);

		ivBg.setFitWidth(width);
		ivBg.setFitHeight(height);
		ivBg.setTranslateX(0);
		ivBg.setTranslateY(0);

		title = draw("Select Character", width, height / 5, 0, 0);

		hoverKnightCanvas = draw("BoardKnight", width / 3, height * 2 / 7, width / 8, height / 4 - 30);
		hoverSpellCasterCanvas = draw("BoardSpellCaster", width / 3, height * 2 / 7, width - width / 8 - width / 3,
				height / 4 - 30);
		hoverClownCanvas = draw("BoardClown", width / 3, height * 2 / 7, width / 8, height / 2 + 10);
		hoverPriestCanvas = draw("BoardPriest", width / 3, height * 2 / 7, width - width / 8 - width / 3,
				height / 2 + 10);

		knightCanvas = draw("Knight", width / 3, height * 2 / 7, width / 8, height / 4 - 30);
		addCanvasEvents(knightCanvas, "Knight");
		spellCasterCanvas = draw("SpellCaster", width / 3, height * 2 / 7, width - width / 8 - width / 3,
				height / 4 - 30);
		addCanvasEvents(spellCasterCanvas, "SpellCaster");
		clownCanvas = draw("Clown", width / 3, height * 2 / 7, width / 8, height / 2 + 10);
		addCanvasEvents(clownCanvas, "Clown");
		priestCanvas = draw("Priest", width / 3, height * 2 / 7, width - width / 8 - width / 3, height / 2 + 10);
		addCanvasEvents(priestCanvas, "Priest");
		startBtn = draw("Start", width / 6, height / 9, width - width / 4 - width / 6, height * 5 / 6 + 10);
		addCanvasEvents(startBtn, "Start");
		backBtn = draw("Back", width / 6, height / 9, width / 4, height * 5 / 6 + 10);
		addCanvasEvents(backBtn, "Back");

		this.getChildren().addAll(ivBg, title, hoverKnightCanvas, hoverSpellCasterCanvas, hoverClownCanvas,
				hoverPriestCanvas, knightCanvas, spellCasterCanvas, clownCanvas, priestCanvas, startBtn, backBtn);

	}

	public Canvas draw(String currentCharName, double width, double height, double posX, double posY) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);

		if (currentCharName == "Select Character") {
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(TITLE_FONT);

			gc.fillText(currentCharName, width / 2, height / 2);
		}

		else if (currentCharName == "Start" || currentCharName == "Back") {
			gc.setFill(Color.LIGHTBLUE);

			gc.fillRoundRect(0, 0, width, height, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);

			gc.fillText(currentCharName, width / 2, height / 2);
		} else if (currentCharName == "Knight" || currentCharName == "SpellCaster" || currentCharName == "Clown"
				|| currentCharName == "Priest") {
			Image img;
			if (currentCharName == "Knight")
				img = new Image("Knight1_head.png");
			else if (currentCharName == "SpellCaster")
				img = new Image("Spellcaster1_head.png");
			else if (currentCharName == "Clown")
				img = new Image("Clown1_head.png");
			else
				img = new Image("Priest1_head.png");
			gc.drawImage(img, width / 2 - 50, height / 2 - 35, 100, 100);

			gc.setStroke(Color.TAN.darker());
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width - 10, height - 10, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setFont(CHA_NAME_FONT);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(currentCharName, width / 2, height / 5);

		} else {
			gc.setFill(Color.WHITE);
			gc.fillRoundRect(5, 5, width - 10, height - 10, 50, 50);
			canvas.setOpacity(0.8);
		}
		return canvas;

	}

	public void drawHoverIndicator(Canvas canvas, String currentCharName) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		if (currentCharName == "Start" || currentCharName == "Back") {
			gc.setFill(Color.LIGHTBLUE.desaturate());

			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(currentCharName, canvas.getWidth() / 2, canvas.getHeight() / 2);
		}

		else {
			gc.setFill(Color.LIGHTCORAL.desaturate());
			gc.fillRoundRect(5, 5, canvas.getWidth() - 10, canvas.getHeight() - 10, 50, 50);
			canvas.setOpacity(0.9);
		}

	}

	public void undrawHoverIndicator(Canvas canvas, String currentCharName) {
		GraphicsContext gc = canvas.getGraphicsContext2D();

		if (currentCharName == "Start" || currentCharName == "Back") {
			gc.setFill(Color.LIGHTBLUE);

			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(currentCharName, canvas.getWidth() / 2, canvas.getHeight() / 2);
		} else {
			gc.setFill(Color.WHITE);
			gc.fillRoundRect(5, 5, canvas.getWidth() - 10, canvas.getHeight() - 10, 50, 50);
			canvas.setOpacity(0.8);
		}
	}

	private void addCanvasEvents(Canvas canvas, String canvasName) {
		canvas.setOnMouseClicked((MouseEvent event) -> {

			if (currentCharName != "Start" && canvasName != "Back") {
				GameManager.setCurrentCha(currentCharName, 0);
				this.currentCharName = canvasName;
				if (currentCharName != "Knight")
					undrawHoverIndicator(hoverKnightCanvas, canvasName);
				if (currentCharName != "SpellCaster")
					undrawHoverIndicator(hoverSpellCasterCanvas, canvasName);
				if (currentCharName != "Clown")
					undrawHoverIndicator(hoverClownCanvas, canvasName);
				if (currentCharName != "Priest")
					undrawHoverIndicator(hoverPriestCanvas, canvasName);
			} else if (GameManager.getCurrentCha() != NULL) {

				if (currentCharName == "Start") {
					GameManager.setCurrentMon();
					Pane gamePlay = new GamePlayScreen();
					SceneManager.gotoSceneOf(gamePlay);
					gamePlay.setFocusTraversable(true);
					new Thread(() -> {
						try {
							SceneManager.getFadeIn().join();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						((GamePlayScreen) gamePlay).start();
					}).start();

				}

				else if (currentCharName.compareTo("Back") == 0) {
					Pane mainMenu = new MainMenuScreen();
					SceneManager.gotoSceneOf(mainMenu);
				}
			}
		});

		canvas.setOnMouseEntered((MouseEvent e) -> {
			if (currentCharName == "Knight")
				drawHoverIndicator(hoverKnightCanvas, canvasName);
			else if (currentCharName == "SpellCaster")
				drawHoverIndicator(hoverSpellCasterCanvas, canvasName);
			else if (currentCharName == "Clown")
				drawHoverIndicator(hoverClownCanvas, canvasName);
			else if (currentCharName == "Priest")
				drawHoverIndicator(hoverPriestCanvas, canvasName);
			else
				drawHoverIndicator(canvas, canvasName);
		});

		canvas.setOnMouseExited((MouseEvent event) -> {
			if (currentCharName == "Knight" && this.currentCharName != canvasName)
				undrawHoverIndicator(hoverKnightCanvas, canvasName);
			else if (currentCharName == "SpellCaster" && this.currentCharName != canvasName)
				undrawHoverIndicator(hoverSpellCasterCanvas, canvasName);
			else if (currentCharName == "Clown" && this.currentCharName != canvasName)
				undrawHoverIndicator(hoverClownCanvas, canvasName);
			else if (currentCharName == "Priest" && this.currentCharName != canvasName)
				undrawHoverIndicator(hoverPriestCanvas, canvasName);
			else if (currentCharName == "Start" || canvasName == "Back")
				undrawHoverIndicator(canvas, canvasName);
		});

	}

}