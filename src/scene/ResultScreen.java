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
import window.SceneManager;

public class ResultScreen extends Pane {
	private static final Font TITLE_FONT = Font.font("Monospace", 55);
	private static final Font BTN_FONT = Font.font("Monospace", 30);
	private static final Font MENU_FONT = Font.font("Monospace", 20);
	private Canvas title;
	private Canvas criticalPerfect;
	private Canvas perfect;
	private Canvas great;
	private Canvas good;
	private Canvas miss;
	private Canvas nextBtn;
	private int width = SceneManager.SCENE_WIDTH;
	private int height = SceneManager.SCENE_HEIGHT;
	private Image bg = new Image("bg15.png");
	private ImageView ivBg = new ImageView(bg);

	public ResultScreen() {
		super();
		ivBg.setFitWidth(width);
		ivBg.setFitHeight(height);
		ivBg.setTranslateX(0);
		ivBg.setTranslateY(0);

		title = drawButton("Result", width * 4 / 7, height / 5, width / 16, 0);
		addCanvasEvents(title, "Result");

		criticalPerfect = drawButton("Critical Perfect", width * 4 / 7, height / 9, width / 16, height / 4 - 20);
		addCanvasEvents(criticalPerfect, "Critical Perfect");
		perfect = drawButton("Perfect", width * 4 / 7, height / 9, width / 16, height / 4 - 20 + height / 7);
		addCanvasEvents(perfect, "Perfect");
		great = drawButton("Great", width * 4 / 7, height / 9, width / 16, height / 4 - 20 + height / 7 * 2);
		addCanvasEvents(great, "Great");
		good = drawButton("Good", width * 4 / 7, height / 9, width / 16, height / 4 - 20 + height / 7 * 3);
		addCanvasEvents(good, "Good");
		miss = drawButton("Miss", width * 4 / 7, height / 9, width / 16, height / 4 - 20 + height / 7 * 4);
		addCanvasEvents(miss, "Miss");
		nextBtn = drawButton("Next", width / 6, height / 9, width / 16 + width * 4 / 7 + 70,
				height / 4 - 30 + height / 7 * 2);
		addCanvasEvents(nextBtn, "Next");

		this.getChildren().addAll(ivBg, title, criticalPerfect, perfect, great, good, miss, nextBtn);

	}

	public Canvas drawButton(String name, double width, double height, double posX, double posY) {
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setTranslateX(posX);
		canvas.setTranslateY(posY);

		if (name == "Result") {

			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFont(TITLE_FONT);
			gc.fillText(name, width / 2, height / 2);
		} else if (name != "Next") {
			canvas.setOpacity(0.9);
			gc.setFill(Color.ALICEBLUE);
			gc.fillRoundRect(2.5, 2.5, width - 5, height - 5, 50, 50);
			gc.setStroke(Color.TAN);
			gc.setLineWidth(5);
			gc.strokeRoundRect(2.5, 2.5, width - 5, height - 5, 50, 50);
			gc.setFill(Color.BLACK);
			gc.setFont(MENU_FONT);
			gc.setTextBaseline(VPos.CENTER);

			if (name.compareTo("Critical Perfect") == 0) {
				gc.fillText(name, 50, height / 2);
				gc.fillText(GameManager.getGameResult().get(0).toString(), 280, height / 2);
			} else if (name == "Perfect") {
				gc.fillText(name, 50, height / 2);
				gc.fillText(GameManager.getGameResult().get(1).toString(), 280, height / 2);
			} else if (name == "Great") {
				gc.fillText(name, 50, height / 2);
				gc.fillText(GameManager.getGameResult().get(2).toString(), 280, height / 2);
			} else if (name == "Good") {
				gc.fillText(name, 50, height / 2);
				gc.fillText(GameManager.getGameResult().get(3).toString(), 280, height / 2);
			} else {
				gc.fillText(name, 50, height / 2);
				gc.fillText(GameManager.getGameResult().get(4).toString(), 280, height / 2);
			}

		} else {
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
		if (name == "Next") {
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
		if (name == "Next") {
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
			Pane result2 = new ResultScreen2();

			if (name == "Next") {

				SceneManager.gotoSceneOf(result2);
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
