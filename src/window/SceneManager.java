package window;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import scene.WelcomeScreen;
import sharedObject.ThreadHolder;

public class SceneManager {

	private static Stage primaryStage;
	private static Canvas welcomeCanvas = new WelcomeScreen();
	private static Pane currentPane = new Pane(welcomeCanvas);
	private static Scene welcomeSceen = new Scene(currentPane);

	private static Thread fadein, fadeout;

	public static final int SCENE_WIDTH = 800;
	public static final int SCENE_HEIGHT = 600;

	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
		gotoWelcomeScreen();
	}

	public static void gotoWelcomeScreen() {
		primaryStage.setScene(welcomeSceen);
		welcomeCanvas.requestFocus();
	}

	public static void gotoSceneOf(Pane pane) {
		fadeout = new Thread(() -> {
			FadeTransition ft = new FadeTransition(Duration.millis(250), currentPane);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.play();
			while (ft.getStatus() != javafx.animation.Animation.Status.STOPPED) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		fadein = new Thread(() -> {
			try {
				fadeout.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentPane = pane;
			FadeTransition ft = new FadeTransition(Duration.millis(250), currentPane);
			ft.setFromValue(0.0);
			ft.setToValue(1.0);
			ft.play();
			Platform.runLater(() -> {
				Scene sceneToGo = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
				primaryStage.setScene(sceneToGo);
			});
		});
		ThreadHolder.threads.add(fadein);
		ThreadHolder.threads.add(fadeout);
		fadeout.start();
		fadein.start();

	}

	public static Thread getFadeIn() {
		return fadein;
	}
}
