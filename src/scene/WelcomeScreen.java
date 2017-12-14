package scene;

import exception.EnterToGameException;
import gameLogic.GameManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class WelcomeScreen extends Canvas {
	private static final Font TITLE_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("Meatloaf.ttf"), 110);
	private static final Font MENU_FONT = Font.font("Monospace", 30);
	private Image bg = new Image("welcome.png");
	private GraphicsContext gc;

	public WelcomeScreen() {
		super(SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		gc = this.getGraphicsContext2D();
		gc.drawImage(bg, 0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFill(Color.BLACK);
		gc.setFont(TITLE_FONT);
		gc.fillText("TRAIN TO THE BEAT", SceneManager.SCENE_WIDTH / 2, SceneManager.SCENE_HEIGHT / 4);
		this.addKeyEventHandler();
	}

	private void addKeyEventHandler() {
		gc = this.getGraphicsContext2D();
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				try {
					keyPressed(event);
				} catch (EnterToGameException e) {
					pleasePressEnter(e.getMessage());
				}
			}
		});

	}

	private void keyPressed(KeyEvent e) throws EnterToGameException {
		if (e.getCode() == KeyCode.ENTER) {
			GameManager.newGame();

		} else if (e.getCode() == KeyCode.ESCAPE) {
			Platform.exit();
		} else {
			throw new EnterToGameException();
		}
	}

	private void pleasePressEnter(String s) {
		gc.setFont(MENU_FONT);
		gc.fillText(s, SceneManager.SCENE_WIDTH / 2, SceneManager.SCENE_HEIGHT / 2);

	}
}
