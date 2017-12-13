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

public class ResultScreen2 extends Pane {
	private static final Font TITLE_FONT = new Font("Monospace", 55);
	private static final Font BTN_FONT = new Font("Monospace", 30);
	private static final Font MENU_FONT = new Font("Monospace", 20);
	private static final Font SCORE_FONT = new Font("Monospace", 15);
	private Canvas title;
	private Canvas before;
	private Canvas after;
	private Canvas nextBtn;
	private Canvas backBtn;
	private Canvas boardBefore, boardAfter;
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	private Image bg = new Image("bg17.png");
	private ImageView ivBg = new ImageView(bg);

	public ResultScreen2() {
		super();
		ivBg.setFitWidth(width);
		ivBg.setFitHeight(height);
		ivBg.setTranslateX(0);
		ivBg.setTranslateY(0);

		title = drawButton("Well Done!", width, height / 5, 0, 0);
		addCanvasEvents(title, "Well Done!");

		boardBefore = drawButton("BoardBefore", width / 3, height / 2, width / 12, height / 4 - 10);
		addCanvasEvents(boardBefore, "BoardBefore");
		before = drawButton("Before", width / 3, height / 2, width / 12, height / 4 - 10);
		addCanvasEvents(before, "Before");

		boardAfter = drawButton("BoardAfter", width / 3, height / 2, width - width / 12 - width / 3, height / 4 - 10);
		addCanvasEvents(boardAfter, "BoardAfter");
		after = drawButton("After", width / 3, height / 2, width - width / 12 - width / 3, height / 4 - 10);
		addCanvasEvents(after, "After");

		nextBtn = drawButton("Next", width / 6, height / 9, width - width / 4 - width / 6 + 60, height * 5 / 6 - 20);
		addCanvasEvents(nextBtn, "Next");

		backBtn = drawButton("Back", width / 6, height / 9, width / 8 + 40, height * 5 / 6 - 20);
		addCanvasEvents(backBtn, "Back");

		this.getChildren().addAll(ivBg, title, boardBefore, boardAfter, before, after, nextBtn, backBtn);

	}

	public Canvas drawButton(String name, double width, double height, double posX, double posY) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);

		if (name == "Well Done!") {
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(TITLE_FONT);
			gc.fillText(name, width / 2, height / 2);
		} else if (name == "Before") {

			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width - 10, height - 10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText("Name : " + GameManager.getCurrentCha().getName(), 50, height / 2 - 60);
			gc.fillText("Level : " + GameManager.getScoreBefore().get(0).intValue(), 50, height / 2 - 35);
			gc.fillText("Atk : " + String.format("%.1f",GameManager.getScoreBefore().get(1)), 50, height / 2 - 10);
			gc.fillText("Exp : " + GameManager.getScoreBefore().get(2).intValue(), 50, height / 2 + 15);

			gc.fillRect(50, height / 2 + 40, 150, 20);
			gc.setFill(Color.SANDYBROWN);
			// let x be old exp
			double x = (GameManager.getScoreBefore().get(2)*1.0/GameManager.getScoreBefore().get(3)*1.0)*150;
			gc.fillRect(50, height / 2 + 40, x, 20);

			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(SCORE_FONT);
			gc.fillText(GameManager.getScoreBefore().get(2).intValue() + "/"
					+ GameManager.getScoreBefore().get(3).intValue(), 125, height / 2 + 50);

		}

		else if (name == "After") {

			gc.setStroke(Color.TAN);
			gc.setLineWidth(10);
			gc.strokeRoundRect(5, 5, width - 10, height - 10, 50, 50);
			gc.setFill(Color.WHITE);
			gc.setFont(MENU_FONT);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText("Name : " + GameManager.getCurrentCha().getName(), 50, height / 2 - 60);
			gc.fillText("Level : " + GameManager.getCurrentCha().getLevel(), 50, height / 2 - 35);
			gc.fillText("Atk : " + String.format("%.1f",GameManager.getCurrentCha().getAtk()), 50, height / 2 - 10);
			gc.fillText("Exp : " + GameManager.getCurrentCha().getCurrentExp(), 50, height / 2 + 15);
			gc.fillRect(50, height / 2 + 40, 150, 20);

			gc.setFill(Color.SANDYBROWN);
			// let x be now exp
			double x = (GameManager.getCurrentCha().getCurrentExp()*1.0 /GameManager.getCurrentCha().getCurrentMaxExp()*1.0)*150;
			
			
			gc.fillRect(50, height / 2 + 40, x, 20);

			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(SCORE_FONT);
			gc.fillText(
					GameManager.getCurrentCha().getCurrentExp() + "/" + GameManager.getCurrentCha().getCurrentMaxExp(),
					125, height / 2 + 50);

		}

		else if (name == "BoardBefore" || name == "BoardAfter") {
			canvas.setOpacity(0.5);
			gc.setFill(Color.BLACK);
			gc.fillRoundRect(5, 5, width - 10, height - 10, 50, 50);
		}

		else {
			gc.setFill(Color.TAN);
			gc.fillRoundRect(0, 0, width, height, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, width / 2, height / 2);
		}
		return canvas;

	}

	public void drawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (name == "Next" || name == "Back") {
			gc.setFill(Color.TAN.brighter());
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth() / 2, canvas.getHeight() / 2);
		}

	}

	public void undrawHoverIndicator(Canvas canvas, String name) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (name == "Next" || name == "Back") {
			gc.setFill(Color.TAN);
			gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(BTN_FONT);
			gc.fillText(name, canvas.getWidth() / 2, canvas.getHeight() / 2);
		}

	}

	private void addCanvasEvents(Canvas canvas, String name) {

		canvas.setOnMouseClicked((MouseEvent event) -> {
			Pane mainMenu = new MainMenuScreen();
			Pane resultScreen = new ResultScreen();

			if (name == "Next") {

				SceneManager.gotoSceneOf(mainMenu);
				GameManager.UpdateScoreBefore();
			} else if (name == "Back") {

				SceneManager.gotoSceneOf(resultScreen);
			}

		});

		canvas.setOnMouseEntered((MouseEvent e) -> {
			drawHoverIndicator(canvas, name);
		});

		canvas.setOnMouseExited((MouseEvent event) -> {
			undrawHoverIndicator(canvas, name);
		});

	}

}
